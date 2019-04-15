package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RetentionVatRecipe {

	private FluidStack input;
	private List<ItemStack> output;
	private List<Float> gravity;

	public RetentionVatRecipe(FluidStack input, List<ItemStack> output, List<Float> gravity){
		this.input = input;
		this.output = output;
		this.gravity = gravity;
	}

	public RetentionVatRecipe(FluidStack input, ItemStack output, float gravity){
		this(input, fakeStack(output), fakeComm(gravity));
	}

	private static ArrayList<ItemStack> fakeStack(ItemStack output) {
		ArrayList<ItemStack> temp = new ArrayList<ItemStack>();
		temp.add(output);
		return temp;
	}

	private static ArrayList<Float> fakeComm(Float gravity) {
		ArrayList<Float> temp = new ArrayList<Float>();
		temp.add(gravity);
		return temp;
	}

	public FluidStack getInput(){
		if(this.input != null) return this.input;
		return null;
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

}