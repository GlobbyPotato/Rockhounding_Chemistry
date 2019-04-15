package com.globbypotato.rockhounding_chemistry.compat.jei.pulling_crucible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PullingCrucibleRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PullingCrucibleRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class PullingCrucibleWrapper extends RHRecipeWrapper<PullingCrucibleRecipe>{

	public PullingCrucibleWrapper(@Nonnull PullingCrucibleRecipe recipe) {
		super(recipe);
	}

	public static List<PullingCrucibleWrapper> getRecipes() {
		List<PullingCrucibleWrapper> recipes = new ArrayList<>();
		for (PullingCrucibleRecipe recipe : PullingCrucibleRecipes.pulling_crucible_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new PullingCrucibleWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(PullingCrucibleRecipe recipe){
		return ((!recipe.getType1() && !recipe.getInput().isEmpty()) || (recipe.getType1() && OreDictionary.getOres(recipe.getOredict1()).size() > 0))
			&& ((!recipe.getType2() && !recipe.getInput().isEmpty()) || (recipe.getType2() && OreDictionary.getOres(recipe.getOredict2()).size() > 0))
			&& recipe.getOutput() != null;
	}

	public List<ItemStack> getInputs() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType1()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict1()));
		}else{
			inputs.add(getRecipe().getInput());
		}
		return inputs;
	}

	public List<ItemStack> getDopants() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType2()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict2()));
		}else{
			inputs.add(getRecipe().getDopant());
		}
		return inputs;
	}

	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<FluidStack> getCarriers(){
		ArrayList<FluidStack> stacks = new ArrayList<FluidStack>();
		stacks.add(BaseRecipes.getFluid(EnumFluid.ARGON, 1000));
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Arrays.asList(getInputs(), getDopants()));
		ingredients.setInputLists(FluidStack.class, Arrays.asList(getCarriers()));
		ingredients.setOutputs(ItemStack.class, Arrays.asList(getOutputs()));
	}

}