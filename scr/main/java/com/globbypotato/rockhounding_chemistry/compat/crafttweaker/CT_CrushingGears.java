package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerGearRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerGearRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.CrushingGears")
public class CT_CrushingGears extends CTSupport{
	public static String name = "Crushing Gears";
	public static ArrayList<MineralSizerGearRecipe> recipeList = MineralSizerGearRecipes.mineral_sizer_gears;

    @ZenMethod
    public static void add(IItemStack gear) {
        if(gear == null || !toStack(gear).getItem().isDamageable()) {error(name + ": " + toStack(gear).getDisplayName()); return;}

        CraftTweakerAPI.apply(new Add(new MineralSizerGearRecipe(toStack(gear))));
    }

		private static class Add implements IAction {
	    	private MineralSizerGearRecipe recipe;
	    	public Add(MineralSizerGearRecipe recipe){
	          super();
	          this.recipe = recipe;
	    	}
	    	@Override
	    	public void apply() {
	    		recipeList.add(this.recipe);
	    	}
			@Override
	    	public String describe() {
	    		return addCaption(name);
	    	}
	    }
}