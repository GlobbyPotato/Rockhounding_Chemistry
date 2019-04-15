package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.EnumMinerals;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class MineralSizerRecipes extends BaseRecipes{
	public static ArrayList<MineralSizerRecipe> mineral_sizer_recipes = new ArrayList<MineralSizerRecipe>();

	public static void machineRecipes(){
		mineral_sizer_recipes.add(new MineralSizerRecipe("oreUninspected", 		Arrays.asList(
																						mineral_ores(1,EnumMinerals.SILICATE), 
																						mineral_ores(1,EnumMinerals.CARBONATE), 
																						mineral_ores(1,EnumMinerals.SULFIDE), 
																						mineral_ores(1,EnumMinerals.OXIDE),
																						mineral_ores(1,EnumMinerals.NATIVE),
																						mineral_ores(1,EnumMinerals.SULFATE), 
																						mineral_ores(1,EnumMinerals.CHROMATE), 
																						mineral_ores(1,EnumMinerals.PHOSPHATE),
																						mineral_ores(1,EnumMinerals.ANTIMONATE),
																						mineral_ores(1,EnumMinerals.BORATE),
																						mineral_ores(1,EnumMinerals.VANADATE), 
																						mineral_ores(1,EnumMinerals.HALIDE),
																						mineral_ores(1,EnumMinerals.ARSENATE)), 
																				Arrays.asList(
																						1, 
																						2, 
																						3, 
																						4, 
																						5, 
																						5, 
																						7, 
																						9, 
																						10, 
																						10, 
																						11, 
																						13, 
																						15)));

		mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(Blocks.STONE, 1, 1), chemicals(2, EnumChemicals.FLUORITE), 4));
		mineral_sizer_recipes.add(new MineralSizerRecipe("blockCoal", chemicals(32, EnumChemicals.CRACKED_COAL), 2));
		mineral_sizer_recipes.add(new MineralSizerRecipe("blockCharcoal", chemicals(16, EnumChemicals.CRACKED_COAL), 2));
	}
}