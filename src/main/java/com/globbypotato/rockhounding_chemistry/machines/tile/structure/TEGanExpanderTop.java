package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

public class TEGanExpanderTop extends TileEntityInv {

	public TEGanExpanderTop() {
		super(0, 0, 0, 0);
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gan_expander_top";
	}


	@Override
	public void update() {
	
	}

}