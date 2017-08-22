package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import com.globbypotato.rockhounding_chemistry.compat.jei.castingbench.CastingRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.CastingRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.CastingBench")
public class CastingRecipes extends CTSupport{
	private static String name = "Casting Bench";

    @ZenMethod
    public static void add(String input, IItemStack output, int casting) {
        if(input == null || output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new AddToCasting(new CastingRecipe(input, toStack(output), casting)));
    }
		    private static class AddToCasting implements IUndoableAction {
		    	private CastingRecipe recipe;
		    	public AddToCasting(CastingRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.castingRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new CastingRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(CastingRecipe recipe : MachineRecipes.castingRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new CastingRecipeWrapper(recipe));
		    				MachineRecipes.castingRecipes.remove(recipe);	
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
        MineTweakerAPI.apply(new RemoveFromCasting(toStack(output)));    
    }
		    private static class RemoveFromCasting implements IUndoableAction {
		    	private ItemStack output;
		    	private CastingRecipe undoRecipe;
		    	public RemoveFromCasting(ItemStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(CastingRecipe recipe : MachineRecipes.castingRecipes){
		    			if(this.output != null && recipe.getOutput().isItemEqual(this.output)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new CastingRecipeWrapper(recipe));
		    				MachineRecipes.castingRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.castingRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new CastingRecipeWrapper(this.undoRecipe));
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