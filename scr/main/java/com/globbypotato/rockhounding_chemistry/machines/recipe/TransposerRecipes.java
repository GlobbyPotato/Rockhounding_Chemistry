package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.TransposerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

public class TransposerRecipes extends BaseRecipes{
	public static ArrayList<TransposerRecipe> transposer_recipes = new ArrayList<TransposerRecipe>();

	public static void machineRecipes() {
		transposer_recipes.add(new TransposerRecipe(	getFluid(EnumFluid.WATER_VAPOUR, 1000), 
														getFluid(EnumFluid.WATER_VAPOUR, 1000)));

		if(ModUtils.hasMekanism()){
			if(CoreUtils.fluidExists(ModUtils.mek_liquid_oxygen)){
				transposer_recipes.add(new TransposerRecipe(	CoreUtils.getFluid(ModUtils.mek_liquid_oxygen, 1000), 
																getFluid(EnumFluid.LIQUID_OXYGEN, 1000)));
			}

			if(CoreUtils.fluidExists(ModUtils.mek_steam)){
				transposer_recipes.add(new TransposerRecipe(	CoreUtils.getFluid(ModUtils.mek_steam, 1000), 
																getFluid(EnumFluid.WATER_VAPOUR, 1000)));
			}

			if(CoreUtils.fluidExists(ModUtils.mek_sulfuric_acid)){
				transposer_recipes.add(new TransposerRecipe(	CoreUtils.getFluid(ModUtils.mek_sulfuric_acid, 1000), 
																getFluid(EnumFluid.SULFURIC_ACID, 1000)));
			}
		}

		if(ModUtils.hasGCPlanets()){
			if(CoreUtils.fluidExists(ModUtils.gc_sulphuric_acid)){
				transposer_recipes.add(new TransposerRecipe(	CoreUtils.getFluid(ModUtils.gc_sulphuric_acid, 1000), 
																getFluid(EnumFluid.SULFURIC_ACID, 1000)));
			}
		}
	}

}