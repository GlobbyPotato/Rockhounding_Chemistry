package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEDepositionBase;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CODepositionBase extends ContainerBase<TEDepositionBase>{
	public CODepositionBase(IInventory playerInventory, TEDepositionBase te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler upgrade = this.tile.getUpgrade();
		IItemHandler output = this.tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 37, 50));//upd1
		this.addSlotToContainer(new SlotItemHandler(upgrade, 1, 123, 50));//upd2
		this.addSlotToContainer(new SlotItemHandler(upgrade, 2, 80, 80));//speed

		this.addSlotToContainer(new SlotItemHandler(output, 0, 80, 50));//output

	}
}