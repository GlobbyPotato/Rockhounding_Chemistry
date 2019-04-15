package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorInjector;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COExtractorInjector extends ContainerBase<TEExtractorInjector>{
	public COExtractorInjector(IInventory playerInventory, TEExtractorInjector te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler upgrade = this.tile.getUpgrade();

    	this.addSlotToContainer(new SlotItemHandler(input, 0, 44,  60));//tube
    	this.addSlotToContainer(new SlotItemHandler(input, 1, 116, 60));//cylinder

    	this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 80,  60));//speed
	}

}