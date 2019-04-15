package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

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
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEFluidOutputTank extends TileEntityTank implements IToxic{
	
	public static int inputSlots = 2;
	public static int templateSlots = 2;

	public FluidTank solutionTank;
	public FluidTank byproductTank;

	public static int SOLUTION_SLOT = 0;
	public static int BYPRODUCT_SLOT = 1;

	public TEFluidOutputTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.solutionTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return false;
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.solutionTank.setTileEntity(this);

		this.byproductTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return false;
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.byproductTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == SOLUTION_SLOT && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == BYPRODUCT_SLOT && CoreUtils.isEmptyBucket(insertingStack)) {
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
		this.solutionTank.readFromNBT(compound.getCompoundTag("SolutionTank"));
		this.byproductTank.readFromNBT(compound.getCompoundTag("ByproductTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound solutionTankNBT = new NBTTagCompound();
		this.solutionTank.writeToNBT(solutionTankNBT);
		compound.setTag("SolutionTank", solutionTankNBT);

		NBTTagCompound byproductTankNBT = new NBTTagCompound();
		this.byproductTank.writeToNBT(byproductTankNBT);
		compound.setTag("ByproductTank", byproductTankNBT);
	
        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.solutionTank, this.byproductTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fillSolution(){
		return this.input.getStackInSlot(SOLUTION_SLOT);
	}

	public ItemStack fillByproduct(){
		return this.input.getStackInSlot(BYPRODUCT_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "lab_oven_outtank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.solutionTank);
		tanks.add(this.byproductTank);
		return tanks;
 	}



	// ----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() { return 5000;}
	//solution
	public boolean hasSolutionFluid(){ return this.solutionTank.getFluid() != null;}
	public FluidStack getSolutionFluid(){return hasSolutionFluid() ? this.solutionTank.getFluid() : null;}
	public int getSolutionAmount() {return hasSolutionFluid() ? this.solutionTank.getFluidAmount() : 0;}
	//byproduct
	public boolean hasByproductFluid(){ return this.byproductTank.getFluid() != null;}
	public FluidStack getByproductFluid(){return hasByproductFluid() ? this.byproductTank.getFluid() : null;}
	public int getByproductAmount() {return hasByproductFluid() ? this.byproductTank.getFluidAmount() : 0;}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			fillContainer(SOLUTION_SLOT, this.solutionTank);
			fillContainer(BYPRODUCT_SLOT, this.byproductTank);
			handleToxic(this.world, this.pos);

			this.markDirtyClient();
		}
	}



}