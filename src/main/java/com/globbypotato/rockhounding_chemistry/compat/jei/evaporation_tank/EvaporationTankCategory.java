package com.globbypotato.rockhounding_chemistry.compat.jei.evaporation_tank;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EvaporationTankCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guievaporationtankjei.png");
	private String uid;

	public EvaporationTankCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 98, 70), "jei." + uid + ".name");
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
		EvaporationTankWrapper wrapper = (EvaporationTankWrapper) recipeWrapper;

		int WATER_SLOT = 0;
		int TANK_SLOT = 1;
		int FLUID_SLOT = 2;
		int SALT_SLOT = 3;

		guiFluidStacks.init(WATER_SLOT, true,  1, 1, 34, 16, 1000, false, null);
		guiFluidStacks.init(FLUID_SLOT, false,  63, 45, 34, 16, 1000, false, null);
		guiItemStacks.init(TANK_SLOT, false, 40, 22);
		guiItemStacks.init(SALT_SLOT, false, 62, 0);

		guiFluidStacks.set(WATER_SLOT, CoreBasics.waterStack(1000));
		guiFluidStacks.set(FLUID_SLOT, wrapper.getOutputs());
		
		guiItemStacks.set(SALT_SLOT, wrapper.getSalt());
		guiItemStacks.set(TANK_SLOT, new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.EVAPORATION_TANK.ordinal()));

	}

}