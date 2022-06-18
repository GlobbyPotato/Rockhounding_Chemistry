package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorStabilizer;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COExtractorStabilizer extends ContainerBase<TEExtractorStabilizer>{
	public COExtractorStabilizer(IInventory playerInventory, TEExtractorStabilizer te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();

        for (int k = 0; k < 6; k++){
        	int offset = (26 * k);
        	this.addSlotToContainer(new SlotItemHandler(input, k, 15 + offset, 56));//inputs
        }
	}

}