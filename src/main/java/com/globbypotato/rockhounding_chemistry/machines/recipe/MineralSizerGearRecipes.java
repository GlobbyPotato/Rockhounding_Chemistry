package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerGearRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.ItemStack;

public class MineralSizerGearRecipes extends BaseRecipes{
	public static ArrayList<MineralSizerGearRecipe> mineral_sizer_gears = new ArrayList<MineralSizerGearRecipe>();

	public static void machineRecipes(){
		mineral_sizer_gears.add(new MineralSizerGearRecipe(new ItemStack(ModItems.CRUSHING_GEAR)));
	}
}