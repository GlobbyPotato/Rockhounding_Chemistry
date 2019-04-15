package com.globbypotato.rockhounding_chemistry.compat.jei.gas_purifier;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasPurifier;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class GasPurifierCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIGasPurifier.TEXTURE_JEI;
	private String uid;

	public GasPurifierCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 88, 99), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		GasPurifierWrapper wrapper = (GasPurifierWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;
		int MSLAG_SLOT = 2;
		int ASLAG_SLOT = 3;

		guiFluidStacks.init(INPUT_SLOT, true,  1, 37, 16, 34, 1000, false, null);
		guiFluidStacks.init(OUTPUT_SLOT, false,  71, 1, 16, 34, 1000, false, null);
		guiItemStacks.init(MSLAG_SLOT, true, 31, 81);
		guiItemStacks.init(ASLAG_SLOT, true, 49, 81);

		guiFluidStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(MSLAG_SLOT, wrapper.getMainSlags());
		guiItemStacks.set(ASLAG_SLOT, wrapper.getAltSlags());
	}

}