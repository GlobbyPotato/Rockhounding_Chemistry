package com.globbypotato.rockhounding_chemistry.compat.jei.petrographer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class PetroRecipeHandler implements IRecipeHandler<PetroRecipeWrapper> {

	@Nonnull
	@Override
	public Class<PetroRecipeWrapper> getRecipeClass() {
		return PetroRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.PETROGRAPHER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull PetroRecipeWrapper recipe) {
		return RHRecipeUID.PETROGRAPHER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull PetroRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull PetroRecipeWrapper wrapper) {
		MineralAnalyzerRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null) {
			return false;
		}

		if (recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}