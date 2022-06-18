package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COFlotationTank extends ContainerBase<TEFlotationTank>{
	public COFlotationTank(IInventory playerInventory, TEFlotationTank te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//void solvent
		this.addSlotToContainer(new SlotItemHandler(template, 1, 80, 22));//filter solvent

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 0){
			this.tile.inputTank.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 1){
			if(!this.tile.isFiltered()){
				ItemStack heldItem = inventoryplayer.getItemStack();
				this.tile.filterManualSolvent = ModUtils.handleAmpoule(heldItem, true, false);
			}
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 2, endIndex, reverseDirection);
    }

}