package com.globbypotato.rockhounding_chemistry.compat.jei.air_compressor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.AirCompressorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.AirCompressorRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraftforge.fluids.FluidStack;

public class AirCompressorWrapper extends RHRecipeWrapper<AirCompressorRecipe>{

	public AirCompressorWrapper(@Nonnull AirCompressorRecipe recipe) {
		super(recipe);
	}

	public static List<AirCompressorWrapper> getRecipes() {
		List<AirCompressorWrapper> recipes = new ArrayList<>();
		for (AirCompressorRecipe recipe : AirCompressorRecipes.air_compressor_recipes) {
			if(recipe.getOutput() != null){
				recipes.add(new AirCompressorWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
	}

}