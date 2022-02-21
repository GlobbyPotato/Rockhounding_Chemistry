package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.ChemicalExtractor")
public class CT_ChemicalExtractor extends CTSupport{
	public static String name = "Chemical Extractor";
	public static ArrayList<ChemicalExtractorRecipe> recipeList = ChemicalExtractorRecipes.extractor_recipes;

    @ZenMethod
    public static void add(String category, IItemStack input, String[] output, int[] quantity) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<String> outputs = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(output[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new ChemicalExtractorRecipe(category, toStack(input), outputs, quantities)));
    }

    @ZenMethod
    public static void add(String category, String input, String[] output, int[] quantity) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<String> outputs = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(output[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new ChemicalExtractorRecipe(category, input, outputs, quantities)));
    }
    		private static class Add implements IAction {
		    	private final ChemicalExtractorRecipe recipe;
		    	public Add(ChemicalExtractorRecipe recipe){
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
        CraftTweakerAPI.apply(new RemoveByInput(toStack(input)));    
    }
    @ZenMethod
    public static void removeByInput(IItemStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByInput(toStack(input)));    
    }
		    private static class RemoveByInput implements IAction {
		    	private ItemStack input;
		    	public RemoveByInput(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(ChemicalExtractorRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && !recipe.getType() && recipe.getInput().isItemEqual(this.input)){
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

    @ZenMethod
    public static void removeByOredict(String input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOredict(input));    
    }
		    private static class RemoveByOredict implements IAction {
		    	private String input;
		    	public RemoveByOredict(String input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(ChemicalExtractorRecipe recipe : recipeList){
		    			if(!Strings.isNullOrEmpty(input) && recipe.getType() && recipe.getOredict().matches(this.input)){
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