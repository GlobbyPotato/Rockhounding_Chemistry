package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import com.globbypotato.rockhounding_chemistry.machines.tile.TileVessel;

public class TEGasHolderBase extends TileVessel {

	public TEGasHolderBase() {
		super(1000000);
	}

	public static String getName(){
		return "gas_holder_base";
	}

	@Override
	public int exhaustOffset(){
		return 2;
	}

	@Override
	public void update() {
		if(!this.world.isRemote){
			checkCollapse();
			this.markDirtyClient();
		}
	}

}