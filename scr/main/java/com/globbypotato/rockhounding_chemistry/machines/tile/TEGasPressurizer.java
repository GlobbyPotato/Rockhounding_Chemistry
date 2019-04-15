package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

public class TEGasPressurizer extends TileEntityInv {

	public TEGasPressurizer() {
		super(0, 0, 0, 0);
	}

	@Override
	public void update() {}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gas_pressurizer";
	}


}
