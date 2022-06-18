package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

public class TECentrifuge extends TileEntityInv {
	
	public TECentrifuge() {
		super(0, 0, 0, 0);
	}

	@Override
	public void update() {}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "centrifuge";
	}

}