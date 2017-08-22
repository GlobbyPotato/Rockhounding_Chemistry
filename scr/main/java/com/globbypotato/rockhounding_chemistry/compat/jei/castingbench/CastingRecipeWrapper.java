package com.globbypotato.rockhounding_chemistry.compat.jei.castingbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.CastingRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class CastingRecipeWrapper extends RHRecipeWrapper<CastingRecipe> {

	public CastingRecipeWrapper(@Nonnull CastingRecipe recipe) {
		super(recipe);
	}

	public static List<CastingRecipeWrapper> getRecipes() {
		List<CastingRecipeWrapper> recipes = new ArrayList<>();
		for (CastingRecipe recipe : MachineRecipes.castingRecipes) {
			recipes.add(new CastingRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	public List<String> getInputs() {
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<Integer> getCastings() {
		return Collections.singletonList(getRecipe().getCasting());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}