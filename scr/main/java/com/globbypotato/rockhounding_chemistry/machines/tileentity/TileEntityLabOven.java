package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityLabOven extends TileEntityMachineTank implements IInternalServer{

	private ItemStackHandler template = new TemplateStackHandler(6);

	public static final int SOLVENT_SLOT = 2;
	public static final int SOLUTION_SLOT = 3;
	public static final int REDSTONE_SLOT = 4;
	public static final int REAGENT_SLOT = 5;
	public static final int SPEED_SLOT = 6;
	public static final int RECYCLE_SLOT = 0;

	public FluidTank solventTank;
	public FluidTank reagentTank;
	public FluidTank outputTank;

	public static int totInput = 7;
	public static int totOutput = 1;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TileEntityLabOven() {
		super(totInput, totOutput, 1);

		solventTank = new FluidTank(1000 + ModConfig.machineTank) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return isActive() && hasSolvent(fluid) && isValidInterval() && isCorrectSolvent(fluid);
			}

			@Override
			public boolean canDrain() {
				return !isValidInterval() || (isValidInterval() && isWrongSolvent(solventTank));
			}
		};
		solventTank.setTileEntity(this);
		solventTank.setCanFill(true);

		reagentTank = new FluidTank(1000 + ModConfig.machineTank) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return isActive() && hasReagent(fluid) && isValidInterval() && isCorrectReagent(fluid);
			}

			@Override
			public boolean canDrain() {
				return !isValidInterval() || (isValidInterval() && isWrongReagent(reagentTank));
			}
		};
		reagentTank.setTileEntity(this);
		reagentTank.setCanFill(true);

		outputTank = new FluidTank(1000 + ModConfig.machineTank);
		outputTank.setTileEntity(this);
		outputTank.setCanDrain(true);
		outputTank.setCanFill(false);

		input = new MachineStackHandler(totInput, this) {
			@Override
			public void validateSlotIndex(int slot){
				if(input.getSlots() < totInput){
					ItemStack[] stacksCloned = stacks;
					input.setSize(totInput);
					for(int x = 0; x < stacksCloned.length; x++){
						stacks[x] = stacksCloned[x];
					}
				}
				if(output.getSlots() < totOutput){
					ItemStack[] stacksCloned = stacks;
					output.setSize(totOutput);
					for(int x = 0; x < stacksCloned.length; x++){
						stacks[x] = stacksCloned[x];
					}
				}
				super.validateSlotIndex(slot);
			}
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate) {
				if (slot == INPUT_SLOT && isActive() && isValidInterval() && hasRecipe(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == FUEL_SLOT && isGatedPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == SOLVENT_SLOT && isActive() && isValidInterval() && hasSolvent(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == REAGENT_SLOT && isActive() && isValidInterval() && hasReagent(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == REDSTONE_SLOT && hasRedstone(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == SOLUTION_SLOT && CoreUtils.isBucketType(insertingStack) && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SPEED_SLOT && ToolUtils.isValidSpeedUpgrade(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot(){
		return this.input.getStackInSlot(SPEED_SLOT);
	}

	public ItemStack soluteSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack recycleSlot(){
		return this.output.getStackInSlot(RECYCLE_SLOT);
	}


	// ----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate() {
		return this.template;
	}

	public int baseSpeed(){
		return ModConfig.speedLabOven;
	}

	public int speedOven() {
		return ToolUtils.isValidSpeedUpgrade(speedSlot()) ? baseSpeed() / ToolUtils.speedUpgrade(speedSlot()): baseSpeed();
	}

	public int getCookTimeMax() {
		return speedOven();
	}

	@Override
	public int getGUIHeight() {
		return GuiLabOven.HEIGHT;
	}

	@Override
	public boolean hasRF() {
		return true;
	}

	@Override
	public boolean isRFGatedByBlend(){
		return true;
	}


	private static int deviceCode() {
		return EnumServer.LAB_OVEN.ordinal();
	}



	// ----------------------- CUSTOM ------------------------
	public boolean isValidInterval() {
		return recipeIndex >= 0 && recipeIndex <= recipeList().size() - 1;
	}

	public ArrayList<LabOvenRecipe> recipeList(){
		return MachineRecipes.labOvenRecipes;
	}

	public LabOvenRecipe getRecipe() {
		return isValidInterval() ? recipeList().get(recipeIndex) : null;
	}

	public boolean isValidRecipe(){
		return getRecipe() != null;
	}

	private boolean hasRecipe(ItemStack stack) {
		return isValidInterval() && recipeList().stream().anyMatch(
				recipe -> stack != null && recipe.getSolute() != null && (isValidSolute(stack) || isValidCatalyst(stack)));
	}

	private boolean hasSolvent(FluidStack stack) {
		return isValidInterval() && recipeList().stream().anyMatch(
				recipe -> stack != null && recipe.getSolvent() != null && stack.isFluidEqual(getRecipe().getSolvent()));
	}

	private boolean hasReagent(FluidStack stack) {
		return isValidInterval() && recipeList().stream().anyMatch(
				recipe -> stack != null && recipe.getReagent() != null && stack.isFluidEqual(getRecipe().getReagent()));
	}

	private boolean isWrongSolvent(FluidTank tank) {
		return isValidInterval() && tank.getFluid() != null && !tank.getFluid().isFluidEqual(getRecipe().getSolvent())
			&& tank.getFluidAmount() > 0;
	}

	private boolean isWrongReagent(FluidTank tank) {
		return isValidInterval() && tank.getFluid() != null && !tank.getFluid().isFluidEqual(getRecipe().getReagent())
			&& tank.getFluidAmount() > 0;
	}

	private boolean isCorrectSolvent(FluidStack fluid) {
		return isValidInterval() && fluid.isFluidEqual(getRecipe().getSolvent());
	}

	private boolean isCorrectReagent(FluidStack fluid) {
		return isValidInterval() && fluid.isFluidEqual(getRecipe().getReagent());
	}

	private boolean isValidCatalyst(ItemStack stack) {
		return getRecipe().isCatalyst() 
			&& stack.getItem().isDamageable() 
			&& ItemStack.areItemsEqualIgnoreDurability(stack, getRecipe().getSolute());
	}

	private boolean isValidSolute(ItemStack stack) {
		return !getRecipe().isCatalyst()
			&& ItemStack.areItemsEqual(getRecipe().getSolute(), stack);
	}

	private ItemStack recipeSolute(){
		return isValidInterval() ? getRecipe().getSolute() : null;
	}

	private FluidStack solventFluid(){
		return solventTank.getFluid();
	}

	private FluidStack reagentFluid(){
		return reagentTank.getFluid();
	}

	private FluidStack outputFluid(){
		return outputTank.getFluid();
	}

	private void handlePurge() {
		if(isActive()){
			if(!isValidInterval() || isValidRecipe()){
				if(soluteSlot() != null && recipeSolute() != null){
					if((!recipeSolute().isItemStackDamageable() && !soluteSlot().isItemEqual(recipeSolute()) && noMatchingOredict(soluteSlot(), recipeSolute())) || (recipeSolute().isItemStackDamageable() && !soluteSlot().isItemEqualIgnoreDurability(recipeSolute())) ){
						if(this.output.canSetOrStack(recycleSlot(), soluteSlot())){
							this.output.setOrStack(RECYCLE_SLOT, soluteSlot());
							this.input.setStackInSlot(OUTPUT_SLOT, null);
						}
					}
				}
			}
		}
	}

	public static boolean noMatchingOredict(ItemStack recipeIngredient, ItemStack slotIngredient) {
		boolean oredictFound = false;
		if(recipeIngredient != null && slotIngredient != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(slotIngredient));
			ArrayList<Integer> recipeIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipeIngredient));
			if(!inputIDs.isEmpty() && inputIDs.size() > 0 && !recipeIDs.isEmpty() && recipeIDs.size() > 0){
				for(Integer recipeList: recipeIDs){
					String recipeDict = OreDictionary.getOreName(recipeList);
					for(Integer inputList: inputIDs){
						String inputDict = OreDictionary.getOreName(inputList);
						if(inputDict.matches(recipeDict)){
							return false;
						}
					}
				}
			}
		}
		return true;
	}


	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.recipeIndex = compound.getInteger("RecipeScan");
		this.solventTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.reagentTank.readFromNBT(compound.getCompoundTag("ReagentTank"));
		this.outputTank.readFromNBT(compound.getCompoundTag("OutputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("RecipeScan", this.recipeIndex);

		NBTTagCompound solventTankNBT = new NBTTagCompound();
		this.solventTank.writeToNBT(solventTankNBT);
		compound.setTag("InputTank", solventTankNBT);

		NBTTagCompound reagentTankNBT = new NBTTagCompound();
		this.reagentTank.writeToNBT(reagentTankNBT);
		compound.setTag("ReagentTank", reagentTankNBT);

		NBTTagCompound outputTankNBT = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankNBT);
		compound.setTag("OutputTank", outputTankNBT);

		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(lavaTank, solventTank, reagentTank, outputTank);
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!isValidInterval()) {
			recipeIndex = -1;
		}
		acceptEnergy();
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		redstoneHandler(REDSTONE_SLOT, baseSpeed());
		lavaHandler();

		if (!worldObj.isRemote) {
			emptyContainer(SOLVENT_SLOT, solventTank);
			emptyContainer(REAGENT_SLOT, reagentTank);
			fillContainer(SOLUTION_SLOT, outputTank);
			handlePurge();

			initializeServer(isRepeatable, hasServer(worldObj, pos), getServer(worldObj, pos), deviceCode());

			if (isValidInterval()) {
				if (canSynthesize()) {
					execute();
				}else{
					cookTime = 0;
				}
			}
			this.markDirtyClient();
		}
	}

	public boolean isCooking() {
		return isValidRecipe() && cookTime > 0;
	}

	public boolean canSynthesize() {
		return isActive()
			&& isValidRecipe()
			&& getInputStack() != null
			&& (isValidSolute(getInputStack()) || isValidCatalyst(getInputStack()))
			&& input.canSetOrFill(outputTank, outputFluid(), getRecipe().getOutput()) 
			&& input.hasEnoughFluid(solventFluid(), getRecipe().getSolvent())
			&& (noReagentUsed() || input.hasEnoughFluid(reagentFluid(), getRecipe().getReagent()))
			&& isRedstoneRequired(this.getCookTimeMax()) 
			&& this.getPower() >= this.getCookTimeMax()
			&& handleServer(hasServer(worldObj, pos), getServer(worldObj, pos), this.currentFile); //server
	}

	private ItemStack getInputStack(){
		return input.getStackInSlot(INPUT_SLOT);
	}

	private boolean noReagentUsed() {
		return reagentFluid() == null && getRecipe().getReagent() == null;
	}

	private void execute() {
		cookTime++;
		powerCount--;
		if(!this.hasFuelBlend()){ redstoneCount--; }
		if (cookTime >= getCookTimeMax()) {
			cookTime = 0;
			handleOutput();
		}
	}

	private void handleOutput() {
		input.drainOrClean(solventTank, getRecipe().getSolvent().amount, true);

		if (reagentFluid() != null && hasReagent(reagentFluid())) {
			input.drainOrClean(reagentTank, getRecipe().getReagent().amount, true);
		}

		outputTank.fillInternal(getRecipe().getOutput(), true);

		if(!getRecipe().isCatalyst()){
			input.decrementSlot(INPUT_SLOT);
		}else{
			input.damageSlot(INPUT_SLOT);
		}
		
		updateServer(hasServer(worldObj, pos), getServer(worldObj, pos), this.currentFile);
	}

	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer(worldObj, pos).isActive()){
			for(int x = 0; x < TileEntityMachineServer.FILE_SLOTS.length; x++){
				if(getServer(worldObj, pos).inputSlot(x) != null){
					ItemStack fileSlot = getServer(worldObj, pos).inputSlot(x).copy();
					if(fileSlot.isItemEqual(BaseRecipes.server_file) && fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isValidFile(tag)){
							if(tag.getInteger("Device") == deviceCode()){
								if(tag.getInteger("Recipe") < recipeList().size()){
									if(tag.getInteger("Done") > 0){
										this.recipeIndex = tag.getInteger("Recipe");
										this.currentFile = x;
										break;
									}
								}
							}
						}
					}
				}
				if(x == TileEntityMachineServer.FILE_SLOTS.length - 1){
					resetFiles(getServer(worldObj, pos), deviceCode());
				}
			}
		}
	}

}