package com.globbypotato.rockhounding_chemistry.compat.jei.gan;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GanRecipeCategory extends RHRecipeCategory {
	private static final int INPUTS[] = new int[]{0,1,2,3,4,5,6,7,8,9};

	public static ResourceLocation guiTexture = new ResourceLocation(Reference.MODID + ":textures/gui/guigan.png");

	public GanRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 15, 109, 130), "jei.guigan.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.GAN;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		GanRecipeWrapper wrapper = (GanRecipeWrapper) recipeWrapper;

		guiItemStacks.init(INPUTS[0], true, 27, 108);
		guiItemStacks.init(INPUTS[1], true, 3,  108);
		guiItemStacks.init(INPUTS[2], true, 3,  90);
		guiItemStacks.init(INPUTS[3], true, 51, 108);
		guiItemStacks.init(INPUTS[4], true, 3,  57);
		guiItemStacks.init(INPUTS[5], true, 51, 57);
		guiItemStacks.init(INPUTS[6], true, 27, 57);
		guiItemStacks.init(INPUTS[7], true, 27, 39);
		guiItemStacks.init(INPUTS[8], true, 27, 21);
		guiItemStacks.init(INPUTS[9], true, 27, 3);
		guiItemStacks.set(INPUTS[0], new ItemStack(ModBlocks.ganController));
		guiItemStacks.set(INPUTS[1], BaseRecipes.gan(2));
		guiItemStacks.set(INPUTS[2], BaseRecipes.gan(4));
		guiItemStacks.set(INPUTS[3], BaseRecipes.gan(0));
		guiItemStacks.set(INPUTS[4], BaseRecipes.gan(3));
		guiItemStacks.set(INPUTS[5], BaseRecipes.gan(1));
		guiItemStacks.set(INPUTS[6], BaseRecipes.gan(5));
		guiItemStacks.set(INPUTS[7], BaseRecipes.gan(5));
		guiItemStacks.set(INPUTS[8], BaseRecipes.gan(5));
		guiItemStacks.set(INPUTS[9], BaseRecipes.gan(15));

	}
}