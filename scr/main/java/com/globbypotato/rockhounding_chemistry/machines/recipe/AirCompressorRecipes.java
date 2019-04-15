package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.AirCompressorRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class AirCompressorRecipes extends BaseRecipes{
	public static ArrayList<AirCompressorRecipe> air_compressor_recipes = new ArrayList<AirCompressorRecipe>();

	public static void machineRecipes() {
		air_compressor_recipes.add(new AirCompressorRecipe(getFluid(EnumFluid.COMPRESSED_AIR, 1000)));
	}

}