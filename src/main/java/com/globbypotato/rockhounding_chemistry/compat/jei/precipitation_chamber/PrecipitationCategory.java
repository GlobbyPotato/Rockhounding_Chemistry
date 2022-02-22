package com.globbypotato.rockhounding_chemistry.compat.jei.precipitation_chamber;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPrecipitationController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class PrecipitationCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIPrecipitationController.TEXTURE_JEI;
	private String uid;

	public PrecipitationCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 120, 53), "jei." + uid + ".name");
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
		PrecipitationWrapper wrapper = (PrecipitationWrapper) recipeWrapper;

		int SOLUTE_SLOT = 0;
		int CATALYST_SLOT = 1;
		int SOLVENT_SLOT = 2;
		int SOLUTION_SLOT = 3;
		int BYPRODUCT_SLOT = 4;

		guiItemStacks.init(SOLUTE_SLOT, true, 23, 16);
		guiItemStacks.init(CATALYST_SLOT, true, 49, 16);
		guiItemStacks.init(BYPRODUCT_SLOT, false, 82, 27);
		guiFluidStacks.init(SOLVENT_SLOT, true,  1, 8, 16, 36, 1000, false, null);
		guiFluidStacks.init(SOLUTION_SLOT, false,  73, 8, 36, 16, 1000, false, null);

		if(!wrapper.getSolutes().isEmpty() && wrapper.getSolutes().get(0) != null){
			guiItemStacks.set(SOLUTE_SLOT, wrapper.getSolutes());
		}

		if(!wrapper.getCatalysts().isEmpty() && wrapper.getCatalysts().get(0) != null){
			guiItemStacks.set(CATALYST_SLOT, wrapper.getCatalysts());
		}

		if(!wrapper.getPrecipitates().isEmpty() && wrapper.getPrecipitates().get(0) != null){
			guiItemStacks.set(BYPRODUCT_SLOT, wrapper.getPrecipitates());
		}

		if(!wrapper.getSolvents().isEmpty() && wrapper.getSolvents().get(0) != null){
			guiFluidStacks.set(SOLVENT_SLOT, wrapper.getSolvents());
		}

		if(!wrapper.getSolutions().isEmpty() && wrapper.getSolutions().get(0) != null){
			guiFluidStacks.set(SOLUTION_SLOT, wrapper.getSolutions());
		}

	}

}