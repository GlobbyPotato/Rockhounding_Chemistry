package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

public class TEExhaustionValve extends TileEntityInv {

	public TEExhaustionValve() {
		super(0, 0, 0, 0);
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "exhaustion_valve";
	}

	@Override
	public void update() {
		if(!this.world.isRemote){

		}
	}


}