package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAirChiller extends ContainerBase<TileEntityAirChiller> {

	public ContainerAirChiller(IInventory playerInventory, TileEntityAirChiller tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 38, 17));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 121, 61));//aotputput
	}

}