package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.OrbiterRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.OrbiterRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.Orbiter")
public class CT_Orbiter extends CTSupport{
	public static String name = "Orbiter";
	public static ArrayList<OrbiterRecipe> recipeList = OrbiterRecipes.exp_recipes;

    @ZenMethod
    public static void add(ILiquidStack input) {
        if(	input == null
        || !toFluid(input).getFluid().isGaseous()) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new OrbiterRecipe(toFluid(input))));
    }
			private static class Add implements IAction {
		    	private final OrbiterRecipe recipe;
		    	public Add(OrbiterRecipe recipe){
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
    public static void remove(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new Remove(toFluid(input)));    
    }
		    private static class Remove implements IAction {
		    	private FluidStack input;
		    	public Remove(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(OrbiterRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInput().isFluidEqual(this.input)){
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