package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierCooler;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COGasifierCooler extends ContainerBase<TEGasifierCooler>{
	public COGasifierCooler(IInventory playerInventory, TEGasifierCooler te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 30,  45));//speed upgrade
		this.addSlotToContainer(new SlotItemHandler(upgrade, 1, 30,  85));//refractary upgrade

	}

}