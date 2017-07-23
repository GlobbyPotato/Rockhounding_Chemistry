package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer.AnalyzerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.LeachingVat")
public class AnalyzerRecipes {
	private static String name = "Mineral Analyzer Recipe";
    @ZenMethod
    public static void add(IItemStack input, IItemStack output) {
        if(input == null || output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> probabilities = new ArrayList<Integer>();

       	outputs.add(CTSupport.toStack(output));
       	probabilities.add(100);

        MineTweakerAPI.apply(new AddToAnalyzer(new MineralAnalyzerRecipe(CTSupport.toStack(input), outputs, probabilities)));
    }
    @ZenMethod
    public static void add(IItemStack input, IItemStack[] output, int[] probability) {
        if(input == null || output == null || probability == null || output.length != probability.length) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Integer> probabilities = new ArrayList<Integer>();

        for(int x = 0; x < output.length; x++){
        	outputs.add(CTSupport.toStack(output[x]));
        	probabilities.add(probability[x]);
        }

        MineTweakerAPI.apply(new AddToAnalyzer(new MineralAnalyzerRecipe(CTSupport.toStack(input), outputs, probabilities)));
    }
		    private static class AddToAnalyzer implements IUndoableAction {
		    	private MineralAnalyzerRecipe recipe;
		    	public AddToAnalyzer(MineralAnalyzerRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.analyzerRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new AnalyzerRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(MineralAnalyzerRecipe recipe : MachineRecipes.analyzerRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new AnalyzerRecipeWrapper(recipe));
		    				MachineRecipes.analyzerRecipes.remove(recipe);	
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
        MineTweakerAPI.apply(new RemoveFromAnalyzer(CTSupport.toStack(input)));    
    }
		    private static class RemoveFromAnalyzer implements IUndoableAction {
		    	private ItemStack input;
		    	private MineralAnalyzerRecipe undoRecipe;
		    	public RemoveFromAnalyzer(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(MineralAnalyzerRecipe recipe : MachineRecipes.analyzerRecipes){
		    			if(this.input != null && recipe.getInput().isItemEqual(this.input)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new AnalyzerRecipeWrapper(recipe));
		    				MachineRecipes.analyzerRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.analyzerRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new AnalyzerRecipeWrapper(this.undoRecipe));
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