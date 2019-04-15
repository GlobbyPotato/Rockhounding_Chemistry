package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PrecipitationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PrecipitationRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.Precipitator")
public class CT_Precipitator extends CTSupport{
	public static String name = "Precipitation Chamber";
	public static ArrayList<PrecipitationRecipe> recipeList = PrecipitationRecipes.precipitation_recipes;

    @ZenMethod
    public static void add(String recipename, IItemStack input, IItemStack catalyst, ILiquidStack solvent, ILiquidStack solution, IItemStack byproduct) {
        if(	   (input == null && catalyst == null) 
        	|| (catalyst != null && !toStack(catalyst).getItem().isDamageable()) 
        	|| solvent == null || solution == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new PrecipitationRecipe(recipename, toStack(input), toStack(catalyst), toFluid(solvent), toFluid(solution), toStack(byproduct))));
    }
    @ZenMethod
    public static void add(String recipename, String input, IItemStack catalyst, ILiquidStack solvent, ILiquidStack solution, IItemStack byproduct) {
        if(	   (input == null && catalyst == null) 
        	|| (catalyst != null && !toStack(catalyst).getItem().isDamageable()) 
        	|| solvent == null || solution == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new PrecipitationRecipe(recipename, input, toStack(catalyst), toFluid(solvent), toFluid(solution), toStack(byproduct))));
    }
			private static class Add implements IAction {
		    	private final PrecipitationRecipe recipe;
		    	public Add(PrecipitationRecipe recipe){
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
		    		for(PrecipitationRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && !recipe.getType() && recipe.getSolute().isItemEqual(this.input)){
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
		    		for(PrecipitationRecipe recipe : recipeList){
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
    public static void removeByOutput(ILiquidStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(output)));    
    }
		    private static class RemoveByOutput implements IAction {
		    	private FluidStack output;
		    	public RemoveByOutput(FluidStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(PrecipitationRecipe recipe : recipeList){
		    			if(this.output != null && recipe.getSolution().isFluidEqual(this.output)){
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
    public static void removeByPrecipitate(IItemStack precipitate) {
        if(precipitate == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByInput(toStack(precipitate)));    
    }
		    private static class RemoveByPrecipitate implements IAction {
		    	private ItemStack input;
		    	public RemoveByPrecipitate(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(PrecipitationRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && recipe.getSolute().isItemEqual(this.input)){
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