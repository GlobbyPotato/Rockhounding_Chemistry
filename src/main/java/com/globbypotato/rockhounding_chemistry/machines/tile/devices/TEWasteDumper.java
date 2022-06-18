package com.globbypotato.rockhounding_chemistry.machines.tile.devices;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTankVessel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEWasteDumper extends TileEntityTankVessel {

	public static int templateSlots = 1;

	public FluidTank inputTank;
	public FluidStack filter = null;

	public TEWasteDumper() {
		super(0, 0, templateSlots, 0);

		this.inputTank = new FluidTank(100000) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillMainFiltered(fluid);
			}

			@Override
			public boolean canDrain() {
				return false;
			}
		};
		this.inputTank.setTileEntity(this);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.filter = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Filter"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

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

	@Override
	public GasHandlerConcatenate getCombinedGasTank() {
		return new GasHandlerConcatenate(this.inputTank);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "waste_dumper";
	}



	//----------------------- TANK HANDLER -----------------------
	public FluidStack getFilter(){
		return this.filter;
	}

	public boolean hasFilter(){
		return getFilter() != null;
	}

	private boolean isMatchingFilter(FluidStack fluid) {
		return hasFilter() ? getFilter().isFluidEqual(fluid) : true;
	}

	public boolean canFillMainFiltered(FluidStack fluid) {
		return fluid != null && isMatchingFilterMain(fluid);
	}

	private boolean isMatchingFilterMain(FluidStack fluid) {
		return hasFilterMain() ? getFilterMain().isFluidEqual(fluid) : true;
	}

	public FluidStack getFilterMain(){return this.filter;}
	public boolean hasFilterMain(){return getFilterMain() != null;}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!world.isRemote){
			if(this.inputTank.getFluid() != null){
				this.inputTank.setFluid(null);
			}
		}
	}

}