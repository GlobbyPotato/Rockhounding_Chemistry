package com.globbypotato.rockhounding_chemistry.compat.jei.bed_reactor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.BedReactorRecipe;
import com.google.common.base.Strings;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BedReactorWrapper extends RHRecipeWrapper<BedReactorRecipe>{

	public BedReactorWrapper(@Nonnull BedReactorRecipe recipe) {
		super(recipe);
	}

	public static List<BedReactorWrapper> getRecipes() {
		List<BedReactorWrapper> recipes = new ArrayList<>();
		for (BedReactorRecipe recipe : BedReactorRecipes.bed_reactor_recipes) {
			if(recipe.getInput1() != null){
				recipes.add(new BedReactorWrapper(recipe));
			}
		}
		return recipes;
	}

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    	String altString = "N/A";
		if(!Strings.isNullOrEmpty(getRecipe().getRecipeName())){
    		altString = getRecipe().getRecipeName();
    	}else{
    		altString = getRecipe().getOutput().getLocalizedName();
    	}
    	GlStateManager.pushMatrix();
   	 	GlStateManager.scale(0.5, 0.5, 1);
    	minecraft.fontRenderer.drawString(altString, 170, 96, Color.RED.getRGB());
		GlStateManager.popMatrix();
    }

	@Nonnull
	public List<FluidStack> getInputs1(){
		return Collections.singletonList(getRecipe().getInput1());
	}

	public List<FluidStack> getInputs2(){
		return Collections.singletonList(getRecipe().getInput2());
	}

	public List<FluidStack> getInputs3(){
		return Collections.singletonList(getRecipe().getInput3());
	}

	public List<FluidStack> getInputs4(){
		return Collections.singletonList(getRecipe().getInput4());
	}

	@Nonnull
	public List<ItemStack> getCatalysts(){
		return Collections.singletonList(getRecipe().getCatalyst());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.FLUID, Arrays.asList(getInputs1(), getInputs2(), getInputs3(), getInputs4()));
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getCatalysts()));
	}

}