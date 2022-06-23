package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.LabBlender")
public class CT_LabBlender extends CTSupport{
	public static String name = "Lab Blender";
	public static ArrayList<LabBlenderRecipe> recipeList = LabBlenderRecipes.lab_blender_recipes;

    @ZenMethod
    public static void add(String[] element, int[] quantity, IItemStack output) {
        if(output == null) {error(name); return;}

        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < element.length; x++){
        	inputs.add(element[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new LabBlenderRecipe(inputs, quantities, toStack(output))));
    }
		    private static class Add implements IAction {
		    	private final LabBlenderRecipe recipe;
		    	public Add(LabBlenderRecipe recipe){
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
    public static void remove(IItemStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Remove(toStack(output)));    
    }
		    private static class Remove implements IAction {
		    	private ItemStack output;
		    	public Remove(ItemStack input) {
		    		super();
		    		this.output = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(LabBlenderRecipe recipe : recipeList){
		    			if(!this.output.isEmpty() && recipe.getOutput().isItemEqual(this.output)){
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