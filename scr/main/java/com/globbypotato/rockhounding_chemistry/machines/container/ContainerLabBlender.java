package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabBlender;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLabBlender extends ContainerBase<TileEntityLabBlender>{
	public ContainerLabBlender(IInventory playerInventory, TileEntityLabBlender te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                this.addSlotToContainer(new SlotItemHandler(input, j + i * 3, 51 + j * 18, 20 + i * 18));//inputs
            }
        }

		this.addSlotToContainer(new SlotItemHandler(input, 9, 8, 8));//fuel
		this.addSlotToContainer(new SlotItemHandler(output, 0, 116, 75));//output
		this.addSlotToContainer(new SlotItemHandler(template, 0, 115,  19));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 69,  75));//lock
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot >= 0 && slot <= 8){
			if(!this.tile.isActive() && this.tile.isLocked()){
				ItemStack heldItem = inventoryplayer.getItemStack();
				if(heldItem != null){
					this.tile.lockList.remove(slot);
					this.tile.lockList.add(slot, heldItem);
				}else{
					if(this.tile.getInput().getStackInSlot(slot) == null){
						this.tile.lockList.remove(slot);
						this.tile.lockList.add(slot, null);
					}
				}
			}
    		return super.slotClick(slot, dragType, clickTypeIn, player);
		}else if(slot == 11){
    		this.tile.activation = !this.tile.activation;
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 12){
    		this.tile.lock = !this.tile.lock;
    		if(!this.tile.isLocked()){
    			this.tile.lockList.clear();
    			this.tile.resetLock();
    		}else{
    			this.tile.lockList.clear();
    			for(Integer stack : this.tile.INPUT_SLOT){
    				if(this.tile.getInput().getStackInSlot(stack) != null){
    					ItemStack lockStack = this.tile.getInput().getStackInSlot(stack).copy();
    					lockStack.stackSize = 1;
        				this.tile.lockList.add(lockStack);
    				}else{
        				this.tile.lockList.add(null);
    				}
    			}
    		}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 11, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 13, endIndex, reverseDirection);
		}
    }

}