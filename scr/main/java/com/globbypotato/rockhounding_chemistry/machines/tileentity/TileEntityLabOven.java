package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityLabOven extends TileEntityMachineTank {

	private ItemStackHandler template = new TemplateStackHandler(3);

	public static final int SOLVENT_SLOT = 2;
	public static final int SOLUTION_SLOT = 3;
	public static final int REDSTONE_SLOT = 4;
	public static final int REAGENT_SLOT = 5;
	public static final int SPEED_SLOT = 6;

	public FluidTank solventTank;
	public FluidTank reagentTank;
	public FluidTank outputTank;

	public TileEntityLabOven() {
		super(7, 0, 1);

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

		input = new MachineStackHandler(INPUT_SLOTS, this) {
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


	// ----------------------- CUSTOM ------------------------
	public boolean isValidInterval() {
		return recipeIndex >= 0 && recipeIndex <= MachineRecipes.labOvenRecipes.size() - 1;
	}

	public LabOvenRecipe getRecipe() {
		return isValidInterval() ? MachineRecipes.labOvenRecipes.get(recipeIndex) : null;
	}

	private boolean hasRecipe(ItemStack stack) {
		return isValidInterval() && MachineRecipes.labOvenRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getSolute() != null && (isValidSolute(stack) || isValidCatalyst(stack)));
	}

	private boolean hasSolvent(FluidStack stack) {
		return isValidInterval() && MachineRecipes.labOvenRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getSolvent() != null && stack.isFluidEqual(getRecipe().getSolvent()));
	}

	private boolean hasReagent(FluidStack stack) {
		return isValidInterval() && MachineRecipes.labOvenRecipes.stream().anyMatch(
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

	private FluidStack solventFluid(){
		return solventTank.getFluid();
	}

	private FluidStack reagentFluid(){
		return reagentTank.getFluid();
	}

	private FluidStack outputFluid(){
		return outputTank.getFluid();
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

			if (isValidInterval()) {
				if (canSynthesize()) {
					execute();
				}
			}
			this.markDirtyClient();
		}
	}

	public boolean isCooking() {
		return getRecipe() != null && canSynthesize() && cookTime > 0;
	}

	public boolean canSynthesize() {
		return isActive()
			&& getRecipe() != null
			&& getInputStack() != null
			&& (isValidSolute(getInputStack()) || isValidCatalyst(getInputStack()))
			&& input.canSetOrFill(outputTank, outputFluid(), getRecipe().getOutput()) 
			&& input.hasEnoughFluid(solventFluid(), getRecipe().getSolvent())
			&& (noReagentUsed() || input.hasEnoughFluid(reagentFluid(), getRecipe().getReagent()))
			&& isRedstoneRequired(this.getCookTimeMax()) 
			&& this.getPower() >= this.getCookTimeMax();
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
	}
}