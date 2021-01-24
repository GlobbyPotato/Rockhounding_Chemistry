package com.globbypotato.rockhounding_chemistry.compat.jei.retention_vat;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIRetentionVat;
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

public class RetentionVatCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIRetentionVat.TEXTURE_JEI;
	private String uid;

	public RetentionVatCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 122, 63), "jei." + uid + ".name");
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
		RetentionVatWrapper wrapper = (RetentionVatWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int FLUO_SLOT = 1;
		int OUTPUT_SLOT = 2;
		int STEAM_SLOT = 3;
		int WATER_SLOT = 4;
		int PULP_SLOT = 5;

		guiFluidStacks.init(INPUT_SLOT, true,  1, 9, 16, 34, 1000, false, null);
		guiItemStacks.init(OUTPUT_SLOT, false, 86, 11);
		guiFluidStacks.init(FLUO_SLOT, true,  31, 11, 18, 18, 1000, false, null);
		guiFluidStacks.init(WATER_SLOT, true, 59, 11, 18, 18, 1000, false, null);
		guiFluidStacks.init(STEAM_SLOT, true, 32, 46, 34, 16, 1000, false, null);
		guiFluidStacks.init(PULP_SLOT, false, 87, 46, 34, 16, 1000, false, null);

		guiFluidStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiFluidStacks.set(FLUO_SLOT, BaseRecipes.getFluid(EnumFluid.HYDROFLUORIC_ACID, 1000));
		guiFluidStacks.set(STEAM_SLOT, BaseRecipes.getFluid(EnumFluid.WATER_VAPOUR, 1000));
		guiFluidStacks.set(WATER_SLOT, new FluidStack(FluidRegistry.WATER, 1000));
		guiFluidStacks.set(PULP_SLOT, BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 1000));

	}

}