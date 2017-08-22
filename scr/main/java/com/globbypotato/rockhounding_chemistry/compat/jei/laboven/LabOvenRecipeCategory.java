package com.globbypotato.rockhounding_chemistry.compat.jei.laboven;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class LabOvenRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 0;
	private static final int SOLVENT_SLOT = 1;
	private static final int REAGENT_SLOT = 2;
	private static final int OUTPUT_SLOT = 3;

	private final static ResourceLocation guiTexture = GuiLabOven.TEXTURE_REF;

	public LabOvenRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 60, 32, 109, 69), "jei.lab_oven.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.LAB_OVEN;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		LabOvenRecipeWrapper wrapper = (LabOvenRecipeWrapper) recipeWrapper;

		guiItemStacks.init(INPUT_SLOT, true, 1, 1);
		guiFluidStacks.init(SOLVENT_SLOT, true,  66, 2, 20, 65, 1000, false, null);
		guiFluidStacks.init(REAGENT_SLOT, true, 88, 2, 20, 65, 1000, false, null);
		guiFluidStacks.init(OUTPUT_SLOT,  false, 24, 2, 20, 65, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(SOLVENT_SLOT, wrapper.getFluidInputs().get(0));
		guiFluidStacks.set(REAGENT_SLOT, wrapper.getFluidReagents().get(0));
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getFluidOutputs().get(0));
	}
}