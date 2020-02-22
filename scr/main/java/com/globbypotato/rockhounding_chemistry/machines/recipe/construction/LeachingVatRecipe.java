package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LeachingVatRecipe {

	private ItemStack input;
	private List<ItemStack> output;
	private List<Float> gravity;
	private FluidStack pulp;
	private String oredict;
	private boolean type;

	public LeachingVatRecipe(ItemStack input, String oredict, boolean type, List<ItemStack> output, List<Float> gravity, @Nullable FluidStack pulp){
		this.input = input;
		this.output = output;
		this.gravity = gravity;
		this.pulp = pulp;
		this.oredict = oredict;
		this.type = type;
	}

	public LeachingVatRecipe(ItemStack input, List<ItemStack> output, List<Float> gravity, @Nullable FluidStack pulp){
		this(input, "", false, output, gravity, pulp);
	}

	public LeachingVatRecipe(String oredict, List<ItemStack> output, List<Float> gravity, @Nullable FluidStack pulp){
		this(ItemStack.EMPTY, oredict, true, output, gravity, pulp);
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

	public ArrayList<Float> getGravity() {
		ArrayList<Float> temp = new ArrayList<Float>();
		temp.addAll(this.gravity);
		return temp;
	}

	public FluidStack getPulp(){
		if(this.pulp != null) return this.pulp.copy();
		return null;
	}

}