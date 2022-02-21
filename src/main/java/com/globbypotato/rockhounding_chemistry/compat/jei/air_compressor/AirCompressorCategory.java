package com.globbypotato.rockhounding_chemistry.compat.jei.air_compressor;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIAirCompressor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class AirCompressorCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIAirCompressor.TEXTURE_JEI;
	private String uid;

	public AirCompressorCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 70, 51), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		AirCompressorWrapper wrapper = (AirCompressorWrapper) recipeWrapper;

		int OUTPUT_SLOT = 0;

		guiFluidStacks.init(OUTPUT_SLOT, false,  35, 34, 34, 16, 1000, false, null);

		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

}