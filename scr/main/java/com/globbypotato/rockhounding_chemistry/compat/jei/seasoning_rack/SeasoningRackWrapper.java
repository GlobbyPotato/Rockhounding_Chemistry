package com.globbypotato.rockhounding_chemistry.compat.jei.seasoning_rack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SeasoningRackRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SeasoningRackRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SeasoningRackWrapper extends RHRecipeWrapper<SeasoningRackRecipe>{

	public SeasoningRackWrapper(@Nonnull SeasoningRackRecipe recipe) {
		super(recipe);
	}

	public static List<SeasoningRackWrapper> getRecipes() {
		List<SeasoningRackWrapper> recipes = new ArrayList<>();
		for (SeasoningRackRecipe recipe : SeasoningRackRecipes.seasoning_rack_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new SeasoningRackWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(SeasoningRackRecipe recipe){
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