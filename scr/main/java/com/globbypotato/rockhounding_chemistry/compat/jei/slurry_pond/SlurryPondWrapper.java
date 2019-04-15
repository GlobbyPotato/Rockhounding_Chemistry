package com.globbypotato.rockhounding_chemistry.compat.jei.slurry_pond;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryPondRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryPondRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlurryPondWrapper extends RHRecipeWrapper<SlurryPondRecipe>{

	public SlurryPondWrapper(@Nonnull SlurryPondRecipe recipe) {
		super(recipe);
	}

	public static List<SlurryPondWrapper> getRecipes() {
		List<SlurryPondWrapper> recipes = new ArrayList<>();
		for (SlurryPondRecipe recipe : SlurryPondRecipes.slurry_pond_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new SlurryPondWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(SlurryPondRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getBath() != null 
			&& recipe.getOutput() != null;
	}

	@Nonnull
	public List<ItemStack> getInputs() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict()));
		}else{
			inputs.add(getRecipe().getInput());
		}
		return inputs;
	}

	public List<FluidStack> getBaths(){
		return Collections.singletonList(getRecipe().getBath());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setInputs(FluidStack.class, getBaths());
		ingredients.setOutputs(FluidStack.class, getOutputs());
	}

}