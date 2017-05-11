package com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiSaltSeasoner;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class SeasonerRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;

	private final static ResourceLocation guiTexture = GuiSaltSeasoner.TEXTURE_REF;

	public SeasonerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 40, 22, 90, 60), "jei.seasoner.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.SALT_SEASONER;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		SeasonerRecipeWrapper wrapper = (SeasonerRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT, true, 12, 19);
		guiItemStacks.init(OUTPUT_SLOT, false, 66, 19);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}
}