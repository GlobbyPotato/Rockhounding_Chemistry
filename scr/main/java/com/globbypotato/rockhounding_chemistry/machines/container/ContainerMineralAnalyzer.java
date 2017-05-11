package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralAnalyzer extends ContainerBase<TileEntityMineralAnalyzer>{
	Slot drain;

	public ContainerMineralAnalyzer(IInventory playerInventory, TileEntityMineralAnalyzer tile){
		super(playerInventory, tile);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 38, 24));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 34));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 70, 34));//consumable
		this.addSlotToContainer(new SlotItemHandler(input, 3, 105, 88));//sulf
		this.addSlotToContainer(new SlotItemHandler(input, 4, 127, 88));//chlo
		this.addSlotToContainer(new SlotItemHandler(input, 5, 149, 88));//fluo

		this.addSlotToContainer(new SlotItemHandler(output, 0, 70, 60));//output
		
		drain = this.addSlotToContainer(new SlotItemHandler(template, 0, 84,  88));//drain

	}
	
	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 7){
			this.tile.drainValve = !this.tile.drainValve;
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 8, endIndex, reverseDirection);
		}
    }

}