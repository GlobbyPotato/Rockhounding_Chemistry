package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.ItemStack;

public class GasPurifierRecipes extends BaseRecipes{
	public static ArrayList<GasPurifierRecipe> gas_purifier_recipes = new ArrayList<GasPurifierRecipe>();

	public static void machineRecipes() {
		gas_purifier_recipes.add(new GasPurifierRecipe(getFluid(EnumFluid.RAW_SYNGAS, 1000), 		getFluid(EnumFluid.SYNGAS, 1000), 			flyash_compound, 	ItemStack.EMPTY));
		gas_purifier_recipes.add(new GasPurifierRecipe(getFluid(EnumFluid.COMPRESSED_AIR, 1000), 	getFluid(EnumFluid.REFINED_AIR, 1000), 		ItemStack.EMPTY, 	ItemStack.EMPTY));
		gas_purifier_recipes.add(new GasPurifierRecipe(getFluid(EnumFluid.RAW_FLUE_GAS, 1000), 		getFluid(EnumFluid.FLUE_GAS, 1000), 		carbon_dust, 		ItemStack.EMPTY));
	}

}