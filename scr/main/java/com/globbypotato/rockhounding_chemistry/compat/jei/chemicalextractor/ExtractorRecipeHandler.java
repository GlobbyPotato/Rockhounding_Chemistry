package com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ExtractorRecipeHandler implements IRecipeHandler<ExtractorRecipeWrapper> {

	@Nonnull
	@Override
	public Class<ExtractorRecipeWrapper> getRecipeClass() {
		return ExtractorRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.EXTRACTOR;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull ExtractorRecipeWrapper recipe) {
		return RHRecipeUID.EXTRACTOR;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull ExtractorRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull ExtractorRecipeWrapper wrapper) {
		ChemicalExtractorRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null) {
			return false;
		}

		if (recipe.getElements() == null) {
			return false;
		}

		if (recipe.getQuantities() == null) {
			return false;
		}
		
		return true;
	}
}