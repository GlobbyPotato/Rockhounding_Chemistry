package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedLow;

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

        for (int k = 0; k < 4; k++){
        	int offset = (18 * k);
        	this.addSlotToContainer(new SlotItemHandler(input, k,   41 , 33 + offset));//side A
        	this.addSlotToContainer(new SlotItemHandler(input, k+4, 119, 33 + offset));//side B
        }

	}
}