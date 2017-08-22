package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class LabBlenderRecipe {

	private List<ItemStack> input;
	private ItemStack output;

	public LabBlenderRecipe(List<ItemStack> input, ItemStack output){
		this.input = input;
		this.output = output;
	}

	public LabBlenderRecipe(List<String> oredict, List<Integer> quantity, ItemStack output){
		this(fakeList(oredict, quantity), output);
	}

	private static List<ItemStack> fakeList(List<String> oredict, List<Integer> quantity) {
		List<ItemStack> temp = new ArrayList<ItemStack>();
		for(int x = 0; x < oredict.size(); x++){
			List<ItemStack> ores = OreDictionary.getOres(oredict.get(x));
			ItemStack entryStack = null;
			if(ores.size() > 0){
				entryStack = ores.get(0);
				entryStack.stackSize = quantity.get(x);
			}
			temp.add(entryStack);
		}
		return temp;
	}

	public ArrayList<ItemStack> getInputs() {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		if(input != null){
			temp.addAll(input);
		}
		return temp;
	}

	public ItemStack getOutput(){
		return this.output.copy();
	}

}