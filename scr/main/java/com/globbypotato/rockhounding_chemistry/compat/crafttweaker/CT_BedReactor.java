package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.BedReactorRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.BedReactor")
public class CT_BedReactor extends CTSupport{
	public static String name = "Fixed Bed Reactor";
	public static ArrayList<BedReactorRecipe> recipeList = BedReactorRecipes.bed_reactor_recipes;

    @ZenMethod
    public static void add(String recipename, ILiquidStack input1, ILiquidStack input2, ILiquidStack input3, ILiquidStack input4, ILiquidStack output, IItemStack catalyst) {
        if(input1 == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new BedReactorRecipe(recipename, toFluid(input1), toFluid(input2), toFluid(input3), toFluid(input4), toFluid(output), toStack(catalyst))));
    }
			private static class Add implements IAction {
		    	private final BedReactorRecipe recipe;
		    	public Add(BedReactorRecipe recipe){
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
    public static void removeByInput1(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new removeByInput1(toFluid(input)));    
    }
    @ZenMethod
    public static void removeByInput2(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new removeByInput2(toFluid(input)));    
    }
    @ZenMethod
    public static void removeByInput3(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new removeByInput3(toFluid(input)));    
    }
    @ZenMethod
    public static void removeByInput4(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new removeByInput4(toFluid(input)));    
    }
    @ZenMethod
    public static void removeByOutput(ILiquidStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(output)));    
    }
		    private static class removeByInput1 implements IAction {
		    	private FluidStack input;
		    	public removeByInput1(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(BedReactorRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInput1().isFluidEqual(this.input)){
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
		    private static class removeByInput2 implements IAction {
		    	private FluidStack input;
		    	public removeByInput2(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(BedReactorRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInput2().isFluidEqual(this.input)){
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
		    private static class removeByInput3 implements IAction {
		    	private FluidStack input;
		    	public removeByInput3(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(BedReactorRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInput3().isFluidEqual(this.input)){
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
		    private static class removeByInput4 implements IAction {
		    	private FluidStack input;
		    	public removeByInput4(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(BedReactorRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInput4().isFluidEqual(this.input)){
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
		    		for(BedReactorRecipe recipe : recipeList){
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