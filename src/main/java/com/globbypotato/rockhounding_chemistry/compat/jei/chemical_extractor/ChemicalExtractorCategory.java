package com.globbypotato.rockhounding_chemistry.compat.jei.chemical_extractor;

import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeCategory;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIExtractorController;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class ChemicalExtractorCategory extends RHRecipeCategory {

	private final static ResourceLocation guiTexture = UIExtractorController.TEXTURE_JEI;
	private String uid;

	public ChemicalExtractorCategory(IGuiHelper guiHelper, String uid) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 155, 73), "jei." + uid + ".name");
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
		ChemicalExtractorWrapper wrapper = (ChemicalExtractorWrapper) recipeWrapper;

		int INPUT_SLOT = 0;
		int NITR_SLOT = 1;
		int CYAN_SLOT = 2;
		int OUTPUT_SLOT = 3;
		int CYLINDER_SLOT = 4;
		int CATALYST_SLOT = 5;

		guiItemStacks.init(INPUT_SLOT, true, 73, 40);
		guiItemStacks.init(OUTPUT_SLOT, false, 137, 39);
		guiFluidStacks.init(NITR_SLOT, true,  1, 22, 16, 34, 1000, false, null);
		guiFluidStacks.init(CYAN_SLOT, true,  20, 22, 16, 34, 1000, false, null);
		guiItemStacks.init(CYLINDER_SLOT, false, 116, 21);
		guiItemStacks.init(CATALYST_SLOT, false, 40, 0);

		guiItemStacks.set(INPUT_SLOT, wrapper.getInputs());
		guiItemStacks.set(OUTPUT_SLOT, wrapper.getOutputs());
		guiFluidStacks.set(NITR_SLOT, new FluidStack(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID), 1000));
		guiFluidStacks.set(CYAN_SLOT, new FluidStack(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE), 1000));
		guiItemStacks.set(CYLINDER_SLOT, BaseRecipes.graduated_cylinder);
		guiItemStacks.set(CATALYST_SLOT, BaseRecipes.fe_catalyst);
	}

}