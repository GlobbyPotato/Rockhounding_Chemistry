package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.TransposerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.TransposerRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.Transposer")
public class CT_Transposer extends CTSupport{
	public static String name = "Transposer";
	public static ArrayList<TransposerRecipe> recipeList = TransposerRecipes.transposer_recipes;

    @ZenMethod
    public static void add(ILiquidStack input, ILiquidStack output) {
        if(input == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new TransposerRecipe(toFluid(input), toFluid(output))));
    }
			private static class Add implements IAction {
		    	private final TransposerRecipe recipe;
		    	public Add(TransposerRecipe recipe){
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
		        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(input)));    
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
				    		for(TransposerRecipe recipe : recipeList){
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
				    		for(TransposerRecipe recipe : recipeList){
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