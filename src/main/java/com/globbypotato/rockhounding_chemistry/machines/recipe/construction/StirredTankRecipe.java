package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraftforge.fluids.FluidStack;

public class StirredTankRecipe {

	private FluidStack solvent, reagent, solution, fume;
	private int voltage;

	public StirredTankRecipe(@Nonnull FluidStack solvent, @Nullable FluidStack reagent, @Nonnull FluidStack solution, @Nullable FluidStack fume, int voltage){
		this.solvent = solvent;
		this.reagent = reagent;
		this.solution = solution;
		this.fume = fume;
		this.voltage = voltage;
	}

	public FluidStack getSolvent(){
		if(this.solvent != null) return this.solvent.copy();
		return null;
	}

	public FluidStack getReagent(){
		if(this.reagent != null) return this.reagent.copy();
		return null;
	}

	public FluidStack getSolution(){
		if(this.solution != null) return this.solution.copy();
		return null;
	}

	public FluidStack getFume(){
		if(this.fume != null) return this.fume.copy();
		return null;
	}

	public int getVoltage(){
		return this.voltage;
	}
}