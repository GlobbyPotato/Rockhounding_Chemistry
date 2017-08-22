package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_core.utils.ProbabilityStack;

import net.minecraft.item.ItemStack;

public class MineralAnalyzerRecipe {

	private ItemStack input;
	private List<ItemStack> output;
	private List<Integer> probability;
	private ArrayList<ProbabilityStack> probabilityOutputs;

	public MineralAnalyzerRecipe(ItemStack input, ItemStack output){
		this(input, fakeStack(output), fakeProb(100));
	}

	public MineralAnalyzerRecipe(ItemStack input, List<ItemStack> output, List<Integer> probability){
		this.input = input;
		this.output = output;
		this.probability = probability;
		this.probabilityOutputs = new ArrayList<ProbabilityStack>();
		for(int i = 0; i < output.size(); i++){
			this.probabilityOutputs.add(new ProbabilityStack(new ItemStack(output.get(i).getItem(), 1, output.get(i).getItemDamage()), probability.get(i).intValue()));
		}
	}

	private static ArrayList<ItemStack> fakeStack(ItemStack output) {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		temp.add(output);
		return temp;
	}

	private static ArrayList<Integer> fakeProb( int prob) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(prob);
		return temp;
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