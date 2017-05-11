package com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SaltSeasonerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SeasonerRecipeHandler implements IRecipeHandler<SeasonerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<SeasonerRecipeWrapper> getRecipeClass() {
		return SeasonerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.SALT_SEASONER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull SeasonerRecipeWrapper recipe) {
		return RHRecipeUID.SALT_SEASONER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull SeasonerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull SeasonerRecipeWrapper wrapper) {
		SaltSeasonerRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}