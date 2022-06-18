package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEContainmentTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COContainmentTank extends ContainerBase<TEContainmentTank>{
	public COContainmentTank(IInventory playerInventory, TEContainmentTank te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 137, 35));//filter
		this.addSlotToContainer(new SlotItemHandler(template, 1, 23, 57));//void

		this.addSlotToContainer(new SlotItemHandler(template, 2, 45, 95));//emit
		this.addSlotToContainer(new SlotItemHandler(template, 3, 63, 95));//+
		this.addSlotToContainer(new SlotItemHandler(template, 4, 81, 95));//-
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 0){ 
			ItemStack heldItem = inventoryplayer.getItemStack();
			this.tile.filter = ModUtils.handleAmpoule(heldItem, true, false);
    		return ItemStack.EMPTY;
		}else if(slot == 1){
			this.tile.inputTank.setFluid(null);
			this.tile.updateNeighbours();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 2){
    		if(this.tile.getEmitType() < 3){
    			this.tile.emitType++;
    		}else{
    			this.tile.emitType = 0;
    		}
			this.tile.updateNeighbours();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 3){
    		if(this.tile.getEmitThreashold() >= 5){
    			this.tile.emitThreashold -= 5;
    		}
			this.tile.updateNeighbours();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 4){
    		if(this.tile.getEmitThreashold() <= 95){
    			this.tile.emitThreashold += 5;
    		}
			this.tile.updateNeighbours();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 5, endIndex, reverseDirection);
    }

}