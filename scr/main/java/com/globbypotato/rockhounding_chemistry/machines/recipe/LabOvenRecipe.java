package com.globbypotato.rockhounding_chemistry.machines.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LabOvenRecipe {

	private final ItemStack solute;
	private final FluidStack solvent;
	private final FluidStack reagent;
	private final FluidStack output;

	public LabOvenRecipe(ItemStack solute, FluidStack solvent, FluidStack reagent, FluidStack output){
		this.solute = solute;
		this.solvent = solvent;
		this.reagent = reagent;
		this.output = output;
	}

	public ItemStack getSolute(){
		return this.solute.copy();
	}

	public FluidStack getSolvent(){
		return this.solvent;
	}

	public FluidStack getReagent(){
		return this.reagent;
	}

	public FluidStack getOutput(){
		return this.output;
	}
}