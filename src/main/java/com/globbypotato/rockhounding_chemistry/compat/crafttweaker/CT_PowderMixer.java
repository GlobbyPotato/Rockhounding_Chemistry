package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PowderMixerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PowderMixerRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.PowderMixer")
public class CT_PowderMixer extends CTSupport{
	public static String name = "Powder Mixer";
	public static ArrayList<PowderMixerRecipe> recipeList = PowderMixerRecipes.powder_mixer_recipes;

    @ZenMethod
    public static void add(String[] slag, int[] quantity, IItemStack output) {
        if(output == null) {error(name); return;}
       
        ArrayList<String> slags = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < slag.length; x++){
        	slags.add(slag[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new PowderMixerRecipe(slags, quantities, toStack(output))));
    }
			private static class Add implements IAction {
		    	private final PowderMixerRecipe recipe;
		    	public Add(PowderMixerRecipe recipe){
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
    public static void removeByOutput(IItemStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toStack(output)));    
    }
		    private static class RemoveByOutput implements IAction {
		    	private ItemStack output;
		    	public RemoveByOutput(ItemStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(PowderMixerRecipe recipe : recipeList){
		    			if(!this.output.isEmpty() && recipe.getOutput().isItemEqual(this.output)){
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