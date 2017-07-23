package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer.AlloyerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.MetalAlloyer")
public class AlloyerRecipes {
	private static String name = "Metal Alloyer Recipe";

    @ZenMethod
    public static void add(String displayName, String[] ingredient, int[] quantity, IItemStack alloy, IItemStack sracp) {
        if(ingredient == null || quantity == null || ingredient.length > 6 || ingredient.length != quantity.length) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<String> ingredients = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();

        for(int x = 0; x < ingredient.length; x++){
        	ingredients.add(ingredient[x]);
        	quantities.add(quantity[x]);
        }

        MineTweakerAPI.apply(new AddToAlloyer(new MetalAlloyerRecipe(displayName, ingredients, quantities, CTSupport.toStack(alloy), CTSupport.toStack(sracp))));
    }
    @ZenMethod
    public static void add(String displayName, String[] ingredient, int[] quantity, IItemStack alloy) {
        if(ingredient == null || quantity == null || ingredient.length > 6 || ingredient.length != quantity.length) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<String> ingredients = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();

        for(int x = 0; x < ingredient.length; x++){
        	ingredients.add(ingredient[x]);
        	quantities.add(quantity[x]);
        }

        MineTweakerAPI.apply(new AddToAlloyer(new MetalAlloyerRecipe(displayName, ingredients, quantities, CTSupport.toStack(alloy), null)));
    }
		    private static class AddToAlloyer implements IUndoableAction {
		    	private MetalAlloyerRecipe recipe;
		    	public AddToAlloyer(MetalAlloyerRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.alloyerRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new AlloyerRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(MetalAlloyerRecipe recipe : MachineRecipes.alloyerRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new AlloyerRecipeWrapper(recipe));
		    				MachineRecipes.alloyerRecipes.remove(recipe);	
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
    public static void remove(IItemStack alloy) {
        if(alloy == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new RemoveFromAlloyer(CTSupport.toStack(alloy)));    
    }
		    private static class RemoveFromAlloyer implements IUndoableAction {
		    	private ItemStack alloy;
		    	private MetalAlloyerRecipe undoRecipe;
		    	public RemoveFromAlloyer(ItemStack alloy) {
		    		super();
		    		this.alloy = alloy;
		    	}
		    	@Override
		    	public void apply() {
		    		for(MetalAlloyerRecipe recipe : MachineRecipes.alloyerRecipes){
		    			if(this.alloy != null && recipe.getOutput().isItemEqual(this.alloy)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new AlloyerRecipeWrapper(recipe));
		    				MachineRecipes.alloyerRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.alloyerRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new AlloyerRecipeWrapper(this.undoRecipe));
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