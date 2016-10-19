package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.Utils;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public abstract class TileEntityInvReceiver extends TileEntityInv  implements IEnergyReceiver, IEnergyStorage {

	public int powerMax = 32000;
	public int powerCount;
	
	public Random rand = new Random();

	protected int cookTime;
	protected int totalCookTime;
	
	public static int FUEL_SLOT;

	public TileEntityInvReceiver(int inputSlots, int outputSlots, int fuelSlot) {
		super(inputSlots, outputSlots);
		this.FUEL_SLOT = fuelSlot;
	}


	protected void fuelHandler() {
		if(input.getStackInSlot(FUEL_SLOT) != null && Utils.isItemFuel(input.getStackInSlot(FUEL_SLOT)) ){
			if( powerCount <= (powerMax - Utils.getItemBurnTime(input.getStackInSlot(FUEL_SLOT))) ){
				burnFuel();
			}
		}
	}
	
	protected void burnFuel() {
		powerCount += Utils.getItemBurnTime(input.getStackInSlot(FUEL_SLOT));
		ItemStack stack = input.getStackInSlot(FUEL_SLOT);
		stack.stackSize--;
		input.setStackInSlot(FUEL_SLOT, stack);
		if(input.getStackInSlot(FUEL_SLOT).stackSize <= 0){input.setStackInSlot(FUEL_SLOT, input.getStackInSlot(FUEL_SLOT).getItem().getContainerItem(input.getStackInSlot(FUEL_SLOT)));}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.powerCount = compound.getInteger("PowerCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PowerCount", this.powerCount);
		return compound;
	}

	abstract boolean canInduct();
	

	public boolean hasPower(){
		return this.powerCount > 0;
	}

	// COFH SUPPORT
	@Override
	public int getEnergyStored(EnumFacing from) {
		if(canInduct()){
			return powerCount;
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return powerMax;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (powerCount == -1) return 0;
			received = Math.min(powerMax - powerCount, maxReceive);
			if (!simulate) {
				powerCount += received;
				if(powerCount >= powerMax){powerCount = powerMax;}
			}
		}
		return received;
	}



	// FORGE ENERGY SUPPORT
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (powerCount == -1) return 0;
			received = Math.min(powerMax - powerCount, maxReceive);
			if (!simulate) {
				powerCount += received;
				if(powerCount >= powerMax){powerCount = powerMax;}
			}
		}
		return received;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		if(canInduct()){
			return powerCount;
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored() {
		return powerMax;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
	}
	
	public IItemHandlerModifiable capOutput(){
		if(input.getStackInSlot(FUEL_SLOT) != null){
			if(input.getStackInSlot(FUEL_SLOT).getItem() == Items.BUCKET){
				return new CombinedInvWrapper(automationOutput,new RangedWrapper(input,1,2));
			}
		}
		return automationOutput;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(facing == EnumFacing.DOWN){
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(capOutput());
			}
			else {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(automationInput);
			}
		}
		return super.getCapability(capability, facing);
	}
}
