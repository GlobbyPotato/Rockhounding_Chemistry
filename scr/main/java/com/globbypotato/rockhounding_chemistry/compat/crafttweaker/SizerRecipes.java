package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer.SizerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.MineralSizer")
public class SizerRecipes extends CTSupport{
	private static String name = "Mineral Sizer Recipe";

    @ZenMethod
    public static void add(IItemStack input, IItemStack output) {
        if(input == null || output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> probabilities = new ArrayList<Integer>();

       	outputs.add(toStack(output));
       	probabilities.add(100);

        MineTweakerAPI.apply(new AddToSizer(new MineralSizerRecipe(toStack(input), outputs, probabilities, false)));
    }
    @ZenMethod
    public static void add(IItemStack input, IItemStack[] output, int[] probability, boolean comminution) {
        if(input == null || output == null || probability == null || output.length != probability.length) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> probabilities = new ArrayList<Integer>();

        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	probabilities.add(probability[x]);
        }

        MineTweakerAPI.apply(new AddToSizer(new MineralSizerRecipe(toStack(input), outputs, probabilities, false)));
    }
    @ZenMethod
    public static void add(IItemStack input, IItemStack[] output) {
        if(input == null || output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> probabilities = new ArrayList<Integer>();

        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	probabilities.add(x+1);
        }

        MineTweakerAPI.apply(new AddToSizer(new MineralSizerRecipe(toStack(input), outputs, probabilities, true)));
    }
		    private static class AddToSizer implements IUndoableAction {
		    	private MineralSizerRecipe recipe;
		    	public AddToSizer(MineralSizerRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.sizerRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new SizerRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(MineralSizerRecipe recipe : MachineRecipes.sizerRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new SizerRecipeWrapper(recipe));
		    				MachineRecipes.sizerRecipes.remove(recipe);	
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
        MineTweakerAPI.apply(new RemoveFromSizer(toStack(input)));    
    }
		    private static class RemoveFromSizer implements IUndoableAction {
		    	private ItemStack input;
		    	MineralSizerRecipe undoRecipe;
		    	public RemoveFromSizer(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		 for(MineralSizerRecipe recipe : MachineRecipes.sizerRecipes){
		    			if(this.input != null && recipe.getInput().isItemEqual(this.input)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new SizerRecipeWrapper(recipe));
		    				MachineRecipes.sizerRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
 		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.sizerRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new SizerRecipeWrapper(this.undoRecipe));
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