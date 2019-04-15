package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEPipelineValve;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPipelineValve extends ContainerBase<TEPipelineValve> {

	public COPipelineValve(IInventory playerInventory, TEPipelineValve tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		for(int x = 0; x < 6; x++){
			int offset = x * 18;
			this.addSlotToContainer(new SlotItemHandler(template, x,  35 + offset, 45));//locks
		}
		for(int x = 6; x < 12; x++){
			int offset = (x-6) * 18;
			this.addSlotToContainer(new SlotItemHandler(template, x,  35 + offset, 64));//filters
		}

		this.addSlotToContainer(new SlotItemHandler(template, 12,  12, 54));//rrobin
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot >= 0 && slot <= 5){
			if(!this.tile.tiltStatus[slot]){
				this.tile.sideStatus[slot] = !this.tile.sideStatus[slot];
				this.tile.markDirtyClient();
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot >= 6 && slot <= 11){
			ItemStack heldItem = inventoryplayer.getItemStack();
			this.tile.sideFilter[slot - 6] = ModUtils.handleAmpoule(heldItem, true, false);
    		return ItemStack.EMPTY;
		}else if(slot == 12){
			this.tile.robin = !this.tile.robin;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 13, endIndex, reverseDirection);
    }

}