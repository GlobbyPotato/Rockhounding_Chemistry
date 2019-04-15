package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.EvaporationTankRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

public class EvaporationTankRecipes extends BaseRecipes{
	public static ArrayList<EvaporationTankRecipe> salt_recipes = new ArrayList<EvaporationTankRecipe>();

	public static void machineRecipes(){
		salt_recipes.add(new EvaporationTankRecipe(CoreBasics.waterStack(1000)));
		salt_recipes.add(new EvaporationTankRecipe(CoreUtils.getFluid(EnumFluid.VIRGIN_WATER.getFluidName(), 1000)));
		salt_recipes.add(new EvaporationTankRecipe(CoreUtils.getFluid(EnumFluid.SALT_BRINE.getFluidName(), 1000)));
		salt_recipes.add(new EvaporationTankRecipe(CoreUtils.getFluid(EnumFluid.DENSE_BRINE.getFluidName(), 1000)));
		salt_recipes.add(new EvaporationTankRecipe(CoreUtils.getFluid(EnumFluid.MOTHER_LIQUOR.getFluidName(), 1000)));
	}
}