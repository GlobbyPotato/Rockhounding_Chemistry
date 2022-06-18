package com.globbypotato.rockhounding_chemistry.compat.jei.shaking_table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ShakingTableRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ShakingTableRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ShakingTableWrapper extends RHRecipeWrapper<ShakingTableRecipe>{

	public ShakingTableWrapper(@Nonnull ShakingTableRecipe recipe) {
		super(recipe);
	}

	public static List<ShakingTableWrapper> getRecipes() {
		List<ShakingTableWrapper> recipes = new ArrayList<>();
		for (ShakingTableRecipe recipe : ShakingTableRecipes.shaking_table_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new ShakingTableWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<ItemStack> getInputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<ItemStack> getSlags(){
		return Collections.singletonList(getRecipe().getSlag());
	}

	@Nonnull
	public List<FluidStack> getLeachate(){
		return Collections.singletonList(getRecipe().getLeachate());
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
	public List<ItemStack> getCompounds() {
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
		ingredients.setInputs(VanillaTypes.ITEM, getInputs());
		ingredients.setOutputs(VanillaTypes.ITEM, getSlags());
		ingredients.setOutputs(VanillaTypes.FLUID, getLeachate());
		ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(getCompounds()));
	}

}