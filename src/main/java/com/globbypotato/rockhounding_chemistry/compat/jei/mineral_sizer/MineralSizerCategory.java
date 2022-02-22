package com.globbypotato.rockhounding_chemistry.compat.jei.mineral_sizer;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerController;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class MineralSizerCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIMineralSizerController.TEXTURE_JEI;

	private String uid;

	public MineralSizerCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 130, 79), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		MineralSizerWrapper wrapper = (MineralSizerWrapper)recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;
		int[] GEAR_SLOTS = new int[8];
		
		for(int gear = 2; gear < GEAR_SLOTS.length + 2; gear++){
			guiItemStacks.init(gear, true, (16*(gear-2)), 27);
		}
		guiItemStacks.init(INPUT_SLOT, true, 0, 0);

		guiItemStacks.init(OUTPUT_SLOT, false, 112, 54);

		for(int gear = 2; gear < GEAR_SLOTS.length + 2; gear++){
			guiItemStacks.set(gear, wrapper.getGears());
		}
		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}

}