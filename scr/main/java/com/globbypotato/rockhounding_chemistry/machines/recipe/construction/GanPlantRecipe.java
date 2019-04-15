package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

public class GanPlantRecipe {

	private FluidStack input;
	private List<FluidStack> output;

	public GanPlantRecipe(FluidStack input, List<FluidStack> output){
		this.input = input;
		this.output = output;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input.copy();
		return null;
	}

	public ArrayList<FluidStack> getOutputs() {
		ArrayList<FluidStack> temp = new ArrayList<FluidStack>();
		if(this.output != null){
			temp.addAll(this.output);
		}
		return temp;
	}

}