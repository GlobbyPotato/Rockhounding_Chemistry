package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasifierPlantRecipe {

	private FluidStack input, output, reactant;
	private ItemStack mainSlag, altSlag;
	private int temperature;

	public GasifierPlantRecipe(FluidStack input, FluidStack reactant, FluidStack output, ItemStack mainSlag, ItemStack altSlag, int temperature){
		this.input = input;
		this.reactant = reactant;
		this.output = output;
		this.mainSlag = mainSlag;
		this.altSlag = altSlag;
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

	public ItemStack getMainSlag(){
		if(!this.mainSlag.isEmpty()) return this.mainSlag.copy();
		return ItemStack.EMPTY;
	}

	public ItemStack getAltSlag(){
		if(!this.altSlag.isEmpty()) return this.altSlag.copy();
		return ItemStack.EMPTY;
	}

	public int getTemperature(){
		return this.temperature;
	}

}