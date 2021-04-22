package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;

public class VatAgitatorRecipe {

	private ItemStack gear;

	public VatAgitatorRecipe(ItemStack gear){
		this.gear = gear;
	}

	public ItemStack getAgitator(){
		if(!this.gear.isEmpty()) return this.gear.copy();
		return ItemStack.EMPTY;
	}

}