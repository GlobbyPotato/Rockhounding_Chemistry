package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralSizer extends ContainerBase<TileEntityMineralSizer>{
	public ContainerMineralSizer(IInventory playerInventory, TileEntityMineralSizer te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 22));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 101, 22));//consumable
		this.addSlotToContainer(new SlotItemHandler(output, 0, 73, 51));//output
		this.addSlotToContainer(new SlotItemHandler(output, 1, 115, 51));//secondary
		this.addSlotToContainer(new SlotItemHandler(output, 2, 109, 79));//waste
	}
	
}