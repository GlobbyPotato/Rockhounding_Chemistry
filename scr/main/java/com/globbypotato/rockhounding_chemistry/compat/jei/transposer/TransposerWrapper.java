package com.globbypotato.rockhounding_chemistry.compat.jei.transposer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.TransposerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.TransposerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.FluidStack;

public class TransposerWrapper extends RHRecipeWrapper<TransposerRecipe>{

	public TransposerWrapper(@Nonnull TransposerRecipe recipe) {
		super(recipe);
	}

	public static List<TransposerWrapper> getRecipes() {
		List<TransposerWrapper> recipes = new ArrayList<>();
		for (TransposerRecipe recipe : TransposerRecipes.transposer_recipes) {
			if(recipe.getInput() != null && recipe.getOutput() != null){
				recipes.add(new TransposerWrapper(recipe));
			}
		}
		return recipes;
	}

	public List<FluidStack> getInputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(FluidStack.class, getInputs());
		ingredients.setOutputs(FluidStack.class, getOutputs());
	}

}