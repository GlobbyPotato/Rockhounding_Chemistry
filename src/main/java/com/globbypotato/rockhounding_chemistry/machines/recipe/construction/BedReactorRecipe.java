package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BedReactorRecipe {
	
	private String name;
	private FluidStack input_1, input_2, input_3, input_4, output;
	private ItemStack catalyst;

	public BedReactorRecipe(String name, FluidStack input_1, FluidStack input_2, FluidStack input_3, FluidStack input_4, FluidStack output, ItemStack catalyst){
		this.name = name;
		this.input_1 = input_1;
		this.input_2 = input_2;
		this.input_3 = input_3;
		this.input_4 = input_4;
		this.output = output;
		this.catalyst = catalyst;
	}

	public String getRecipeName(){
		return this.name;
	}

	public FluidStack getInput1(){
		if(this.input_1 != null) return this.input_1.copy();
		return null;
	}

	public FluidStack getInput2(){
		if(this.input_2 != null) return this.input_2.copy();
		return null;
	}

	public FluidStack getInput3(){
		if(this.input_3 != null) return this.input_3.copy();
		return null;
	}

	public FluidStack getInput4(){
		if(this.input_4 != null) return this.input_4.copy();
		return null;
	}

	public FluidStack getOutput(){
		if(this.output != null) return this.output.copy();
		return null;
	}

	public ItemStack getCatalyst(){
		if(!this.catalyst.isEmpty()) return this.catalyst.copy();
		return ItemStack.EMPTY;
	}

}