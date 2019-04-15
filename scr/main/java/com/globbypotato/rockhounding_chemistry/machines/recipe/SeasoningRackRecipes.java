package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SeasoningRackRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class SeasoningRackRecipes extends BaseRecipes{
	public static ArrayList<SeasoningRackRecipe> seasoning_rack_recipes = new ArrayList<SeasoningRackRecipe>();

	public static void machineRecipes(){
		seasoning_rack_recipes.add(new SeasoningRackRecipe(raw_salt_block, chemicals(9, EnumChemicals.SALT)));
		seasoning_rack_recipes.add(new SeasoningRackRecipe(poor_salt_block, chemicals(2, EnumChemicals.SALT)));
	}
}