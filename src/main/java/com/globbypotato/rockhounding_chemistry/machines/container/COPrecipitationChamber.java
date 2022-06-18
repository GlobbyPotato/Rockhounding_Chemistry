package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEPrecipitationChamber;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPrecipitationChamber extends ContainerBase<TEPrecipitationChamber>{
	public COPrecipitationChamber(IInventory playerInventory, TEPrecipitationChamber te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler output = this.tile.getOutput();

    	this.addSlotToContainer(new SlotItemHandler(output, 0, 80,  71));//precipitate
	}

}