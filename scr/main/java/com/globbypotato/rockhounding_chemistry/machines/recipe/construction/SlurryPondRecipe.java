package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SlurryPondRecipe {

	private ItemStack input;
	private FluidStack bath, output;
	private String oredict;
	private boolean type;

	public SlurryPondRecipe(ItemStack input, String oredict, boolean type, FluidStack bath, FluidStack output){
		this.input = input;
		this.bath = bath;
		this.output = output;
		this.oredict = oredict;
		this.type = type;
	}

	public SlurryPondRecipe(ItemStack input, FluidStack bath, FluidStack output){
		this(input, "", false, bath, output);
	}

	public SlurryPondRecipe(String oredict, FluidStack bath, FluidStack output){
		this(ItemStack.EMPTY, oredict, true, bath, output);
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public FluidStack getBath(){
		if(this.bath != null) return this.bath.copy();
		return null;
	}

	public FluidStack getOutput(){
		if(this.output != null) return this.output.copy();
		return null;
	}

}