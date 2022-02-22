package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.VatAgitatorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.VatAgitatorRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.SlurryAgitators")
public class CT_SlurryAgitators extends CTSupport{
	public static String name = "Slurry Agitators";
	public static ArrayList<VatAgitatorRecipe> recipeList = VatAgitatorRecipes.leaching_vat_agitator;

    @ZenMethod
    public static void add(IItemStack gear) {
        if(gear == null || !toStack(gear).getItem().isDamageable()) {error(name + ": " + toStack(gear).getDisplayName()); return;}

        CraftTweakerAPI.apply(new Add(new VatAgitatorRecipe(toStack(gear))));
    }

		private static class Add implements IAction {
	    	private VatAgitatorRecipe recipe;
	    	public Add(VatAgitatorRecipe recipe){
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