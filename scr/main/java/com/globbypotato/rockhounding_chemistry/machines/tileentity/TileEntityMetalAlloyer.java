package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMetalAlloyer extends TileEntityMachineTank {

    public boolean doScan;
    public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,6};
    public static final int SLOT_CONSUMABLE = 7;
	public static final int SLOT_SCRAP = 1;
	public static final int SLOT_LOADER = 8;

	private ItemStackHandler template = new TemplateStackHandler(10);
	public static int SLOT_FAKE[] = new int[]{0,1,2,3,4,5,6,7,8};

	public TileEntityMetalAlloyer() {
		super(11, 2, 0);

		input =  new MachineStackHandler(INPUT_SLOTS, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_INPUTS[1] && slot < SLOT_INPUTS.length && activation && isMatchingOredict(insertingStack, slot)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_LOADER && activation && canEqualize() && isMatchingIngredient(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && CoreUtils.isPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONSUMABLE && CoreUtils.hasConsumable(ToolUtils.pattern, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	public int getMaxCookTime(){
		return ModConfig.speedAlloyer;
	}

	@Override
	public int getGUIHeight() {
		return GuiMetalAlloyer.HEIGHT;
	}

	public boolean canEqualize(){
		return ModConfig.ingredientEqualizer;
	}



	//----------------------- CUSTOM -----------------------
	public boolean isMatchingOredict(ItemStack stack, int slot) {
		if(stack != null){
			int[] oreIDs = OreDictionary.getOreIDs(stack);
			if(oreIDs.length > 0) {
				for(int i = 0; i < oreIDs.length; i++) {
					String oreName = OreDictionary.getOreName(oreIDs[i]);
					if(template.getStackInSlot(slot) != null){
						int[] tempIDs = OreDictionary.getOreIDs(template.getStackInSlot(slot));
						if(tempIDs.length > 0) {
							for(int j = 0; j < tempIDs.length; j++) {
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
		return false;
	}

	private boolean isMatchingIngredient(ItemStack stack) {
		if(stack != null){
			int[] oreIDs = OreDictionary.getOreIDs(stack);
			if(oreIDs.length > 0){
				for(int i = 0; i < oreIDs.length; i++) {
					String oreName = OreDictionary.getOreName(oreIDs[i]);
					for(int k = 1; k <= 6; k++) {
						if(input.getStackInSlot(k) != null){
							int[] tempIDs = OreDictionary.getOreIDs(input.getStackInSlot(k));
							if(tempIDs.length > 0){
								for(int j = 0; j < tempIDs.length; j++) {
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
		return false;
	}

	public int countRecipes(){
		return recipeIndex;
	}

	public boolean isValidInterval() {
		return countRecipes() >= 0 && countRecipes() <= MachineRecipes.alloyerRecipes.size() - 1;
	}

	public void resetGrid(){
		for(int x = 1; x < SLOT_INPUTS.length; x++){ template.setStackInSlot(x, null);}
	}

	public MetalAlloyerRecipe getRecipe(){
		return isValidInterval() ? MachineRecipes.alloyerRecipes.get(countRecipes()) : null;
	}

	private ItemStack demoAlloy(int slot){
		return template.getStackInSlot(SLOT_FAKE[slot]);
	}


	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.recipeIndex = compound.getInteger("RecipeCount");
		this.activation = compound.getBoolean("Activation");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("RecipeCount", this.recipeIndex);
		compound.setBoolean("Activation", this.activation);
		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(lavaTank);
	}

	
	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		lavaHandler();
		if(!worldObj.isRemote){
			if(!isValidInterval()){recipeIndex = -1;}

			//show alloy
			showAlloy();
			if(( isValidInterval() && doScan) || (!doScan && countRecipes() >= 0 && demoAlloy(0) != null && demoAlloy(1) == null) ){ 
				showIngredients();
			}
			
			// equalize oredict
			if(isValidInterval() && canEqualize()){
				equalizeOredict();
			}
			
			//reset grid
			if(countRecipes() < 0 && demoAlloy(0) != null){
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
		return activation
			&& isFullRecipe(dusts) 
			&& CoreUtils.hasConsumable(ToolUtils.pattern, input.getStackInSlot(SLOT_CONSUMABLE)) 
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
		for (int x = 0; x < dusts.size(); x++){
			input.getStackInSlot(x + 1).stackSize -= quantities.get(x); 
			if(input.getStackInSlot(x + 1).stackSize <= 0) { 
				input.setStackInSlot(x + 1, null); 
			}
		}
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
		ItemStack outputSlot = output.getStackInSlot(OUTPUT_SLOT);
		return outputSlot != null 
			&& demoAlloy(0) != null 
			&& outputSlot.isItemEqual(demoAlloy(0)) 
			&& outputSlot.stackSize <= outputSlot.getMaxStackSize() - outputStack.stackSize;
	}

	private boolean canStackScraps(ItemStack outputScrap){
		ItemStack scrapSlot = output.getStackInSlot(SLOT_SCRAP);
		return scrapSlot != null 
			&& scrapSlot.isItemEqual(outputScrap) 
			&& scrapSlot.stackSize <= scrapSlot.getMaxStackSize() - 4;
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

	private void equalizeOredict() {
		ItemStack loaderStack = input.getStackInSlot(SLOT_LOADER);
		if(loaderStack != null){
			for(int x = 1; x <= 6; x++){
				if(isMatchingOredict(loaderStack, x)){
					if(input.getStackInSlot(x) != null){
						if(input.canIncrementSlot(input.getStackInSlot(x))){
							input.getStackInSlot(x).stackSize++;
							loaderStack.stackSize--;
							if(loaderStack.stackSize <= 0){
								input.setStackInSlot(SLOT_LOADER, null);
							}
						}
					}else{
						input.setStackInSlot(x, loaderStack);
						input.setStackInSlot(SLOT_LOADER, null);
					}
				}
			}
		}
	}

}