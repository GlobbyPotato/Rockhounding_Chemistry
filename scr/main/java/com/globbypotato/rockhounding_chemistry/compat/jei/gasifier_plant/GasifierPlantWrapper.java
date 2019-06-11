package com.globbypotato.rockhounding_chemistry.compat.jei.gasifier_plant;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasifierPlantWrapper extends RHRecipeWrapper<GasifierPlantRecipe>{

	public GasifierPlantWrapper(@Nonnull GasifierPlantRecipe recipe) {
		super(recipe);
	}

	public static List<GasifierPlantWrapper> getRecipes() {
		List<GasifierPlantWrapper> recipes = new ArrayList<>();
		for (GasifierPlantRecipe recipe : GasifierPlantRecipes.gasifier_plant_recipes) {
			if(recipe.getInput() != null && recipe.getReactant() != null){
				recipes.add(new GasifierPlantWrapper(recipe));
			}
		}
		return recipes;
	}

	@Nonnull
	public List<FluidStack> getSlurries(){
		return Collections.singletonList(getRecipe().getInput());
	}

	@Nonnull
	public List<FluidStack> getReactants(){
		return Collections.singletonList(getRecipe().getReactant());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	public List<ItemStack> getMainSlags() {
		return Collections.singletonList(getRecipe().getMainSlag());
	}

	public List<ItemStack> getAltSlags() {
		return Collections.singletonList(getRecipe().getAltSlag());
	}

	public List<Integer> getTemperatures() {
		return Collections.singletonList(getRecipe().getTemperature());
	}

	public List<ItemStack> getUtils() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.REFRACTORY_CASING.ordinal()));
		return stacks;
	}

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    	String voltString = "N/A";
    	if(getRecipe().getTemperature() > 0){
    		voltString = String.valueOf(getRecipe().getTemperature()) + "K";
    	}
    	GlStateManager.pushMatrix();
   	 	GlStateManager.scale(1, 1, 1);
    	minecraft.fontRenderer.drawString(voltString, 90, 20, Color.red.getRGB());
		GlStateManager.popMatrix();
    }

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, getUtils());
		ingredients.setInputLists(VanillaTypes.FLUID, Arrays.asList(getSlurries(), getReactants()));
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
		ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(getMainSlags(), getAltSlags()));
	}

}