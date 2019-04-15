package com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_gases;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class PollutantGasCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = new ResourceLocation(Reference.MODID + ":textures/gui/jei/pollutantfluidjei.png");
	private String uid;

	public PollutantGasCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 51, 32), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		PollutantGasWrapper wrapper = (PollutantGasWrapper) recipeWrapper;

		int INPUT_SLOT = 0;

		guiFluidStacks.init(INPUT_SLOT, true, 34, 8);

		guiFluidStacks.set(INPUT_SLOT, wrapper.getInputs());
	}

}