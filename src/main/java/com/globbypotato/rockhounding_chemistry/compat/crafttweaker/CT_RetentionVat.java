package com.globbypotato.rockhounding_chemistry.compat.crafttweaker;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.RetentionVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.rockhounding_chemistry.RetentionVat")
public class CT_RetentionVat extends CTSupport{
	public static String name = "Retention Vat";
	public static ArrayList<RetentionVatRecipe> recipeList = RetentionVatRecipes.retention_vat_recipes;

    @ZenMethod
    public static void add(ILiquidStack input, IItemStack[] output, float[] gravity) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Float> gravities = new ArrayList<Float>();
        for(int x = 0; x < output.length; x++){
        	outputs.add(toStack(output[x]));
        	gravities.add(gravity[x]);
        }

        CraftTweakerAPI.apply(new Add(new RetentionVatRecipe(toFluid(input), outputs, gravities)));
    }
    @ZenMethod
    public static void add(ILiquidStack input, IItemStack output, float gravity) {
        if(input == null || output == null) {error(name); return;}

        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        ArrayList<Float> gravities = new ArrayList<Float>();
       	outputs.add(toStack(output));
       	gravities.add(gravity);

        CraftTweakerAPI.apply(new Add(new RetentionVatRecipe(toFluid(input), outputs, gravities)));
    }
		    private static class Add implements IAction {
		    	private final RetentionVatRecipe recipe;
		    	public Add(RetentionVatRecipe recipe){
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
    public static void remove(ILiquidStack input) {
        if(input == null) {error(name); return;}
        CraftTweakerAPI.apply(new Remove(toFluid(input)));    
    }
		    private static class Remove implements IAction {
		    	private FluidStack input;
		    	public Remove(FluidStack input) {
		    		super();
		    		this.input = input;
		    	}
		    	@Override
		    	public void apply() {
		    		for(RetentionVatRecipe recipe : recipeList){
		    			if(this.input != null && recipe.getInput().isFluidEqual(this.input)){
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