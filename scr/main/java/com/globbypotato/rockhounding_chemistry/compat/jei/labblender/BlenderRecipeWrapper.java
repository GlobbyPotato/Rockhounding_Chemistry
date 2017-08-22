package com.globbypotato.rockhounding_chemistry.compat.jei.labblender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class BlenderRecipeWrapper extends RHRecipeWrapper<LabBlenderRecipe> {
	
	public BlenderRecipeWrapper(@Nonnull LabBlenderRecipe recipe) {
		super(recipe);
	}

	public static List<BlenderRecipeWrapper> getRecipes() {
		List<BlenderRecipeWrapper> recipes = new ArrayList<>();
		for (LabBlenderRecipe recipe : MachineRecipes.blenderRecipes) {
			recipes.add(new BlenderRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	public List<ItemStack> getInputs() {
		return getRecipe().getInputs();
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}