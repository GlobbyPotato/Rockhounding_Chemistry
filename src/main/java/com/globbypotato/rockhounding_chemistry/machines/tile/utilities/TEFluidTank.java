package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.tile.IToxic;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileTank;

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

			handleToxic(this.world, this.pos);

			if(this.rand.nextInt(10) == 0) {
				this.markDirtyClient();
			}
		}
	}

}