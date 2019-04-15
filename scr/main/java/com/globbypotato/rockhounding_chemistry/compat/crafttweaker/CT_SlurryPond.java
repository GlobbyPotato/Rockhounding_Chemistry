package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryPondRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryPondRecipe;
import com.google.common.base.Strings;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.SlurryPond")
public class CT_SlurryPond extends CTSupport{
	public static String name = "Slurry Pond";
	public static ArrayList<SlurryPondRecipe> recipeList = SlurryPondRecipes.slurry_pond_recipes;

    @ZenMethod
    public static void add(IItemStack input, ILiquidStack bath, ILiquidStack output) {
        if(input == null || bath == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new SlurryPondRecipe(toStack(input), toFluid(bath), toFluid(output))));
    }
    @ZenMethod
    public static void add(String oredict, ILiquidStack bath, ILiquidStack output) {
        if(bath == null || output == null) {error(name); return;}
        CraftTweakerAPI.apply(new Add(new SlurryPondRecipe(oredict, toFluid(bath), toFluid(output))));
    }
			private static class Add implements IAction {
		    	private final SlurryPondRecipe recipe;
		    	public Add(SlurryPondRecipe recipe){
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
		    private static class RemoveByInput implements IAction {
		    	private ItemStack input;
		    	public RemoveByInput(ItemStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(SlurryPondRecipe recipe : recipeList){
		    			if(!this.input.isEmpty() && !recipe.getType() && recipe.getInput().isItemEqual(this.input)){
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

    @ZenMethod
    public static void removeByOredict(String input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOredict(input));    
    }
		    private static class RemoveByOredict implements IAction {
		    	private String input;
		    	public RemoveByOredict(String input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(SlurryPondRecipe recipe : recipeList){
		    			if(!Strings.isNullOrEmpty(input) && recipe.getType() && recipe.getOredict().matches(this.input)){
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

    @ZenMethod
    public static void removeByOutput(ILiquidStack output) {
        if(output == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByOutput(toFluid(output)));    
    }
		    private static class RemoveByOutput implements IAction {
		    	private FluidStack output;
		    	public RemoveByOutput(FluidStack output) {
		    		super();
		    		this.output = output;
		    	}
		    	@Override
		    	public void apply() {
		    		for(SlurryPondRecipe recipe : recipeList){
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