package com.globbypotato.rockhounding_chemistry.compat.jei.laboven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LabOvenRecipeWrapper extends RHRecipeWrapper<LabOvenRecipe> {
	
	public LabOvenRecipeWrapper(@Nonnull LabOvenRecipe recipe) {
		super(recipe);
	}

	public static List<LabOvenRecipeWrapper> getRecipes() {
		List<LabOvenRecipeWrapper> recipes = new ArrayList<>();
		for (LabOvenRecipe recipe : ModRecipes.labOvenRecipes) {
			recipes.add(new LabOvenRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs(){
		return Collections.singletonList(getRecipe().getSolute());
	}

	@Nonnull
	@Override
	public List<FluidStack> getFluidInputs(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	public List<FluidStack> getFluidReagents(){
		return Collections.singletonList(getRecipe().getReagent());
	}

	@Override
	public List<FluidStack> getFluidOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}