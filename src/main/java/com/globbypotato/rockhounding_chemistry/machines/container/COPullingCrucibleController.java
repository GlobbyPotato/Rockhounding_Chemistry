package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEPullingCrucibleController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPullingCrucibleController extends ContainerBase<TEPullingCrucibleController>{
	public COPullingCrucibleController(IInventory playerInventory, TEPullingCrucibleController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 80, 64));//input

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 152, 74));//speed

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){
    		this.tile.dummyRecipe = null;
    		return super.slotClick(slot, dragType, clickTypeIn, player);
		}else if(slot == 1){ 
    		this.tile.dummyRecipe = null;
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 1, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 2, endIndex, reverseDirection);
    }

}