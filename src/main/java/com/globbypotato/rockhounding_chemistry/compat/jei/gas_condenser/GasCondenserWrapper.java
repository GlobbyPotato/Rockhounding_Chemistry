package com.globbypotato.rockhounding_chemistry.compat.jei.gas_condenser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasCondenserRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasCondenserRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraftforge.fluids.FluidStack;

public class GasCondenserWrapper extends RHRecipeWrapper<GasCondenserRecipe>{

	public GasCondenserWrapper(@Nonnull GasCondenserRecipe recipe) {
		super(recipe);
	}

	public static List<GasCondenserWrapper> getRecipes() {
		List<GasCondenserWrapper> recipes = new ArrayList<>();
		for (GasCondenserRecipe recipe : GasCondenserRecipes.gas_condenser_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new GasCondenserWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getInputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.FLUID, getInputs());
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
	}

}