package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;

public class ContainerAirCompresser extends ContainerBase<TileEntityAirCompresser> {

	public ContainerAirCompresser(IInventory playerInventory, TileEntityAirCompresser tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {	}

}