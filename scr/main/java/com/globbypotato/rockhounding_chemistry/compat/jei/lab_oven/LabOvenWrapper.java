package com.globbypotato.rockhounding_chemistry.compat.jei.lab_oven;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;
import com.google.common.base.Strings;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class LabOvenWrapper extends RHRecipeWrapper<LabOvenRecipe>{

	public LabOvenWrapper(@Nonnull LabOvenRecipe recipe) {
		super(recipe);
	}

	public static List<LabOvenWrapper> getRecipes() {
		List<LabOvenWrapper> recipes = new ArrayList<>();
		for (LabOvenRecipe recipe : LabOvenRecipes.lab_oven_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new LabOvenWrapper(recipe));
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
    		altString = getRecipe().getSolution().getLocalizedName();
    	}
    	GlStateManager.pushMatrix();
   	 	GlStateManager.scale(0.5, 0.5, 1);
    	minecraft.fontRenderer.drawString(altString, 0, 0, Color.RED.getRGB());

    	minecraft.fontRenderer.drawString("Primary", 22, 57, Color.BLUE.getRGB());
    	minecraft.fontRenderer.drawString("Secondary", 22, 113, Color.BLUE.getRGB());

    	minecraft.fontRenderer.drawString("Produce", 178, 57, Color.BLUE.getRGB());
    	minecraft.fontRenderer.drawString("Waste", 191, 113, Color.BLUE.getRGB());

    	GlStateManager.popMatrix();
    }

	private static boolean isValidRecipe(LabOvenRecipe recipe){
		return ((!recipe.getType() && !recipe.getSolute().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getSolvent() != null;
	}

	public List<ItemStack> getSolutes() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict()));
		}else{
			inputs.add(getRecipe().getSolute());
		}
		return inputs;
	}

	public List<ItemStack> getCatalysts() {
		return Collections.singletonList(getRecipe().getCatalyst());
	}

	@Nonnull
	public List<FluidStack> getSolvents(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	public List<FluidStack> getReagents(){
		return Collections.singletonList(getRecipe().getReagent());
	}

	@Nonnull
	public List<FluidStack> getSolutions(){
		return Collections.singletonList(getRecipe().getSolution());
	}

	public List<FluidStack> getByproducts(){
		return Collections.singletonList(getRecipe().getByproduct());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(getSolutes(), getCatalysts()));
		ingredients.setInputLists(VanillaTypes.FLUID, Arrays.asList(getSolvents(),getReagents()));
		ingredients.setOutputLists(VanillaTypes.FLUID, Arrays.asList(getSolutions(), getByproducts()));
	}

}