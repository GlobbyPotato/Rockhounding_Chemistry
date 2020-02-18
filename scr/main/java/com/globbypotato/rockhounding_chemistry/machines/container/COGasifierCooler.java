package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierCooler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COGasifierCooler extends ContainerBase<TEGasifierCooler>{
	public COGasifierCooler(IInventory playerInventory, TEGasifierCooler te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 30,  35));//speed upgrade
		this.addSlotToContainer(new SlotItemHandler(upgrade, 1, 30,  75));//refractary upgrade

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 2){ 
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 2, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 3, endIndex, reverseDirection);
    }
}