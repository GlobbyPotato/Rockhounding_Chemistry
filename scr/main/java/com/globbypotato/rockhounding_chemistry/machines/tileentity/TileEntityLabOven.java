package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_chemistry.utils.FuelUtils;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityLabOven extends TileEntityMachineEnergy implements IFluidHandlingTile {

	private ItemStackHandler template = new TemplateStackHandler(3);

	public static final int SOLVENT_SLOT = 2;
	public static final int SOLUTION_SLOT = 3;
	public static final int REDSTONE_SLOT = 4;
	public static final int REAGENT_SLOT = 5;

	public FluidTank inputTank;
	public FluidTank reagentTank;
	public FluidTank outputTank;

	public TileEntityLabOven() {
		super(6, 0, 1);

		inputTank = new FluidTank(1000 + ModConfig.machineTank) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return activation && hasSolvent(fluid) && isValidInterval() && isCorrectSolvent(fluid);
			}

			@Override
			public boolean canDrain() {
				return !isValidInterval() || (isValidInterval() && isWrongSolvent(inputTank));
			}
		};
		inputTank.setTileEntity(this);
		inputTank.setCanFill(true);

		reagentTank = new FluidTank(1000 + ModConfig.machineTank) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return activation && hasReagent(fluid) && isValidInterval() && isCorrectReagent(fluid);
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
				if (slot == INPUT_SLOT && activation && isValidInterval() && hasRecipe(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == FUEL_SLOT
						&& (FuelUtils.isItemFuel(insertingStack) || ToolUtils.hasinductor(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == SOLVENT_SLOT && activation && isValidInterval()
						&& hasSolvent(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == REAGENT_SLOT && activation && isValidInterval()
						&& hasReagent(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == REDSTONE_SLOT && hasRedstone(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == SOLUTION_SLOT && ToolUtils.isBucketType(insertingStack) && isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN_OUT);
		this.markDirtyClient();
	}

	// ----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate() {
		return this.template;
	}

	public int getCookTimeMax() {
		return ModConfig.speedLabOven;
	}

	@Override
	public int getGUIHeight() {
		return GuiLabOven.HEIGHT;
	}

	@Override
	public boolean hasRF() {
		return true;
	}

	// ----------------------- CUSTOM ------------------------
	public boolean isValidInterval() {
		return recipeIndex >= 0 && recipeIndex <= ModRecipes.labOvenRecipes.size() - 1;
	}

	public LabOvenRecipe getRecipe() {
		return isValidInterval() ? ModRecipes.labOvenRecipes.get(recipeIndex) : null;
	}

	private boolean hasRecipe(ItemStack stack) {
		return isValidInterval() && ModRecipes.labOvenRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getSolute() != null && stack.isItemEqual(getRecipe().getSolute()));
	}

	private boolean hasSolvent(FluidStack stack) {
		return isValidInterval() && ModRecipes.labOvenRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getSolvent() != null && stack.isFluidEqual(getRecipe().getSolvent()));
	}

	private boolean hasReagent(FluidStack stack) {
		return isValidInterval() && ModRecipes.labOvenRecipes.stream().anyMatch(
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

	private boolean isEmptyBucket(ItemStack insertingStack) {
		if (!FluidRegistry.isUniversalBucketEnabled()) {
			return insertingStack.getItem() == ModFluids.beaker;
		} else {
			return insertingStack.getItem() == Items.BUCKET || (insertingStack.getItem() instanceof UniversalBucket
					& FluidUtil.getFluidContained(insertingStack).containsFluid(null));
		}

	}

	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.recipeIndex = compound.getInteger("RecipeScan");
		this.activation = compound.getBoolean("Activation");
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.reagentTank.readFromNBT(compound.getCompoundTag("ReagentTank"));
		this.outputTank.readFromNBT(compound.getCompoundTag("OutputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("RecipeScan", this.recipeIndex);
		compound.setBoolean("Activation", this.activation);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		NBTTagCompound reagentTankNBT = new NBTTagCompound();
		this.reagentTank.writeToNBT(reagentTankNBT);
		compound.setTag("ReagentTank", reagentTankNBT);

		NBTTagCompound outputTankNBT = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankNBT);
		compound.setTag("OutputTank", outputTankNBT);

		return compound;
	}

	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem,
				this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}

	private void fillContainer(int slot, FluidTank tank) {
		if (ToolUtils.isBucketType(input.getStackInSlot(slot)) && outputTank.getFluid() != null
				&& outputTank.getFluidAmount() >= 1000) {
			if (FluidUtil.tryFillContainer(input.getStackInSlot(slot), tank, 1000, null, false) != null) {
				if (input.getStackInSlot(SOLUTION_SLOT).stackSize > 1) {
					ItemStack droppingBeaker = FluidUtil.tryFillContainer(input.getStackInSlot(slot), tank, 1000, null,
							true);
					EnumFacing front = EnumFacing.getFront(getBlockMetadata());
					BlockPos frontPos = pos.offset(front.getOpposite());
					EntityItem entityitem = new EntityItem(worldObj, frontPos.getX() + 0.5D, frontPos.getY() + 0.5D,
							frontPos.getZ() + 0.5D, droppingBeaker);
					worldObj.spawnEntityInWorld(entityitem);
					input.decrementSlot(SOLUTION_SLOT);
				} else {
					input.setStackInSlot(slot,
							FluidUtil.tryFillContainer(input.getStackInSlot(slot), tank, 1000, null, true));
				}
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return true;
		else
			return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}

	private FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(inputTank, reagentTank, outputTank);
	}

	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!isValidInterval()) {
			recipeIndex = -1;
		}
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		redstoneHandler(REDSTONE_SLOT, this.getCookTimeMax());

		if (!worldObj.isRemote) {
			emptyContainer(SOLVENT_SLOT, inputTank);
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
		return activation && getRecipe() != null
				&& ItemStack.areItemsEqual(getRecipe().getSolute(), input.getStackInSlot(INPUT_SLOT))
				&& (outputTank.getFluid() == null
						|| (outputTank.getFluid() != null
								&& outputTank.getFluid().isFluidEqual(getRecipe().getOutput())
								&& outputTank.getFluidAmount() <= (outputTank.getCapacity()
										- getRecipe().getOutput().amount)))
				&& (inputTank.getFluid() != null && inputTank.getFluid().isFluidEqual(getRecipe().getSolvent())
						&& inputTank.getFluidAmount() >= getRecipe().getSolvent().amount)
				&& ((reagentTank.getFluid() == null && getRecipe().getReagent() == null)
						|| (reagentTank.getFluid() != null
								&& reagentTank.getFluid().isFluidEqual(getRecipe().getReagent())
								&& reagentTank.getFluidAmount() >= getRecipe().getReagent().amount))
				&& this.getRedstone() >= this.getCookTimeMax() && this.getPower() >= this.getCookTimeMax();
	}

	private void execute() {
		cookTime++;
		powerCount--;
		redstoneCount--;
		if (cookTime >= getCookTimeMax()) {
			cookTime = 0;
			handleOutput();
		}
	}

	private void handleOutput() {
		inputTank.getFluid().amount -= getRecipe().getSolvent().amount;
		if (inputTank.getFluid().amount <= 0) {
			inputTank.setFluid(null);
		}
		if (reagentTank.getFluid() != null && hasReagent(reagentTank.getFluid())) {
			reagentTank.getFluid().amount -= getRecipe().getReagent().amount;
			if (reagentTank.getFluid().amount <= 0) {
				reagentTank.setFluid(null);
			}
		}
		outputTank.fillInternal(getRecipe().getOutput(), true);
		input.decrementSlot(INPUT_SLOT);
	}

}