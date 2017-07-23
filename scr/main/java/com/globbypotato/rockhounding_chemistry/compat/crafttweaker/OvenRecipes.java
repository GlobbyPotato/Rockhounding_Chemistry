package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import com.globbypotato.rockhounding_chemistry.compat.jei.laboven.LabOvenRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.LabOven")
public class OvenRecipes {
	private static String name = "Lab Oven Recipe";

    @ZenMethod
    public static void add(IItemStack solute, boolean catalyst, ILiquidStack solvent, int solventAmount, ILiquidStack reagent, int reagentAmount, ILiquidStack solution, int solutionAmount) {
        if(solute == null || solvent == null || solution == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        FluidStack solventStack = CTSupport.getFluid(solvent, solventAmount);
        FluidStack reagentStack = CTSupport.getFluid(reagent, reagentAmount);
        FluidStack solutionStack = CTSupport.getFluid(solution, solutionAmount);
        MineTweakerAPI.apply(new AddToOven(new LabOvenRecipe(CTSupport.toStack(solute), catalyst, solventStack, reagentStack, solutionStack)));
    }
    @ZenMethod
    public static void add(IItemStack solute, boolean catalyst, ILiquidStack solvent, int solventAmount, ILiquidStack solution, int solutionAmount) {
        if(solute == null || solvent == null || solution == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        FluidStack solventStack = CTSupport.getFluid(solvent, solventAmount);
        FluidStack solutionStack = CTSupport.getFluid(solution, solutionAmount);
        MineTweakerAPI.apply(new AddToOven(new LabOvenRecipe(CTSupport.toStack(solute), catalyst, solventStack, null, solutionStack)));
    }
		    private static class AddToOven implements IUndoableAction {
		    	private LabOvenRecipe recipe;
		    	public AddToOven(LabOvenRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.labOvenRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new LabOvenRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(LabOvenRecipe recipe : MachineRecipes.labOvenRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new LabOvenRecipeWrapper(recipe));
		    				MachineRecipes.labOvenRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public boolean canUndo() {return true;}
		    	@Override
		    	public String describe() {return null;}
		    	@Override
		    	public String describeUndo() {return null;}
		    	@Override
		    	public Object getOverrideKey() {return null;}
		    }


    @ZenMethod
    public static void remove(ILiquidStack solution) {
        if(solution == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new RemoveFromOven(CTSupport.toFluid(solution)));    
    }
		    private static class RemoveFromOven implements IUndoableAction {
		    	private FluidStack solution;
		    	private LabOvenRecipe undoRecipe;
		    	public RemoveFromOven(FluidStack solution) {
		    		super();
		    		this.solution = solution;
		    	}
		    	@Override
		    	public void apply() {
		    		for(LabOvenRecipe recipe : MachineRecipes.labOvenRecipes){
		    			if(this.solution != null && recipe.getOutput().isFluidEqual(this.solution)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new LabOvenRecipeWrapper(recipe));
		    				MachineRecipes.labOvenRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.labOvenRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new LabOvenRecipeWrapper(this.undoRecipe));
		    		}
		    	}
		    	@Override
		    	public boolean canUndo() {return true;}
		    	@Override
		    	public String describe() {return null;}
		    	@Override
		    	public String describeUndo() {return null;}
		    	@Override
		    	public Object getOverrideKey() {return null;}
		    }
}