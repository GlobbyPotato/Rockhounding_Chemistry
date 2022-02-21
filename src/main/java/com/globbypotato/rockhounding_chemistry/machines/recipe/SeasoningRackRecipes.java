package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SeasoningRackRecipe;

import net.minecraft.item.ItemStack;

public class SeasoningRackRecipes {
	public static ArrayList<SeasoningRackRecipe> seasoning_rack_recipes = new ArrayList<SeasoningRackRecipe>();

	public static void machineRecipes(){
		seasoning_rack_recipes.add(new SeasoningRackRecipe(		new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.RAW_SALT.ordinal()), 
																new ItemStack(ModItems.CHEMICAL_ITEMS, 9, EnumChemicals.SALT.ordinal())));

		seasoning_rack_recipes.add(new SeasoningRackRecipe(		new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.POOR_RAW_SALT.ordinal()), 
																new ItemStack(ModItems.CHEMICAL_ITEMS, 2, EnumChemicals.SALT.ordinal())));
	}
}