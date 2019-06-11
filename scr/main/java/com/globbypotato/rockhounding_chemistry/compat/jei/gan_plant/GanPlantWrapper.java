package com.globbypotato.rockhounding_chemistry.compat.jei.gan_plant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GanPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GanPlantRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraftforge.fluids.FluidStack;

public class GanPlantWrapper extends RHRecipeWrapper<GanPlantRecipe>{

	public GanPlantWrapper(@Nonnull GanPlantRecipe recipe) {
		super(recipe);
	}

	public static List<GanPlantWrapper> getRecipes() {
		List<GanPlantWrapper> recipes = new ArrayList<>();
		for (GanPlantRecipe recipe : GanPlantRecipes.gan_plant_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new GanPlantWrapper(recipe));
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
		return getRecipe().getOutputs();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.FLUID, getInputs());
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
	}

}