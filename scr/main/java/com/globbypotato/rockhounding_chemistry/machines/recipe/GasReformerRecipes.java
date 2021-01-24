package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class GasReformerRecipes extends BaseRecipes{
	public static ArrayList<GasReformerRecipe> gas_reformer_recipes = new ArrayList<GasReformerRecipe>();

	public static void machineRecipes() {
		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.NITROGEN, 40), 			getFluid(EnumFluid.SYNGAS, 20), 			getFluid(EnumFluid.AMMONIA, 30),		os_catalyst));
		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.AMMONIA, 20), 			getFluid(EnumFluid.OXYGEN, 30), 			getFluid(EnumFluid.NITRIC_ACID, 15),	pt_catalyst));
		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.SYNGAS, 10), 				getFluid(EnumFluid.WATER_VAPOUR, 30), 		getFluid(EnumFluid.METHANOL, 10),		ze_catalyst));
		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.CARBON_DIOXIDE, 20),	 	getFluid(EnumFluid.WATER_VAPOUR, 20), 		getFluid(EnumFluid.SYNGAS, 10),			co_catalyst));
		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.WATER_VAPOUR, 20), 		getFluid(EnumFluid.SYNGAS, 10), 			getFluid(EnumFluid.HYDROGEN, 20),		ni_catalyst));
//		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.SYNGAS, 10), 				getFluid(EnumFluid.HYDROGEN, 20), 			getFluid(EnumFluid.PROPYLENE, 10),		ni_catalyst));
		gas_reformer_recipes.add(new GasReformerRecipe(getFluid(EnumFluid.PROPYLENE, 20), 			getFluid(EnumFluid.OXYGEN, 20), 			getFluid(EnumFluid.ACRYLIC_ACID, 20),	mo_catalyst));
	}
}