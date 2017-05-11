package com.globbypotato.rockhounding_chemistry.utils;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProbabilityStack {

	private ItemStack stack;
	private int probability;
	
	public ProbabilityStack(ItemStack stack, int probability){
		this.stack = stack;
		this.probability = probability;
	}
	
	public ProbabilityStack(Item item, int meta, int probability){
		this(new ItemStack(item,1,meta),probability);
	}
	
	public ProbabilityStack(Item item, int probability){
		this(new ItemStack(item),probability);
	}
	
	public ProbabilityStack(Block block, int meta, int probability){
		this(new ItemStack(block,1,meta),probability);
	}
	
	public ItemStack getStack(){
		return this.stack.copy();
	}
	
	public int getProbability(){
		return this.probability;
	}
	
	public static ItemStack calculateProbability(ArrayList<ProbabilityStack> stacks){
		int totalProbability = 0;
		Random r = new Random();
		for(ProbabilityStack stack: stacks){
			totalProbability += stack.getProbability();
		}
		int rando = r.nextInt(totalProbability);
		int trackingProb = 0;
		for(ProbabilityStack stack: stacks){
			trackingProb += stack.getProbability();
			if(trackingProb >= rando) return stack.getStack();
		}
		return null;
	}
}
