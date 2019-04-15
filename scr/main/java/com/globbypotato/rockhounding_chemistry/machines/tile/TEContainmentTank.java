package com.globbypotato.rockhounding_chemistry.machines.tile;

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

			emptyContainer(FILL_BUCKET, this.inputTank);
			fillContainer(DRAIN_BUCKET, this.inputTank);

			this.markDirtyClient();
		}
	}

}