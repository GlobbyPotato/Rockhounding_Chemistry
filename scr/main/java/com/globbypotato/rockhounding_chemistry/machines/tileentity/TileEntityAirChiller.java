package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.machines.gui.GuiAirChiller;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TileEntityAirChiller extends TileEntityMachineTank{

	public static final int SOLUTION_SLOT = 0;
	public static final int EXTRACT_SLOT = 1;

	public FluidTank inputTank;

	public static int totInput = 2;
	public static int totOutput = 0;

	public TileEntityAirChiller() {
		super(totInput, totOutput, 0);

		this.inputTank = new FluidTank(30000) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return true;
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.inputTank.setTileEntity(this);

		this.input = new MachineStackHandler(totInput, this) {
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate) {
				if(slot == SOLUTION_SLOT && CoreUtils.isBucketType(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == EXTRACT_SLOT && CoreUtils.isBucketType(insertingStack) && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		this.markDirtyClient();
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiAirChiller.HEIGHT;
	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			emptyContainer(SOLUTION_SLOT, this.inputTank);
			fillContainer(EXTRACT_SLOT, this.inputTank);
			this.markDirtyClient();
		}
	}

}