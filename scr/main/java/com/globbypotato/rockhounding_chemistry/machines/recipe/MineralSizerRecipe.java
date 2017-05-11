package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.utils.ProbabilityStack;

import net.minecraft.item.ItemStack;

public class MineralSizerRecipe {

	private ItemStack input;
	private List<ItemStack> output;
	private List<Integer> probability;
	private ArrayList<ProbabilityStack> probabilityOutputs;

	private static List<ItemStack> fakeStack = new ArrayList<ItemStack>();
	private static List<Integer> fakeProb = new ArrayList<Integer>();
	
	public MineralSizerRecipe(ItemStack input, ItemStack output){
		this(input, fakeStack, fakeProb);
		fakeProb.add(100);
		fakeStack.add(output);
	}

	public MineralSizerRecipe(ItemStack input, List<ItemStack> output, List<Integer> probability){
		this.input = input;
		this.output = output;
		this.probability = probability;
		this.probabilityOutputs = new ArrayList<ProbabilityStack>();
		for(int i = 0; i < output.size(); i++){
			this.probabilityOutputs.add(new ProbabilityStack(new ItemStack(output.get(i).getItem(), 1, output.get(i).getItemDamage()), probability.get(i).intValue()));
		}
	}

	public ItemStack getInput(){
		return this.input.copy();
	}

	public ArrayList<ItemStack> getOutput() {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		if(this.output != null){
			temp.addAll(this.output);
		}
		return temp;
	}

	public ArrayList<Integer> getProbability() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if(this.probability != null){
			temp.addAll(this.probability);
		}
		return temp;
	}

	public ArrayList<ProbabilityStack> getProbabilityStack(){
		return this.probabilityOutputs;
	}

}