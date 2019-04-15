package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.MineralSizer")
public class CT_MineralSizer extends CTSupport{
	public static String name = "Mineral Sizer";
	public static ArrayList<MineralSizerRecipe> recipeList = MineralSizerRecipes.mineral_sizer_recipes;

    @ZenMethod
    public static void add(IItemStack input, IItemStack[] output, int[] comminution) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> comminutions = new ArrayList<Integer>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	comminutions.add(comminution[x]);
        }
        CraftTweakerAPI.apply(new Add(new MineralSizerRecipe(toStack(input), outputs, comminutions)));
    }
    @ZenMethod
    public static void add(IItemStack input, IItemStack output) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> comminutions = new ArrayList<Integer>();
       	outputs.add(toStack(output));
       	comminutions.add(0);
        CraftTweakerAPI.apply(new Add(new MineralSizerRecipe(toStack(input), outputs, comminutions)));
    }
    @ZenMethod
    public static void add(String input, IItemStack[] output, int[] comminution) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> comminutions = new ArrayList<Integer>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	comminutions.add(comminution[x]);
        }
        CraftTweakerAPI.apply(new Add(new MineralSizerRecipe(input, outputs, comminutions)));
    }
    @ZenMethod
    public static void add(String input, IItemStack output) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> comminutions = new ArrayList<Integer>();
       	outputs.add(toStack(output));
       	comminutions.add(0);
        CraftTweakerAPI.apply(new Add(new MineralSizerRecipe(input, outputs, comminutions)));
    }	
    		
    		private static class Add implements IAction {
		    	private final MineralSizerRecipe recipe;
		    	public Add(MineralSizerRecipe recipe){
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
    @ZenMethod
    public static void removeByInput(IItemStack input) {
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
		    		for(MineralSizerRecipe recipe : recipeList){
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
		    		for(MineralSizerRecipe recipe : recipeList){
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