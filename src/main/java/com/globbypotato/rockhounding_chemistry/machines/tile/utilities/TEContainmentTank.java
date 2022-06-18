package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import com.globbypotato.rockhounding_chemistry.machines.tile.TileTank;

public class TEContainmentTank extends TileTank{

	public TEContainmentTank() {
		super();
	}



	// ----------------------- HANDLER -----------------------
	public static String getName(){
		return "containment_tank";
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {	
			if(this.rand.nextInt(10) == 0) {
				this.markDirtyClient();
			}
		}
	}

}