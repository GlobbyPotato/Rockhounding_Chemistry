package com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class SizerRecipeWrapper extends RHRecipeWrapper<MineralSizerRecipe> {
	
	public SizerRecipeWrapper(@Nonnull MineralSizerRecipe recipe) {
		super(recipe);
	}

	public static List<SizerRecipeWrapper> getRecipes() {
		List<SizerRecipeWrapper> recipes = new ArrayList<>();
		for (MineralSizerRecipe recipe : ModRecipes.sizerRecipes) {
			recipes.add(new SizerRecipeWrapper(recipe));
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
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		outputs.addAll(getRecipe().getOutput());
		return outputs;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {}

}