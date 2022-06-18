package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

public class GasPurifierRecipe {

	private FluidStack input, output;
	private List<String> elements;
	private List<Integer> quantities;

	public GasPurifierRecipe(FluidStack input, FluidStack output, List<String> element, List<Integer> quantity){
		this.input = input;
		this.output = output;
		this.elements = element;
		this.quantities = quantity;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input.copy();
		return null;
	}

	public FluidStack getOutput(){
		if(this.output != null) return this.output.copy();
		return null;
	}

	public ArrayList<String> getElements() {
		ArrayList<String> temp = new ArrayList<String>();
		if(this.elements != null){
			temp.addAll(this.elements);
		}
		return temp;
	}

	public ArrayList<Integer> getQuantities() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if(this.quantities != null){
			temp.addAll(this.quantities);
		}
		return temp;
	}

}