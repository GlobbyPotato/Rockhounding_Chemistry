package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryDrumRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryDrumRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.SlurryDrum")
public class CT_SlurryDrum extends CTSupport{
	public static String name = "Slurry Drum";
	public static ArrayList<SlurryDrumRecipe> recipeList = SlurryDrumRecipes.slurry_drum_recipes;

    @ZenMethod
    public static void add(IItemStack input, ILiquidStack output) {
        if(input == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new SlurryDrumRecipe(toStack(input), toFluid(output))));
    }
			private static class Add implements IAction {
		    	private final SlurryDrumRecipe recipe;
		    	public Add(SlurryDrumRecipe recipe){
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
		    public static void removeByInput(IItemStack input) {
		        if(input == null) {error(name); return;}
		        CraftTweakerAPI.apply(new RemoveByInput(toStack(input)));    
		    }
		    @ZenMethod
		    public static void removeByOutput(ILiquidStack output) {
		        if(output == null) {error(name); return;}
		        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(output)));    
		    }
				    private static class RemoveByInput implements IAction {
				    	private ItemStack input;
				    	public RemoveByInput(ItemStack input) {
				    		super();
				    		this.input = input;
				    	}
				    	@Override
				    	public void apply() {
				    		for(SlurryDrumRecipe recipe : recipeList){
				    			if(!this.input.isEmpty() && recipe.getInput().isItemEqual(this.input)){
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
				    private static class RemoveByOutput implements IAction {
				    	private FluidStack output;
				    	public RemoveByOutput(FluidStack output) {
				    		super();
				    		this.output = output;
				    	}
				    	@Override
				    	public void apply() {
				    		for(SlurryDrumRecipe recipe : recipeList){
				    			if(this.output != null && recipe.getOutput().isFluidEqual(this.output)){
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