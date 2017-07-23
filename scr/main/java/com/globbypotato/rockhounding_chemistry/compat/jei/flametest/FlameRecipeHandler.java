package com.globbypotato.rockhounding_chemistry.compat.jei.flametest;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.FlameTestRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class FlameRecipeHandler implements IRecipeHandler<FlameRecipeWrapper> {

	@Nonnull
	@Override
	public Class<FlameRecipeWrapper> getRecipeClass() {
		return FlameRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.FLAME_TEST;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull FlameRecipeWrapper recipe) {
		return RHRecipeUID.FLAME_TEST;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull FlameRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull FlameRecipeWrapper wrapper) {
		FlameTestRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}