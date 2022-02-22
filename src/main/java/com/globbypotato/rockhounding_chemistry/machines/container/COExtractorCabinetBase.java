package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEElementsCabinetBase;

import net.minecraft.inventory.IInventory;

public class COExtractorCabinetBase extends ContainerBase<TEElementsCabinetBase>{
	public COExtractorCabinetBase(IInventory playerInventory, TEElementsCabinetBase te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
	}

}