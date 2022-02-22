package com.globbypotato.rockhounding_chemistry.machines.tile;

public class TEPressureVessel extends TileVessel {

	public TEPressureVessel() {
		super(1000);
	}

	public static String getName(){
		return "pressure_vessel";
	}

	@Override
	public void update() {
		if(!this.world.isRemote){
			checkCollapse();
			this.markDirtyClient();
		}
	}

}