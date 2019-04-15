package com.globbypotato.rockhounding_chemistry.compat.jei.heat_exchanger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.HeatExchangerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.HeatExchangerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.FluidStack;

public class HeatExchangerWrapper extends RHRecipeWrapper<HeatExchangerRecipe>{

	public HeatExchangerWrapper(@Nonnull HeatExchangerRecipe recipe) {
		super(recipe);
	}

	public static List<HeatExchangerWrapper> getRecipes() {
		List<HeatExchangerWrapper> recipes = new ArrayList<>();
		for (HeatExchangerRecipe recipe : HeatExchangerRecipes.heat_exchanger_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new HeatExchangerWrapper(recipe));
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
		ingredients.setInputs(FluidStack.class, getInputs());
		ingredients.setOutputs(FluidStack.class, getOutputs());
	}

}