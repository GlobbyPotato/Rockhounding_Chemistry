package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition.DepositionRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.DepositionChamber")
public class DepositionRecipes {
	private static String name = "Deposition Chamber Recipe";

    @ZenMethod
    public static void add(IItemStack input, IItemStack output, ILiquidStack solvent, int solventAmount, int temperature, int pressure) {
        if(input == null || solvent == null || output == null || temperature == 0 || pressure == 0) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        FluidStack solventStack = CTSupport.getFluid(solvent, solventAmount);
        MineTweakerAPI.apply(new AddToDeposition(new DepositionChamberRecipe(CTSupport.toStack(input), CTSupport.toStack(output), solventStack, temperature, pressure)));
    }
		    private static class AddToDeposition implements IUndoableAction {
		    	private DepositionChamberRecipe recipe;
		    	public AddToDeposition(DepositionChamberRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.depositionRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new DepositionRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(DepositionChamberRecipe recipe : MachineRecipes.depositionRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new DepositionRecipeWrapper(recipe));
		    				MachineRecipes.depositionRecipes.remove(recipe);	
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
    public static void remove(IItemStack output) {
        if(output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new RemoveFromDeposition(CTSupport.toStack(output)));    
    }
		    private static class RemoveFromDeposition implements IUndoableAction {
		    	private ItemStack output;
		    	private DepositionChamberRecipe undoRecipe;
		    	public RemoveFromDeposition(ItemStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(DepositionChamberRecipe recipe : MachineRecipes.depositionRecipes){
		    			if(this.output != null && recipe.getOutput().isItemEqual(this.output)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new DepositionRecipeWrapper(recipe));
		    				MachineRecipes.depositionRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.depositionRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new DepositionRecipeWrapper(this.undoRecipe));
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