package com.globbypotato.rockhounding_chemistry.compat.jei.orbiter_exp;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class OrbiterCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guiorbiterjei.png");
	private String uid;

	public OrbiterCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 129, 62), "jei." + uid + ".name");
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
		OrbiterWrapper wrapper = (OrbiterWrapper) recipeWrapper;

		int WASTE_SLOT = 0;
		int EXP_SLOT = 1;
		int PROBE_SLOT = 2;

		guiFluidStacks.init(WASTE_SLOT, true,  1, 1, 16, 34, 1000, false, null);
		guiFluidStacks.init(EXP_SLOT, false,  51, 44, 34, 16, 1000, false, null);
		guiItemStacks.init(PROBE_SLOT, true, 96, 22);

		guiFluidStacks.set(WASTE_SLOT, new FluidStack(ModFluids.TOXIC_WASTE, 1000));
		guiFluidStacks.set(EXP_SLOT, wrapper.getInputs());
		guiItemStacks.set(PROBE_SLOT, wrapper.getProbes());


	}

}