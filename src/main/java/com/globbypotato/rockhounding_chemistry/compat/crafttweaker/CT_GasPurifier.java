package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasPurifierRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.GasPurifier")
public class CT_GasPurifier extends CTSupport{
	public static String name = "Gas Purifier";
	public static ArrayList<GasPurifierRecipe> recipeList = GasPurifierRecipes.gas_purifier_recipes;

    @ZenMethod
    public static void add(ILiquidStack input, ILiquidStack output, String[] slag, int[] quantity) {
        if(input == null || output == null || !toFluid(input).getFluid().isGaseous() || !toFluid(output).getFluid().isGaseous()) {error(name); return;}
       
        ArrayList<String> slags = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < slag.length; x++){
        	slags.add(slag[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new GasPurifierRecipe(toFluid(input), toFluid(output), slags, quantities)));
    }
			private static class Add implements IAction {
		    	private final GasPurifierRecipe recipe;
		    	public Add(GasPurifierRecipe recipe){
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
    public static void removeByInput(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByInput(toFluid(input)));    
    }
		    private static class RemoveByInput implements IAction {
		    	private FluidStack input;
		    	public RemoveByInput(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(GasPurifierRecipe recipe : recipeList){
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
		    		for(GasPurifierRecipe recipe : recipeList){
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