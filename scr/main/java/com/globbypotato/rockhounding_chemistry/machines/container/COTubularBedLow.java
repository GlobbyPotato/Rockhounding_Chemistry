package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TETubularBedLow;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COTubularBedLow extends ContainerBase<TETubularBedLow>{
	public COTubularBedLow(IInventory playerInventory, TETubularBedLow te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler output = this.tile.getOutput();

        for (int k = 0; k < 4; k++){
        	int offset = (18 * k);
        	this.addSlotToContainer(new SlotItemHandler(input, k,   44 , 23 + offset));//side A
        	this.addSlotToContainer(new SlotItemHandler(input, k+4, 116, 23 + offset));//side B
        }

       	this.addSlotToContainer(new SlotItemHandler(output, 0, 80, 96));//purge

	}
}