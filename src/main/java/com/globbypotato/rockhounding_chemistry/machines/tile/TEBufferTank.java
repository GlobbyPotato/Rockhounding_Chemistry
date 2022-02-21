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

public class TEBufferTank extends TileEntityTank implements IToxic{
	
	public static int inputSlots = 1;
	public static int templateSlots = 1;

	public FluidTank inputTank;

	public static int DRAIN_SLOT = 0;

	public TEBufferTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return false;
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.inputTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == DRAIN_SLOT && CoreUtils.isEmptyBucket(insertingStack)) {
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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound solutionTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(solutionTankNBT);
		compound.setTag("InputTank", solutionTankNBT);

        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack drainSlot(){
		return this.input.getStackInSlot(DRAIN_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "buffer_tank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}



	// ----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() { return 10000;}
	//solution
	public boolean hasTankFluid(){ return this.inputTank.getFluid() != null;}
	public FluidStack getTankFluid(){return hasTankFluid() ? this.inputTank.getFluid() : null;}
	public int getTankAmount() {return hasTankFluid() ? this.inputTank.getFluidAmount() : 0;}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			fillContainer(DRAIN_SLOT, this.inputTank);
			handleToxic(this.world, this.pos);

			this.markDirtyClient();
		}
	}


}