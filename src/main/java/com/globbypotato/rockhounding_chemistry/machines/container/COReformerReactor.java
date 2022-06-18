package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEReformerReactor;

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

       	this.addSlotToContainer(new SlotItemHandler(input, 0, 43,  36));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 1, 43,  55));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 2, 43,  74));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 3, 117, 36));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 4, 117, 55));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 5, 117, 74));//inputs

       	this.addSlotToContainer(new SlotItemHandler(input, 6, 19,  46));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 7, 19,  65));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 8, 141, 46));//inputs
       	this.addSlotToContainer(new SlotItemHandler(input, 9, 141, 65));//inputs

	}

}