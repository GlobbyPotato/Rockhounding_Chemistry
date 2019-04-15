package com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_gases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PollutantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantGasRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.FluidStack;

public class PollutantGasWrapper extends RHRecipeWrapper<PollutantGasRecipe>{

	public PollutantGasWrapper(@Nonnull PollutantGasRecipe recipe) {
		super(recipe);
	}

	public static List<PollutantGasWrapper> getRecipes() {
		List<PollutantGasWrapper> recipes = new ArrayList<>();
		for (PollutantGasRecipe recipe : PollutantRecipes.pollutant_gas_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new PollutantGasWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getInputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(FluidStack.class, getInputs());
	}

}