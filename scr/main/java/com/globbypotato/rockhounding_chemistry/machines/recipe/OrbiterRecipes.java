package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.OrbiterRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

public class OrbiterRecipes extends BaseRecipes{
	public static ArrayList<OrbiterRecipe> exp_recipes = new ArrayList<OrbiterRecipe>();

	public static void machineRecipes(){
		if(ModUtils.hasOpenBlocks() || ModUtils.hasEnderIO()){
			exp_recipes.add(new OrbiterRecipe(ModUtils.liquidXP()));
		}
	}
}