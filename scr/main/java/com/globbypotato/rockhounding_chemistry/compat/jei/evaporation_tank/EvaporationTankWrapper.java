package com.globbypotato.rockhounding_chemistry.compat.jei.evaporation_tank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.EvaporationTankRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.EvaporationTankRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class EvaporationTankWrapper extends RHRecipeWrapper<EvaporationTankRecipe>{

	public EvaporationTankWrapper(@Nonnull EvaporationTankRecipe recipe) {
		super(recipe);
	}

	public static List<EvaporationTankWrapper> getRecipes() {
		List<EvaporationTankWrapper> recipes = new ArrayList<>();
		for (EvaporationTankRecipe recipe : EvaporationTankRecipes.salt_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new EvaporationTankWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<ItemStack> getSalt(){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.poor_salt_block);
		stacks.add(BaseRecipes.raw_salt_block);
		return stacks;
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutputs(FluidStack.class, getOutputs());
		ingredients.setOutputs(ItemStack.class, getSalt());
	}

}