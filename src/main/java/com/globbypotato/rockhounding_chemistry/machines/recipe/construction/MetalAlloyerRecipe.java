package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class MetalAlloyerRecipe {

	private List<String> input;
	private List<Integer> quantity;
	private ItemStack output;

	public MetalAlloyerRecipe(List<String> input, List<Integer> quantity, ItemStack output){
		this.input = input;
		this.quantity = quantity;
		this.output = output;
	}

	public ArrayList<String> getInputs() {
		ArrayList<String> temp = new ArrayList<String>();
		if(this.input != null){
			temp.addAll(this.input);
		}
		return temp;
	}

	public ArrayList<Integer> getQuantities() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if(this.quantity != null){
			temp.addAll(this.quantity);
		}
		return temp;
	}

	public ItemStack getOutput(){
		if(!this.output.isEmpty()) return this.output.copy();
		return ItemStack.EMPTY;
	}

}