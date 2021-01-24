package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.BedReactorRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.ItemStack;

public class BedReactorRecipes extends BaseRecipes{
	public static ArrayList<BedReactorRecipe> bed_reactor_recipes = new ArrayList<BedReactorRecipe>();

	public static void machineRecipes() {
		bed_reactor_recipes.add(new BedReactorRecipe(	"",
														getFluid(EnumFluid.NITROGEN, 100), 
														getFluid(EnumFluid.HYDROGEN, 300),
														null,
														null,
														getFluid(EnumFluid.AMMONIA, 200), 			
														new ItemStack(ModItems.FE_CATALYST)));

		bed_reactor_recipes.add(new BedReactorRecipe(	"",
														getFluid(EnumFluid.SYNGAS, 250), 
														getFluid(EnumFluid.WATER_VAPOUR, 500),
														null,
														null,
														getFluid(EnumFluid.PROPYLENE, 300), 			
														new ItemStack(ModItems.AU_CATALYST)));
	}

}