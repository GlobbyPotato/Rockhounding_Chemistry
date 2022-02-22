package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TileTank extends TileEntityTank{
	public int emitType;
	public int emitThreashold;

	public static int inputSlots = 2;
	public static int templateSlots = 5;

	public FluidTank inputTank;
	public static int FILL_BUCKET = 0;
	public static int DRAIN_BUCKET = 1;
	public FluidStack filter = null;

	public TileTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillFiltered(fluid) && isValidSubstance(fluid);
			}

			@Override
			public boolean canDrain() {
				return true;
			}

			@Override
			public void onContentsChanged(){
				updateNeighbours();
		    }

		};
		this.inputTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == FILL_BUCKET && canFillFiltered(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == DRAIN_BUCKET && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.filter = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Filter"));
		this.emitThreashold = compound.getInteger("EmitThreashold");
		this.emitType = compound.getInteger("EmitType");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("EmitType", this.emitType);
		compound.setInteger("EmitThreashold", this.emitThreashold);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		if(getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        this.filter.writeToNBT(filterNBT);
	        compound.setTag("Filter", filterNBT);
		}

        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fillSlot(){
		return this.input.getStackInSlot(FILL_BUCKET);
	}

	public ItemStack drainSlot(){
		return this.input.getStackInSlot(DRAIN_BUCKET);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}



	// ----------------------- CUSTOM -----------------------
	public void updateNeighbours() {
		this.world.notifyNeighborsOfStateChange(this.pos, this.world.getBlockState(this.pos).getBlock(), true);
	}

	public int emittedPower() {
		int powerFraction = getTankCapacity() / 16;
		// by level
		if(getEmitType() == 1){
			return ((15 * getTankAmount()) / getTankCapacity());

		// on over threashold
		}else if(getEmitType() == 2){
			return getTankAmount() >= ((getTankCapacity() / 100) * getEmitThreashold()) ? 15 : 0;

		// off over threashold
		}else if(getEmitType() == 3){
			return getTankAmount() < ((getTankCapacity() / 100) * getEmitThreashold()) ? 15 : 0;
		}

		return 0;
	}



	//----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() {
		return 100000;
	}

	public boolean hasTankFluid(){
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getTankFluid(){
		return hasTankFluid() ? this.inputTank.getFluid() : null;
	}

	public int getTankAmount() {
		return hasTankFluid() ? this.inputTank.getFluidAmount() : 0;
	}

	public FluidStack getFilter(){
		return this.filter;
	}

	public boolean hasFilter(){
		return getFilter() != null;
	}

	private boolean isMatchingFilter(FluidStack fluid) {
		return hasFilter() ? getFilter().isFluidEqual(fluid) : true;
	}

//fluid I/O
	public boolean canFillFiltered(FluidStack fluid) {
		return isMatchingFilter(fluid) && this.input.canSetOrFillFluid(this.inputTank, getTankFluid(), fluid);
	}

	public boolean canDrainFluid(FluidStack fluid){
		return canDrainFiltered(fluid);
	}

	private boolean canDrainFiltered(FluidStack fluid) {
		return isMatchingFilter(fluid) && this.input.canDrainFluid(getTankFluid(), fluid);
	}



	// ----------------------- CUSTOM -----------------------
	public boolean isValidSubstance(FluidStack fluid) {
		return ModUtils.isLightFluid(fluid.getFluid());
	}

	public int getEmitType() {
		return this.emitType;
	}

	public int getEmitThreashold() {
		return this.emitThreashold;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {}

}