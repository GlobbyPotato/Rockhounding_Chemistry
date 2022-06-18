package com.globbypotato.rockhounding_chemistry.compat.jei.heat_exchanger;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIHeatExchangerController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class HeatExchangerCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIHeatExchangerController.TEXTURE_JEI;
	private String uid;

	public HeatExchangerCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 108, 66), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		HeatExchangerWrapper wrapper = (HeatExchangerWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;

		guiFluidStacks.init(INPUT_SLOT, true,  1, 17, 16, 34, 1000, false, null);
		guiFluidStacks.init(OUTPUT_SLOT, false,  91, 31, 16, 34, 1000, false, null);

		guiFluidStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

}