package com.globbypotato.rockhounding_chemistry.compat.jei.labblender;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class BlenderRecipeHandler implements IRecipeHandler<BlenderRecipeWrapper> {

	@Nonnull
	@Override
	public Class<BlenderRecipeWrapper> getRecipeClass() {
		return BlenderRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return RHRecipeUID.BLENDER;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull BlenderRecipeWrapper recipe) {
		return RHRecipeUID.BLENDER;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull BlenderRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull BlenderRecipeWrapper wrapper) {
		LabBlenderRecipe recipe = wrapper.getRecipe();
		
		if (recipe.getInputs().size() <= 0 || recipe.getOutput() == null) {
			return false;
		}
		for(ItemStack stack : recipe.getInputs()){
			if(stack == null) return false;
		}
		return true;
	}
}