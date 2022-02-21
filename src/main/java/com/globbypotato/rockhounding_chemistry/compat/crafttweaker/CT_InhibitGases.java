package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GanPlantRecipes;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.InhibitGases")
public class CT_InhibitGases extends CTSupport{
	public static String name = "Inhibit Gases";
	public static ArrayList<String> recipeList = GanPlantRecipes.inhibited_gases;

    @ZenMethod
    public static void inhibit(String element) {
        if(element == null) {error(name); return;}

        CraftTweakerAPI.apply(new InhibitFromGan(element));
    }

		    private static class InhibitFromGan implements IAction {
		    	String input;
		    	public InhibitFromGan(String input) {
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