package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TETransposer;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COTransposer extends ContainerBase<TETransposer>{
	public COTransposer(IInventory playerInventory, TETransposer te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 83,  95));//activation fluid
		this.addSlotToContainer(new SlotItemHandler(template, 1, 83,  23));//activation gas
		this.addSlotToContainer(new SlotItemHandler(template, 2, 25,  59));//filter main
		this.addSlotToContainer(new SlotItemHandler(template, 3, 147, 95));//void fluid out
		this.addSlotToContainer(new SlotItemHandler(template, 4, 147, 23));//void gas out
		this.addSlotToContainer(new SlotItemHandler(template, 5, 95,  59));//void main

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 138,  59));//speed upgrade

		
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
        if(slot == 0){
        	this.tile.isFluidActive = !this.tile.isFluidActive;
    		return ItemStack.EMPTY;
        }else if(slot == 1){
        	this.tile.isGasActive = !this.tile.isGasActive;
    		return ItemStack.EMPTY;
		}else if(slot == 2){ 
			ItemStack heldItem = inventoryplayer.getItemStack();
			this.tile.filterMain = ModUtils.handleAmpoule(heldItem, true, true);
    		return ItemStack.EMPTY;
		}else if(slot == 3){
			this.tile.outputTankFluid.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 4){
			this.tile.outputTankGas.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 5){
			this.tile.inputTankMain.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 6, endIndex, reverseDirection);
    }

}