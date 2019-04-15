package com.globbypotato.rockhounding_chemistry.compat.jei.retention_vat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.RetentionVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RetentionVatWrapper extends RHRecipeWrapper<RetentionVatRecipe>{

	public RetentionVatWrapper(@Nonnull RetentionVatRecipe recipe) {
		super(recipe);
	}

	public static List<RetentionVatWrapper> getRecipes() {
		List<RetentionVatWrapper> recipes = new ArrayList<>();
		for (RetentionVatRecipe recipe : RetentionVatRecipes.retention_vat_recipes) {
			if(recipe.getInput() != null || recipe.getOutput().size() > 0){
				recipes.add(new RetentionVatWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getInputs() {
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		outputs.addAll(getRecipe().getOutput());
		return outputs;
	}

	@Nonnull
	public List<FluidStack> getSolvents() {
		List<FluidStack> outputs = new ArrayList<FluidStack>();
		outputs.add(BaseRecipes.getFluid(EnumFluid.HYDROFLUORIC_ACID, 1000));
		outputs.add(BaseRecipes.getFluid(EnumFluid.WATER_VAPOUR, 1000));
		outputs.add(new FluidStack(FluidRegistry.WATER, 1000));
		return outputs;
	}

	@Nonnull
	public List<FluidStack> getSolutions() {
		List<FluidStack> outputs = new ArrayList<FluidStack>();
		outputs.add(BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 1000));
		return outputs;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(FluidStack.class, Arrays.asList(getInputs(), getSolvents()));
		ingredients.setOutputs(ItemStack.class, getOutputs());
		ingredients.setOutputs(FluidStack.class, getSolutions());
	}

}