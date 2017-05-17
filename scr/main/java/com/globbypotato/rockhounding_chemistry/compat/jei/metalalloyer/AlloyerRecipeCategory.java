package com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class AlloyerRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT_1 = 0;
	private static final int INPUT_SLOT_2 = 1;
	private static final int INPUT_SLOT_3 = 2;
	private static final int INPUT_SLOT_4 = 3;
	private static final int INPUT_SLOT_5 = 4;
	private static final int INPUT_SLOT_6 = 5;
	private static final int PATTERN_SLOT = 6;
	private static final int OUTPUT_SLOT = 7;
	private static final int SCRAP_SLOT = 8;

	private final static ResourceLocation guiTexture = GuiMetalAlloyer.TEXTURE_REF;

	public AlloyerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 33, 51, 128, 57), "jei.alloyer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.ALLOYER;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		AlloyerRecipeWrapper wrapper = (AlloyerRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT_1, true, 19, 1);
		guiItemStacks.init(INPUT_SLOT_2, true, 37, 1);
		guiItemStacks.init(INPUT_SLOT_3, true, 55, 1);
		guiItemStacks.init(INPUT_SLOT_4, true, 73, 1);
		guiItemStacks.init(INPUT_SLOT_5, true, 91, 1);
		guiItemStacks.init(INPUT_SLOT_6, true, 109,1);
		guiItemStacks.init(PATTERN_SLOT, true, 64, 38);
		guiItemStacks.init(OUTPUT_SLOT, true, 42, 38);
		guiItemStacks.init(SCRAP_SLOT, false, 86, 38);

		for(String dict : OreDictionary.getOreNames()){
			for (int x = 0; x < wrapper.getElements().size(); x++){
				if(wrapper.getElements().get(x).matches(dict)){
					if(OreDictionary.getOres(dict).size() > 0){
						ItemStack ingr = OreDictionary.getOres(dict).get(0);
						ingr.stackSize = wrapper.getQuantities().get(x);
						guiItemStacks.set(x, ingr);
					}
				}
			}
		}

		guiItemStacks.set(PATTERN_SLOT, new ItemStack(ModItems.ingotPattern));
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(SCRAP_SLOT, wrapper.getScraps());
	}
}