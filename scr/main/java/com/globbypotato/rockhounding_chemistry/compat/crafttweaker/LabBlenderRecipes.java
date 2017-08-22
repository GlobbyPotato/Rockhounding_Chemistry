package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.compat.jei.labblender.BlenderRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.LabBlender")
public class LabBlenderRecipes extends CTSupport{
	private static String name = "Lab Blender";

    @ZenMethod
    public static void add(IItemStack[] ingr, IItemStack output) {

        ArrayList<ItemStack> input = new ArrayList<ItemStack>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();

        for(int x = 0; x < ingr.length; x++){
       		input.add( toStack(ingr[x]) );	
        	quantities.add(toStack(ingr[x]).stackSize);
        }
    	if(input.size() <= 0 || input == null || output == null) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new AddToBlender(new LabBlenderRecipe(input, toStack(output))));
    }

    @ZenMethod
    public static void add(String[] oredict, int[] quantity, IItemStack output) {

        ArrayList<ItemStack> input = new ArrayList<ItemStack>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
		for(int x = 0; x < oredict.length; x++){
			List<ItemStack> ores = OreDictionary.getOres(oredict[x]);
			if(ores.size() > 0){
				ItemStack entryStack = ores.get(0);
				entryStack.stackSize = quantity[x];
				input.add(entryStack);
			}
		}
    	if(input == null || output == null || input.size() != quantity.length) {MineTweakerAPI.logError(name + ": Invalid recipe."); return;}
        MineTweakerAPI.apply(new AddToBlender(new LabBlenderRecipe(input, toStack(output))));
    }

		    private static class AddToBlender implements IUndoableAction {
		    	private LabBlenderRecipe recipe;
		    	public AddToBlender(LabBlenderRecipe recipe){
		          super();
		          this.recipe = recipe;
		    	}
		    	@Override
		    	public void apply() {
		    		MachineRecipes.blenderRecipes.add(this.recipe);
		    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new BlenderRecipeWrapper(this.recipe));
		    	}
		    	@Override
		    	public void undo() {
		    		for(LabBlenderRecipe recipe : MachineRecipes.blenderRecipes){
		    			if(recipe.equals(this.recipe)){
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new BlenderRecipeWrapper(recipe));
		    				MachineRecipes.blenderRecipes.remove(recipe);	
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
		    	private LabBlenderRecipe undoRecipe;
		    	public RemoveFromCasting(ItemStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(LabBlenderRecipe recipe : MachineRecipes.blenderRecipes){
		    			if(this.output != null && recipe.getOutput().isItemEqual(this.output)){
				    		this.undoRecipe = recipe;
		    				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new BlenderRecipeWrapper(recipe));
		    				MachineRecipes.blenderRecipes.remove(recipe);	
	                        break;
		    			}
		    		}
		    	}
		    	@Override
		    	public void undo() {
		    		if(this.undoRecipe != null){
			    		MachineRecipes.blenderRecipes.add(this.undoRecipe);
			    		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new BlenderRecipeWrapper(this.undoRecipe));
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