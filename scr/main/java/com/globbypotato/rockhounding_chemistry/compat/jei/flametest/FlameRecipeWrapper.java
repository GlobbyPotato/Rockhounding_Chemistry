package com.globbypotato.rockhounding_chemistry.compat.jei.flametest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.FlameTestRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class FlameRecipeWrapper extends RHRecipeWrapper<FlameTestRecipe> {

	public FlameRecipeWrapper(@Nonnull FlameTestRecipe recipe) {
		super(recipe);
	}

	public static List<FlameRecipeWrapper> getRecipes() {
		List<FlameRecipeWrapper> recipes = new ArrayList<>();
		for (FlameTestRecipe recipe : MachineRecipes.flamesRecipes) {
			recipes.add(new FlameRecipeWrapper(recipe));
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