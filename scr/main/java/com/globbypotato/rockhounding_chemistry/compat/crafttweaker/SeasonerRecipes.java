package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning.SeasonerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SaltSeasonerRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.SeasoningRack")
public class SeasonerRecipes {
	private static String name = "Seasoning Rack Recipe";

    @ZenMethod
    public static void add(IItemStack input, IItemStack output) {
        if(input == null || output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new AddToSeasoner(new SaltSeasonerRecipe(CTSupport.toStack(input), CTSupport.toStack(output))));
    }
		    private static class AddToSeasoner implements IUndoableAction {
		    	private SaltSeasonerRecipe recipe;
		    	public AddToSeasoner(SaltSeasonerRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.seasonerRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new SeasonerRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(SaltSeasonerRecipe recipe : MachineRecipes.seasonerRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new SeasonerRecipeWrapper(recipe));
		    				MachineRecipes.seasonerRecipes.remove(recipe);	
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
    public static void remove(IItemStack input) {
        if(input == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new RemoveFromSeasoner(CTSupport.toStack(input)));    
    }
		    private static class RemoveFromSeasoner implements IUndoableAction {
		    	private ItemStack input;
		    	private SaltSeasonerRecipe undoRecipe;
		    	public RemoveFromSeasoner(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(SaltSeasonerRecipe recipe : MachineRecipes.seasonerRecipes){
		    			if(this.input != null && recipe.getInput().isItemEqual(this.input)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new SeasonerRecipeWrapper(recipe));
		    				MachineRecipes.seasonerRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.seasonerRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new SeasonerRecipeWrapper(this.undoRecipe));
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