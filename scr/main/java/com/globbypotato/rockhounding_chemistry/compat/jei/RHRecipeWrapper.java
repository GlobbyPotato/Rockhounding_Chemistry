package com.globbypotato.rockhounding_chemistry.compat.jei;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class RHRecipeWrapper<R> extends BlankRecipeWrapper {

	@Nonnull
	private final R recipe;

	public RHRecipeWrapper(@Nonnull R recipe) {
		this.recipe = recipe;
	}

	@Nonnull
	public R getRecipe() {
		return recipe;
	}
}