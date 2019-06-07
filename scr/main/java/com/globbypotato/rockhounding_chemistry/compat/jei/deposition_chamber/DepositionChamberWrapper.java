package com.globbypotato.rockhounding_chemistry.compat.jei.deposition_chamber;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.google.common.base.Strings;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class DepositionChamberWrapper extends RHRecipeWrapper<DepositionChamberRecipe>{

	public DepositionChamberWrapper(@Nonnull DepositionChamberRecipe recipe) {
		super(recipe);
	}

	public static List<DepositionChamberWrapper> getRecipes() {
		List<DepositionChamberWrapper> recipes = new ArrayList<>();
		for (DepositionChamberRecipe recipe : DepositionChamberRecipes.deposition_chamber_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new DepositionChamberWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(DepositionChamberRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getSolvent() != null
			&& !recipe.getOutput().isEmpty();
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

	public List<ItemStack> getOutputs() {
		return Collections.singletonList(getRecipe().getOutput());
	}

	@Nonnull
	public List<FluidStack> getSolvents(){
		return Collections.singletonList(getRecipe().getSolvent());
	}

	@Nonnull
	public List<FluidStack> getCarriers(){
		return Collections.singletonList(getRecipe().getCarrier());
	}

	@Nonnull
	public List<Integer> getTemperatures(){
		return Collections.singletonList(getRecipe().getTemperature());
	}

	@Nonnull
	public List<Integer> getPressures() {
		return Collections.singletonList(getRecipe().getPressure());
	}

	public List<ItemStack> getUtils() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.casing_upd);
		stacks.add(BaseRecipes.insulation_upd);
		return stacks;
	}

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    	String tempString = "N/A";
    	if(getRecipe().getTemperature() > 0){
    		tempString = String.valueOf(getRecipe().getTemperature()) + "K";
    	}
    	String pressString = "N/A";
    	if(getRecipe().getPressure() > 0){
    		pressString = String.valueOf(getRecipe().getPressure()) + "uP";
    	}
    	String altString = "N/A";
		if(!Strings.isNullOrEmpty(getRecipe().getRecipeName())){
    		altString = getRecipe().getRecipeName();
    	}else{
    		altString = getRecipe().getOutput().getDisplayName();
    	}

    	GlStateManager.pushMatrix();
   	 	GlStateManager.scale(0.5, 0.5, 1);
    	minecraft.fontRenderer.drawString(altString, 0, 170, Color.red.getRGB());
		GlStateManager.popMatrix();

    	GlStateManager.pushMatrix();
   	 	GlStateManager.scale(0.5, 0.5, 1);
    	minecraft.fontRenderer.drawString(tempString, 0, 147, Color.blue.getRGB());
    	minecraft.fontRenderer.drawString(pressString, 247, 147, Color.blue.getRGB());
		GlStateManager.popMatrix();
    }

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Arrays.asList(getInputs(), getUtils()));
		ingredients.setInputLists(FluidStack.class, Arrays.asList(getSolvents(),getCarriers()));
		ingredients.setOutputs(ItemStack.class, Arrays.asList(getOutputs()));
	}

}