package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.GasifierPlant")
public class CT_GasifierPlant extends CTSupport{
	public static String name = "Gasification Plant";
	public static ArrayList<GasifierPlantRecipe> recipeList = GasifierPlantRecipes.gasifier_plant_recipes;

    @ZenMethod
    public static void add(ILiquidStack input, ILiquidStack reactant, ILiquidStack output, String[] slag, int[] quantity, int temperature) {
        if(input == null || reactant == null || output == null || !toFluid(output).getFluid().isGaseous()) {error(name); return;}
        
        ArrayList<String> slags = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < slag.length; x++){
        	slags.add(slag[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new GasifierPlantRecipe(toFluid(input), toFluid(reactant), toFluid(output), slags, quantities, temperature)));
    }
			private static class Add implements IAction {
		    	private final GasifierPlantRecipe recipe;
		    	public Add(GasifierPlantRecipe recipe){
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
    @ZenMethod
    public static void removeByOutput(ILiquidStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(output)));    
    }
		    private static class RemoveByInput implements IAction {
		    	private FluidStack input;
		    	public RemoveByInput(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(GasifierPlantRecipe recipe : recipeList){
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
		    private static class RemoveByOutput implements IAction {
		    	private FluidStack output;
		    	public RemoveByOutput(FluidStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(GasifierPlantRecipe recipe : recipeList){
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