package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

public class TETubularBedTop extends TileEntityInv {

	public TETubularBedTop() {
		super(0, 0, 0, 0);
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "tubular_bed_top";
	}


	@Override
	public void update() {
	
	}

}