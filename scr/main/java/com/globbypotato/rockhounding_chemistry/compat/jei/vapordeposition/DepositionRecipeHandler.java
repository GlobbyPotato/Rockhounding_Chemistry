package com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class DepositionRecipeHandler implements IRecipeHandler<DepositionRecipeWrapper> {

	@Nonnull
	@Override
	public Class<DepositionRecipeWrapper> getRecipeClass() {
		return DepositionRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.VAPOR_DEPOSITION;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull DepositionRecipeWrapper recipe) {
		return RHRecipeUID.VAPOR_DEPOSITION;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull DepositionRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull DepositionRecipeWrapper wrapper) {
		DepositionChamberRecipe recipe = wrapper.getRecipe();
		if (recipe.getInput() == null 
		|| recipe.getSolvent() == null 
		|| recipe.getOutput() == null) {
			return false;
		}
		return true;
	}
}