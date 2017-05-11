package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.utils.FuelUtils;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityMachineEnergy extends TileEntityMachineInv  implements IStorage {

	public Random rand = new Random();

	public int FUEL_SLOT;

	public int cookTime = 0;
	public int recipeIndex = -1;
	public boolean activation;

	public int powerCount = 0;
	public int powerMax = 32000;
	public int redstoneCount = 0;
	public int redstoneMax = 32000;
	public int yeldCount = 0;
	public int yeldMax = 900;
	public int chargeCount = 0;
	public int chargeMax = 1000000;

	public static int consumedSulf = 10;
	public static int consumedChlo = 30;
	public static int consumedFluo = 20;

	public static int consumedNitr = 10;
	public static int consumedPhos = 30;
	public static int consumedCyan = 20;

	public static boolean allowInductor;
	public boolean permanentInductor;
	
	public TileEntityMachineEnergy(int inputSlots, int outputSlots, int fuelSlot) {
		super(inputSlots, outputSlots);
		this.FUEL_SLOT = fuelSlot;
	}

	//---------------- CUSTOM ----------------
	@Override
	public boolean hasRF() { 
		return false; 
	}

	@Override
	public int getGUIHeight() { 
		return 0; 
	}

	@Override
	public boolean canInduct() {
		return hasPermanentInduction() || (!hasPermanentInduction() && input.getStackInSlot(FUEL_SLOT) != null && input.getStackInSlot(FUEL_SLOT).isItemEqual(ToolUtils.inductor));
	}

	public boolean hasPermanentInduction() { 
		return allowInductor && permanentInductor; 
	}

	protected void fuelHandler(ItemStack stack) {
		if(stack != null) {
			if(FuelUtils.isItemFuel(stack) ){
				if( powerCount <= (powerMax - FuelUtils.getItemBurnTime(stack)) ){
					burnFuel(stack);
				}
			}
			if(ItemStack.areItemsEqual(stack, ToolUtils.inductor)){
				if(allowInductor && !permanentInductor){
					permanentInductor = true;
					input.setStackInSlot(FUEL_SLOT, null);
				}
			}
		}
	}

	protected void burnFuel(ItemStack stack) {
		powerCount += FuelUtils.getItemBurnTime(stack);
		stack.stackSize--;
		if(stack.stackSize <= 0){
			input.setStackInSlot(FUEL_SLOT, stack.getItem().getContainerItem(input.getStackInSlot(FUEL_SLOT)));
		}
	}

	protected boolean hasRedstone(ItemStack insertingStack) {
		return insertingStack != null 
			&& (insertingStack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) || insertingStack.getItem() == Items.REDSTONE);
	}

	protected void redstoneHandler(int slot, int cooktime) {
		ItemStack stack = input.getStackInSlot(slot);
		if(stack != null){
			if(stack.getItem() == Items.REDSTONE && redstoneCount <= (redstoneMax - cooktime)){
				burnRedstone(slot, stack, cooktime);
			}else if(stack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) && redstoneCount <= (redstoneMax - (cooktime * 9))){
				burnRedstone(slot, stack, cooktime * 9);
			}
		}
	}

	private void burnRedstone(int slot, ItemStack stack, int charge) {
		redstoneCount += charge; 
		stack.stackSize--;
		if(stack.stackSize <= 0){
			input.setStackInSlot(slot, null);
		}
	}

	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.powerCount = compound.getInteger("PowerCount");
		this.redstoneCount = compound.getInteger("RedstoneCount");
		this.cookTime = compound.getInteger("CookTime");
		this.permanentInductor = compound.getBoolean("Inductor");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PowerCount", this.powerCount);
		compound.setInteger("RedstoneCount", this.redstoneCount);
		compound.setInteger("CookTime", this.cookTime);
		compound.setBoolean("Inductor", this.permanentInductor);
		return compound;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if ((capability == CapabilityEnergy.ENERGY)) {
			return (T)CapabilityEnergy.ENERGY.cast(this);
		}
		return super.getCapability(capability, facing);
	}



	//---------------- COFH ----------------
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return calculateEnergy(maxReceive, false);
	}

	//---------------- FORGE ----------------
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return calculateEnergy(maxReceive, false);
	}

	public int calculateEnergy(int maxReceive, boolean simulate){
		int energyReceived = 0;
		if((canInduct() && hasRF() && isFullRedstone()) || (canInduct() && !hasRF()) ){
	        energyReceived = Math.min(this.getPowerMax() - this.getPower(), Math.min(maxReceive, maxReceive));
	        if (!simulate){
	        	this.powerCount += energyReceived;
	        }
			if(isFullPower()){this.powerCount = this.powerMax;}
		}else if(hasRF()){
	        energyReceived = Math.min(this.getRedstoneMax() - this.getRedstone(), Math.min(maxReceive, maxReceive));
	        if (!simulate){
	        	this.redstoneCount += energyReceived;
	        }
			if(isFullRedstone()){this.redstoneCount = this.redstoneMax;}
		}
        return maxReceive > 0 ? energyReceived : 0;
	}



	//---------------- VARIABLES ----------------
	@Override
	public int getPower() { 	  return this.powerCount; }
	@Override
	public int getPowerMax() { 	  return this.powerMax; }
	@Override
	public int getRedstone() { 	  return this.redstoneCount; }
	@Override
	public int getRedstoneMax() { return this.redstoneMax; }
	@Override
	public int getChargeMax() {   return this.chargeMax; }
	@Override
	public int getYeld() { 	  	  return this.yeldCount; }
	@Override
	public int getYeldMax() {     return this.yeldMax; }



	//---------------- MISC ----------------
	public int rfTransfer(){
    	return 40;
    }

	@Override
	public void update() {}

}