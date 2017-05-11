package com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDepositionChamber;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class DepositionRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 0;
	private static final int SOLVENT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;

	private final static ResourceLocation guiTexture = GuiDepositionChamber.TEXTURE_JEI;
	
	public DepositionRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 121, 81), "jei.deposition_chamber.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.VAPOR_DEPOSITION;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		IDrawableStatic  tempBar;
		
		DepositionRecipeWrapper wrapper = (DepositionRecipeWrapper) recipeWrapper;

		guiItemStacks.init(INPUT_SLOT, true, 54, 17);
		guiFluidStacks.init(SOLVENT_SLOT, true,  100, 15, 20, 65, 10000, false, null);
		guiItemStacks.init(OUTPUT_SLOT, true, 54, 54);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(SOLVENT_SLOT, wrapper.getFluidInputs().get(0));
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs().get(0));
	}
}