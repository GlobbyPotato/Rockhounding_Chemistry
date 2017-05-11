package com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class AnalyzerRecipeHandler implements IRecipeHandler<AnalyzerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<AnalyzerRecipeWrapper> getRecipeClass() {
		return AnalyzerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.ANALYZER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull AnalyzerRecipeWrapper recipe) {
		return RHRecipeUID.ANALYZER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull AnalyzerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull AnalyzerRecipeWrapper wrapper) {
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