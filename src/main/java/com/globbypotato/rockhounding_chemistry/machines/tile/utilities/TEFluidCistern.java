package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.tile.IToxic;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileTank;

import net.minecraftforge.fluids.FluidTank;

public class TEFluidCistern extends TileTank implements IToxic{

	public TEFluidCistern() {
		super();

	}



	// ----------------------- HANDLER -----------------------
	public static String getName(){
		return "fluid_cistern";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}



	//----------------------- CUSTOM -----------------------
	@Override
	public int getTankCapacity() {
		return 40000;
	}



	//----------------------- PROCESS -----------------------
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