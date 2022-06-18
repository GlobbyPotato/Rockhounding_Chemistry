package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELabBlenderTank;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COLabBlenderTank extends ContainerBase<TELabBlenderTank>{
	public COLabBlenderTank(IInventory playerInventory, TELabBlenderTank te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

        for (int k = 0; k < 7; k++){
        	int offsetX = (18 * k);
        	int offsetY = (6 * k);
        	this.addSlotToContainer(new SlotItemHandler(input, k, 126 - offsetX, 57 - offsetY));//inputs
        }
        
		this.addSlotToContainer(new SlotItemHandler(template, 0, 26, 96));//lock

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot >= 0 && slot < TELabBlenderTank.INPUT_SLOT.length){
			if(this.tile.isLocked()){
				ItemStack heldItem = inventoryplayer.getItemStack().copy();
				if(!heldItem.isEmpty()){
					this.tile.lockList.remove(slot);
					this.tile.lockList.add(slot, heldItem);
				}else{
					if(this.tile.getInput().getStackInSlot(slot).isEmpty()){
						this.tile.lockList.remove(slot);
						this.tile.lockList.add(slot, ItemStack.EMPTY);
					}
				}
			}
    		return super.slotClick(slot, dragType, clickTypeIn, player);
		}else if(slot == 7){
    		this.tile.lock = !this.tile.lock;
    		if(!this.tile.isLocked()){
    			this.tile.lockList.clear();
    			this.tile.resetLock();
    		}else{
    			this.tile.lockList.clear();
    			for(Integer stack : TELabBlenderTank.INPUT_SLOT){
    				if(!this.tile.getInput().getStackInSlot(stack).isEmpty()){
    					ItemStack lockStack = this.tile.getInput().getStackInSlot(stack).copy();
    					lockStack.setCount(1);
        				this.tile.lockList.add(lockStack);
    				}else{
        				this.tile.lockList.add(ItemStack.EMPTY);
    				}
    			}
    		}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 8, endIndex, reverseDirection);
    }

}