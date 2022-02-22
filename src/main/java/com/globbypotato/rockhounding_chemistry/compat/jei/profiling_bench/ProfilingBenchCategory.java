package com.globbypotato.rockhounding_chemistry.compat.jei.profiling_bench;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public class ProfilingBenchCategory extends RHRecipeCategory {

	public static final ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guiprofilingbench.png");
	private String uid;

	public ProfilingBenchCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(TEXTURE_JEI, 0, 0, 72, 36), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		ProfilingBenchWrapper wrapper = (ProfilingBenchWrapper)recipeWrapper;

		int INPUT_SLOT = 0;
		int CASTING_SLOT = 1;
		int OUTPUT_SLOT = 2;

		guiItemStacks.init(INPUT_SLOT, true, 0, 18);
		guiItemStacks.init(CASTING_SLOT, false, 27, 18);
		guiItemStacks.init(OUTPUT_SLOT, false, 54, 18);

		guiItemStacks.set(INPUT_SLOT, wrapper.getStackedInputs());
		guiItemStacks.set(CASTING_SLOT, wrapper.getCastings());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
	}
}