package com.globbypotato.rockhounding_chemistry.machines.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LabOvenRecipe {

	private final ItemStack solute;
	private final FluidStack solvent;
	private final FluidStack reagent;
	private final FluidStack output;
	private boolean catalyst;

	public LabOvenRecipe(ItemStack solute, boolean catalyst, FluidStack solvent, FluidStack reagent, FluidStack output){
		this.solute = solute;
		this.solvent = solvent;
		this.reagent = reagent;
		this.output = output;
		this.catalyst = catalyst;
	}

	public LabOvenRecipe(ItemStack solute, boolean catalyst, FluidStack solvent, FluidStack output){
		this(solute, catalyst, solvent, null, output);
	}

	public ItemStack getSolute(){
		return this.solute.copy();
	}
	
	public boolean isCatalyst(){
		return this.catalyst;
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