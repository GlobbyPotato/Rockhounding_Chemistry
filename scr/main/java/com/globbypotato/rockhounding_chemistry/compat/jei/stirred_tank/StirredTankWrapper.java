package com.globbypotato.rockhounding_chemistry.compat.jei.stirred_tank;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.StirredTankRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.StirredTankRecipe;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.FluidStack;

public class StirredTankWrapper extends RHRecipeWrapper<StirredTankRecipe>{

	public StirredTankWrapper(@Nonnull StirredTankRecipe recipe) {
		super(recipe);
	}

	public static List<StirredTankWrapper> getRecipes() {
		List<StirredTankWrapper> recipes = new ArrayList<>();
		for (StirredTankRecipe recipe : StirredTankRecipes.stirred_tank_recipes) {
			if(recipe.getReagent() != null && recipe.getSolvent() != null && recipe.getSolution() != null){
				recipes.add(new StirredTankWrapper(recipe));
			}
		}
		return recipes;
	}

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    	String voltString = "N/A";
    	if(getRecipe().getVoltage() > 0){
    		voltString = String.valueOf(getRecipe().getVoltage() * 10) + " RF";
    	}
    	GlStateManager.pushMatrix();
   	 	GlStateManager.scale(0.85, 0.85, 1);
    	minecraft.fontRenderer.drawString(voltString, 3, 5, Color.red.getRGB());
		GlStateManager.popMatrix();
    }

	@Nonnull
	public List<FluidStack> getSolvents(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	@Nonnull
	public List<FluidStack> getReagents(){
		return Collections.singletonList(getRecipe().getReagent());
	}

	@Nonnull
	public List<FluidStack> getSolutions(){
		return Collections.singletonList(getRecipe().getSolution());
	}

	public List<FluidStack> getFumes(){
		return Collections.singletonList(getRecipe().getFume());
	}
	
	public List<Integer> getVoltages(){
		return Collections.singletonList(getRecipe().getVoltage());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(FluidStack.class, Arrays.asList(getSolvents(), getReagents()));
		ingredients.setOutputLists(FluidStack.class, Arrays.asList(getSolutions(), getFumes()));
	}

}