package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEAirCompressor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COAirCompressor extends ContainerBase<TEAirCompressor>{
	public COAirCompressor(IInventory playerInventory, TEAirCompressor te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 26,  24));//fuel

		this.addSlotToContainer(new SlotItemHandler(template, 0, 152, 91));//activation
		
		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 70, 88));//inductor

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 1){ 
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