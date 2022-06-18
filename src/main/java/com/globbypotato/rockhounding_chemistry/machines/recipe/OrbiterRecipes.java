package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.OrbiterRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraftforge.fluids.FluidStack;

public class OrbiterRecipes extends BaseRecipes{
	public static ArrayList<OrbiterRecipe> exp_recipes = new ArrayList<OrbiterRecipe>();

	public static void machineRecipes(){
		exp_recipes.add(new OrbiterRecipe(new FluidStack(ModFluids.XPJUICE, 1000)));

		if(ModUtils.hasOpenBlocks() || ModUtils.hasEnderIO()){
			exp_recipes.add(new OrbiterRecipe(ModUtils.liquidXP()));
		}
	}
}