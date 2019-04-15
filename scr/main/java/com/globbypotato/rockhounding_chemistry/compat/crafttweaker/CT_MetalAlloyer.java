package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.MetalAlloyer")
public class CT_MetalAlloyer extends CTSupport{
	public static String name = "Metal Alloyer";
	public static ArrayList<MetalAlloyerRecipe> recipeList = MetalAlloyerRecipes.metal_alloyer_recipes;

    @ZenMethod
    public static void add(String[] input, int[] quantity, IItemStack output) {
        if(input == null || output == null || input.length != quantity.length) {error(name); return;}

        ArrayList<String> inputs = new ArrayList<String>();
        for(int x = 0; x < input.length; x++){inputs.add(input[x]);}
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < quantity.length; x++){quantities.add(quantity[x]);}

        CraftTweakerAPI.apply(new Add(new MetalAlloyerRecipe(inputs, quantities, toStack(output))));
    }
		    private static class Add implements IAction {
		    	private final MetalAlloyerRecipe recipe;
		    	public Add(MetalAlloyerRecipe recipe){
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
    public static void remove(IItemStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new Remove(toStack(input)));    
    }
		    private static class Remove implements IAction {
		    	private ItemStack input;
		    	public Remove(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(MetalAlloyerRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && recipe.getOutput().isItemEqual(this.input)){
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