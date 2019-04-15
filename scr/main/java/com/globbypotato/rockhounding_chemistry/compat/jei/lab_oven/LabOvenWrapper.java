package com.globbypotato.rockhounding_chemistry.compat.jei.lab_oven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class LabOvenWrapper extends RHRecipeWrapper<LabOvenRecipe>{

	public LabOvenWrapper(@Nonnull LabOvenRecipe recipe) {
		super(recipe);
	}

	public static List<LabOvenWrapper> getRecipes() {
		List<LabOvenWrapper> recipes = new ArrayList<>();
		for (LabOvenRecipe recipe : LabOvenRecipes.lab_oven_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new LabOvenWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(LabOvenRecipe recipe){
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

	public List<FluidStack> getReagents(){
		return Collections.singletonList(getRecipe().getReagent());
	}

	@Nonnull
	public List<FluidStack> getSolutions(){
		return Collections.singletonList(getRecipe().getSolution());
	}

	public List<FluidStack> getByproducts(){
		return Collections.singletonList(getRecipe().getByproduct());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Arrays.asList(getSolutes(), getCatalysts()));
		ingredients.setInputLists(FluidStack.class, Arrays.asList(getSolvents(),getReagents()));
		ingredients.setOutputLists(FluidStack.class, Arrays.asList(getSolutions(), getByproducts()));
	}

}