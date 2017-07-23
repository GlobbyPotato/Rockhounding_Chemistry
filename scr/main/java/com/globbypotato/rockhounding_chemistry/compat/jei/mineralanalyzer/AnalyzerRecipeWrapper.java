package com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class AnalyzerRecipeWrapper extends RHRecipeWrapper<MineralAnalyzerRecipe> {
	
	public AnalyzerRecipeWrapper(@Nonnull MineralAnalyzerRecipe recipe) {
		super(recipe);
	}
	
	public static List<AnalyzerRecipeWrapper> getRecipes() {
		List<AnalyzerRecipeWrapper> recipes = new ArrayList<>();
		for (MineralAnalyzerRecipe recipe : MachineRecipes.analyzerRecipes) {
			recipes.add(new AnalyzerRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	@Override
	public List<ItemStack> getInputs() {
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		outputs.addAll(getRecipe().getOutput());
		return outputs;
	}
	
	@Override
	public List<FluidStack> getFluidInputs(){
		ArrayList<FluidStack> stacks = new ArrayList<FluidStack>();
		stacks.add(new FluidStack(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID), ModConfig.consumedSulf));
		stacks.add(new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID), ModConfig.consumedChlo));
		stacks.add(new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROFLUORIC_ACID), ModConfig.consumedFluo));
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
	}

}