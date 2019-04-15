package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasCondenserRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class GasCondenserRecipes extends BaseRecipes{
	public static ArrayList<GasCondenserRecipe> gas_condenser_recipes = new ArrayList<GasCondenserRecipe>();

	public static void machineRecipes() {
		gas_condenser_recipes.add(new GasCondenserRecipe(getFluid(EnumFluid.NITROGEN, 70), 		getFluid(EnumFluid.LIQUID_NITROGEN, 1)));
		gas_condenser_recipes.add(new GasCondenserRecipe(getFluid(EnumFluid.OXYGEN, 90), 		getFluid(EnumFluid.LIQUID_OXYGEN, 1)));
		gas_condenser_recipes.add(new GasCondenserRecipe(getFluid(EnumFluid.AMMONIA, 80), 		getFluid(EnumFluid.LIQUID_AMMONIA, 1)));
	}
}