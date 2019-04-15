package com.globbypotato.rockhounding_chemistry.compat.jei.precipitation_chamber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PrecipitationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PrecipitationRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class PrecipitationWrapper extends RHRecipeWrapper<PrecipitationRecipe>{

	public PrecipitationWrapper(@Nonnull PrecipitationRecipe recipe) {
		super(recipe);
	}

	public static List<PrecipitationWrapper> getRecipes() {
		List<PrecipitationWrapper> recipes = new ArrayList<>();
		for (PrecipitationRecipe recipe : PrecipitationRecipes.precipitation_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new PrecipitationWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(PrecipitationRecipe recipe){
		return ((!recipe.getType() && !recipe.getSolute().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getSolvent() != null;
	}

	public List<ItemStack> getSolutes() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict()));
		}else{
			inputs.add(getRecipe().getSolute());
		}
		return inputs;
	}

	public List<ItemStack> getCatalysts() {
		return Collections.singletonList(getRecipe().getCatalyst());
	}

	@Nonnull
	public List<FluidStack> getSolvents(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	@Nonnull
	public List<FluidStack> getSolutions(){
		return Collections.singletonList(getRecipe().getSolution());
	}

	@Nonnull
	public List<ItemStack> getPrecipitates(){
		return Collections.singletonList(getRecipe().getPrecipitate());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Arrays.asList(getSolutes(), getCatalysts()));
		ingredients.setInputs(FluidStack.class, getSolvents());
		ingredients.setOutputs(FluidStack.class, getSolutions());
		ingredients.setOutputs(ItemStack.class, getPrecipitates());
	}

}