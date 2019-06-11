package com.globbypotato.rockhounding_chemistry.compat.jei.stirred_tank;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIStirredTankTop;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class StirredTankCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIStirredTankTop.TEXTURE_JEI;
	private String uid;

	public StirredTankCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 140, 92), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		StirredTankWrapper wrapper = (StirredTankWrapper) recipeWrapper;

		int SOLVENT_SLOT = 0;
		int REAGENT_SLOT = 1;
		int SOLUTION_SLOT = 2;
		int FUME_SLOT = 3;

		guiFluidStacks.init(SOLVENT_SLOT, true,  13, 38, 16, 34, 1000, false, null);
		guiFluidStacks.init(REAGENT_SLOT, true,  113, 38, 16, 34, 1000, false, null);
		guiFluidStacks.init(SOLUTION_SLOT, false,  54, 75, 34, 16, 1000, false, null);
		guiFluidStacks.init(FUME_SLOT, false,  105, 9, 34, 16, 1000, false, null);

		if(!wrapper.getSolvents().isEmpty() && wrapper.getSolvents().get(0) != null){
			guiFluidStacks.set(SOLVENT_SLOT, wrapper.getSolvents());
		}
		if(!wrapper.getReagents().isEmpty() && wrapper.getReagents().get(0) != null){
			guiFluidStacks.set(REAGENT_SLOT, wrapper.getReagents());
		}
		if(!wrapper.getSolutions().isEmpty() && wrapper.getSolutions().get(0) != null){
			guiFluidStacks.set(SOLUTION_SLOT, wrapper.getSolutions());
		}
		if(!wrapper.getFumes().isEmpty() && wrapper.getFumes().get(0) != null){
			guiFluidStacks.set(FUME_SLOT, wrapper.getFumes());
		}
	}

}