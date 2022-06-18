package com.globbypotato.rockhounding_chemistry.compat.jei.orbiter_exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumProbes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.OrbiterRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.OrbiterRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class OrbiterWrapper extends RHRecipeWrapper<OrbiterRecipe>{

	public OrbiterWrapper(@Nonnull OrbiterRecipe recipe) {
		super(recipe);
	}

	public static List<OrbiterWrapper> getRecipes() {
		List<OrbiterWrapper> recipes = new ArrayList<>();
		for (OrbiterRecipe recipe : OrbiterRecipes.exp_recipes) {
			if(recipe.getInput() != null){
				recipes.add(new OrbiterWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getSolvents() {
		List<FluidStack> outputs = new ArrayList<FluidStack>();
		outputs.add(BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 1000));
		outputs.add(BaseRecipes.getFluid(EnumFluid.TOXIC_SLUDGE, 1000));
		return outputs;
	}

	@Nonnull
	public List<FluidStack> getInputs(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<ItemStack> getProbes() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(int x = 0; x < EnumProbes.size(); x++) {
			stacks.add(new ItemStack(ModItems.PROBE_ITEMS, 1, x));
		}
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.FLUID, getSolvents());
		ingredients.setOutputs(VanillaTypes.FLUID, getInputs());
		ingredients.setInputs(VanillaTypes.ITEM, getProbes());

	}

}