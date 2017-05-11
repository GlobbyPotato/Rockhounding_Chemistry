package com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class DepositionRecipeWrapper extends RHRecipeWrapper<DepositionChamberRecipe> {
	
	public DepositionRecipeWrapper(@Nonnull DepositionChamberRecipe recipe) {
		super(recipe);
	}

	public static List<DepositionRecipeWrapper> getRecipes() {
		List<DepositionRecipeWrapper> recipes = new ArrayList<>();
		for (DepositionChamberRecipe recipe : ModRecipes.depositionRecipes) {
			recipes.add(new DepositionRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Override
	public List<ItemStack> getInputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Override
	public List<FluidStack> getFluidInputs(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	@Override
	public List<ItemStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	public List<Integer> getTemperatures(){
		return Collections.singletonList(getRecipe().getTemperature());
	}

	public List<Integer> getPressures(){
		return Collections.singletonList(getRecipe().getPressure());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}