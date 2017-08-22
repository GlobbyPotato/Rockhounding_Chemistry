package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityUltraBattery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerUltraBattery extends ContainerBase<TileEntityUltraBattery> {

	public ContainerUltraBattery(IInventory playerInventory, TileEntityUltraBattery tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler template = tile.getTemplate();
		this.addSlotToContainer(new SlotItemHandler(template, 0, 67, 68));//D
		this.addSlotToContainer(new SlotItemHandler(template, 1, 44, 46));//U
		this.addSlotToContainer(new SlotItemHandler(template, 2, 44, 24));//N
		this.addSlotToContainer(new SlotItemHandler(template, 3, 44, 68));//S
		this.addSlotToContainer(new SlotItemHandler(template, 4, 22, 46));//W
		this.addSlotToContainer(new SlotItemHandler(template, 5, 66, 46));//E

		this.addSlotToContainer(new SlotItemHandler(template, 6, 22, 24));//charge
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot >= 0 && slot <= 5){
			this.tile.sideStatus[slot] = !this.tile.sideStatus[slot];
			if(this.tile.sideStatus[slot]){
				this.tile.sideStatus[6] = false; 
			}
    		return null;
		}else if(slot == 6){
			this.tile.sideStatus[slot] = !this.tile.sideStatus[slot];
			for(int x = 0; x < 6; x++){
				this.tile.sideStatus[x] = false; 
			}
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 7, endIndex, reverseDirection);
		}
    }

}