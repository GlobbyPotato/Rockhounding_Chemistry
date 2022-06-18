package com.globbypotato.rockhounding_chemistry.compat.jei.powder_mixer;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSpeeds;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPowderMixerController;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class PowderMixerCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIPowderMixerController.TEXTURE_JEI;
	private String uid;

	public PowderMixerCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 78, 53), "jei." + uid + ".name");
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		PowderMixerWrapper wrapper = (PowderMixerWrapper) recipeWrapper;

		int INGREDIENT_SLOT = 0;
		int OUTPUT_SLOT = 1;
		int INGR_SLOT = 2;

		guiItemStacks.init(INGREDIENT_SLOT, false, 0, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 49, 35);
		guiItemStacks.init(INGR_SLOT, true, 0, 28);

		ItemStack recipeList = new ItemStack(ModItems.SPEED_ITEMS, 1, EnumSpeeds.BASE.ordinal());
		recipeList.setTagCompound(new NBTTagCompound());
		recipeList.getTagCompound().setString("Title", TextFormatting.GRAY + "Powder: " + TextFormatting.DARK_GREEN + wrapper.getOutputs().get(0).getDisplayName());
		
		NBTTagList quantityList = new NBTTagList();
		for(int i = 0; i < wrapper.getElements().size(); i++){
			NBTTagCompound tagdusts = new NBTTagCompound();
			tagdusts.setString("Ingr" + i, wrapper.getElements().get(i) + ": " + TextFormatting.YELLOW + wrapper.getQuantities().get(i) + " ppc");
			quantityList.appendTag(tagdusts);
		}
		recipeList.getTagCompound().setTag("DustList", quantityList);

		guiItemStacks.set(INGREDIENT_SLOT, recipeList);
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(INGR_SLOT, wrapper.getStackedInputs());

	}

}