package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraftforge.fluids.FluidStack;

public class AirCompressorRecipe {

	private FluidStack output;

	public AirCompressorRecipe(FluidStack output){
		this.output = output;
	}

	public FluidStack getOutput(){
		if(this.output != null) return this.output;
		return null;
	}

}