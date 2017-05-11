package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MachineStackHandler extends ItemStackHandler{

	TileEntityMachineInv tile;
	public MachineStackHandler(int slots,TileEntityMachineInv tile) {
		super(slots);
		this.tile = tile;

	}

	public boolean canSetOrStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return (stackInSlot == null || (stackInSlot != null && stackInSlot.stackSize <= stackInSlot.getMaxStackSize() - recipeOutput.stackSize && stackInSlot.isItemEqual(recipeOutput)));
	}

	public void setOrStack(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, stackToSet);
		}else{
			for(int x = 0; x < stackToSet.stackSize; x++){
				incrementSlot(slot);
			}
		}
	}

	public void incrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		if(temp.stackSize + 1 <= temp.getMaxStackSize()){
			temp.stackSize++;
		}
		this.setStackInSlot(slot, temp);
	}
	
	public void setOrIncrement(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, stackToSet);
		}else{
			incrementSlot(slot);
		}
	}

	public void decrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		temp.stackSize--;
		if(temp.stackSize <= 0){
			this.setStackInSlot(slot, null);
		}else{
			this.setStackInSlot(slot, temp);
		}
	}

	public void damageSlot(int slot) {
		if(this.getStackInSlot(slot) != null){
			int damage = this.getStackInSlot(slot).getItemDamage() + 1;
			this.getStackInSlot(slot).setItemDamage(damage);
			if(damage >= this.getStackInSlot(slot).getMaxDamage()){
				this.getStackInSlot(slot).stackSize--;
			}
			if(this.getStackInSlot(slot).stackSize <= 0){
				this.setStackInSlot(slot, null);
			}
		}
	}
}