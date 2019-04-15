package com.globbypotato.rockhounding_chemistry.compat.jei.gas_reformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasReformerWrapper extends RHRecipeWrapper<GasReformerRecipe>{

	public GasReformerWrapper(@Nonnull GasReformerRecipe recipe) {
		super(recipe);
	}

	public static List<GasReformerWrapper> getRecipes() {
		List<GasReformerWrapper> recipes = new ArrayList<>();
		for (GasReformerRecipe recipe : GasReformerRecipes.gas_reformer_recipes) {
			if(recipe.getInputA() != null && recipe.getInputB() != null){
				recipes.add(new GasReformerWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getInputsA(){
		return Collections.singletonList(getRecipe().getInputA());
	}

	@Nonnull
	public List<FluidStack> getInputsB(){
		return Collections.singletonList(getRecipe().getInputB());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<ItemStack> getRecipeCatalysts(){
		return Collections.singletonList(getRecipe().getCatalyst());
	}

	@Nonnull
	public List<ItemStack> getCatalysts(){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.nl_catalyst);
		stacks.add(BaseRecipes.gr_catalyst);
		stacks.add(BaseRecipes.wg_catalyst);
		return stacks;
	}

	@Nonnull
	public List<ItemStack> getSpecificCatalysts(){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.addAll(getRecipeCatalysts());
		stacks.add(BaseRecipes.au_catalyst);
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(FluidStack.class, Arrays.asList(getInputsA(), getInputsB()));
		ingredients.setOutputs(FluidStack.class, getOutputs());
		ingredients.setInputLists(ItemStack.class, Arrays.asList(getCatalysts(),getSpecificCatalysts()));
	}

}