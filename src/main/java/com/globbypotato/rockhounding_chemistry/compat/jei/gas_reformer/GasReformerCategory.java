package com.globbypotato.rockhounding_chemistry.compat.jei.gas_reformer;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIReformerController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class GasReformerCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIReformerController.TEXTURE_JEI;
	private String uid;

	public GasReformerCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 148, 76), "jei." + uid + ".name");
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
		GasReformerWrapper wrapper = (GasReformerWrapper) recipeWrapper;

		int INPUTA_SLOT = 0;
		int INPUTB_SLOT = 1;
		int OUTPUT_SLOT = 2;
		int CATALYSTA_SLOT = 3;
		int CATALYSTB_SLOT = 4;
		int CATALYSTC_SLOT = 5;
		int CATALYSTD_SLOT = 6;

		guiFluidStacks.init(INPUTA_SLOT, true,   27, 12, 16, 34, 1000, false, null);
		guiFluidStacks.init(INPUTB_SLOT, true,  105, 40, 16, 34, 1000, false, null);
		guiFluidStacks.init(OUTPUT_SLOT, false,  66, 22, 16, 34, 1000, false, null);
		guiItemStacks.init(CATALYSTA_SLOT, true, 30,  56);
		guiItemStacks.init(CATALYSTB_SLOT, true, 100,  12);
		guiItemStacks.init(CATALYSTC_SLOT, true, 5,   15);
		guiItemStacks.init(CATALYSTD_SLOT, true, 125, 53);

		guiFluidStacks.set(INPUTA_SLOT, wrapper.getInputsA());
		guiFluidStacks.set(INPUTB_SLOT, wrapper.getInputsB());
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(CATALYSTA_SLOT, wrapper.getCatalysts());
		guiItemStacks.set(CATALYSTB_SLOT, wrapper.getCatalysts());
		guiItemStacks.set(CATALYSTC_SLOT, wrapper.getSpecificCatalysts());
		guiItemStacks.set(CATALYSTD_SLOT, wrapper.getSpecificCatalysts());
	}

}