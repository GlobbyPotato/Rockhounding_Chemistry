package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTank;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COMineralSizerTank extends ContainerBase<TEMineralSizerTank>{
	public COMineralSizerTank(IInventory playerInventory, TEMineralSizerTank te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();

        for (int k = 0; k < 4; k++){
        	int offset = (26 * k);
        	this.addSlotToContainer(new SlotItemHandler(input, k, 41 + offset, 44));//inputs
        }
	}

}