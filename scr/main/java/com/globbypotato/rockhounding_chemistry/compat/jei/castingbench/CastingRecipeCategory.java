package com.globbypotato.rockhounding_chemistry.compat.jei.castingbench;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiCastingBench;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class CastingRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int CASTING_SLOT = 3;

	private final static ResourceLocation guiTexture = GuiCastingBench.TEXTURE_REF;

	public CastingRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 50, 30, 76, 20), "jei.casting_bench.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.CASTING;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		CastingRecipeWrapper wrapper = (CastingRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT, true, 2, 1);
		guiItemStacks.init(OUTPUT_SLOT, false, 56, 1);
		guiItemStacks.init(CASTING_SLOT, false, 29, 1);

		for(String dict : OreDictionary.getOreNames()){
			for (int x = 0; x < wrapper.getInputs().size(); x++){
				if(wrapper.getInputs().get(x).matches(dict)){
					if(OreDictionary.getOres(dict).size() > 0){
						ItemStack ingr = OreDictionary.getOres(dict).get(0);
						guiItemStacks.set(INPUT_SLOT, ingr);
					}
				}
			}
		}

		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(CASTING_SLOT, new ItemStack(ModItems.patternItems, 1, wrapper.getCastings().get(0)));
	}
}