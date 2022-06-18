package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumMinerals;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfide;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ShakingTableRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ShakingTableRecipes extends BaseRecipes{
	public static ArrayList<ShakingTableRecipe> shaking_table_recipes = new ArrayList<ShakingTableRecipe>();

	public static void machineRecipes() {
		shaking_table_recipes.add(new ShakingTableRecipe(	new ItemStack(Blocks.STONE, 1, 1),
															new ItemStack(Blocks.GRAVEL),
															Arrays.asList(	"compoundLead", "compoundFluorite", "compoundQuartz", "compoundSulfur"),
															Arrays.asList(	20, 15, 10, 4),
															getFluid(EnumFluid.LOW_LEACHATE, 200)));

		shaking_table_recipes.add(new ShakingTableRecipe(	mineral_ores(1, EnumMinerals.NATIVE),
															native_stack(1, EnumNative.AURICUPRIDE),
															Arrays.asList(	"ductCopper", "dustGold", "dustCarbon", "dustPlatinum"),
															Arrays.asList(	40, 35, 10, 10),
															getFluid(EnumFluid.HIGH_LEACHATE, 400)));

		shaking_table_recipes.add(new ShakingTableRecipe(	mineral_ores(1, EnumMinerals.CHROMATE),
															sulfide_stack(1, EnumSulfide.GALENA),
															Arrays.asList(	"ductChromium", "dustLead", "dustSilicon", "dustCopper"),
															Arrays.asList(	30, 30, 15, 10),
															getFluid(EnumFluid.LEACHATE, 600)));

	}
}