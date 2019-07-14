package com.globbypotato.rockhounding_chemistry.compat.jei.gas_reformer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.google.common.base.Strings;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GasReformerWrapper extends RHRecipeWrapper<GasReformerRecipe>{

	public GasReformerWrapper(@Nonnull GasReformerRecipe recipe) {
		super(recipe);
	}

	public static List<GasReformerWrapper> getRecipes() {
		List<GasReformerWrapper> recipes = new ArrayList<>();
		for (GasReformerRecipe recipe : GasReformerRecipes.gas_reformer_recipes) {
			if(recipe.getInputA() != null && recipe.getInputB() != null){
				recipes.add(new GasReformerWrapper(recipe));
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
    	minecraft.fontRenderer.drawString(altString, 0, 0, Color.RED.getRGB());
		GlStateManager.popMatrix();
    }

	@Nonnull
	public List<FluidStack> getInputsA(){
		return Collections.singletonList(getRecipe().getInputA());
	}

	@Nonnull
	public List<FluidStack> getInputsB(){
		return Collections.singletonList(getRecipe().getInputB());
	}

	@Nonnull
	public List<FluidStack> getOutputs(){
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<ItemStack> getRecipeCatalysts(){
		return Collections.singletonList(getRecipe().getCatalyst());
	}

	@Nonnull
	public List<ItemStack> getCatalysts(){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.nl_catalyst);
		stacks.add(BaseRecipes.gr_catalyst);
		stacks.add(BaseRecipes.wg_catalyst);
		return stacks;
	}

	@Nonnull
	public List<ItemStack> getSpecificCatalysts(){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.addAll(getRecipeCatalysts());
		stacks.add(BaseRecipes.au_catalyst);
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.FLUID, Arrays.asList(getInputsA(), getInputsB()));
		ingredients.setOutputs(VanillaTypes.FLUID, getOutputs());
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getCatalysts(),getSpecificCatalysts()));
	}

}