package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltSeasoner;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSaltSeasoner extends ContainerBase<TileEntitySaltSeasoner>{
	public ContainerSaltSeasoner(IInventory playerInventory, TileEntitySaltSeasoner te) {
		super(playerInventory,te);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 53, 42));//input
		this.addSlotToContainer(new SlotItemHandler(output, 0, 107, 42));//output
	}
}