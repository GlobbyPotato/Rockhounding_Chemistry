package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;

public class ToxicMutationRecipe {

	private ItemStack input, output;
	private String oredict;
	private boolean type;

	public ToxicMutationRecipe(ItemStack input, String oredict, boolean type, ItemStack output){
		this.input = input;
		this.output = output;
		this.oredict = oredict;
		this.type = type;
	}

	public ToxicMutationRecipe(ItemStack input, ItemStack output){
		this(input, "", false, output);
	}

	public ToxicMutationRecipe(String oredict, ItemStack output){
		this(ItemStack.EMPTY, oredict, true, output);
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input;
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
	}

	public ItemStack getOutput(){
		if(!this.output.isEmpty()) return this.output;
		return ItemStack.EMPTY;
	}

}