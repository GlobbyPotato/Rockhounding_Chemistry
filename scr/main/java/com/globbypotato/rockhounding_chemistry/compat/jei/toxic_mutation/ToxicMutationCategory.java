package com.globbypotato.rockhounding_chemistry.compat.jei.toxic_mutation;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class ToxicMutationCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guitoxicmutationjei.png");

	private String uid;

	public ToxicMutationCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 86, 66), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		ToxicMutationWrapper wrapper = (ToxicMutationWrapper)recipeWrapper;

		int INPUT_SLOT = 0;
		int OUTPUT_SLOT = 1;
		int TOXIC_SLOT = 2;

		guiItemStacks.init(INPUT_SLOT, true, 0, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 33, 39);
		guiFluidStacks.init(TOXIC_SLOT, true,  69, 1, 16, 16, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiFluidStacks.set(TOXIC_SLOT, BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 1000));
	}
}