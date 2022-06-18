package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWasteDumper;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COWasteDumper extends ContainerBase<TEWasteDumper>{
	public COWasteDumper(IInventory playerInventory, TEWasteDumper te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 77));//filter
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 0){ 
			ItemStack heldItem = inventoryplayer.getItemStack();
			this.tile.filter = ModUtils.handleAmpoule(heldItem, true, true);
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 1, endIndex, reverseDirection);
    }

}