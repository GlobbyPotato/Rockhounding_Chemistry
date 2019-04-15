package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.HeatExchangerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.HeatExchangerRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.HeatExchanger")
public class CT_HeatExchanger extends CTSupport{
	public static String name = "Heat Exchanger";
	public static ArrayList<HeatExchangerRecipe> recipeList = HeatExchangerRecipes.heat_exchanger_recipes;

    @ZenMethod
    public static void add(ILiquidStack input, ILiquidStack output) {
        if(	input == null || output == null 
        || !toFluid(input).getFluid().isGaseous() 
        || !toFluid(output).getFluid().isGaseous() 
        || toFluid(output).getFluid().getTemperature() >= toFluid(input).getFluid().getTemperature()) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new HeatExchangerRecipe(toFluid(input), toFluid(output))));
    }
			private static class Add implements IAction {
		    	private final HeatExchangerRecipe recipe;
		    	public Add(HeatExchangerRecipe recipe){
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
		    		for(HeatExchangerRecipe recipe : recipeList){
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
		    		for(HeatExchangerRecipe recipe : recipeList){
		    			if(this.output != null && recipe.getOutput().isFluidEqual(this.output)){
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