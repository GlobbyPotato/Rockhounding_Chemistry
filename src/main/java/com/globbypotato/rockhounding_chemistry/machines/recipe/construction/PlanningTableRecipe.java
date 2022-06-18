package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class PlanningTableRecipe {

	private List<ItemStack> input;
	private int order;

	public PlanningTableRecipe(int order, List<ItemStack> input){
		this.input = input;
		this.order = order;
	}

	public int getOrder(){
		return this.order;
	}

	public ArrayList<ItemStack> getInputs() {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		if(this.input != null){
			temp.addAll(this.input);
		}
		return temp;
	}
}