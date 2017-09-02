package com.globbypotato.rockhounding_chemistry.compat.jei.gan;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DistillationTowerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class GanRecipeHandler implements IRecipeHandler<GanRecipeWrapper> {

	@Nonnull
	@Override
	public Class<GanRecipeWrapper> getRecipeClass() {
		return GanRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.GAN;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull GanRecipeWrapper recipe) {
		return RHRecipeUID.GAN;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull GanRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull GanRecipeWrapper wrapper) {
		DistillationTowerRecipe recipe = wrapper.getRecipe();
		if (recipe.getSolute() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}