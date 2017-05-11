package com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class AlloyerRecipeWrapper extends RHRecipeWrapper<MetalAlloyerRecipe> {
	
	public AlloyerRecipeWrapper(@Nonnull MetalAlloyerRecipe recipe) {
		super(recipe);
	}

	public static List<AlloyerRecipeWrapper> getRecipes() {
		List<AlloyerRecipeWrapper> recipes = new ArrayList<>();
		for (MetalAlloyerRecipe recipe : ModRecipes.alloyerRecipes) {
			recipes.add(new AlloyerRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	public List<String> getElements() {
		return getRecipe().getDusts();
	}

	@Nonnull
	public List<Integer> getQuantities() {
		return getRecipe().getQuantities();
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	public List<ItemStack> getScraps() {
		return Collections.singletonList(getRecipe().getScrap());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}