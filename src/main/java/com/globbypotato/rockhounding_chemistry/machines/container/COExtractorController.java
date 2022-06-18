package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COExtractorController extends ContainerBase<TEExtractorController>{
	public COExtractorController(IInventory playerInventory, TEExtractorController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 80, 59));//input

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 10, 97));//lo
		this.addSlotToContainer(new SlotItemHandler(template, 2, 41, 97));//hi

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 134,  61));//speed upgrade

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
    	}else if(slot == 2){
			if(this.tile.getIntensity() >= 2){
				this.tile.intensity -= 1;
			}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
        	return ItemStack.EMPTY;
		}else if(slot == 3){
			if(this.tile.getIntensity() <= 15){
				this.tile.intensity += 1;
			}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 1, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 4, endIndex, reverseDirection);
    }

}