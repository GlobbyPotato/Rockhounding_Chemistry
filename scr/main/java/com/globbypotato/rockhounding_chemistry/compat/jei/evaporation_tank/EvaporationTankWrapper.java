package com.globbypotato.rockhounding_chemistry.compat.jei.evaporation_tank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.machines.recipe.EvaporationTankRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.EvaporationTankRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
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
		stacks.add(new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.POOR_RAW_SALT.ordinal()));
		stacks.add(new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.RAW_SALT.ordinal()));
		return stacks;
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
		ingredients.setOutputs(VanillaTypes.ITEM, getSalt());
	}

}