package com.globbypotato.rockhounding_chemistry.machines.container;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEDisposer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CODisposer extends ContainerBase<TEDisposer>{
	public CODisposer(IInventory playerInventory, TEDisposer te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

		for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                this.addSlotToContainer(new SlotItemHandler(input, j + i * 3, 62 + j * 18, 22 + i * 18));//inputs
            }
        }

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 58,  77));//-
		this.addSlotToContainer(new SlotItemHandler(template, 2, 102, 77));//+
		this.addSlotToContainer(new SlotItemHandler(template, 3, 42,  77));//--
		this.addSlotToContainer(new SlotItemHandler(template, 4, 118, 77));//++
		this.addSlotToContainer(new SlotItemHandler(template, 5, 117, 40));//lock
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot >= 0 && slot < TEDisposer.INPUT_SLOT.length){
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
		}else if(slot == 9){ 
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 9){
    		this.tile.activation = !this.tile.activation;
			doClickSound(player, tile.getWorld(), tile.getPos());
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
		}else if(slot == 10){
			if(this.tile.interval >= 2){
				this.tile.interval -= 2;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
		}else if(slot == 11){
			if(this.tile.interval <= 1198){
				this.tile.interval += 2;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
		}else if(slot == 12){
			if(this.tile.interval >= 10){
				this.tile.interval -= 10;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
		}else if(slot == 13){
			if(this.tile.interval <= 1190){
				this.tile.interval += 10;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
		}else if(slot == 14){
    		this.tile.lock = !this.tile.lock;
    		if(!this.tile.isLocked()){
    			this.tile.lockList = new ArrayList<ItemStack>();
    			this.tile.resetLock();
    		}else{
    			this.tile.lockList = new ArrayList<ItemStack>();
    			for(Integer stack : TEDisposer.INPUT_SLOT){
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
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 9, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 15, endIndex, reverseDirection);
		}
    }

}