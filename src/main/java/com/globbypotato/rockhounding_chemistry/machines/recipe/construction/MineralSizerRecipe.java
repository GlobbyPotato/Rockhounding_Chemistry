package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class MineralSizerRecipe {

	private ItemStack input;
	private List<ItemStack> output;
	private List<Integer> comminution;
	private String oredict;
	private boolean type;

	public MineralSizerRecipe(ItemStack input, String oredict, boolean type, List<ItemStack> output, List<Integer> comminution){
		this.input = input;
		this.output = output;
		this.comminution = comminution;
		this.oredict = oredict;
		this.type = type;
	}

	public MineralSizerRecipe(ItemStack input, ItemStack output, int comminution){
		this(input, "", false, fakeStack(output), fakeComm(comminution));
	}

	public MineralSizerRecipe(ItemStack input, List<ItemStack> output, List<Integer> comminution){
		this(input, "", false, output, comminution);
	}

	public MineralSizerRecipe(String oredict, ItemStack output, int comminution){
		this(ItemStack.EMPTY, oredict, true, fakeStack(output), fakeComm(comminution));
	}

	public MineralSizerRecipe(String oredict, List<ItemStack> output, List<Integer> comminution){
		this(ItemStack.EMPTY, oredict, true, output, comminution);
	}

	private static ArrayList<ItemStack> fakeStack(ItemStack output) {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		temp.add(output);
		return temp;
	}

	private static ArrayList<Integer> fakeComm(Integer comminution) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(comminution);
		return temp;
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
	}

	public ArrayList<ItemStack> getOutput() {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		if(this.output != null){
			temp.addAll(this.output);
		}
		return temp;
	}

	public ArrayList<Integer> getComminution() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.addAll(this.comminution);
		return temp;
	}

}