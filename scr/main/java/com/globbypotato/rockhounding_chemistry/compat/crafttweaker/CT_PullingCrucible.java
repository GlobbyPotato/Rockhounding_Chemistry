package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PullingCrucibleRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PullingCrucibleRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.PullingCrucible")
public class CT_PullingCrucible extends CTSupport{
	public static String name = "Pulling Crucible";
	public static ArrayList<PullingCrucibleRecipe> recipeList = PullingCrucibleRecipes.pulling_crucible_recipes;

    @ZenMethod
    public static void add(IItemStack input, IItemStack dopant, IItemStack output) {
        if(input == null || dopant == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new PullingCrucibleRecipe(toStack(input), toStack(dopant), toStack(output))));
    }
    @ZenMethod
    public static void add(String input, IItemStack dopant, IItemStack output) {
        if(input == null || dopant == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new PullingCrucibleRecipe(input, toStack(dopant), toStack(output))));
    }
    @ZenMethod
    public static void add(IItemStack input, String dopant, IItemStack output) {
        if(input == null || dopant == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new PullingCrucibleRecipe(toStack(input), dopant, toStack(output))));
    }
    @ZenMethod
    public static void add(String input, String dopant, IItemStack output) {
        if(input == null || dopant == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new PullingCrucibleRecipe(input, dopant, toStack(output))));
    }
		    private static class Add implements IAction {
		    	private final PullingCrucibleRecipe recipe;
		    	public Add(PullingCrucibleRecipe recipe){
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
		    		for(PullingCrucibleRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && !recipe.getType1() && recipe.getInput().isItemEqual(this.input)){
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
    public static void removeByInputOredict(String input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByInputOredict(input));    
    }
		    private static class RemoveByInputOredict implements IAction {
		    	private String input;
		    	public RemoveByInputOredict(String input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(PullingCrucibleRecipe recipe : recipeList){
		    			if(!Strings.isNullOrEmpty(input) && recipe.getType1() && recipe.getOredict1().matches(this.input)){
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
    public static void removeByDopant(IItemStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByDopant(toStack(input)));    
    }
		    private static class RemoveByDopant implements IAction {
		    	private ItemStack input;
		    	public RemoveByDopant(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(PullingCrucibleRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && !recipe.getType2() && recipe.getDopant().isItemEqual(this.input)){
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
    public static void removeByDopantOredict(String input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByDopantOredict(input));    
    }
		    private static class RemoveByDopantOredict implements IAction {
		    	private String input;
		    	public RemoveByDopantOredict(String input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(PullingCrucibleRecipe recipe : recipeList){
		    			if(!Strings.isNullOrEmpty(input) && recipe.getType2() && recipe.getOredict2().matches(this.input)){
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
		    		for(PullingCrucibleRecipe recipe : recipeList){
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