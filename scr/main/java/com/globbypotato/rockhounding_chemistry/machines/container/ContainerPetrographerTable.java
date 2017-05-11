package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPetrographerTable extends ContainerBase<TileEntityPetrographerTable>{
	public ContainerPetrographerTable(IInventory playerInventory, TileEntityPetrographerTable te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
        this.addSlotToContainer(new SlotItemHandler(input, 0, 80, 24));//tool
        this.addSlotToContainer(new SlotItemHandler(input, 1, 44, 60));//ore
        this.addSlotToContainer(new SlotItemHandler(input, 2, 116, 60));//shard
	}
}