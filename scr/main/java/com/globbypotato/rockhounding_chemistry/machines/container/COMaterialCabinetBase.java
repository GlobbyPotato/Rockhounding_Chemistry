package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEMaterialCabinetBase;

import net.minecraft.inventory.IInventory;

public class COMaterialCabinetBase extends ContainerBase<TEMaterialCabinetBase>{
	public COMaterialCabinetBase(IInventory playerInventory, TEMaterialCabinetBase te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
	}

}