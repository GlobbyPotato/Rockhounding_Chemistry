package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class LabBlenderRecipe {

	private List<ItemStack> input;
	private ItemStack output;

	public LabBlenderRecipe(List<ItemStack> input, ItemStack output){
		this.input = input;
		this.output = output;
	}

	public LabBlenderRecipe(ItemStack input, ItemStack output){
		this(fakeStack(input), output);
	}

	private static ArrayList<ItemStack> fakeStack(ItemStack input) {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		temp.add(input);
		return temp;
	}

	public ArrayList<ItemStack> getInputs() {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		if(this.input != null){
			temp.addAll(this.input);
		}
		return temp;
	}

	public ItemStack getOutput(){
		if(!this.output.isEmpty()) return this.output.copy();
		return ItemStack.EMPTY;
	}

}