package com.globbypotato.rockhounding_chemistry.compat.jei.profiling_bench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ProfilingBenchRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ProfilingBenchRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ProfilingBenchWrapper extends RHRecipeWrapper<ProfilingBenchRecipe>{

	public ProfilingBenchWrapper(@Nonnull ProfilingBenchRecipe recipe) {
		super(recipe);
	}

	public static List<ProfilingBenchWrapper> getRecipes() {
		List<ProfilingBenchWrapper> recipes = new ArrayList<>();
		for (ProfilingBenchRecipe recipe : ProfilingBenchRecipes.profiling_bench_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new ProfilingBenchWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(ProfilingBenchRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getOutput() != null;
	}

	@Nonnull
	public List<ItemStack> getStackedInputs() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict()));
		}else{
			inputs.add(getRecipe().getInput());
		}
		return inputs;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<ItemStack> getCastings() {
		return Collections.singletonList(new ItemStack(ModItems.PATTERN_ITEMS, 1, getRecipe().getCasting()));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getStackedInputs(), getCastings()));
		ingredients.setOutputs(VanillaTypes.ITEM, getOutputs());
	}

}