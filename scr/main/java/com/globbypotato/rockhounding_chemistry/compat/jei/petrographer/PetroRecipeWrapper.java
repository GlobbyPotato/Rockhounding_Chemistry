package com.globbypotato.rockhounding_chemistry.compat.jei.petrographer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

public class PetroRecipeWrapper extends RHRecipeWrapper<MineralAnalyzerRecipe> {
	
	public PetroRecipeWrapper(@Nonnull MineralAnalyzerRecipe recipe) {
		super(recipe);
	}
	
	public static List<PetroRecipeWrapper> getRecipes() {
		List<PetroRecipeWrapper> recipes = new ArrayList<>();
		for (MineralAnalyzerRecipe recipe : MachineRecipes.analyzerRecipes) {
			recipes.add(new PetroRecipeWrapper(recipe));
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
	public void getIngredients(IIngredients ingredients) {
		
	}

}