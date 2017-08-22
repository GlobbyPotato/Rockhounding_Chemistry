package com.globbypotato.rockhounding_chemistry.handlers;

import java.util.Arrays;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rockhounding.api.machines.IReciper;

public class ExampleRecipes extends IReciper{
	public static void init(){

		
	/**
	 * ROCKHOUNDING: CHEMISTRY   -   CASTING BENCH
	 * The Casting bench produces several metal furnitures for the mod
	 */
	
		/**
		 * Adds a custom recipe to the Casting bench.
		 * Left-click to scroll the available patterns, right-click to add/remove the recipe items 
		 * 
		 * @param input : the OreDictionary String related to the input material
		 * @param outputStack : the output itemstack. The stacksize is considered too
		 * @param pattern : the numeric code of the required pattern. 0-Coils, 1-Rods, 2-Foils, 3-Arm, 4-Casing, 5-Customized
		 */
		sendToCasting("blockGold", new ItemStack(Items.GOLD_INGOT, 9), 5);
	
	
		
	/**
	 * ROCKHOUNDING: CHEMISTRY   -   LAB BLENDER
	 * The Lab Blender refines solid composts used by the mod
	 */
	
		/**
		 * Adds a custom recipe to the Lab Blender via oredict.
		 * 
		 * @param oredict : the oredict string representing the input itemstack
		 * @param quantity : the amount of input required
		 * @param outputStack : the output itemstack. The stacksize is considered too
		 */
		sendToBlender(Arrays.asList("dustCalcium"), Arrays.asList(9), new ItemStack(Items.COAL, 2, 0));
	
		/**
		 * Adds a custom recipe to the Lab Blender via itemstack.
		 * 
		 * @param inputStack : the input itemstack
		 * @param quantity : the amount of input required
		 * @param outputStack : the output itemstack. The stacksize is considered too
		 */
		sendToBlender(Arrays.asList(new ItemStack(Items.SLIME_BALL, 2, 0), new ItemStack(Items.GUNPOWDER, 4, 0)), new ItemStack(Items.MAGMA_CREAM, 2, 0));
	}
}