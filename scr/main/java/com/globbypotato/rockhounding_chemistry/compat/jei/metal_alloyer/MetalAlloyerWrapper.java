package com.globbypotato.rockhounding_chemistry.compat.jei.metal_alloyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MetalAlloyerWrapper extends RHRecipeWrapper<MetalAlloyerRecipe>{

	public MetalAlloyerWrapper(@Nonnull MetalAlloyerRecipe recipe) {
		super(recipe);
	}

	public static List<MetalAlloyerWrapper> getRecipes() {
		List<MetalAlloyerWrapper> recipes = new ArrayList<>();
		for (MetalAlloyerRecipe recipe : MetalAlloyerRecipes.metal_alloyer_recipes) {
			if((!recipe.getInputs().isEmpty() && recipe.getInputs().size() > 0)){
				recipes.add(new MetalAlloyerWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<String> getInputs() {
		return getRecipe().getInputs();
	}

	@Nonnull
	public List<ItemStack> getStackedInputs() {
		ArrayList<ItemStack> inputIDs = new ArrayList<ItemStack>();
		for(int x = 0; x < getRecipe().getInputs().size(); x++){
			inputIDs.addAll(OreDictionary.getOres(getRecipe().getInputs().get(x)));
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

	@Nonnull
	public List<ItemStack> getMiscs() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.ingot_pattern);
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getMiscs(), getStackedInputs()));
		ingredients.setOutputs(VanillaTypes.ITEM, getOutputs());
	}

}