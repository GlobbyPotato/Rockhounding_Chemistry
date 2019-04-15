package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantFluidRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantGasRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraftforge.fluids.FluidStack;

public class PollutantRecipes extends BaseRecipes{
	public static ArrayList<PollutantFluidRecipe> pollutant_fluid_recipes = new ArrayList<PollutantFluidRecipe>();
	public static ArrayList<PollutantGasRecipe> pollutant_gas_recipes = new ArrayList<PollutantGasRecipe>();
	public static ArrayList<FluidStack> pollutant_fluids = new ArrayList<FluidStack>();
	public static ArrayList<FluidStack> pollutant_gases = new ArrayList<FluidStack>();

	public static void machineRecipes(){
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.TOXIC_WASTE.getFluidName(), 1000));
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.TOXIC_SLUDGE.getFluidName(), 1000));
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.NITRIC_ACID.getFluidName(), 1000));
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.LEACHATE.getFluidName(), 1000));
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.LIQUID_AMMONIA.getFluidName(), 1000));
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.METHANOL.getFluidName(), 1000));
		pollutant_fluids.add(CoreUtils.getFluid(EnumFluid.CHLOROMETHANE.getFluidName(), 1000));

		for(int x = 0; x < pollutant_fluids.size(); x++){
			pollutant_fluid_recipes.add(new PollutantFluidRecipe(pollutant_fluids.get(x)));
		}

		pollutant_gases.add(CoreUtils.getFluid(EnumFluid.AMMONIA.getFluidName(), 1000));
		pollutant_gases.add(CoreUtils.getFluid(EnumFluid.RAW_SYNGAS.getFluidName(), 1000));
		pollutant_gases.add(CoreUtils.getFluid(EnumFluid.SYNGAS.getFluidName(), 1000));

		for(int x = 0; x < pollutant_gases.size(); x++){
			pollutant_gas_recipes.add(new PollutantGasRecipe(pollutant_gases.get(x)));
		}
	}
}