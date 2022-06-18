package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class PowderMixerRecipe {

	private ItemStack output;
	private List<String> elements;
	private List<Integer> quantities;

	public PowderMixerRecipe(List<String> element, List<Integer> quantity, ItemStack output){
		this.elements = element;
		this.quantities = quantity;
		this.output = output;
	}

	public ArrayList<String> getElements() {
		ArrayList<String> temp = new ArrayList<String>();
		if(this.elements != null){
			temp.addAll(this.elements);
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
		if(this.output != null) return this.output.copy();
		return null;
	}

}