package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.ProbabilityStack;
import com.globbypotato.rockhounding_core.utils.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMineralSizer extends TileEntityMachineTank {

	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;
	public static final int SPEED_SLOT = 3;
	public int comminution;
	private ItemStackHandler template = new TemplateStackHandler(3);
	public ArrayList<ItemStack> resultList = new ArrayList<ItemStack>();

	public TileEntityMineralSizer() {
		super(4,3,1);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && (isValidInput(insertingStack) || isValidOredict(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && isGatedPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && CoreUtils.hasConsumable(ToolUtils.gear, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SPEED_SLOT && ToolUtils.isValidSpeedUpgrade(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.comminution = compound.getInteger("Comminution");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Comminution", getComminution());
		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(lavaTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack speedSlot(){
		return this.input.getStackInSlot(SPEED_SLOT);
	}


	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralSizer.HEIGHT;
	}

	public int speedSizer(){
		return ToolUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedSizer / ToolUtils.speedUpgrade(speedSlot()): ModConfig.speedSizer;
	}

	public int comminutionFactor(){
		return ToolUtils.isValidSpeedUpgrade(speedSlot()) ? (ModConfig.comminutionFactor * 2) / ToolUtils.speedUpgrade(speedSlot()): ModConfig.comminutionFactor;
	}

	private static int sizedQuantity() {
		return ModConfig.maxMineral > 0 ? ModConfig.maxMineral : 1;
	}

	public int getMaxCookTime(){
		return hasComminution() ? speedSizer() + (getComminution() * comminutionFactor()) : speedSizer();
	}



	//----------------------- CUSTOM -----------------------
	public int getComminution(){
		if(isPowered() && hasComminution()){
			return getBlockPower();
		}else if(!isPowered() && hasComminution()){
			return this.comminution;
		}else if(!hasComminution()){
			return 0;
		}
		return 0;
	}

	public boolean isPowered(){
		return worldObj.isBlockPowered(this.pos);
	}

	public int getBlockPower(){
		return isPowered() ? worldObj.isBlockIndirectlyGettingPowered(this.pos) : 0;
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<MineralSizerRecipe> recipeList(){
		return MachineRecipes.sizerRecipes;
	}

	public MineralSizerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public boolean isValidInput(ItemStack stack){
		return recipeList().stream().anyMatch(recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}

	boolean isValidOredict(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));
			for(MineralSizerRecipe recipe: recipeList()){
				ArrayList<Integer> recipeIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipe.getInput()));
				for(Integer ores: recipeIDs){
					String recipeName = OreDictionary.getOreName(ores);
					for(Integer inputs: inputIDs){
						String inputName = OreDictionary.getOreName(inputs);
						if(inputName.matches(recipeName)) return true;
					}
				}
			}
		}
		return false;
	}

	public MineralSizerRecipe getCurrentRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(getRecipeList(x).getInput() != null && (isMatchingInput(x) || isMatchingOredict(inputSlot(), getRecipeList(x).getInput()))){
				return getRecipeList(x);
			}
		}
		return null;
	}

	private static boolean isMatchingOredict(ItemStack insertingStack, ItemStack recipeStack) {
		if(insertingStack != null && recipeStack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(insertingStack));
			ArrayList<Integer> recipeIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipeStack));
			if(inputIDs.size() > 0 && recipeIDs.size() > 0){
				for(Integer recipeList: recipeIDs){
					String recipeDict = OreDictionary.getOreName(recipeList);
					for(Integer inputList: inputIDs){
						String inputDict = OreDictionary.getOreName(inputList);
						if(inputDict.matches(recipeDict)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean isMatchingInput(int x) {
		return ItemStack.areItemsEqual(getRecipeList(x).getInput(), this.input.getStackInSlot(INPUT_SLOT));
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public ArrayList<ItemStack> recipeOutput(){
		return isValidRecipe() ? getCurrentRecipe().getOutput() : null;
	}

	public ArrayList<Integer> recipeComminution(){
		return isValidRecipe() ? getCurrentRecipe().getProbability() : null;
	}

	public boolean hasComminution(){
		return isValidRecipe() ? getCurrentRecipe().getComminution() : false;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		acceptEnergy();
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		lavaHandler();
		if(!worldObj.isRemote){
			if(canProcess(input.getStackInSlot(INPUT_SLOT))){
				cookTime++;
				powerCount--;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess(ItemStack stack) {
		return isActive()
			&& isValidRecipe()
			&& allOutputsEmpty()
			&& getPower() >= getMaxCookTime()
			&& hasResult()
			&& handleConsumable();
	}

	public boolean hasResult() {
		this.resultList.clear();
		if(isValidRecipe()){
			for(int stack = 0; stack < recipeOutput().size(); stack++){
				if(recipeComminution().get(stack) == getComminution()){
					this.resultList.add(recipeOutput().get(stack));
				}
			}
		}
		return isValidRecipe() && (!hasComminution() || (!this.resultList.isEmpty() && this.resultList.size() > 0));
	}

	private boolean allOutputsEmpty(){
		return (output.getStackInSlot(OUTPUT_SLOT) == null
			&& output.getStackInSlot(GOOD_SLOT) == null
			&& output.getStackInSlot(WASTE_SLOT) == null);
	}

	private void process() {
		if(isValidRecipe()){
			int mix = getCurrentRecipe().getOutput().size();
			if(!getCurrentRecipe().getComminution()){
				//calculate primary output
				if(mix > 1){
					output.setStackInSlot(OUTPUT_SLOT, ProbabilityStack.calculateProbability(getCurrentRecipe().getProbabilityStack()).copy());
					output.getStackInSlot(OUTPUT_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral) + 1;
				}else{
					output.setStackInSlot(OUTPUT_SLOT, getCurrentRecipe().getOutput().get(0).copy());
				}
				//calculate secondary output
				if(rand.nextInt(100) < 25 && mix > 1){
					output.setStackInSlot(GOOD_SLOT, ProbabilityStack.calculateProbability(getCurrentRecipe().getProbabilityStack()).copy());
					output.getStackInSlot(GOOD_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral / 2) + 1;
				}
				//calculate waste output
				if(rand.nextInt(100) < 5 && mix > 1){
					output.setStackInSlot(WASTE_SLOT, ProbabilityStack.calculateProbability(getCurrentRecipe().getProbabilityStack()).copy());
					output.getStackInSlot(WASTE_SLOT).stackSize = 1;
				}
			}else{
				output.setStackInSlot(OUTPUT_SLOT, pickLevelOutput().copy());
				if(recipeOutput().size() > 1){
					output.getStackInSlot(OUTPUT_SLOT).stackSize = sizedQuantity();
				}

				if(this.resultList.size() > 1){
					if(this.rand.nextInt(100) < 30){
						output.setStackInSlot(GOOD_SLOT, pickLevelOutput().copy());
						output.getStackInSlot(GOOD_SLOT).stackSize = 1 + this.rand.nextInt(sizedQuantity() / 2);
					}
				}

				if(recipeOutput().size() > 1 && this.rand.nextInt(100) < 15){
					output.setStackInSlot(WASTE_SLOT, pickRecipeOutput().copy());
					output.getStackInSlot(WASTE_SLOT).stackSize = 1;
				}
			}
		}
		handleConsumableDamage();
		input.decrementSlot(INPUT_SLOT);
	}

	private ItemStack pickLevelOutput() {
		return this.resultList.get(this.rand.nextInt(this.resultList.size()));
	}

	private ItemStack pickRecipeOutput() {
		return isValidRecipe() ? recipeOutput().get(this.rand.nextInt(recipeOutput().size())) : null;
	}

	private void handleConsumableDamage() {
		if(isValidRecipe()){
			if(getCurrentRecipe().getComminution()){
				input.damageSlot(CONSUMABLE_SLOT, getComminution());
			}else{
				input.damageSlot(CONSUMABLE_SLOT);
			}
		}
	}

	private boolean handleConsumable() {
		return CoreUtils.hasConsumable(ToolUtils.gear, input.getStackInSlot(CONSUMABLE_SLOT));
	}

}