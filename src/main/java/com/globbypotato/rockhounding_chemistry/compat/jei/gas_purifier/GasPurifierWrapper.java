package com.globbypotato.rockhounding_chemistry.compat.jei.gas_purifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasPurifierRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasPurifierWrapper extends RHRecipeWrapper<GasPurifierRecipe>{

	public GasPurifierWrapper(@Nonnull GasPurifierRecipe recipe) {
		super(recipe);
	}

	public static List<GasPurifierWrapper> getRecipes() {
		List<GasPurifierWrapper> recipes = new ArrayList<>();
		for (GasPurifierRecipe recipe : GasPurifierRecipes.gas_purifier_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new GasPurifierWrapper(recipe));
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

	public List<ItemStack> getMainSlags() {
		return Collections.singletonList(getRecipe().getMainSlag());
	}

	public List<ItemStack> getAltSlags() {
		return Collections.singletonList(getRecipe().getAltSlag());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.FLUID, getInputs());
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
		ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(getMainSlags(), getAltSlags()));
	}

}