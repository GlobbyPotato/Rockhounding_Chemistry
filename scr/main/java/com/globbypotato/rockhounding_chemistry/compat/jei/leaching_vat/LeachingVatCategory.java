package com.globbypotato.rockhounding_chemistry.compat.jei.leaching_vat;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILeachingVatController;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class LeachingVatCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UILeachingVatController.TEXTURE_JEI;
	private String uid;

	public LeachingVatCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 128, 63), "jei." + uid + ".name");
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
		LeachingVatWrapper wrapper = (LeachingVatWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int SULF_SLOT = 1;
		int CHLO_SLOT = 2;
		int WATER_SLOT = 3;
		int OUTPUT_SLOT = 4;
		int STEAM_SLOT = 5;
		int PULP_SLOT = 6;

		guiItemStacks.init(INPUT_SLOT, true, 0, 11);
		guiItemStacks.init(OUTPUT_SLOT, false, 110, 11);
		guiFluidStacks.init(SULF_SLOT, true,  27, 11, 18, 18, 1000, false, null);
		guiFluidStacks.init(CHLO_SLOT, true,  55, 11, 18, 18, 1000, false, null);
		guiFluidStacks.init(WATER_SLOT, true, 83, 11, 18, 18, 1000, false, null);
		guiFluidStacks.init(STEAM_SLOT, true, 28, 46, 34, 16, 1000, false, null);
		guiFluidStacks.init(PULP_SLOT, false, 93, 46, 34, 16, 1000, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiFluidStacks.set(SULF_SLOT, BaseRecipes.getFluid(EnumFluid.SODIUM_HYDROXIDE, 1000));
		guiFluidStacks.set(CHLO_SLOT, BaseRecipes.getFluid(EnumFluid.HYDROCHLORIC_ACID, 1000));
		guiFluidStacks.set(WATER_SLOT, new FluidStack(FluidRegistry.WATER, 1000));
		guiFluidStacks.set(STEAM_SLOT, BaseRecipes.getFluid(EnumFluid.WATER_VAPOUR, 1000));
		guiFluidStacks.set(PULP_SLOT, wrapper.getSolutions());
	}

}