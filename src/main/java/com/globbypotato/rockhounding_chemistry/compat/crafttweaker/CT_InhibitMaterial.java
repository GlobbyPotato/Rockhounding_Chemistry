package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.InhibitMaterial")
public class CT_InhibitMaterial extends CTSupport{
	public static String name = "Inhibit Material";
	public static ArrayList<String> recipeList = MaterialCabinetRecipes.inhibited_material;

    @ZenMethod
    public static void inhibit(String element) {
        if(element == null) {error(name); return;}

        CraftTweakerAPI.apply(new InhibitFromExtractor(element));
    }

		    private static class InhibitFromExtractor implements IAction {
		    	String input;
		    	public InhibitFromExtractor(String input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
	    			if(!recipeList.contains(this.input)){
	    				recipeList.add(this.input.toLowerCase());
	    			}
		    	}
		    	@Override
		    	public String describe() {
		    		return addCaption(name);
		    	}
		    }
}