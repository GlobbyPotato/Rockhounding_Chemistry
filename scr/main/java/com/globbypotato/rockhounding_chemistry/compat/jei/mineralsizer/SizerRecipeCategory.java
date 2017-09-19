package com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SizerRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int GEAR_SLOT = 3;

	private final static ResourceLocation guiTexture = GuiMineralSizer.TEXTURE_REF;

	public SizerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 32, 19, 67, 77), "jei.sizer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.SIZER;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		SizerRecipeWrapper wrapper = (SizerRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT, true, 27, 0);
		guiItemStacks.init(GEAR_SLOT, true, 0, 27);
		guiItemStacks.init(OUTPUT_SLOT, false, 27, 55);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(GEAR_SLOT, ToolUtils.gear);
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}
}