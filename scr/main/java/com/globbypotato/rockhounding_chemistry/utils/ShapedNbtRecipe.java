package com.globbypotato.rockhounding_chemistry.utils;

import com.globbypotato.rockhounding_chemistry.blocks.GanBlocks;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedNbtRecipe extends ShapedOreRecipe{

    public ShapedNbtRecipe(ItemStack result, Object[] recipe) {
		super(result, recipe);
	}

    public static void register(){
    	RecipeSorter.register("rockhounding_chemistry:shapednbtrecipe",  ShapedNbtRecipe.class, Category.SHAPED, "after:forge:shapedore");
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1){ 
		for (int x = 0; x < var1.getSizeInventory(); x++){
    		if(var1.getStackInSlot(x) != null){ 
    			if(Block.getBlockFromItem(var1.getStackInSlot(x).getItem()) instanceof GanBlocks){
    				ItemStack input = var1.getStackInSlot(x);
    				if(input.hasTagCompound()){
    					output.setTagCompound(input.getTagCompound());
    				}
    			}
    		}
    	}
    	return output.copy(); 
    }
}