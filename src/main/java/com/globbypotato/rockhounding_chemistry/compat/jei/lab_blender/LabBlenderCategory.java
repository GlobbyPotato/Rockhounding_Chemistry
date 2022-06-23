package com.globbypotato.rockhounding_chemistry.compat.jei.lab_blender;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabBlenderController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LabBlenderCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UILabBlenderController.TEXTURE_JEI;
	private String uid;

	public LabBlenderCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 154, 69), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		LabBlenderWrapper wrapper = (LabBlenderWrapper) recipeWrapper;

		int OUTPUT_SLOT = 8;

		for (int x = 0; x < 7; x++){
			guiItemStacks.init(x, true, 108 - (x * 18), 0);
		}
		guiItemStacks.init(OUTPUT_SLOT, false, 95, 51);

		for(int y = 0; y < wrapper.getElements().size(); y++){
			ItemStack setElement = wrapper.getStackedInputs().get(y).copy();
			setElement.setCount(wrapper.getQuantities().get(y));
			guiItemStacks.set(y, setElement);
		}
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());

	}

}