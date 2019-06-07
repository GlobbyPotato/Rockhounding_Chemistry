package com.globbypotato.rockhounding_chemistry.compat.jei.deposition_chamber;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIDepositionChamberTop;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class DepositionChamberCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIDepositionChamberTop.TEXTURE_JEI;
	private String uid;

	public DepositionChamberCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 155, 88), "jei." + uid + ".name");
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
		DepositionChamberWrapper wrapper = (DepositionChamberWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int SOLVENT_SLOT = 1;
		int CARRIER_SLOT = 2;
		int OUTPUT_SLOT = 3;

		guiItemStacks.init(INPUT_SLOT, true, 51, 32);
		guiItemStacks.init(OUTPUT_SLOT, false, 51, 61);
		guiFluidStacks.init(SOLVENT_SLOT, true,  98, 46, 16, 34, 1000, false, null);
		guiFluidStacks.init(CARRIER_SLOT, true,  18, 1, 34, 16, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiFluidStacks.set(SOLVENT_SLOT, wrapper.getSolvents());
		guiFluidStacks.set(CARRIER_SLOT, wrapper.getCarriers());

	}

}