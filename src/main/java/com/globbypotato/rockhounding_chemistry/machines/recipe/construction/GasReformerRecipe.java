package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasReformerRecipe {

	private String name;
	private FluidStack inputA, inputB, output;
	private ItemStack catalyst;

	public GasReformerRecipe(String name, FluidStack inputA, FluidStack inputB, FluidStack output, ItemStack catalyst){
		this.name = name;
		this.inputA = inputA;
		this.inputB = inputB;
		this.output = output;
		this.catalyst = catalyst;
	}

	public GasReformerRecipe(FluidStack inputA, FluidStack inputB, FluidStack output, ItemStack catalyst){
		this("", inputA, inputB, output, catalyst);
	}

	public String getRecipeName(){
		return this.name;
	}

	public FluidStack getInputA(){
		if(this.inputA != null) return this.inputA.copy();
		return null;
	}

	public FluidStack getInputB(){
		if(this.inputB != null) return this.inputB.copy();
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