package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityGanController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGanController extends ContainerBase<TileEntityGanController>{

	public ContainerGanController(IInventory playerInventory, TileEntityGanController te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 110, 33));//cycle 
		this.addSlotToContainer(new SlotItemHandler(template, 1, 110, 16));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 2, 110, 60));//acquiring
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){
			if(this.tile.activationKey){
				this.tile.cycleKey = !this.tile.cycleKey;
			}
    		return null;
		}else if(slot == 1){
    		this.tile.activationKey = !this.tile.activationKey;
    		return null;
		}else if(slot == 2){
			if(!this.tile.cycleKey){
				this.tile.compressKey = !this.tile.compressKey;
			}
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, 0, 1, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 3, endIndex, reverseDirection);
		}
    }

}