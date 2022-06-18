package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class GasPurifierRecipes extends BaseRecipes{
	public static ArrayList<GasPurifierRecipe> gas_purifier_recipes = new ArrayList<GasPurifierRecipe>();

	public static void machineRecipes() {
		gas_purifier_recipes.add(new GasPurifierRecipe(			getFluid(EnumFluid.RAW_SYNGAS, 1000), 		
																getFluid(EnumFluid.SYNGAS, 1000), 			
																Arrays.asList(	"compoundCoke", "compoundFlyash"), 
																Arrays.asList(	8, 5)));

		gas_purifier_recipes.add(new GasPurifierRecipe(			getFluid(EnumFluid.COMPRESSED_AIR, 1000), 	
																getFluid(EnumFluid.REFINED_AIR, 1000), 		
																Arrays.asList(), 
																Arrays.asList()));

		gas_purifier_recipes.add(new GasPurifierRecipe(			getFluid(EnumFluid.RAW_FLUE_GAS, 1000), 	
																getFluid(EnumFluid.FLUE_GAS, 1000), 		
																Arrays.asList(	"compoundFlyash", "compoundCarbon"), 
																Arrays.asList(	8, 5)));
	}

}