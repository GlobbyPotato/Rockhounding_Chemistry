package com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class AnalyzerRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int OUTPUT_SLOT = 2;
	private static final int TUBE_SLOT = 3;
	private static final int SULF_SLOT = 4;
	private static final int CHLOR_SLOT = 5;
	private static final int FLUO_SLOT = 6;

	private final static ResourceLocation guiTexture = GuiMineralAnalyzer.TEXTURE_REF;

	public AnalyzerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 43, 18, 126, 69), "jei.analyzer.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.ANALYZER;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		AnalyzerRecipeWrapper wrapper = (AnalyzerRecipeWrapper) recipeWrapper;	

		guiFluidStacks.init(SULF_SLOT, true, 71, 8, 16, 60, 100, false, null);
		guiFluidStacks.init(CHLOR_SLOT, true, 90, 8, 16, 60, 100, false, null);
		guiFluidStacks.init(FLUO_SLOT, true, 109, 8, 16, 60, 100, false, null);
		
		guiFluidStacks.set(SULF_SLOT, new FluidStack(ModFluids.SULFURIC_ACID, ModConfig.consumedSulf));
		guiFluidStacks.set(CHLOR_SLOT, new FluidStack(ModFluids.HYDROCHLORIC_ACID, ModConfig.consumedChlo));
		guiFluidStacks.set(FLUO_SLOT, new FluidStack(ModFluids.HYDROFLUORIC_ACID, ModConfig.consumedFluo));

		guiItemStacks.init(INPUT_SLOT, true, 0, 11);
		guiItemStacks.init(OUTPUT_SLOT, false, 32, 41);
		guiItemStacks.init(TUBE_SLOT, true, 0, 41);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiItemStacks.set(TUBE_SLOT, ToolUtils.agitator);
	}
}