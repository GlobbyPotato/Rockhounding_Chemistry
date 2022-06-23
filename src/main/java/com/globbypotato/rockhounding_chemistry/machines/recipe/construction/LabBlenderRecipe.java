package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class LabBlenderRecipe {

	private List<String> input;
	private List<Integer> quantities;
	private ItemStack output;

	public LabBlenderRecipe(List<String> input, List<Integer> quantities, ItemStack output){
		this.input = input;
		this.quantities = quantities;
		this.output = output;
	}

	public ArrayList<String> getElements() {
		ArrayList<String> temp = new ArrayList<String>();
		if(this.input != null){
			temp.addAll(this.input);
		}
		return temp;
	}

	public ArrayList<Integer> getQuantities() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if(this.quantities != null){
			temp.addAll(this.quantities);
		}
		return temp;
	}

	public ItemStack getOutput(){
		if(!this.output.isEmpty()) return this.output.copy();
		return ItemStack.EMPTY;
	}

}