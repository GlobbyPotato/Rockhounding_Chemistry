package com.globbypotato.rockhounding_chemistry.compat.jei.petrographer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPetrographerTable;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PetroRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int PETRO_SLOT = 3;

	private final static ResourceLocation guiTexture = GuiPetrographerTable.TEXTURE_REF;

	public PetroRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 40, 20, 100, 60), "jei.petrographer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.PETROGRAPHER;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		PetroRecipeWrapper wrapper = (PetroRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT, true, 3, 39);
		guiItemStacks.init(OUTPUT_SLOT, false, 75, 39);
		guiItemStacks.init(PETRO_SLOT, true, 39, 3);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(PETRO_SLOT, new ItemStack(ModItems.petrographer));
	}
}