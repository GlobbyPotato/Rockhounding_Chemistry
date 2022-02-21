package com.globbypotato.rockhounding_chemistry.compat.jei.lab_oven;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabOvenController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class LabOvenCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UILabOvenController.TEXTURE_JEI;
	private String uid;

	public LabOvenCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 120, 62), "jei." + uid + ".name");
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
		LabOvenWrapper wrapper = (LabOvenWrapper) recipeWrapper;

		int SOLUTE_SLOT = 0;
		int CATALYST_SLOT = 1;
		int SOLVENT_SLOT = 2;
		int REAGENT_SLOT = 3;
		int SOLUTION_SLOT = 4;
		int BYPRODUCT_SLOT = 5;

		guiItemStacks.init(SOLUTE_SLOT, true, 51, 11);
		guiItemStacks.init(CATALYST_SLOT, true, 51, 33);
		guiFluidStacks.init(SOLVENT_SLOT, true,  1, 9,  34, 16, 1000, false, null);
		guiFluidStacks.init(REAGENT_SLOT, true,  1, 37, 34, 16, 1000, false, null);
		guiFluidStacks.init(SOLUTION_SLOT, false,   85, 9,  34, 16, 1000, false, null);
		guiFluidStacks.init(BYPRODUCT_SLOT, false,  85, 37, 34, 16, 1000, false, null);

		if(!wrapper.getSolutes().isEmpty() && wrapper.getSolutes().get(0) != null){
			guiItemStacks.set(SOLUTE_SLOT, wrapper.getSolutes());
		}

		if(!wrapper.getCatalysts().isEmpty() && wrapper.getCatalysts().get(0) != null){
			guiItemStacks.set(CATALYST_SLOT, wrapper.getCatalysts());
		}

		if(!wrapper.getSolvents().isEmpty() && wrapper.getSolvents().get(0) != null){
			guiFluidStacks.set(SOLVENT_SLOT, wrapper.getSolvents());
		}

		if(!wrapper.getReagents().isEmpty() && wrapper.getReagents().get(0) != null){
			guiFluidStacks.set(REAGENT_SLOT, wrapper.getReagents());
		}

		if(!wrapper.getSolutions().isEmpty() && wrapper.getSolutions().get(0) != null){
			guiFluidStacks.set(SOLUTION_SLOT, wrapper.getSolutions());
		}

		if(!wrapper.getByproducts().isEmpty() && wrapper.getByproducts().get(0) != null){
			guiFluidStacks.set(BYPRODUCT_SLOT, wrapper.getByproducts());
		}

	}

}