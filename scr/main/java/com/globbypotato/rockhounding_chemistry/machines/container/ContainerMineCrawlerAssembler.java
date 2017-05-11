package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawlerAssembler;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineCrawlerAssembler extends ContainerBase<TileEntityMineCrawlerAssembler>{
	public ContainerMineCrawlerAssembler(IInventory playerInventory, TileEntityMineCrawlerAssembler te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

        this.addSlotToContainer(new SlotItemHandler(input, 0, 35, 17));//casing
        this.addSlotToContainer(new SlotItemHandler(input, 1, 8,  17));//mode
        this.addSlotToContainer(new SlotItemHandler(input, 2, 62, 17));//arms

        this.addSlotToContainer(new SlotItemHandler(input, 3, 17, 39));//grid1
        this.addSlotToContainer(new SlotItemHandler(input, 4, 35, 39));//grid2
        this.addSlotToContainer(new SlotItemHandler(input, 5, 53, 39));//grid3
        this.addSlotToContainer(new SlotItemHandler(input, 6, 17, 57));//grid4
        this.addSlotToContainer(new SlotItemHandler(input, 7, 35, 57));//grid5
        this.addSlotToContainer(new SlotItemHandler(input, 8, 53, 57));//grid6

        this.addSlotToContainer(new SlotItemHandler(input, 9, 62, 79));//memory

        this.addSlotToContainer(new SlotItemHandler(input, 10, 89, 17));//F
        this.addSlotToContainer(new SlotItemHandler(input, 11, 89, 39));//A
        this.addSlotToContainer(new SlotItemHandler(input, 12, 89, 61));//T
        this.addSlotToContainer(new SlotItemHandler(input, 13, 89, 83));//L
        this.addSlotToContainer(new SlotItemHandler(input, 14, 111, 17));//R
        this.addSlotToContainer(new SlotItemHandler(input, 15, 111, 39));//D
        this.addSlotToContainer(new SlotItemHandler(input, 16, 111, 61));//P
        this.addSlotToContainer(new SlotItemHandler(input, 17, 111, 83));//S

        this.addSlotToContainer(new SlotItemHandler(input, 18, 152, 48));//loader
        this.addSlotToContainer(new SlotItemHandler(input, 19, 152, 21));//material
        
        this.addSlotToContainer(new SlotItemHandler(output, 0, 35, 79));//output
	}
}