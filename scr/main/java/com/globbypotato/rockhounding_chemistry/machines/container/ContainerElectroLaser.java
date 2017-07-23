package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityElectroLaser;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerElectroLaser extends ContainerBase<TileEntityElectroLaser> {

	public ContainerElectroLaser(IInventory playerInventory, TileEntityElectroLaser tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 125, 17));//input
	}

}