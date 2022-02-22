package com.globbypotato.rockhounding_chemistry.compat.jei.bed_reactor;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITubularBedController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class BedReactorCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UITubularBedController.TEXTURE_JEI;
	private String uid;

	public BedReactorCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 128, 74), "jei." + uid + ".name");
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
		BedReactorWrapper wrapper = (BedReactorWrapper) recipeWrapper;

		int INPUT1_SLOT = 0;
		int INPUT2_SLOT = 1;
		int INPUT3_SLOT = 2;
		int INPUT4_SLOT = 3;
		int CATALYST_SLOT = 4;
		int OUTPUT_SLOT = 5;

		guiFluidStacks.init(INPUT1_SLOT, true,  14, 1, 16, 34, 1000, false, null);
		guiFluidStacks.set(INPUT1_SLOT, wrapper.getInputs1());

		if(!wrapper.getInputs2().isEmpty() && wrapper.getInputs2().get(0) != null){
			guiFluidStacks.init(INPUT2_SLOT, true,  33, 1, 16, 34, 1000, false, null);
			guiFluidStacks.set(INPUT2_SLOT, wrapper.getInputs2());
		}
		if(!wrapper.getInputs3().isEmpty() && wrapper.getInputs3().get(0) != null){
			guiFluidStacks.init(INPUT3_SLOT, true,  52, 1, 16, 34, 1000, false, null);
			guiFluidStacks.set(INPUT3_SLOT, wrapper.getInputs3());
		}
		if(!wrapper.getInputs4().isEmpty() && wrapper.getInputs4().get(0) != null){
			guiFluidStacks.init(INPUT4_SLOT, true,  71, 1, 16, 34, 1000, false, null);
			guiFluidStacks.set(INPUT4_SLOT, wrapper.getInputs4());
		}

		guiItemStacks.init(CATALYST_SLOT, true, 0,  46);
		guiItemStacks.set(CATALYST_SLOT, wrapper.getCatalysts());

		guiFluidStacks.init(OUTPUT_SLOT, false,  85, 54, 34, 16, 1000, false, null);
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

}