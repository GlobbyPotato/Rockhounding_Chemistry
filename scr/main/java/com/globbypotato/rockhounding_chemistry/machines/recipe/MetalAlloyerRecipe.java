package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class MetalAlloyerRecipe {
	private String display;
	private List<String> dusts;
	private List<Integer> quantities;
	private ItemStack output;
	private ItemStack scrap;

	public MetalAlloyerRecipe(String display, List<String> dust, List<Integer> quantity, ItemStack output, ItemStack scrap){
		this.display = display;
		this.dusts = dust;
		this.quantities = quantity;
		this.output = output;
		this.scrap = scrap;
	}

	public ArrayList<String> getDusts() {
		ArrayList<String> temp = new ArrayList<String>();
		if(dusts != null){
			temp.addAll(dusts);
		}
		return temp;
	}

	public ArrayList<Integer> getQuantities() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if(quantities != null){
			temp.addAll(quantities);
		}
		return temp;
	}

	public ItemStack getOutput() {
		if(output != null) return output.copy();
		else return null;
	}

	public ItemStack getScrap() {
		if(scrap != null) return scrap.copy();
		else return null;
	}

	public String getAlloyName(){
		return this.display;
	}

}