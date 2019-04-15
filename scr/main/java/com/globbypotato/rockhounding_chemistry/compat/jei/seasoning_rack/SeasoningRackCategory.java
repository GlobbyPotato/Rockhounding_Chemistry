package com.globbypotato.rockhounding_chemistry.compat.jei.seasoning_rack;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISeasoningRack;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class SeasoningRackCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UISeasoningRack.TEXTURE_JEI;

	private String uid;

	public SeasoningRackCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 72, 58), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		SeasoningRackWrapper wrapper = (SeasoningRackWrapper)recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;

		guiItemStacks.init(INPUT_SLOT, true, 0, 21);

		guiItemStacks.init(OUTPUT_SLOT, false, 54, 21);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}
}