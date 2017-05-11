package com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SizerRecipeHandler implements IRecipeHandler<SizerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<SizerRecipeWrapper> getRecipeClass() {
		return SizerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.SIZER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull SizerRecipeWrapper recipe) {
		return RHRecipeUID.SIZER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull SizerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull SizerRecipeWrapper wrapper) {
		MineralSizerRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}