package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEEvaporationTank;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COEvaporationTank extends ContainerBase<TEEvaporationTank>{
	public COEvaporationTank(IInventory playerInventory, TEEvaporationTank te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler output = this.tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(output, 0, 34, 45));//salt
	}

}