package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GanPlantRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class GanPlantRecipes extends BaseRecipes{
	public static ArrayList<GanPlantRecipe> gan_plant_recipes = new ArrayList<GanPlantRecipe>();

	public static void machineRecipes() {
		gan_plant_recipes.add(new GanPlantRecipe( getFluid(EnumFluid.COOLED_AIR, 1000), 
									Arrays.asList(getFluid(EnumFluid.NITROGEN, 1000), 
												  getFluid(EnumFluid.OXYGEN, 1000), 
												  getFluid(EnumFluid.ARGON, 1000), 
												  getFluid(EnumFluid.CARBON_DIOXIDE, 1000), 
												  getFluid(EnumFluid.NEON, 1000), 
												  getFluid(EnumFluid.HELIUM, 1000), 
												  getFluid(EnumFluid.KRYPTON, 1000), 
												  getFluid(EnumFluid.XENON, 1000))));
	}

}