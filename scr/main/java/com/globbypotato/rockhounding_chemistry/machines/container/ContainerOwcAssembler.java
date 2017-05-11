package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerOwcAssembler extends ContainerBase<TileEntityOwcAssembler>{
	public ContainerOwcAssembler(IInventory playerInventory, TileEntityOwcAssembler te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		
        this.addSlotToContainer(new SlotItemHandler(input, 0, 8, 17));//bulkhead
        this.addSlotToContainer(new SlotItemHandler(input, 1, 8,  35));//concrete
        this.addSlotToContainer(new SlotItemHandler(input, 2, 8, 53));//duct
        this.addSlotToContainer(new SlotItemHandler(input, 3, 8, 71));//turbine
        this.addSlotToContainer(new SlotItemHandler(input, 4, 89, 17));//generator
        this.addSlotToContainer(new SlotItemHandler(input, 5, 89, 35));//valve
        this.addSlotToContainer(new SlotItemHandler(input, 6, 89, 53));//storage
        this.addSlotToContainer(new SlotItemHandler(input, 7, 89, 71));//controller
	}
}