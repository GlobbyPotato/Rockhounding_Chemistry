package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;

public class ProfilingBenchRecipe {

	private ItemStack input, output;
	private int casting;
	private boolean type;
	private String oredict;

	public ProfilingBenchRecipe(ItemStack input, String oredict, boolean type, ItemStack output, int casting){
		this.input = input;
		this.output = output;
		this.casting = casting;
		this.type = type;
		this.oredict = oredict;
	}

	public ProfilingBenchRecipe(ItemStack input, ItemStack output, int casting){
		this(input, "", false, output, casting);
	}

	public ProfilingBenchRecipe(String oredict, ItemStack output, int casting){
		this(ItemStack.EMPTY, oredict, true, output, casting);
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public ItemStack getOutput(){
		if(!this.output.isEmpty()) return this.output.copy();
		return ItemStack.EMPTY;
	}

	public int getCasting(){
		return this.casting;
	}
	
	public boolean getType(){
		return this.type;
	}

	public String getOredict(){
		return this.oredict;
	}

}