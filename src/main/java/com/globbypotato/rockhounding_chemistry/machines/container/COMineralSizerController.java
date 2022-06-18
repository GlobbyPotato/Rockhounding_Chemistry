package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COMineralSizerController extends ContainerBase<TEMineralSizerController>{
	public COMineralSizerController(IInventory playerInventory, TEMineralSizerController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 14, 68));//input

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 62, 21));//lo
		this.addSlotToContainer(new SlotItemHandler(template, 2, 98, 21));//hi

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 146, 26));//speed
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 1){ 
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
    	}else if(slot == 2){ 
    		if(this.tile.getComminution() > 0){
    			this.tile.comminution--;
    			this.tile.pushGears();
    		}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
        	return ItemStack.EMPTY;
		}else if(slot == 3){
    		if(this.tile.getComminution() < 15){
    			this.tile.comminution++;
    			this.tile.pushGears();
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