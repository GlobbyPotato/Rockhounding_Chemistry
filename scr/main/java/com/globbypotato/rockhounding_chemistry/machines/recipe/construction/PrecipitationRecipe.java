package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PrecipitationRecipe {

	private String name;
	private ItemStack solute, catalyst, precipitate;
	private FluidStack solvent, solution;
	private String oredict;
	private boolean type;

	public PrecipitationRecipe(String name, @Nullable ItemStack solute, @Nullable ItemStack catalyst, String oredict, boolean type, @Nonnull FluidStack solvent, @Nonnull FluidStack solution, @Nullable ItemStack precipitate){
		this.name = name;
		this.solute = solute;
		this.catalyst = catalyst;
		this.solvent = solvent;
		this.solution = solution;
		this.precipitate = precipitate;
		this.oredict = oredict;
		this.type = type;
	}

	//stack + catalyst
	public PrecipitationRecipe(String name, @Nullable ItemStack solute, @Nullable ItemStack catalyst, @Nonnull FluidStack solvent, @Nonnull FluidStack solution, @Nullable ItemStack precipitate){
		this(name, solute, catalyst, "", false, solvent, solution, precipitate);	
	}

	//stack only
	public PrecipitationRecipe(String name, @Nullable ItemStack solute, @Nonnull FluidStack solvent, @Nonnull FluidStack solution, @Nullable ItemStack precipitate){
		this(name, solute, ItemStack.EMPTY, "", false, solvent, solution, precipitate);	
	}

	//oredict + catalyst
	public PrecipitationRecipe(String name, String solute, @Nullable ItemStack catalyst, @Nonnull FluidStack solvent, @Nonnull FluidStack solution, @Nullable ItemStack precipitate){
		this(name, ItemStack.EMPTY, catalyst, solute, true, solvent, solution, precipitate);	
	}

	//oredict only
	public PrecipitationRecipe(String name, String solute, @Nonnull FluidStack solvent, @Nonnull FluidStack solution, @Nullable ItemStack precipitate){
		this(name, ItemStack.EMPTY, ItemStack.EMPTY, solute, true, solvent, solution, precipitate);	
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

	public FluidStack getSolution(){
		if(this.solution != null) return this.solution.copy();
		return null;
	}

	public ItemStack getPrecipitate(){
		if(!this.precipitate.isEmpty()) return this.precipitate.copy();
		return ItemStack.EMPTY;
	}

}