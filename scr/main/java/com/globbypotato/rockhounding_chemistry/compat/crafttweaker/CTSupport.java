package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

public class CTSupport {

	public static void init() {
		if (Loader.isModLoaded("MineTweaker3")) {
			CTSupport.loadSupport();
	    }
	}

    public static void loadSupport() {
        MineTweakerAPI.registerClass(SeasonerRecipes.class);
        MineTweakerAPI.registerClass(SizerRecipes.class);
        MineTweakerAPI.registerClass(AnalyzerRecipes.class);
        MineTweakerAPI.registerClass(AlloyerRecipes.class);
        MineTweakerAPI.registerClass(ExtractorRecipes.class);
        MineTweakerAPI.registerClass(OvenRecipes.class);
        MineTweakerAPI.registerClass(DepositionRecipes.class);
        MineTweakerAPI.registerClass(CastingRecipes.class);
        MineTweakerAPI.registerClass(LabBlenderRecipes.class);
    }

    public static ItemStack toStack(IItemStack iStack){
		if(iStack == null){
			return null;
		}
		return (ItemStack) iStack.getInternal();
    }

    public static FluidStack toFluid(ILiquidStack iStack){
		if (iStack == null) {
			return null;
		}
		return (FluidStack) iStack.getInternal();
    }
    
    public static FluidStack getFluid(ILiquidStack fluid, int amount){
		if (fluid == null) {
			return null;
		}
		return new FluidStack(toFluid(fluid), amount);
    }
}