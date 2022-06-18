package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumMinerals;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumOxide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSilicate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfide;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

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

		mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(Blocks.STONE, 1, 1), 	chemicals(2, EnumChemicals.FLUORITE), 4));
		mineral_sizer_recipes.add(new MineralSizerRecipe("blockCoal", 							chemicals(32, EnumChemicals.CRACKED_COAL), 8));
		mineral_sizer_recipes.add(new MineralSizerRecipe("blockCharcoal", 						chemicals(16, EnumChemicals.CRACKED_CHARCOAL), 8));
		mineral_sizer_recipes.add(new MineralSizerRecipe("sandstone", 							chemicals(8,  EnumChemicals.CRACKED_LIME), 5));
		mineral_sizer_recipes.add(new MineralSizerRecipe("blockBituminous", 					chemicals(48, EnumChemicals.CRACKED_COAL), 7));
		mineral_sizer_recipes.add(new MineralSizerRecipe("blockAnthracite", 					chemicals(64, EnumChemicals.CRACKED_COAL), 9));

		if(ModUtils.hasRhRocksIntegtation()) {
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 0), 		mineral_ores(1, EnumMinerals.SILICATE), 1));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_b(), 1, 6), 		mineral_ores(1, EnumMinerals.SILICATE), 1));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_e(), 1, 14), 		mineral_ores(1, EnumMinerals.SILICATE), 1));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_g(), 1, 6), 		mineral_ores(1, EnumMinerals.SILICATE), 1));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 12), 		mineral_ores(1, EnumMinerals.SILICATE), 1));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_e(), 1, 6), 		mineral_ores(1, EnumMinerals.SILICATE), 1));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_b(), 1, 5), 		mineral_ores(1, EnumMinerals.CARBONATE), 2));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_e(), 1, 9), 		mineral_ores(1, EnumMinerals.CARBONATE), 2));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_b(), 1, 12), 		mineral_ores(1, EnumMinerals.OXIDE), 4));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_h(), 1, 14), 		mineral_ores(1, EnumMinerals.PHOSPHATE), 9));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_d(), 1, 5), 		mineral_ores(1, EnumMinerals.CHROMATE), 7));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_h(), 1, 0), 		mineral_ores(1, EnumMinerals.ANTIMONATE), 10));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_h(), 1, 11), 		mineral_ores(1, EnumMinerals.ANTIMONATE), 10));

			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_c(), 1, 13), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE), 
																														silicate_stack(1,EnumSilicate.FUCHSITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1, 
																														10,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 11), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE), 
																														silicate_stack(1,EnumSilicate.FUCHSITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1, 
																														10,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 2), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 13), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_c(), 1, 2), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_c(), 1, 14), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_e(), 1, 0), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_e(), 1, 15), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_f(), 1, 12), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_g(), 1, 7), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														silicate_stack(1,EnumSilicate.MUSCOVITE)), 
																												Arrays.asList(
																														1,
																														4,
																														14,
																														6)));

			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 4), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SILICATE),
																														mineral_ores(1,EnumMinerals.OXIDE),
																														chemicals(1, EnumChemicals.FLUORITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														oxide_stack(1,EnumOxide.CASSITERITE)), 
																												Arrays.asList(
																														1,
																														4,
																														4,
																														14,
																														12)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_a(), 1, 9), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SULFIDE),
																														sulfide_stack(1, EnumSulfide.PYRITE),
																														silicate_stack(1,EnumSilicate.BIOTITE),
																														oxide_stack(1,EnumOxide.CASSITERITE)), 
																												Arrays.asList(
																														3,
																														7,
																														14,
																														12)));
			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_b(), 1, 3), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.SULFATE),
																														mineral_ores(1,EnumMinerals.SULFIDE)), 
																												Arrays.asList(
																														6,
																														3)));

			mineral_sizer_recipes.add(new MineralSizerRecipe(new ItemStack(ModUtils.rhrocks_h(), 1, 15), 		Arrays.asList(
																														mineral_ores(1,EnumMinerals.OXIDE),
																														oxide_stack(1,EnumOxide.RUTILE)), 
																												Arrays.asList(
																														4,
																														12)));
		}

	}
}