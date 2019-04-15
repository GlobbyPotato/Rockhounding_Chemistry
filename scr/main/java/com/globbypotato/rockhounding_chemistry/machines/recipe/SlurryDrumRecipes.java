package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryDrumRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class SlurryDrumRecipes extends BaseRecipes{
	public static ArrayList<SlurryDrumRecipe> slurry_drum_recipes = new ArrayList<SlurryDrumRecipe>();

	public static void machineRecipes() {
		slurry_drum_recipes.add(new SlurryDrumRecipe(coal_tar_compound, 	getFluid(EnumFluid.COAL_TAR, 100)));
	}

}