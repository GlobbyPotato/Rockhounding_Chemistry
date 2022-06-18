package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

public class TEStirredTankBase extends TileEntityInv {

	public TEStirredTankBase() {
		super(0, 0, 0, 0);
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "stirred_tank_base";
	}


	@Override
	public void update() {
	
	}

}