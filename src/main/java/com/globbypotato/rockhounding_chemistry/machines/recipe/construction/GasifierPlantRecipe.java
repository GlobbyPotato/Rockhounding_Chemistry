package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

public class GasifierPlantRecipe {

	private FluidStack input, output, reactant;
	private List<String> elements;
	private List<Integer> quantities;
	private int temperature;

	public GasifierPlantRecipe(FluidStack input, FluidStack reactant, FluidStack output, List<String> element, List<Integer> quantity, int temperature){
		this.input = input;
		this.reactant = reactant;
		this.output = output;
		this.elements = element;
		this.quantities = quantity;
		this.temperature = temperature;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input.copy();
		return null;
	}

	public FluidStack getReactant(){
		if(this.reactant != null) return this.reactant.copy();
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

	public int getTemperature(){
		return this.temperature;
	}

}