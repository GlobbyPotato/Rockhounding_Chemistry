package com.globbypotato.rockhounding_chemistry.compat.jei.pulling_crucible;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPullingCrucibleBase;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class PullingCrucibleCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIPullingCrucibleBase.TEXTURE_JEI;
	private String uid;

	public PullingCrucibleCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 90, 78), "jei." + uid + ".name");
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
		PullingCrucibleWrapper wrapper = (PullingCrucibleWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int DOPANT_SLOT = 1;
		int OUTPUT_SLOT = 2;
		int ARGON_SLOT = 3;

		guiItemStacks.init(INPUT_SLOT, true, 36, 44);
		guiItemStacks.init(DOPANT_SLOT, true, 72, 21);
		guiItemStacks.init(OUTPUT_SLOT, false, 36, 7);
		guiFluidStacks.init(ARGON_SLOT, true,  1, 35, 16, 34, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(DOPANT_SLOT, wrapper.getDopants());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiFluidStacks.set(ARGON_SLOT, wrapper.getCarriers());

	}

}