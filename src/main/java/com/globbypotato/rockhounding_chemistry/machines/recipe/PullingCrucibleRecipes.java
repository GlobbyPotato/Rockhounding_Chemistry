package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PullingCrucibleRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class PullingCrucibleRecipes extends BaseRecipes{
	public static ArrayList<PullingCrucibleRecipe> pulling_crucible_recipes = new ArrayList<PullingCrucibleRecipe>();

	public static void machineRecipes() {
		pulling_crucible_recipes.add(new PullingCrucibleRecipe(	"compoundPureYag",
																"dustNeodymium",
																alloy_gems_ingot(1, EnumAlloyGems.ND_YAG)));
		
		pulling_crucible_recipes.add(new PullingCrucibleRecipe(	"compoundPureYag",
																"dustErbium",
																alloy_gems_ingot(1, EnumAlloyGems.ER_YAG)));

		pulling_crucible_recipes.add(new PullingCrucibleRecipe(	"compoundPureYag",
																"dustYtterbium",
																alloy_gems_ingot(1, EnumAlloyGems.YB_YAG)));

	}

}