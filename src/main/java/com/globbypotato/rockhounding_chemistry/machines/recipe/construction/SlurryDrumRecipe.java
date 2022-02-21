package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SlurryDrumRecipe {

	private FluidStack output;
	private ItemStack input;
	private String oredict;
	private boolean type;

	public SlurryDrumRecipe(ItemStack input, String oredict, boolean type, FluidStack output){
		this.input = input;
		this.output = output;
		this.oredict = oredict;
		this.type = type;
	}

	public SlurryDrumRecipe(ItemStack input, FluidStack output){
		this(input, "", false, output);
	}

	public SlurryDrumRecipe(String oredict, FluidStack output){
		this(ItemStack.EMPTY, oredict, true, output);
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
	}

	public FluidStack getOutput(){
		if(this.output != null) return this.output.copy();
		return null;
	}

}