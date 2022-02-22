package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasPurifierRecipe {

	private FluidStack input, output;
	private ItemStack mainSlag, altSlag;

	public GasPurifierRecipe(FluidStack input, FluidStack output, ItemStack mainSlag, ItemStack altSlag){
		this.input = input;
		this.output = output;
		this.mainSlag = mainSlag;
		this.altSlag = altSlag;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input.copy();
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

}