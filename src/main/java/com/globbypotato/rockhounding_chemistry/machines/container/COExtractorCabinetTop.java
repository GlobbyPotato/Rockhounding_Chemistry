package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEElementsCabinetTop;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COExtractorCabinetTop extends ContainerBase<TEElementsCabinetTop>{
	public COExtractorCabinetTop(IInventory playerInventory, TEElementsCabinetTop te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();

    	this.addSlotToContainer(new SlotItemHandler(input, 0, 35,  71));//dust
    	this.addSlotToContainer(new SlotItemHandler(input, 1, 80,  63));//cylinder

	}
}