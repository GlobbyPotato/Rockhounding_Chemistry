package com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SaltSeasonerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class SeasonerRecipeWrapper extends RHRecipeWrapper<SaltSeasonerRecipe> {
	
	public SeasonerRecipeWrapper(@Nonnull SaltSeasonerRecipe recipe) {
		super(recipe);
	}

	public static List<SeasonerRecipeWrapper> getRecipes() {
		List<SeasonerRecipeWrapper> recipes = new ArrayList<>();
		for (SaltSeasonerRecipe recipe : ModRecipes.seasonerRecipes) {
			recipes.add(new SeasonerRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}