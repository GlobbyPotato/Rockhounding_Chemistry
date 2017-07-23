package com.globbypotato.rockhounding_chemistry.machines.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class DistillationTowerRecipe {

	private final ItemStack solute;
	private final FluidStack output;

	public DistillationTowerRecipe(ItemStack solute, FluidStack output){
		this.solute = solute;
		this.output = output;
	}

	public ItemStack getSolute(){
		return this.solute.copy();
	}

	public FluidStack getOutput(){
		return this.output;
	}
}