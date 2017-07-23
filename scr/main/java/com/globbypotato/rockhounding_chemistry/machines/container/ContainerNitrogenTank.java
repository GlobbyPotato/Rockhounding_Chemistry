package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerNitrogenTank extends ContainerBase<TileEntityNitrogenTank> {

	public ContainerNitrogenTank(IInventory playerInventory, TileEntityNitrogenTank tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 108, 70));//output
	}

}