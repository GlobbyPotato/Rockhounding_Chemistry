package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEPullingCrucibleTop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPullingCrucibleTop extends ContainerBase<TEPullingCrucibleTop>{
	public COPullingCrucibleTop(IInventory playerInventory, TEPullingCrucibleTop te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 80, 60));//input
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){
    		this.tile.getBase().dummyRecipe = null;
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

}