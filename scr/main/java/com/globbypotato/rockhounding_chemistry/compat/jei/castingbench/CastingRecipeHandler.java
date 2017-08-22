package com.globbypotato.rockhounding_chemistry.compat.jei.castingbench;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.CastingRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CastingRecipeHandler implements IRecipeHandler<CastingRecipeWrapper> {

	@Nonnull
	@Override
	public Class<CastingRecipeWrapper> getRecipeClass() {
		return CastingRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.CASTING;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull CastingRecipeWrapper recipe) {
		return RHRecipeUID.CASTING;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull CastingRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull CastingRecipeWrapper wrapper) {
		CastingRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null || recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}