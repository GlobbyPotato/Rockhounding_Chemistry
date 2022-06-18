package com.globbypotato.rockhounding_chemistry.compat.jei.slurry_pond;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISlurryPondController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class SlurryPondCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UISlurryPondController.TEXTURE_JEI;
	private String uid;

	public SlurryPondCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 76, 44), "jei." + uid + ".name");
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
		SlurryPondWrapper wrapper = (SlurryPondWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int BATH_SLOT = 1;
		int OUTPUT_SLOT = 2;

		guiItemStacks.init(INPUT_SLOT, true, 29, 9);
		guiFluidStacks.init(BATH_SLOT, true,  1, 1, 16, 34, 1000, false, null);
		guiFluidStacks.init(OUTPUT_SLOT, false,  59, 1, 16, 34, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(BATH_SLOT, wrapper.getBaths());
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

}