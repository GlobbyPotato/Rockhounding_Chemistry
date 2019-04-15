package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TERetentionVat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CORetentionVat extends ContainerBase<TERetentionVat>{
	public CORetentionVat(IInventory playerInventory, TERetentionVat te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 57, 75));//lo
		this.addSlotToContainer(new SlotItemHandler(template, 2, 103, 75));//hi

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 152, 58));//speed
		this.addSlotToContainer(new SlotItemHandler(upgrade, 1, 35, 75));//filter
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){ 
    		this.tile.dummyRecipe = null;
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
    	}else if(slot == 1){ 
    		this.tile.dummyRecipe = null;
			if(this.tile.getGravity() > 0){
				this.tile.gravity -= this.tile.filterMove();
				if(this.tile.gravity < 0) {this.tile.gravity = 0;}
			}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
        	return ItemStack.EMPTY;
		}else if(slot == 2){
    		this.tile.dummyRecipe = null;
			if(this.tile.getGravity() <= 32.00F - (this.tile.filterMove()) ){
				this.tile.gravity += this.tile.filterMove();
			}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 0, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 3, endIndex, reverseDirection);
    }

}