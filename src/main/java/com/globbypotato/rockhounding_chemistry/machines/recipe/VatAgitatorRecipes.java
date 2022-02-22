package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.VatAgitatorRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.ItemStack;

public class VatAgitatorRecipes extends BaseRecipes{
	public static ArrayList<VatAgitatorRecipe> leaching_vat_agitator = new ArrayList<VatAgitatorRecipe>();

	public static void machineRecipes(){
		leaching_vat_agitator.add(new VatAgitatorRecipe(new ItemStack(ModItems.SLURRY_AGITATOR)));
	}
}