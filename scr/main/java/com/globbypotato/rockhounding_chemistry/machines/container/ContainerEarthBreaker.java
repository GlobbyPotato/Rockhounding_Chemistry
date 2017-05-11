package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerEarthBreaker extends ContainerBase<TileEntityEarthBreaker>{
	public ContainerEarthBreaker(IInventory playerInventory, TileEntityEarthBreaker te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 152, 75));//switch
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){
    		this.tile.activator = !this.tile.activator; 
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 1, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 1, endIndex, reverseDirection);
		}
    }

}