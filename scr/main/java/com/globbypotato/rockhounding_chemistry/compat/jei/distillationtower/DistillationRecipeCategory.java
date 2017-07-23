package com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiGanController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class DistillationRecipeCategory extends RHRecipeCategory {

	private static final int OUTPUT_SLOT = 0;

	private final static ResourceLocation guiTexture = GuiGanController.TEXTURE_JEI;

	public DistillationRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 72, 83), "jei.distillation_tower.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.DISTILLATION_TOWER;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		DistillationRecipeWrapper wrapper = (DistillationRecipeWrapper) recipeWrapper;

		guiFluidStacks.init(OUTPUT_SLOT,  false, 18, 4, 23, 60, 1000, false, null);

		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getFluidOutputs().get(0));
	}
}