package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraftforge.fluids.FluidStack;

public class PollutantGasRecipe {

	private FluidStack input;

	public PollutantGasRecipe(FluidStack input){
		this.input = input;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input.copy();
		return null;
	}

}