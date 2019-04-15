package com.globbypotato.rockhounding_chemistry.compat.jei.slurry_drum;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISlurryDrum;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class SlurryDrumCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UISlurryDrum.TEXTURE_JEI;
	private String uid;

	public SlurryDrumCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 54, 44), "jei." + uid + ".name");
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
		SlurryDrumWrapper wrapper = (SlurryDrumWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;

		guiItemStacks.init(INPUT_SLOT, true, 7, 9);
		guiFluidStacks.init(OUTPUT_SLOT, false,  37, 1, 16, 34, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

}