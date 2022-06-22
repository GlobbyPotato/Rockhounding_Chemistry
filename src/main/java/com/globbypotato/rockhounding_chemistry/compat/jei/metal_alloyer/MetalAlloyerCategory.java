package com.globbypotato.rockhounding_chemistry.compat.jei.metal_alloyer;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSpeeds;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MetalAlloyerCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIMetalAlloyerController.TEXTURE_JEI;
	private String uid;

	public MetalAlloyerCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 102, 49), "jei." + uid + ".name");
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
		MetalAlloyerWrapper wrapper = (MetalAlloyerWrapper) recipeWrapper;

		int INGREDIENT_SLOT = 0;
		int PATTERN_SLOT = 1;
		int OUTPUT_SLOT = 2;
		int INGR_SLOT = 3;
		int WATER_SLOT = 4;
		int CYLINDER_SLOT = 5;

		guiItemStacks.init(CYLINDER_SLOT, true, 0, 0);
		guiItemStacks.init(INGREDIENT_SLOT, false, 28, 0);
		guiItemStacks.init(PATTERN_SLOT, true, 56, 0);
		guiItemStacks.init(OUTPUT_SLOT, false, 84, 0);
		guiItemStacks.init(INGR_SLOT, true, 0, 24);
		guiFluidStacks.init(WATER_SLOT, false, 57, 32, 34, 16, 1000, false, null);

		ItemStack recipeList = new ItemStack(ModItems.SPEED_ITEMS, 1, EnumSpeeds.BASE.ordinal());
		recipeList.setTagCompound(new NBTTagCompound());
		recipeList.getTagCompound().setString("Title", TextFormatting.GRAY + "Alloy: " + TextFormatting.DARK_GREEN + wrapper.getOutputs().get(0).getDisplayName());
		
		NBTTagList quantityList = new NBTTagList();
		for(int i = 0; i < wrapper.getInputs().size(); i++){
			NBTTagCompound tagdusts = new NBTTagCompound();
			tagdusts.setString("Ingr" + i, wrapper.getInputs().get(i) + ": " + TextFormatting.YELLOW + wrapper.getQuantities().get(i) + " ppc");
			quantityList.appendTag(tagdusts);
		}
		recipeList.getTagCompound().setTag("DustList", quantityList);

		guiItemStacks.set(INGREDIENT_SLOT, recipeList);
		guiItemStacks.set(PATTERN_SLOT, wrapper.getMiscs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(INGR_SLOT, wrapper.getStackedInputs());
		guiFluidStacks.set(WATER_SLOT, new FluidStack(FluidRegistry.WATER, 1000));
		guiItemStacks.set(CYLINDER_SLOT, BaseRecipes.graduated_cylinder);
	}

}