package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCastingBench extends ContainerBase<TileEntityCastingBench>{

	public ContainerCastingBench(IInventory playerInventory, TileEntityCastingBench te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 53, 32));//input
		this.addSlotToContainer(new SlotItemHandler(output, 0, 107, 32));//output
	}

}