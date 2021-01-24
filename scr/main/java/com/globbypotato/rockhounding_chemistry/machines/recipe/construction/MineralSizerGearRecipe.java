package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;

public class MineralSizerGearRecipe {

	private ItemStack gear;

	public MineralSizerGearRecipe(ItemStack gear){
		this.gear = gear;
	}

	public ItemStack getGear(){
		if(!this.gear.isEmpty()) return this.gear.copy();
		return ItemStack.EMPTY;
	}

}