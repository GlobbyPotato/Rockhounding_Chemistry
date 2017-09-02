package com.globbypotato.rockhounding_chemistry.compat.jei.gan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DistillationTowerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GanRecipeWrapper extends RHRecipeWrapper<DistillationTowerRecipe> {
	
	public GanRecipeWrapper(@Nonnull DistillationTowerRecipe recipe) {
		super(recipe);
	}

	public static List<GanRecipeWrapper> getRecipes() {
		List<GanRecipeWrapper> recipes = new ArrayList<>();
		for (DistillationTowerRecipe recipe : MachineRecipes.distillationRecipes) {
			recipes.add(new GanRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs(){
		return Collections.singletonList(getRecipe().getSolute());
	}

	@Override
	public List<FluidStack> getFluidOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}