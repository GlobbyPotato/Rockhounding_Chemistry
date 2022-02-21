package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.LeachingVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.LeachingVat")
public class CT_LeachingVat extends CTSupport{
	public static String name = "Leaching Vat";
	public static ArrayList<LeachingVatRecipe> recipeList = LeachingVatRecipes.leaching_vat_recipes;

    @ZenMethod
    public static void add(IItemStack input, IItemStack[] output, float[] gravity, ILiquidStack pulp) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Float> gravities = new ArrayList<Float>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	gravities.add(gravity[x]);
        }
        CraftTweakerAPI.apply(new Add(new LeachingVatRecipe(toStack(input), outputs, gravities, toFluid(pulp))));
    }
    @ZenMethod
    public static void add(String input, IItemStack[] output, float[] gravity, ILiquidStack pulp) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Float> gravities = new ArrayList<Float>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	gravities.add(gravity[x]);
        }
        CraftTweakerAPI.apply(new Add(new LeachingVatRecipe(input, outputs, gravities, toFluid(pulp))));
    }
		    private static class Add implements IAction {
		    	private final LeachingVatRecipe recipe;
		    	public Add(LeachingVatRecipe recipe){
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
		    		for(LeachingVatRecipe recipe : recipeList){
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
		    		for(LeachingVatRecipe recipe : recipeList){
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