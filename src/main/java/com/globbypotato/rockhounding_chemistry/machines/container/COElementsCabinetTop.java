package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetTop;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COElementsCabinetTop extends ContainerBase<TEElementsCabinetTop>{
	public COElementsCabinetTop(IInventory playerInventory, TEElementsCabinetTop te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();

    	this.addSlotToContainer(new SlotItemHandler(input, 0, 35,  71));//dust
    	this.addSlotToContainer(new SlotItemHandler(input, 1, 80,  63));//cylinder

	}
}