package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.StirredTankRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.StirredTankRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.StirredTank")
public class CT_StirredTank extends CTSupport{
	public static String name = "Continous Stirred Tank";
	public static ArrayList<StirredTankRecipe> recipeList = StirredTankRecipes.stirred_tank_recipes;

    @ZenMethod
    public static void add(ILiquidStack solvent, ILiquidStack reagent, ILiquidStack solution, ILiquidStack fume, int voltage) {
        if(solvent == null || reagent == null || solution == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new StirredTankRecipe(toFluid(solvent), toFluid(reagent), toFluid(solution), toFluid(fume), voltage)));
    }
			private static class Add implements IAction {
		    	private final StirredTankRecipe recipe;
		    	public Add(StirredTankRecipe recipe){
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
		    public static void removeBySolvent(ILiquidStack solvent) {
		        if(solvent == null) {error(name); return;}
		        CraftTweakerAPI.apply(new RemoveBySolvent(toFluid(solvent)));    
		    }
		    @ZenMethod
		    public static void removeByReagent(ILiquidStack reagent) {
		        if(reagent == null) {error(name); return;}
		        CraftTweakerAPI.apply(new RemoveByReagent(toFluid(reagent)));    
		    }
		    @ZenMethod
		    public static void removeBySolution(ILiquidStack solution) {
		        if(solution == null) {error(name); return;}
		        CraftTweakerAPI.apply(new RemoveBySolution(toFluid(solution)));    
		    }
				    private static class RemoveBySolvent implements IAction {
				    	private FluidStack solvent;
				    	public RemoveBySolvent(FluidStack solvent) {
				    		super();
				    		this.solvent = solvent;
				    	}
				    	@Override
				    	public void apply() {
				    		for(StirredTankRecipe recipe : recipeList){
				    			if(this.solvent != null && recipe.getSolvent().isFluidEqual(this.solvent)){
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
				    private static class RemoveByReagent implements IAction {
				    	private FluidStack reagent;
				    	public RemoveByReagent(FluidStack reagent) {
				    		super();
				    		this.reagent = reagent;
				    	}
				    	@Override
				    	public void apply() {
				    		for(StirredTankRecipe recipe : recipeList){
				    			if(this.reagent != null && recipe.getReagent().isFluidEqual(this.reagent)){
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
				    private static class RemoveBySolution implements IAction {
				    	private FluidStack solution;
				    	public RemoveBySolution(FluidStack solution) {
				    		super();
				    		this.solution = solution;
				    	}
				    	@Override
				    	public void apply() {
				    		for(StirredTankRecipe recipe : recipeList){
				    			if(this.solution != null && recipe.getSolution().isFluidEqual(this.solution)){
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