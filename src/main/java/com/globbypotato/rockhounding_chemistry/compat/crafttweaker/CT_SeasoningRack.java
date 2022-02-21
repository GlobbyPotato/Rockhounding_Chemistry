package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.SeasoningRackRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SeasoningRackRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.SeasoningRack")
public class CT_SeasoningRack extends CTSupport{
	public static String name = "Seasoning Rack";
	public static ArrayList<SeasoningRackRecipe> recipeList = SeasoningRackRecipes.seasoning_rack_recipes;

    @ZenMethod
    public static void add(IItemStack input, IItemStack output) {
        if(input == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new SeasoningRackRecipe(toStack(input), toStack(output))));
    }
    @ZenMethod
    public static void add(String oredict, IItemStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new SeasoningRackRecipe(oredict, toStack(output))));
    }
		    private static class Add implements IAction {
		    	private final SeasoningRackRecipe recipe;
		    	public Add(SeasoningRackRecipe recipe){
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
		    		for(SeasoningRackRecipe recipe : recipeList){
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
		    		for(SeasoningRackRecipe recipe : recipeList){
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

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toStack(output)));    
    }
		    private static class RemoveByOutput implements IAction {
		    	private ItemStack output;
		    	public RemoveByOutput(ItemStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(SeasoningRackRecipe recipe : recipeList){
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