package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCabinet;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COMineralSizerCabinet extends ContainerBase<TEMineralSizerCabinet>{
	public COMineralSizerCabinet(IInventory playerInventory, TEMineralSizerCabinet te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();

		for (int i = 0; i < 5; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new SlotItemHandler(input, j + i * 9, 8 + j * 18, 23 + i * 18));//inputs
            }
        }
	}

}