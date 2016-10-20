package com.globbypotato.rockhounding_chemistry.machines.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MineralSizerRecipe {

	private ItemStack input;
	private ItemStack output;
	
	public MineralSizerRecipe(Item input, ItemStack output){
		this(new ItemStack(input),output);
	}
	
	public MineralSizerRecipe(Item input, int inputMeta, Item output, int outputMeta){
		this(new ItemStack(input,1,inputMeta),new ItemStack(output,1,outputMeta));
	}
	
	public MineralSizerRecipe(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
	}

	
	public MineralSizerRecipe(Block inputBlock, ItemStack output) {
		this(new ItemStack(inputBlock), output);
	}

	public MineralSizerRecipe(Block input, int inputMeta, Item output, int outputMeta) {
		this(new ItemStack(input,1,inputMeta),new ItemStack(output,1,outputMeta));
	}

	public ItemStack getInput() {
		return input.copy();
	}


	public ItemStack getOutput() {
		if(output != null) return output.copy();
		else return null;
	}

	
	
}
