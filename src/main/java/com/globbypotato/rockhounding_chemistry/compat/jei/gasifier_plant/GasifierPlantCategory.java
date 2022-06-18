package com.globbypotato.rockhounding_chemistry.compat.jei.gasifier_plant;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasifierController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class GasifierPlantCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIGasifierController.TEXTURE_JEI;
	private String uid;

	public GasifierPlantCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 124, 82), "jei." + uid + ".name");
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
		GasifierPlantWrapper wrapper = (GasifierPlantWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int REACTANT_SLOT = 1;
		int OUTPUT_SLOT = 2;
		int SLAG_SLOT = 3;

		guiFluidStacks.init(INPUT_SLOT, true,  11, 2, 16, 34, 1000, false, null);
		guiFluidStacks.init(REACTANT_SLOT, true,  35, 18, 16, 34, 1000, false, null);
		guiFluidStacks.init(OUTPUT_SLOT, true,  101, 46, 16, 34, 1000, false, null);
		guiItemStacks.init(SLAG_SLOT, true, 0, 63);
		
		guiFluidStacks.set(INPUT_SLOT, wrapper.getSlurries());
		guiFluidStacks.set(REACTANT_SLOT, wrapper.getReactants());
		guiFluidStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(SLAG_SLOT, wrapper.getSlags());

	}

}