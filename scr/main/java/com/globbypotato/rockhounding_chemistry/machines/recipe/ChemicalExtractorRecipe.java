package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ChemicalExtractorRecipe {
	private String category;
	private ItemStack input;
	private List<String> elements;
	private List<Integer> quantities;

	public ChemicalExtractorRecipe(String category, ItemStack input, List<String> element, List<Integer> quantity){
		this.input = input;
		this.elements = element;
		this.quantities = quantity;
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public ItemStack getInput() {
		return this.input.copy();
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
}