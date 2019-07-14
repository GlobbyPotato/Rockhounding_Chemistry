package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ChemicalExtractorRecipe {
	private String category;
	private ItemStack input;
	private List<String> elements;
	private List<Integer> quantities;
	private String oredict;
	private boolean type;

	public ChemicalExtractorRecipe(String category, ItemStack input, String oredict, boolean type, List<String> element, List<Integer> quantity){
		this.input = input;
		this.elements = element;
		this.quantities = quantity;
		this.category = category;
		this.oredict = oredict;
		this.type = type;
	}

	public ChemicalExtractorRecipe(String category, ItemStack input, List<String> element, List<Integer> quantity){
		this(category, input, "", false, element, quantity);
	}

	public ChemicalExtractorRecipe(String category, String oredict, List<String> element, List<Integer> quantity){
		this(category, ItemStack.EMPTY, oredict, true, element, quantity);
	}

	public String getCategory() {
		return this.category;
	}

	public ItemStack getInput() {
		if(!this.input.isEmpty()) return this.input;
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
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