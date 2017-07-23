package com.globbypotato.rockhounding_chemistry.compat.jei.flametest;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class FlameRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;

	public static ResourceLocation guiTexture = new ResourceLocation(Reference.MODID + ":textures/gui/guiflametest.png");

	public FlameRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 75, 40), "jei.flametest.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.FLAME_TEST;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		FlameRecipeWrapper wrapper = (FlameRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT, true, 0, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 54, 20);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}
}