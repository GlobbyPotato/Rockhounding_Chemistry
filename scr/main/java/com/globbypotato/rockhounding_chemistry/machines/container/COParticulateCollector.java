package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEParticulateCollector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COParticulateCollector extends ContainerBase<TEParticulateCollector>{
	public COParticulateCollector(IInventory playerInventory, TEParticulateCollector te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler output = this.tile.getOutput();
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 106, 45));//slag1
		this.addSlotToContainer(new SlotItemHandler(template, 1, 106, 72));//slag2

		this.addSlotToContainer(new SlotItemHandler(output, 0, 34, 45));//flyash
		this.addSlotToContainer(new SlotItemHandler(output, 1, 34, 72));//sulfur


	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0 || slot == 1){
    		return ItemStack.EMPTY;
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 2, endIndex, reverseDirection);
    }

}