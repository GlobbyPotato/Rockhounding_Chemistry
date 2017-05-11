package com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class ExtractorRecipeWrapper extends RHRecipeWrapper<ChemicalExtractorRecipe> {
	
	public ExtractorRecipeWrapper(@Nonnull ChemicalExtractorRecipe recipe) {
		super(recipe);
	}

	public static List<ExtractorRecipeWrapper> getRecipes() {
		List<ExtractorRecipeWrapper> recipes = new ArrayList<>();
		for (ChemicalExtractorRecipe recipe : ModRecipes.extractorRecipes) {
			recipes.add(new ExtractorRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	public List<ItemStack> getInputs() {
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<String> getElements() {
		return getRecipe().getElements();
	}

	@Nonnull
	public List<Integer> getQuantities() {
		return getRecipe().getQuantities();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}