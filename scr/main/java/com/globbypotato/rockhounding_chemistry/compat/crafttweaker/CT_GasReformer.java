package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.GasReformer")
public class CT_GasReformer extends CTSupport{
	public static String name = "Reforming Reactor";
	public static ArrayList<GasReformerRecipe> recipeList = GasReformerRecipes.gas_reformer_recipes;

    @ZenMethod
    public static void add(ILiquidStack inputA, ILiquidStack inputB, ILiquidStack output, IItemStack catalyst) {
        if(inputA == null || inputB == null || output == null || !toFluid(inputA).getFluid().isGaseous() || !toFluid(inputB).getFluid().isGaseous() || catalyst == null || !toStack(catalyst).getItem().isDamageable()) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new GasReformerRecipe("", toFluid(inputA), toFluid(inputB), toFluid(output), toStack(catalyst))));
    }

    @ZenMethod
    public static void add(String recipename, ILiquidStack inputA, ILiquidStack inputB, ILiquidStack output, IItemStack catalyst) {
        if(inputA == null || inputB == null || output == null || !toFluid(inputA).getFluid().isGaseous() || !toFluid(inputB).getFluid().isGaseous() || catalyst == null || !toStack(catalyst).getItem().isDamageable()) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new GasReformerRecipe(recipename, toFluid(inputA), toFluid(inputB), toFluid(output), toStack(catalyst))));
    }
			private static class Add implements IAction {
		    	private final GasReformerRecipe recipe;
		    	public Add(GasReformerRecipe recipe){
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
    public static void removeByInputA(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new removeByInputA(toFluid(input)));    
    }
    @ZenMethod
    public static void removeByInputB(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new removeByInputB(toFluid(input)));    
    }
    @ZenMethod
    public static void removeByOutput(ILiquidStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(output)));    
    }
		    private static class removeByInputA implements IAction {
		    	private FluidStack input;
		    	public removeByInputA(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(GasReformerRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInputA().isFluidEqual(this.input)){
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
		    private static class removeByInputB implements IAction {
		    	private FluidStack input;
		    	public removeByInputB(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(GasReformerRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInputB().isFluidEqual(this.input)){
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
		    		for(GasReformerRecipe recipe : recipeList){
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