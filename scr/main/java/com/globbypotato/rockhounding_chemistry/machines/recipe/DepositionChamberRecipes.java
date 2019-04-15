package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class DepositionChamberRecipes extends BaseRecipes{
	public static ArrayList<DepositionChamberRecipe> deposition_chamber_recipes = new ArrayList<DepositionChamberRecipe>();

	public static void machineRecipes() {
		deposition_chamber_recipes.add(new DepositionChamberRecipe(	"dustSilicon", 	
																	alloy_tech_ingot(1, EnumAlloyTech.SIENA), 	
																	getFluid(EnumFluid.AMMONIA, 400), 	
																	1900, 
																	10000,
																	getFluid(EnumFluid.OXYGEN, 200)));

		deposition_chamber_recipes.add(new DepositionChamberRecipe(	"dustSilicon", 	
																	alloy_tech_ingot(1, EnumAlloyTech.CARBORUNDUM), 
																	getFluid(EnumFluid.SYNGAS, 500),		
																	2500, 
																	24000,
																	getFluid(EnumFluid.OXYGEN, 800)));

		deposition_chamber_recipes.add(new DepositionChamberRecipe(	"ingotTitanium", 		
																	alloy_tech_ingot(1, EnumAlloyTech.TINITE), 	
																	getFluid(EnumFluid.AMMONIA,	600), 	
																	1500, 
																	30000,
																	getFluid(EnumFluid.OXYGEN, 300)));

		deposition_chamber_recipes.add(new DepositionChamberRecipe(	"dustTungsten", 
																	chemicals(1, EnumChemicals.WIDIA_COMPOUND), 
																	getFluid(EnumFluid.SYNGAS, 500), 	
																	1200, 
																	2000,
																	getFluid(EnumFluid.OXYGEN, 600)));
	}
}