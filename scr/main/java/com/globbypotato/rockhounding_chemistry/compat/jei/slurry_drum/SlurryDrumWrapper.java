package com.globbypotato.rockhounding_chemistry.compat.jei.slurry_drum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryDrumRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryDrumRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlurryDrumWrapper extends RHRecipeWrapper<SlurryDrumRecipe>{

	public SlurryDrumWrapper(@Nonnull SlurryDrumRecipe recipe) {
		super(recipe);
	}

	public static List<SlurryDrumWrapper> getRecipes() {
		List<SlurryDrumWrapper> recipes = new ArrayList<>();
		for (SlurryDrumRecipe recipe : SlurryDrumRecipes.slurry_drum_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new SlurryDrumWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(SlurryDrumRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
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

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setOutputs(FluidStack.class, getOutputs());
	}

}