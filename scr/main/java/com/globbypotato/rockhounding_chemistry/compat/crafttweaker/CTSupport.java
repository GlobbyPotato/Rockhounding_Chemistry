package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

public class CTSupport {

    public static void loadSupport() {
    	if(Loader.isModLoaded("crafttweaker")){
	        CraftTweakerAPI.registerClass(CT_SeasoningRack.class);
	        CraftTweakerAPI.registerClass(CT_ProfilingBench.class);
	        CraftTweakerAPI.registerClass(CT_SlurryPond.class);
	        CraftTweakerAPI.registerClass(CT_MineralSizer.class);
	        CraftTweakerAPI.registerClass(CT_LabBlender.class);
	        CraftTweakerAPI.registerClass(CT_LabOven.class);
	        CraftTweakerAPI.registerClass(CT_HeatExchanger.class);
	        CraftTweakerAPI.registerClass(CT_GasReformer.class);
	        CraftTweakerAPI.registerClass(CT_GasPurifier.class);
	        CraftTweakerAPI.registerClass(CT_GasifierPlant.class);
	        CraftTweakerAPI.registerClass(CT_GasCondenser.class);
	        CraftTweakerAPI.registerClass(CT_LeachingVat.class);
	        CraftTweakerAPI.registerClass(CT_RetentionVat.class);
	        CraftTweakerAPI.registerClass(CT_ChemicalExtractor.class);
	        CraftTweakerAPI.registerClass(CT_InhibitElements.class);
	        CraftTweakerAPI.registerClass(CT_MaterialCabinet.class);
	        CraftTweakerAPI.registerClass(CT_MetalAlloyer.class);
	        CraftTweakerAPI.registerClass(CT_DepositionChamber.class);
	        CraftTweakerAPI.registerClass(CT_PullingCrucible.class);
	        CraftTweakerAPI.registerClass(CT_Transposer.class);
	        CraftTweakerAPI.registerClass(CT_ToxicMutation.class);
	        CraftTweakerAPI.registerClass(CT_PollutantFluids.class);
	        CraftTweakerAPI.registerClass(CT_PollutantGases.class);
	        CraftTweakerAPI.registerClass(CT_SlurryDrum.class);
	        CraftTweakerAPI.registerClass(CT_StirredTank.class);
	        CraftTweakerAPI.registerClass(CT_Precipitator.class);
	        CraftTweakerAPI.registerClass(CT_InhibitGases.class);
    	}
    }

    public static String addCaption(String name) {
		return "Adding a recipe for the " + name;
	}

    public static String removeCaption(String name) {
		return "Removing a recipe for the " + name;
	}

    public static void error(String name) {
    	CraftTweakerAPI.logError("Invalid recipe detected for " + name);
    }

    public static ItemStack toStack(IItemStack iStack){
		if(iStack == null){
			return ItemStack.EMPTY;
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