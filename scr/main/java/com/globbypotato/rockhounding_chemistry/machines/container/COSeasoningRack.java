package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TESeasoningRack;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COSeasoningRack extends ContainerBase<TESeasoningRack>{
	public COSeasoningRack(IInventory playerInventory, TESeasoningRack te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler output = this.tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 53, 57));//input
		this.addSlotToContainer(new SlotItemHandler(output, 0, 107, 57));//output

	}
}