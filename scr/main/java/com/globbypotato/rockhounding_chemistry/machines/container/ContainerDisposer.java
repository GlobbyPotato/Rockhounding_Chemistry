package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDisposer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDisposer extends ContainerBase<TileEntityDisposer>{
	public ContainerDisposer(IInventory playerInventory, TileEntityDisposer te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                this.addSlotToContainer(new SlotItemHandler(input, j + i * 3, 62 + j * 18, 20 + i * 18));//inputs
            }
        }

		this.addSlotToContainer(new SlotItemHandler(template, 0, 43,  38));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 58,  75));//-
		this.addSlotToContainer(new SlotItemHandler(template, 2, 102, 75));//+
		this.addSlotToContainer(new SlotItemHandler(template, 3, 42,  75));//--
		this.addSlotToContainer(new SlotItemHandler(template, 4, 118, 75));//++
		this.addSlotToContainer(new SlotItemHandler(template, 5, 117, 38));//lock
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 9){
    		this.tile.activation = !this.tile.activation;
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 10){
			if(this.tile.interval >= 2){
				this.tile.interval -= 2;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 11){
			if(this.tile.interval <= 1198){
				this.tile.interval += 2;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 12){
			if(this.tile.interval >= 10){
				this.tile.interval -= 10;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 13){
			if(this.tile.interval <= 1190){
				this.tile.interval += 10;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 14){
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
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 9, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 15, endIndex, reverseDirection);
		}
    }

}