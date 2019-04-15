package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.item.ItemStack;

public class TELabOvenChamber extends TileEntityInv {

	public TELabOvenChamber() {
		super(0, 1, 0, 0);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "lab_oven_chamber";
	}



	//----------------------- SLOTS -----------------------
	public ItemStack mainSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {}


}