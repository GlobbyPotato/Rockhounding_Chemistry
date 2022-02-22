package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class MaterialCabinetRecipes extends BaseRecipes{
	public static ArrayList<MaterialCabinetRecipe> material_cabinet_recipes = new ArrayList<MaterialCabinetRecipe>();

	public static void machineRecipes() {
		material_cabinet_recipes.add(new MaterialCabinetRecipe("Wi", "compoundWidia", "Widia"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("Sa", "dustSand", "Filtered Sand"));
	}
}