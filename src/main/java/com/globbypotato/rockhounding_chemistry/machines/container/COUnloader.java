package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COUnloader extends ContainerBase<TEUnloader>{
	public COUnloader(IInventory playerInventory, TEUnloader te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler output = this.tile.getOutput();

    	this.addSlotToContainer(new SlotItemHandler(output, 0, 80,  90));//unloader
	}

}