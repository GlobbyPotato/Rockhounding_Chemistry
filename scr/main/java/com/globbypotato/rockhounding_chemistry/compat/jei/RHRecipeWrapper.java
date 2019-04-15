package com.globbypotato.rockhounding_chemistry.compat.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class RHRecipeWrapper<R> implements IRecipeWrapper {

	@Nonnull
	private final R recipe;

	public RHRecipeWrapper(@Nonnull R recipe) {
		this.recipe = recipe;
	}

	@Nonnull
	public R getRecipe() {
		return this.recipe;
	}

}