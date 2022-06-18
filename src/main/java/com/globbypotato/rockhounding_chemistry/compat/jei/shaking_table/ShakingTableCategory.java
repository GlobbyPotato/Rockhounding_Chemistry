package com.globbypotato.rockhounding_chemistry.compat.jei.shaking_table;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIShredderController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class ShakingTableCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIShredderController.TEXTURE_JEI;
	private String uid;

	public ShakingTableCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 109, 53), "jei." + uid + ".name");
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
		ShakingTableWrapper wrapper = (ShakingTableWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;
		int SLAG_SLOT = 3;
		int COMPOUND_SLOT = 4;
		int LEACHATE_SLOT = 5;

		guiItemStacks.init(INPUT_SLOT, true, 0, 19);
		guiItemStacks.init(SLAG_SLOT, true, 68, 23);
		guiItemStacks.init(COMPOUND_SLOT, true, 68, 3);
		guiFluidStacks.init(LEACHATE_SLOT, false,  92, 9, 16, 34, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiFluidStacks.set(LEACHATE_SLOT, wrapper.getLeachate());
		guiItemStacks.set(SLAG_SLOT, wrapper.getSlags());
		guiItemStacks.set(COMPOUND_SLOT, wrapper.getCompounds());
	}

}