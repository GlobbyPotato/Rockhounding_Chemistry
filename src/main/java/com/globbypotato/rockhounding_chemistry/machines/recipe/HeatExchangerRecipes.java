package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.HeatExchangerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class HeatExchangerRecipes extends BaseRecipes{
	public static ArrayList<HeatExchangerRecipe> heat_exchanger_recipes = new ArrayList<HeatExchangerRecipe>();

	public static void machineRecipes() {
		heat_exchanger_recipes.add(new HeatExchangerRecipe(		getFluid(EnumFluid.REFINED_AIR, 1000), 
																getFluid(EnumFluid.COOLED_AIR, 1000)));
	}

}