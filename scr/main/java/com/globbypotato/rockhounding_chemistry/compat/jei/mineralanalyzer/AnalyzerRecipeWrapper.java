package com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMachineEnergy;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class AnalyzerRecipeWrapper extends RHRecipeWrapper<MineralAnalyzerRecipe> {
	
	public AnalyzerRecipeWrapper(@Nonnull MineralAnalyzerRecipe recipe) {
		super(recipe);
	}
	
	public static List<AnalyzerRecipeWrapper> getRecipes() {
		List<AnalyzerRecipeWrapper> recipes = new ArrayList<>();
		for (MineralAnalyzerRecipe recipe : ModRecipes.analyzerRecipes) {
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
		stacks.add(new FluidStack(ModFluids.SULFURIC_ACID, TileEntityMachineEnergy.consumedSulf));
		stacks.add(new FluidStack(ModFluids.HYDROCHLORIC_ACID, TileEntityMachineEnergy.consumedChlo));
		stacks.add(new FluidStack(ModFluids.HYDROFLUORIC_ACID, TileEntityMachineEnergy.consumedFluo));
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
	}

}