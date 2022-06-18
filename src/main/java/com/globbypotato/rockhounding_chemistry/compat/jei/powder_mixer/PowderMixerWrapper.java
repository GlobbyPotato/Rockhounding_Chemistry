package com.globbypotato.rockhounding_chemistry.compat.jei.powder_mixer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PowderMixerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PowderMixerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PowderMixerWrapper extends RHRecipeWrapper<PowderMixerRecipe>{

	public PowderMixerWrapper(@Nonnull PowderMixerRecipe recipe) {
		super(recipe);
	}

	public static List<PowderMixerWrapper> getRecipes() {
		List<PowderMixerWrapper> recipes = new ArrayList<>();
		for (PowderMixerRecipe recipe : PowderMixerRecipes.powder_mixer_recipes) {
			if((!recipe.getElements().isEmpty() && recipe.getElements().size() > 0)){
				recipes.add(new PowderMixerWrapper(recipe));
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