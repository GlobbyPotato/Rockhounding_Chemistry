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

public class TEFluidInputTank extends TileEntityTank implements IToxic {
	
	public static int inputSlots = 2;
	public static int templateSlots = 4;

	public boolean isFiltered;

	public FluidTank solventTank;
	public FluidTank reagentTank;

	public static int SOLVENT_SLOT = 0;
	public static int REAGENT_SLOT = 1;

	public FluidStack filterSolvent = null;
	public FluidStack filterReagent = null;

	public FluidStack filterManualSolvent = null;
	public FluidStack filterManualReagent = null;

	public TEFluidInputTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.solventTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillSolvent(fluid) || canForceSolvent(fluid);
			}

			@Override
			public boolean canDrain() {
				return canDrainFilteredSolvent(getSolventFluid());
			}
		};
		this.solventTank.setTileEntity(this);

		this.reagentTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillReagent(fluid) || canForceReagent(fluid);
			}

			@Override
			public boolean canDrain() {
				return canDrainFilteredReagent(getReagentFluid());
			}
		};
		this.reagentTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == SOLVENT_SLOT && (canForceSolvent(FluidUtil.getFluidContained(insertingStack)) || canFillSolvent(FluidUtil.getFluidContained(insertingStack)))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == REAGENT_SLOT && canForceReagent(FluidUtil.getFluidContained(insertingStack)) || canFillReagent(FluidUtil.getFluidContained(insertingStack))) {
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
		this.solventTank.readFromNBT(compound.getCompoundTag("SolventTank"));
		this.reagentTank.readFromNBT(compound.getCompoundTag("ReagentTank"));
		this.filterSolvent = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("FilterSolvent"));
		this.filterReagent = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("FilterReagent"));
		this.filterManualSolvent = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("ManualSolvent"));
		this.filterManualReagent = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("ManualReagent"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("Filtered", isFiltered());
		
		NBTTagCompound solventTankNBT = new NBTTagCompound();
		this.solventTank.writeToNBT(solventTankNBT);
		compound.setTag("SolventTank", solventTankNBT);

		NBTTagCompound reagentTankNBT = new NBTTagCompound();
		this.reagentTank.writeToNBT(reagentTankNBT);
		compound.setTag("ReagentTank", reagentTankNBT);

		if(getFilterSolvent() != null){
	        NBTTagCompound filterSolventNBT = new NBTTagCompound();
	        this.filterSolvent.writeToNBT(filterSolventNBT);
	        compound.setTag("FilterSolvent", filterSolventNBT);
		}

		if(getFilterReagent() != null){
	        NBTTagCompound filterReagentNBT = new NBTTagCompound();
	        this.filterReagent.writeToNBT(filterReagentNBT);
	        compound.setTag("FilterReagent", filterReagentNBT);
		}

		if(getManualSolvent() != null){
	        NBTTagCompound manualSolventNBT = new NBTTagCompound();
	        this.filterManualSolvent.writeToNBT(manualSolventNBT);
	        compound.setTag("ManualSolvent", manualSolventNBT);
		}

		if(getManualReagent() != null){
	        NBTTagCompound manualReagentNBT = new NBTTagCompound();
	        this.filterManualReagent.writeToNBT(manualReagentNBT);
	        compound.setTag("ManualReagent", manualReagentNBT);
		}

        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.solventTank, this.reagentTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fillSolvent(){
		return this.input.getStackInSlot(SOLVENT_SLOT);
	}

	public ItemStack fillReagent(){
		return this.input.getStackInSlot(REAGENT_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "lab_oven_intank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.solventTank);
		tanks.add(this.reagentTank);
		return tanks;
 	}



	// ----------------------- CUSTOM -----------------------
	public FluidStack getFilterSolvent(){ return this.filterSolvent; }
	public boolean hasFilterSolvent(){ return this.filterSolvent != null; }

	public FluidStack getFilterReagent(){ return this.filterReagent; }
	public boolean hasFilterReagent(){ return this.filterReagent != null; }

	public FluidStack getManualSolvent(){ return this.filterManualSolvent; }
	public boolean hasManualSolvent(){ return this.filterManualSolvent != null; }

	public FluidStack getManualReagent(){ return this.filterManualReagent; }
	public boolean hasManualReagent(){ return this.filterManualReagent != null; }

	public boolean isFiltered(){
		return this.isFiltered;
	}



	// ----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() { return 5000;}
	//solvent
	public boolean hasSolventFluid(){ return this.solventTank.getFluid() != null;}
	public FluidStack getSolventFluid(){return hasSolventFluid() ? this.solventTank.getFluid() : null;}
	public int getSolventAmount() {return hasSolventFluid() ? this.solventTank.getFluidAmount() : 0;}
	//reagent
	public boolean hasReagentFluid(){ return this.reagentTank.getFluid() != null;}
	public FluidStack getReagentFluid(){return hasReagentFluid() ? this.reagentTank.getFluid() : null;}
	public int getReagentAmount() {return hasReagentFluid() ? this.reagentTank.getFluidAmount() : 0;}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			emptyContainer(SOLVENT_SLOT, this.solventTank);
			emptyContainer(REAGENT_SLOT, this.reagentTank);

			this.markDirtyClient();
		}
	}

	public boolean canForceSolvent(FluidStack fluid) {
		return this.input.canSetOrFillFluid(this.solventTank, getSolventFluid(), fluid) 
			&& isFiltered() 
			&& hasFilterSolvent() 
			&& fluid.isFluidEqual(getFilterSolvent());
	}

	public boolean canForceReagent(FluidStack fluid) {
		return this.input.canSetOrFillFluid(this.reagentTank, getReagentFluid(), fluid) 
			&& isFiltered() 
			&& hasFilterReagent() 
			&& fluid.isFluidEqual(getFilterReagent());
	}

	boolean canDrainFilteredSolvent(FluidStack fluid) {
		return isFiltered()
			&& (!hasFilterSolvent() || (hasFilterSolvent() && !fluid.isFluidEqual(getFilterSolvent())));
	}

	boolean canDrainFilteredReagent(FluidStack fluid) {
		return isFiltered()
			&& (!hasFilterReagent() || (hasFilterReagent() && !fluid.isFluidEqual(getFilterReagent())));
	}

	public boolean canFillSolvent(FluidStack fluid) {
		return this.input.canSetOrFillFluid(this.solventTank, getSolventFluid(), fluid) && isManualFilteredSolvent(fluid);
	}

	private boolean isManualFilteredSolvent(FluidStack fluid) {
		return !isFiltered() ? !hasManualSolvent() || (hasManualSolvent() && getManualSolvent().isFluidEqual(fluid)) : false;
	}

	public boolean canFillReagent(FluidStack fluid) {
		return this.input.canSetOrFillFluid(this.reagentTank, getReagentFluid(), fluid) && isManualFilteredReagent(fluid);
	}

	private boolean isManualFilteredReagent(FluidStack fluid) {
		return !isFiltered() ? !hasManualReagent() || (hasManualReagent() && getManualReagent().isFluidEqual(fluid)) : false;
	}

}