package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryDrumRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.ItemStack;

public class SlurryDrumRecipes extends BaseRecipes{
	public static ArrayList<SlurryDrumRecipe> slurry_drum_recipes = new ArrayList<SlurryDrumRecipe>();

	public static void machineRecipes() {
		slurry_drum_recipes.add(new SlurryDrumRecipe(	new ItemStack(ModItems.CHEMICAL_ITEMS, 1, EnumChemicals.COAL_TAR_COMPOUND.ordinal()), 	
														getFluid(EnumFluid.COAL_TAR, 100)));
	}

}