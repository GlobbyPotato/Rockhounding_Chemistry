package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ShakingTableRecipe {
	private ItemStack input, slag;
	private FluidStack leachate;
	private List<String> elements;
	private List<Integer> quantities;
	private String oredict;
	private boolean type;

	public ShakingTableRecipe(ItemStack input, String oredict, boolean type, ItemStack slag, List<String> element, List<Integer> quantity, FluidStack leachate){
		this.input = input;
		this.oredict = oredict;
		this.type = type;
		this.slag = slag;
		this.elements = element;
		this.quantities = quantity;
		this.leachate = leachate;
	}

	public ShakingTableRecipe(ItemStack input, ItemStack slag, List<String> element, List<Integer> quantity, FluidStack leachate){
		this(input, "", false, slag, element, quantity, leachate);
	}

	public ShakingTableRecipe(String oredict, ItemStack slag, List<String> element, List<Integer> quantity, FluidStack leachate){
		this(ItemStack.EMPTY, oredict, true, slag, element, quantity, leachate);
	}

	public ItemStack getInput() {
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
	}

	public ItemStack getSlag() {
		if(!this.slag.isEmpty()) return this.slag.copy();
		return ItemStack.EMPTY;
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
	
	public FluidStack getLeachate(){
		if(this.leachate != null) return this.leachate.copy();
		return null;
	}

}