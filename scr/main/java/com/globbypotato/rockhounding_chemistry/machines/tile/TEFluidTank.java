package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import net.minecraftforge.fluids.FluidTank;

public class TEFluidTank extends TileTank implements IToxic{

	public TEFluidTank() {
		super();
	}



	// ----------------------- HANDLER -----------------------
	public static String getName(){
		return "generic_fluid_tank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			emptyContainer(FILL_BUCKET, this.inputTank);
			fillContainer(DRAIN_BUCKET, this.inputTank);
			handleToxic(this.world, this.pos);

			this.markDirtyClient();
		}
	}

}