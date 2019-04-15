package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TESlurryPond;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COSlurryPond extends ContainerBase<TESlurryPond>{
	public COSlurryPond(IInventory playerInventory, TESlurryPond te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 41, 62));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 99, 27));//solvent bucket
		this.addSlotToContainer(new SlotItemHandler(input, 2, 142, 92));//slurry bucket

		this.addSlotToContainer(new SlotItemHandler(template, 0, 8, 27));//void in
		this.addSlotToContainer(new SlotItemHandler(template, 1, 51, 92));//void out
		this.addSlotToContainer(new SlotItemHandler(template, 2, 119, 27));//filter
		this.addSlotToContainer(new SlotItemHandler(template, 3, 109, 61));//conc-
		this.addSlotToContainer(new SlotItemHandler(template, 4, 145, 61));//conc+

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 3){ 
			this.tile.inputTank.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 4){ 
			this.tile.outputTank.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 5){
			ItemStack heldItem = inventoryplayer.getItemStack();
			this.tile.filter = ModUtils.handleAmpoule(heldItem, true, false);
    		return ItemStack.EMPTY;
		}else if(slot == 6){
			if(this.tile.getConcentration() > 1){this.tile.concentration--;}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 7){
			if(this.tile.getConcentration() < 10){this.tile.concentration++;}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 3, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 8, endIndex, reverseDirection);
    }

}