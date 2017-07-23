package com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ExtractorRecipeWrapper extends RHRecipeWrapper<ChemicalExtractorRecipe> {
	
	public ExtractorRecipeWrapper(@Nonnull ChemicalExtractorRecipe recipe) {
		super(recipe);
	}

	public static List<ExtractorRecipeWrapper> getRecipes() {
		List<ExtractorRecipeWrapper> recipes = new ArrayList<>();
		for (ChemicalExtractorRecipe recipe : MachineRecipes.extractorRecipes) {
			recipes.add(new ExtractorRecipeWrapper(recipe));
		}
		return recipes;
	}

	@Nonnull
	public List<ItemStack> getInputs() {
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<String> getElements() {
		return getRecipe().getElements();
	}

	@Nonnull
	public List<Integer> getQuantities() {
		return getRecipe().getQuantities();
	}

	@Override
	public List<FluidStack> getFluidInputs(){
		ArrayList<FluidStack> stacks = new ArrayList<FluidStack>();
		stacks.add(new FluidStack(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID), ModConfig.consumedNitr));
		stacks.add(new FluidStack(EnumFluid.pickFluid(EnumFluid.PHOSPHORIC_ACID), ModConfig.consumedPhos));
		stacks.add(new FluidStack(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE), ModConfig.consumedCyan));
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {}

}