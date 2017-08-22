package com.globbypotato.rockhounding_chemistry.machines.recipe;

import net.minecraft.item.ItemStack;

public class CastingRecipe {

	private String input;
	private ItemStack output;
	private int casting;

	public CastingRecipe(String input, ItemStack output, int casting){
		this.input = input;
		this.output = output;
		this.casting = casting;
	}

	public String getInput(){
		return this.input;
	}

	public ItemStack getOutput() {
		return this.output.copy();
	}

	public int getCasting() {
		return this.casting;
	}

}