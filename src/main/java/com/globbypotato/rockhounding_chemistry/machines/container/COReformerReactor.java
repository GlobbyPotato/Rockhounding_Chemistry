package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerReactor;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COReformerReactor extends ContainerBase<TEReformerReactor>{
	public COReformerReactor(IInventory playerInventory, TEReformerReactor te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler output = this.tile.getOutput();

       	this.addSlotToContainer(new SlotItemHandler(input, 0, 43,  31));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 1, 43,  50));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 2, 43,  69));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 3, 117, 31));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 4, 117, 50));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 5, 117, 69));//inputs

       	this.addSlotToContainer(new SlotItemHandler(input, 6, 19,  41));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 7, 19,  60));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 8, 141, 41));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 9, 141, 60));//inputs

       	this.addSlotToContainer(new SlotItemHandler(output, 0, 17, 95));//purge

	}

}