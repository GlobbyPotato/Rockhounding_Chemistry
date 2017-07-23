package com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DistillationTowerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class DistillationRecipeHandler implements IRecipeHandler<DistillationRecipeWrapper> {

	@Nonnull
	@Override
	public Class<DistillationRecipeWrapper> getRecipeClass() {
		return DistillationRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.DISTILLATION_TOWER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull DistillationRecipeWrapper recipe) {
		return RHRecipeUID.DISTILLATION_TOWER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull DistillationRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull DistillationRecipeWrapper wrapper) {
		DistillationTowerRecipe recipe = wrapper.getRecipe();
		if (recipe.getSolute() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}