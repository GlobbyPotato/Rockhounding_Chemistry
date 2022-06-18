package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.ShakingTableRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ShakingTableRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.ShakingTable")
public class CT_ShakingTable extends CTSupport{
	public static String name = "Shaking Table Separator";
	public static ArrayList<ShakingTableRecipe> recipeList = ShakingTableRecipes.shaking_table_recipes;

    @ZenMethod
    public static void add(IItemStack input, IItemStack output, String[] slag, int[] quantity, ILiquidStack leachate) {
        if(input == null || output == null || toFluid(leachate).getFluid() == null) {error(name); return;}
       
        ArrayList<String> slags = new ArrayList<String>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        for(int x = 0; x < slag.length; x++){
        	slags.add(slag[x]);
        	quantities.add(quantity[x]);
        }

        CraftTweakerAPI.apply(new Add(new ShakingTableRecipe(toStack(input), toStack(output), slags, quantities, toFluid(leachate))));
    }
			private static class Add implements IAction {
		    	private final ShakingTableRecipe recipe;
		    	public Add(ShakingTableRecipe recipe){
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
		    		for(ShakingTableRecipe recipe : recipeList){
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
		    		for(ShakingTableRecipe recipe : recipeList){
		    			if(!this.output.isEmpty() && recipe.getSlag().isItemEqual(this.output)){
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
    public static void removeByWaste(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new RemoveByWaste(toFluid(input)));    
    }
		    private static class RemoveByWaste implements IAction {
		    	private FluidStack input;
		    	public RemoveByWaste(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(ShakingTableRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getLeachate().isFluidEqual(this.input)){
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