package com.globbypotato.rockhounding_chemistry.compat.jei.gas_expander;

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

public class GasExpanderWrapper extends RHRecipeWrapper<GasCondenserRecipe>{

	public GasExpanderWrapper(@Nonnull GasCondenserRecipe recipe) {
		super(recipe);
	}

	public static List<GasExpanderWrapper> getRecipes() {
		List<GasExpanderWrapper> recipes = new ArrayList<>();
		for (GasCondenserRecipe recipe : GasCondenserRecipes.gas_condenser_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new GasExpanderWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getInputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.FLUID, getInputs());
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
	}

}