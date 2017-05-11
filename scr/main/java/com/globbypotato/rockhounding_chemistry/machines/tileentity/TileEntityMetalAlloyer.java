package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_chemistry.utils.FuelUtils;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMetalAlloyer extends TileEntityMachineEnergy {

    public boolean doScan;
    public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,6};
    public static final int SLOT_CONSUMABLE = 7;
	public static final int SLOT_SCRAP = 1;

	private ItemStackHandler template = new TemplateStackHandler(9);
	public static int SLOT_FAKE[] = new int[]{0,1,2,3,4,5,6,7,8};

	public TileEntityMetalAlloyer() {
		super(10, 2, 0);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_INPUTS[1] && slot < SLOT_INPUTS.length && isMatchingOredict(insertingStack, slot)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && (FuelUtils.isItemFuel(insertingStack) || ToolUtils.hasinductor(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONSUMABLE && ToolUtils.hasConsumable(ToolUtils.pattern, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	public int getMaxCookTime(){
		return ModConfig.alloyingSpeed;
	}

	@Override
	public int getGUIHeight() {
		return GuiMetalAlloyer.HEIGHT;
	}



	//----------------------- CUSTOM -----------------------
	public boolean isMatchingOredict(ItemStack stack, int slot) {
		if(stack != null){
			int[] oreIDs = OreDictionary.getOreIDs(stack);
			if(oreIDs.length > 0) {
				for(int i = 0; i < oreIDs.length; i++) {
					if(oreIDs[i] > -1) {
						String oreName = OreDictionary.getOreName(oreIDs[i]);
						if(template.getStackInSlot(slot) != null){
							int[] tempIDs = OreDictionary.getOreIDs(template.getStackInSlot(slot));
							if(tempIDs.length > 0) {
								for(int j = 0; j < tempIDs.length; j++) {
									if(tempIDs[j] > -1) {
										String tempName = OreDictionary.getOreName(tempIDs[j]);
										if(oreName != null && tempName != null && oreName.matches(tempName)){
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public int countRecipes(){
		return recipeIndex;
	}

	public boolean isValidInterval() {
		return countRecipes() >= 0 && countRecipes() <= ModRecipes.alloyerRecipes.size() - 1;
	}

	public void resetGrid(){
		for(int x = 1; x < SLOT_INPUTS.length; x++){ template.setStackInSlot(x, null);}
	}

	public MetalAlloyerRecipe getRecipe(){
		return isValidInterval() ? ModRecipes.alloyerRecipes.get(countRecipes()) : null;
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.recipeIndex = compound.getInteger("RecipeCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("RecipeCount", this.recipeIndex);
		return compound;
	}


	
	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		if(!worldObj.isRemote){
			//show alloy
			if(!isValidInterval()){recipeIndex = -1;}
			showAlloy();
			if(( isValidInterval() && doScan)
				|| (!doScan && countRecipes() >= 0 && template.getStackInSlot(SLOT_FAKE[0]) != null && template.getStackInSlot(SLOT_FAKE[1]) == null) ){ 
				showIngredients();
			}
			//reset grid
			if(countRecipes() < 0 && template.getStackInSlot(SLOT_FAKE[0]) != null){
				resetGrid();
			}
			//cast alloy
			if(isValidInterval()){
				if(canAlloy(getRecipe().getDusts(), getRecipe().getQuantities())){
					execute(getRecipe().getDusts(), getRecipe().getQuantities());
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canAlloy(List<String> dusts, List<Integer> quantities) {
		return isFullRecipe(dusts) 
			&& ToolUtils.hasConsumable(ToolUtils.pattern, input.getStackInSlot(SLOT_CONSUMABLE)) 
			&& (output.getStackInSlot(OUTPUT_SLOT) == null || canStackIngots(getRecipe().getOutput()))
			&& (output.getStackInSlot(SLOT_SCRAP) == null || canStackScraps(getRecipe().getScrap()))
			&& getPower() >= getMaxCookTime();
	}
			
	private void execute(List<String> dusts, List<Integer> quantities) {
		cookTime++; powerCount--;
		if(cookTime >= getMaxCookTime()) {
			cookTime = 0; 
			handleOutput(dusts, quantities);
		}
	}

	private void handleOutput(List<String> dusts, List<Integer> quantities) {
		//decrease input 
		for (int x = 0; x < dusts.size(); x++){input.getStackInSlot(x + 1).stackSize -= quantities.get(x); if(input.getStackInSlot(x + 1).stackSize <= 0) { input.setStackInSlot(x + 1, null); }}
		//decrease consumable
		input.damageSlot(SLOT_CONSUMABLE);
		//add output
		ItemStack outputStack = getRecipe().getOutput();
		ItemStack alloyIngotStack = new ItemStack(outputStack.getItem(), outputStack.stackSize, outputStack.getItemDamage());
		output.setOrStack(OUTPUT_SLOT, alloyIngotStack);
		//add scrap
		ItemStack scrapStack = getRecipe().getScrap();
		if(scrapStack != null){
			ItemStack alloyNuggetStack = new ItemStack(scrapStack.getItem(), rand.nextInt(4) + 1, scrapStack.getItemDamage());
			output.setOrStack(SLOT_SCRAP, alloyNuggetStack);
		}
	}

	private boolean isFullRecipe(List<String> dusts) {
		int full = 0;
		for(int x = 0; x < dusts.size(); x++){
			if(template.getStackInSlot(x + 1) != null && input.getStackInSlot(x + 1) != null
				&& isComparableOredict(x, x + 1, dusts) 
				&& input.getStackInSlot(x + 1).stackSize >= template.getStackInSlot(x + 1).stackSize){
				full++;
			}
		}
		return full == dusts.size();
	}

	private boolean canStackIngots(ItemStack outputStack){
		return output.getStackInSlot(OUTPUT_SLOT) != null && template.getStackInSlot(SLOT_FAKE[0]) != null && output.getStackInSlot(OUTPUT_SLOT).isItemEqual(template.getStackInSlot(SLOT_FAKE[0])) && output.getStackInSlot(OUTPUT_SLOT).stackSize <= output.getStackInSlot(OUTPUT_SLOT).getMaxStackSize() - outputStack.stackSize;
	}

	private boolean canStackScraps(ItemStack outputScrap){
		return output.getStackInSlot(SLOT_SCRAP) != null && output.getStackInSlot(SLOT_SCRAP).isItemEqual(outputScrap) && output.getStackInSlot(SLOT_SCRAP).stackSize <= output.getStackInSlot(SLOT_SCRAP).getMaxStackSize() - 4;
	}

	private boolean isComparableOredict(int x, int j, List<String> dusts) {
		if(dusts.get(x) != null){
			int[] oreIDs = OreDictionary.getOreIDs(template.getStackInSlot(j));
			if(oreIDs.length > 0) {
				for(int i = 0; i < oreIDs.length; i++) {
					if(oreIDs[i] > -1) {
						String oreName = OreDictionary.getOreName(oreIDs[i]);
						if(oreName != null && oreName.matches(dusts.get(x))){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private void showAlloy() {
		if(isValidInterval()){
			template.setStackInSlot(SLOT_FAKE[0], getRecipe().getOutput());
		}else{
			template.setStackInSlot(SLOT_FAKE[0], null);
		}
	}

	private void showIngredients() {
		countIngredients(countRecipes(), getRecipe().getDusts(), getRecipe().getQuantities());
		doScan = false;
	}

    private void countIngredients(int countRecipes, List<String> dusts, List<Integer> quantities) {
		for(int x = 0; x < dusts.size(); x++){
			for(ItemStack dust : OreDictionary.getOres(dusts.get(x))) {
	    		if(dust != null){
	    			template.setStackInSlot(x + 1, dust);
	    			template.getStackInSlot(x + 1).stackSize = quantities.get(x);
	    		}
			}
		}
	}

}