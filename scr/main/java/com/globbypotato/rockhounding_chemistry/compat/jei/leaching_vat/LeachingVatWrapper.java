package com.globbypotato.rockhounding_chemistry.compat.jei.leaching_vat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LeachingVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class LeachingVatWrapper extends RHRecipeWrapper<LeachingVatRecipe>{

	public LeachingVatWrapper(@Nonnull LeachingVatRecipe recipe) {
		super(recipe);
	}

	public static List<LeachingVatWrapper> getRecipes() {
		List<LeachingVatWrapper> recipes = new ArrayList<>();
		for (LeachingVatRecipe recipe : LeachingVatRecipes.leaching_vat_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new LeachingVatWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(LeachingVatRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getOutput() != null
			&& recipe.getOutput().size() > 0;
	}

	public List<ItemStack> getInputs() {
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
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		outputs.addAll(getRecipe().getOutput());
		return outputs;
	}

	@Nonnull
	public List<FluidStack> getSolvents() {
		List<FluidStack> outputs = new ArrayList<FluidStack>();
		outputs.add(BaseRecipes.getFluid(EnumFluid.SODIUM_HYDROXIDE, 1000));
		outputs.add(BaseRecipes.getFluid(EnumFluid.HYDROCHLORIC_ACID, 1000));
		outputs.add(BaseRecipes.getFluid(EnumFluid.WATER_VAPOUR, 1000));
		outputs.add(new FluidStack(FluidRegistry.WATER, 1000));
		return outputs;
	}

	@Nonnull
	public List<FluidStack> getSolutions() {
		return Collections.singletonList(new FluidStack(getRecipe().getPulp().getFluid(), 1000));
	}

	@Nonnull
	public List<ItemStack> getCatalysts() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.slurry_agitator);
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getInputs(), getCatalysts()));
		ingredients.setOutputs(VanillaTypes.ITEM, getOutputs());
		ingredients.setInputs(VanillaTypes.FLUID, getSolvents());
		ingredients.setOutputs(VanillaTypes.FLUID, getSolutions());
	}

}