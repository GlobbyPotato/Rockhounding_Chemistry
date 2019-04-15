package com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_fluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PollutantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantFluidRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.FluidStack;

public class PollutantFluidWrapper extends RHRecipeWrapper<PollutantFluidRecipe>{

	public PollutantFluidWrapper(@Nonnull PollutantFluidRecipe recipe) {
		super(recipe);
	}

	public static List<PollutantFluidWrapper> getRecipes() {
		List<PollutantFluidWrapper> recipes = new ArrayList<>();
		for (PollutantFluidRecipe recipe : PollutantRecipes.pollutant_fluid_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new PollutantFluidWrapper(recipe));
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