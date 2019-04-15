package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraftforge.fluids.FluidStack;

public class PollutantFluidRecipe {

	private FluidStack input;

	public PollutantFluidRecipe(FluidStack input){
		this.input = input;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input.copy();
		return null;
	}

}