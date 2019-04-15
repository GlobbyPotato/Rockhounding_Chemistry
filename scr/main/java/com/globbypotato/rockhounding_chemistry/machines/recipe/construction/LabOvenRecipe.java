package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LabOvenRecipe {

	private String name, oredict;
	private ItemStack solute, catalyst;
	private FluidStack solvent, reagent, solution, byproduct;
	private boolean type;

	public LabOvenRecipe(String name, @Nullable ItemStack solute, @Nullable ItemStack catalyst, String oredict, boolean type,  @Nonnull FluidStack solvent, @Nullable FluidStack reagent, @Nonnull FluidStack solution, @Nullable FluidStack byproduct){
		this.name = name;
		this.solute = solute;
		this.catalyst = catalyst;
		this.solvent = solvent;
		this.reagent = reagent;
		this.solution = solution;
		this.byproduct = byproduct;
		this.oredict = oredict;
		this.type = type;
	}

	//stack + catalyst
	public LabOvenRecipe(String name, @Nullable ItemStack solute, @Nullable ItemStack catalyst, @Nonnull FluidStack solvent, @Nullable FluidStack reagent, @Nonnull FluidStack solution, @Nullable FluidStack byproduct){
		this(name, solute, catalyst, "", false, solvent, reagent, solution, byproduct);
	}

	//stack only
	public LabOvenRecipe(String name, @Nullable ItemStack solute, @Nonnull FluidStack solvent, @Nullable FluidStack reagent, @Nonnull FluidStack solution, @Nullable FluidStack byproduct){
		this(name, solute, ItemStack.EMPTY, "", false, solvent, reagent, solution, byproduct);
	}

	//oredict + catalyst
	public LabOvenRecipe(String name, String oredict, @Nullable ItemStack catalyst, @Nonnull FluidStack solvent, @Nullable FluidStack reagent, @Nonnull FluidStack solution, @Nullable FluidStack byproduct){
		this(name, ItemStack.EMPTY, catalyst, oredict, true, solvent, reagent, solution, byproduct);
	}

	//oredict only
	public LabOvenRecipe(String name, String oredict, @Nonnull FluidStack solvent, @Nullable FluidStack reagent, @Nonnull FluidStack solution, @Nullable FluidStack byproduct){
		this(name, ItemStack.EMPTY, ItemStack.EMPTY, oredict, true, solvent, reagent, solution, byproduct);
	}

	public String getRecipeName(){
		return this.name;
	}

	public ItemStack getSolute(){
		if(!this.solute.isEmpty()) return this.solute.copy();
		return ItemStack.EMPTY;
	}

	public ItemStack getCatalyst(){
		if(!this.catalyst.isEmpty()) return this.catalyst.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
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

	public FluidStack getByproduct(){
		if(this.byproduct != null) return this.byproduct.copy();
		return null;
	}

}