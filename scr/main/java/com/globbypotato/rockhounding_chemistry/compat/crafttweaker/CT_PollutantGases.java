package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PollutantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantGasRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.PollutantGases")
public class CT_PollutantGases extends CTSupport{
	public static String name = "Pollutant Gases";
	public static ArrayList<FluidStack> pollutantList = PollutantRecipes.pollutant_gases;
	public static ArrayList<PollutantGasRecipe> recipeList = PollutantRecipes.pollutant_gas_recipes;

    @ZenMethod
    public static void pollute(ILiquidStack fluid) {
        if(fluid == null || !toFluid(fluid).getFluid().isGaseous()) {error(name); return;}

        CraftTweakerAPI.apply(new DeclarePollutant(toFluid(fluid)));
    }

		    private static class DeclarePollutant implements IAction {
		    	FluidStack input;
		    	public DeclarePollutant(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
	    			if(!pollutantList.contains(this.input)){
	    				pollutantList.add(this.input);
	    				recipeList.add(new PollutantGasRecipe(this.input));
	    			}
		    	}
		    	@Override
		    	public String describe() {
		    		return addCaption(name);
		    	}
		    }
}