package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserAmplifier;

import net.minecraft.inventory.IInventory;

public class ContainerLaserAmplifier extends ContainerBase<TileEntityLaserAmplifier> {

	public ContainerLaserAmplifier(IInventory playerInventory, TileEntityLaserAmplifier tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {	}

}