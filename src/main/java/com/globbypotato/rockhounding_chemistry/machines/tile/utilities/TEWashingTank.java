package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.tile.IToxic;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TEWashingTank extends TileTank implements IToxic{

	public TEWashingTank() {
		super();
	}



	// ----------------------- HANDLER -----------------------
	public static String getName(){
		return "washing_tank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}

	public boolean isValidSubstance(FluidStack fluid) {
		return ModUtils.isLightFluid(fluid.getFluid()) || ModUtils.isHeavyFluid(fluid.getFluid());
	}

	public int getTankCapacity() {
		return 30000;
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