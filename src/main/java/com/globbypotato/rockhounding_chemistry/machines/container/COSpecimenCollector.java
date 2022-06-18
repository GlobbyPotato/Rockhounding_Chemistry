package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TESpecimenCollector;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COSpecimenCollector extends ContainerBase<TESpecimenCollector>{
	public COSpecimenCollector(IInventory playerInventory, TESpecimenCollector te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler output = this.tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(output, 0, 80, 47));//main
		this.addSlotToContainer(new SlotItemHandler(output, 1, 53, 71));//secondary
		this.addSlotToContainer(new SlotItemHandler(output, 2, 107, 71));//waste

	}

}