package com.globbypotato.rockhounding_chemistry.compat.jei.lab_blender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

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
			if((!recipe.getInputs().isEmpty() && recipe.getInputs().size() > 0)){
				recipes.add(new LabBlenderWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<ItemStack> getInputs() {
		return getRecipe().getInputs();
	}

	@Nonnull
	public ArrayList<ArrayList<ItemStack>> getStackedInputs() {
		ArrayList<ArrayList<ItemStack>> jeiIDs = new ArrayList<ArrayList<ItemStack>>(getRecipe().getInputs().size());
		for(int k = 0; k < getInputs().size(); k++){
			int ingrSize = getInputs().get(k).getCount();
			ArrayList<Integer> inputIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(getInputs().get(k)));
			if(!inputIDs.isEmpty() && inputIDs.size() > 0){
				ArrayList<String> recipeIDs = new ArrayList<String>();
				for(Integer inputs: inputIDs){
					recipeIDs.add(OreDictionary.getOreName(inputs));
					if(recipeIDs.size() > 0){
						for(String dict : recipeIDs){
							ArrayList<ItemStack> ingrIDs = new ArrayList<ItemStack>();
							for(int x = 0; x < OreDictionary.getOres(dict).size(); x++){
								ItemStack ingrstack = OreDictionary.getOres(dict).get(x).copy();
								ingrstack.setCount(ingrSize);
								ingrIDs.add(ingrstack);
							}
							jeiIDs.add(k, ingrIDs);
						}
					}
				}
			}else{
				ArrayList<ItemStack> ingrIDs = new ArrayList<ItemStack>();
				ingrIDs.add(getInputs().get(k));
				jeiIDs.add(k, ingrIDs);
			}
		}
		return jeiIDs;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, getInputs());
		ingredients.setOutputs(VanillaTypes.ITEM, getOutputs());
	}

}