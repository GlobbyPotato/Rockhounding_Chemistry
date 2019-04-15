package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ToxicMutationRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ToxicMutationRecipes extends BaseRecipes{
	public static ArrayList<ToxicMutationRecipe> toxic_mutation_recipes = new ArrayList<ToxicMutationRecipe>();

	public static void machineRecipes(){
		toxic_mutation_recipes.add(new ToxicMutationRecipe(new ItemStack(Items.POTATO), new ItemStack(Items.POISONOUS_POTATO)));
		toxic_mutation_recipes.add(new ToxicMutationRecipe(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.NETHER_WART)));
		toxic_mutation_recipes.add(new ToxicMutationRecipe(new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.NETHER_WART)));
		toxic_mutation_recipes.add(new ToxicMutationRecipe(new ItemStack(Items.LEATHER), new ItemStack(Items.ROTTEN_FLESH)));

		toxic_mutation_recipes.add(new ToxicMutationRecipe("dyeRed", new ItemStack(Items.COAL)));

	}
}