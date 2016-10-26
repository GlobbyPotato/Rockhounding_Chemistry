package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModDictionary {

	public static void loadDictionary()  {
		for(int x = 0; x < ModArray.chemicalDustsOredict.length; x++){
			OreDictionary.registerOre(ModArray.chemicalDustsOredict[x], new ItemStack(ModItems.chemicalDusts, 1, x));	
		}
		OreDictionary.registerOre("dustSalt", new ItemStack(ModItems.chemicalItems, 1, 1));	
		OreDictionary.registerOre("itemFluorite", new ItemStack(ModItems.halideShards, 1, 4));	
		OreDictionary.registerOre("itemPyrite", new ItemStack(ModItems.sulfideShards, 1, 6));	
		OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.miscItems, 1, 2));	
		OreDictionary.registerOre("ingotCopper", new ItemStack(ModItems.miscItems, 1, 25));	
		OreDictionary.registerOre("ingotLead", new ItemStack(ModItems.miscItems, 1, 27));	
		

		for(int x = 0; x < ModArray.alloyArray.length; x++){
			OreDictionary.registerOre(ModArray.alloyBlocksOredict[x], new ItemStack(ModBlocks.alloyBlocks, 1, x));
		}
		for(int x = 0; x < ModArray.alloyBArray.length; x++){
			OreDictionary.registerOre(ModArray.alloyBBlocksOredict[x], new ItemStack(ModBlocks.alloyBBlocks, 1, x));
		}
		for(int x = 0; x < ModArray.alloyItemsOredict.length; x++){
			OreDictionary.registerOre(ModArray.alloyItemsOredict[x], new ItemStack(ModItems.alloyItems, 1, x));	
		}
		for(int x = 0; x < ModArray.alloyBItemsOredict.length; x++){
			OreDictionary.registerOre(ModArray.alloyBItemsOredict[x], new ItemStack(ModItems.alloyBItems, 1, x));	
		}

	}

}