package com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class AlloyerRecipeHandler implements IRecipeHandler<AlloyerRecipeWrapper> {

	@Nonnull
	@Override
	public Class<AlloyerRecipeWrapper> getRecipeClass() {
		return AlloyerRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.ALLOYER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull AlloyerRecipeWrapper recipe) {
		return RHRecipeUID.ALLOYER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull AlloyerRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull AlloyerRecipeWrapper wrapper) {
		MetalAlloyerRecipe recipe = wrapper.getRecipe();
		if (recipe.getDusts() == null) {
			return false;
		}

		if (recipe.getQuantities() == null) {
			return false;
		}

		if (recipe.getOutput() == null) {
			return false;
		}

		return true;
	}
}