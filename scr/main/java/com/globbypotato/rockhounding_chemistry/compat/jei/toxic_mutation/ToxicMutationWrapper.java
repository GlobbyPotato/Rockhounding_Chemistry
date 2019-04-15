package com.globbypotato.rockhounding_chemistry.compat.jei.toxic_mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ToxicMutationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ToxicMutationRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ToxicMutationWrapper extends RHRecipeWrapper<ToxicMutationRecipe>{

	public ToxicMutationWrapper(@Nonnull ToxicMutationRecipe recipe) {
		super(recipe);
	}

	public static List<ToxicMutationWrapper> getRecipes() {
		List<ToxicMutationWrapper> recipes = new ArrayList<>();
		for (ToxicMutationRecipe recipe : ToxicMutationRecipes.toxic_mutation_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new ToxicMutationWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(ToxicMutationRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getOutput() != null;
	}

	public List<ItemStack> getInputs() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict()));
		}else{
			inputs.add(getRecipe().getInput());
		}
		return inputs;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}

}