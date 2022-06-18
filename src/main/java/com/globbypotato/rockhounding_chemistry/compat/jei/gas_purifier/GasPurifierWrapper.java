package com.globbypotato.rockhounding_chemistry.compat.jei.gas_purifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasPurifierRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

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

	@Nonnull
	public List<String> getElements() {
		ArrayList<String> allowedDicts = new ArrayList<String>();
		for(int x = 0; x < getRecipe().getElements().size(); x++) {
			if(!(MaterialCabinetRecipes.inhibited_material.contains(getRecipe().getElements().get(x).toLowerCase()))) {
				allowedDicts.add(getRecipe().getElements().get(x));
			}
		}
		return allowedDicts;
	}

	@Nonnull
	public List<Integer> getQuantities() {
		return getRecipe().getQuantities();
	}

	@Nonnull
	public List<ItemStack> getSlags() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(int x = 0; x < getElements().size(); x++){
			ArrayList<ItemStack> firstDict = new ArrayList<ItemStack>();
			String recipeDict = getElements().get(x);
			if(!OreDictionary.getOres(recipeDict).isEmpty()){
				firstDict.addAll(OreDictionary.getOres(recipeDict));
				if(!firstDict.isEmpty() && firstDict.size() > 0){
					stacks.addAll(firstDict);
				}
			}
		}
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.FLUID, getInputs());
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
		ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(getSlags()));
	}

}