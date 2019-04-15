package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;

public class PullingCrucibleRecipe {

	private ItemStack input, output, dopant;
	private String oredict1, oredict2;
	private boolean type1, type2;

	public PullingCrucibleRecipe(ItemStack input, String oredict1, boolean type1, ItemStack dopant, String oredict2, boolean type2, ItemStack output){
		this.input = input;
		this.dopant = dopant;
		this.output = output;
		this.oredict1 = oredict1;
		this.type1 = type1;
		this.oredict2 = oredict2;
		this.type2 = type2;
	}

	public PullingCrucibleRecipe(ItemStack input, ItemStack dopant, ItemStack output){
		this(input, "", false, dopant, "", false, output);
	}
	public PullingCrucibleRecipe(String input, ItemStack dopant, ItemStack output){
		this(ItemStack.EMPTY, input, true, dopant, "", false, output);
	}
	public PullingCrucibleRecipe(ItemStack input, String dopant, ItemStack output){
		this(input, "", false, ItemStack.EMPTY, dopant, true, output);
	}
	public PullingCrucibleRecipe(String input, String dopant, ItemStack output){
		this(ItemStack.EMPTY, input, true, ItemStack.EMPTY, dopant, true, output);
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict1(){
		return this.oredict1;
	}

	public boolean getType1(){
		return this.type1;
	}

	public ItemStack getDopant(){
		if(!this.dopant.isEmpty()) return this.dopant.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict2(){
		return this.oredict2;
	}

	public boolean getType2(){
		return this.type2;
	}

	public ItemStack getOutput(){
		if(!this.output.isEmpty()) return this.output.copy();
		return ItemStack.EMPTY;
	}

}