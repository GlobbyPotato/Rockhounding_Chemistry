package com.globbypotato.rockhounding_chemistry.machines.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class DepositionChamberRecipe {

	private ItemStack input, output;
	private FluidStack solvent;
	private int temp, press;

	public DepositionChamberRecipe(ItemStack input, ItemStack output, FluidStack solvent, int temp, int press){
		this.input = input;
		this.output = output;
		this.solvent = solvent;
		this.temp = temp;
		this.press = press;
	}

	public ItemStack getInput(){
		return this.input.copy();
	}

	public ItemStack getOutput() {
		return this.output.copy();
	}

	public FluidStack getSolvent(){
		return this.solvent;
	}

	public int getTemperature() {
		return this.temp;
	}

	public int getPressure() {
		return this.press;
	}

}