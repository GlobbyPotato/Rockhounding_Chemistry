package com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeUID;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMachineEnergy;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class ExtractorRecipeCategory extends RHRecipeCategory {

	private static final int INPUT_SLOT = 1;
	private static final int TUBE_SLOT = 2;
	private static final int CYLINDER_SLOT = 3;

	private static final int NITR_SLOT = 4;
	private static final int PHOS_SLOT = 5;
	private static final int CYAN_SLOT = 6;

	private final static ResourceLocation guiTexture = GuiChemicalExtractor.TEXTURE_JEI;

	public ExtractorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 145, 82), "jei.extractor.name");
	}

	@Nonnull
	@Override
	public String getUid() {
		return RHRecipeUID.EXTRACTOR;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		ExtractorRecipeWrapper wrapper = (ExtractorRecipeWrapper) recipeWrapper;	

		guiItemStacks.init(INPUT_SLOT, true, 0, 0);
		guiItemStacks.init(TUBE_SLOT, false, 0, 20);
		guiItemStacks.init(CYLINDER_SLOT, true, 103, 55);
		guiFluidStacks.init(NITR_SLOT, true,  23, 1, 16, 60, 100, false, null);
		guiFluidStacks.init(PHOS_SLOT, false, 43, 1, 16, 60, 100, false, null);
		guiFluidStacks.init(CYAN_SLOT, false, 63, 1, 16, 60, 100, false, null);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(TUBE_SLOT, new ItemStack(ModItems.testTube));
		guiItemStacks.set(CYLINDER_SLOT, new ItemStack(ModItems.cylinder));
		guiFluidStacks.set(NITR_SLOT, new FluidStack(ModFluids.NITRIC_ACID, TileEntityMachineEnergy.consumedNitr));
		guiFluidStacks.set(PHOS_SLOT, new FluidStack(ModFluids.PHOSPHORIC_ACID, TileEntityMachineEnergy.consumedPhos));
		guiFluidStacks.set(CYAN_SLOT, new FluidStack(ModFluids.SODIUM_CYANIDE, TileEntityMachineEnergy.consumedCyan));
	}
}