package com.globbypotato.rockhounding_chemistry.compat.jei.lab_blender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class LabBlenderWrapper extends RHRecipeWrapper<LabBlenderRecipe>{

	public LabBlenderWrapper(@Nonnull LabBlenderRecipe recipe) {
		super(recipe);
	}

	public static List<LabBlenderWrapper> getRecipes() {
		List<LabBlenderWrapper> recipes = new ArrayList<>();
		for (LabBlenderRecipe recipe : LabBlenderRecipes.lab_blender_recipes) {
			if((!recipe.getElements().isEmpty() && recipe.getElements().size() > 0)){
				recipes.add(new LabBlenderWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<String> getElements() {
		return getRecipe().getElements();
	}

	@Nonnull
	public List<ItemStack> getStackedInputs() {
		ArrayList<ItemStack> inputIDs = new ArrayList<ItemStack>();
		for(int x = 0; x < getRecipe().getElements().size(); x++){
			inputIDs.addAll(OreDictionary.getOres(getRecipe().getElements().get(x)));
		}
		return inputIDs;
	}

	@Nonnull
	public List<Integer> getQuantities() {
		return getRecipe().getQuantities();
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getStackedInputs()));
		ingredients.setOutputs(VanillaTypes.ITEM, getOutputs());
	}

}