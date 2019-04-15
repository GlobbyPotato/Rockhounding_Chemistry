package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import joptsimple.internal.Strings;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.MaterialCabinet")
public class CT_MaterialCabinet extends CTSupport{
	public static String name = "Material Cabinet";
	public static ArrayList<MaterialCabinetRecipe> recipeList = MaterialCabinetRecipes.material_cabinet_recipes;

    @ZenMethod
    public static void add(String symbol, String oredict, String name) {
        if(symbol == null || oredict == null || name == null) {error(name); return;}

        CraftTweakerAPI.apply(new Add(new MaterialCabinetRecipe(symbol, oredict, name)));
    }
		    private static class Add implements IAction {
		    	private final MaterialCabinetRecipe recipe;
		    	public Add(MaterialCabinetRecipe recipe){
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


    @ZenMethod
    public static void remove(String oredict) {
        if(oredict == null) {error(name); return;}
        CraftTweakerAPI.apply(new Remove(oredict));    
    }
		    private static class Remove implements IAction {
		    	private String oredict;
		    	public Remove(String oredict) {
		    		super();
		    		this.oredict = oredict;
		    	}
		    	@Override
		    	public void apply() {
		    		for(MaterialCabinetRecipe recipe : recipeList){
		    			if(!Strings.isNullOrEmpty(oredict) && recipe.getOredict().matches(this.oredict)){
		    				recipeList.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public String describe() {
		    		return removeCaption(name);
		    	}
		    }
}