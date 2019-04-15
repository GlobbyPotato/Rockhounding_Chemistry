package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEFlotationTank extends TileEntityTank implements IToxic {
	
	public static int inputSlots = 1;
	public static int templateSlots = 2;

	public boolean isFiltered;

	public FluidTank inputTank;

	public static int SOLVENT_SLOT = 0;

	public FluidStack filterSolvent = null;

	public FluidStack filterManualSolvent = null;

	public TEFlotationTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillSolvent(fluid) || canForceSolvent(fluid);
			}

			@Override
			public boolean canDrain() {
				return !isFilteredSolvent(getSolventFluid());
			}
		};
		this.inputTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == SOLVENT_SLOT && (canForceSolvent(FluidUtil.getFluidContained(insertingStack)) || canFillSolvent(FluidUtil.getFluidContained(insertingStack)))) {
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
		this.isFiltered = compound.getBoolean("Filtered");
		this.inputTank.readFromNBT(compound.getCompoundTag("SolventTank"));
		this.filterSolvent = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("FilterSolvent"));
		this.filterManualSolvent = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("ManualSolvent"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("Filtered", isFiltered());
		
		NBTTagCompound solventTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(solventTankNBT);
		compound.setTag("SolventTank", solventTankNBT);

		if(getFilterSolvent() != null){
	        NBTTagCompound filterSolventNBT = new NBTTagCompound();
	        this.filterSolvent.writeToNBT(filterSolventNBT);
	        compound.setTag("FilterSolvent", filterSolventNBT);
		}

		if(getManualSolvent() != null){
	        NBTTagCompound manualSolventNBT = new NBTTagCompound();
	        this.filterManualSolvent.writeToNBT(manualSolventNBT);
	        compound.setTag("ManualSolvent", manualSolventNBT);
		}

        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fillSolvent(){
		return this.input.getStackInSlot(SOLVENT_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "flotation_tank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}



	// ----------------------- CUSTOM -----------------------
	public FluidStack getFilterSolvent(){ return this.filterSolvent; }
	public boolean hasFilterSolvent(){ return this.filterSolvent != null; }

	public FluidStack getManualSolvent(){ return this.filterManualSolvent; }
	public boolean hasManualSolvent(){ return this.filterManualSolvent != null; }

	public boolean isFiltered(){
		return this.isFiltered;
	}



	// ----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() { return 10000;}
	//solvent
	public boolean hasSolventFluid(){ return this.inputTank.getFluid() != null;}
	public FluidStack getSolventFluid(){return hasSolventFluid() ? this.inputTank.getFluid() : null;}
	public int getSolventAmount() {return hasSolventFluid() ? this.inputTank.getFluidAmount() : 0;}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			emptyContainer(SOLVENT_SLOT, this.inputTank);
			handleToxic(this.world, this.pos);

			this.markDirtyClient();
		}
	}

	public boolean canForceSolvent(FluidStack fluid) {
		return this.input.canSetOrFillFluid(this.inputTank, getSolventFluid(), fluid) && isFilteredSolvent(fluid);
	}

	boolean isFilteredSolvent(FluidStack fluid) {
		return isFiltered() && hasFilterSolvent() && fluid.isFluidEqual(getFilterSolvent());
	}

	public boolean canFillSolvent(FluidStack fluid) {
		return this.input.canSetOrFillFluid(this.inputTank, getSolventFluid(), fluid) && isManualFilteredSolvent(fluid);
	}

	private boolean isManualFilteredSolvent(FluidStack fluid) {
		return !isFiltered() ? !hasManualSolvent() || (hasManualSolvent() && getManualSolvent().isFluidEqual(fluid)) : false;
	}

}