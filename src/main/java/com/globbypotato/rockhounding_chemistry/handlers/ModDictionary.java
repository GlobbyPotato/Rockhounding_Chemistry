package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModContents;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModDictionary {

	public static void loadDictionary()  {
		for(int x = 0; x < ModArray.chemicalDustsOredict.length; x++){
			OreDictionary.registerOre(ModArray.chemicalDustsOredict[x], new ItemStack(ModContents.chemicalDusts, 1, x));	
		}
		OreDictionary.registerOre("dustSalt", new ItemStack(ModContents.chemicalItems, 1, 1));	
		OreDictionary.registerOre("itemFluorite", new ItemStack(ModContents.halideShards, 1, 4));	
		OreDictionary.registerOre("itemPyrite", new ItemStack(ModContents.sulfideShards, 1, 6));	
		OreDictionary.registerOre("nuggetIron", new ItemStack(ModContents.miscItems, 1, 4));	
		

		for(int x = 0; x < ModArray.alloyArray.length; x++){
			OreDictionary.registerOre(ModArray.alloyBlocksOredict[x], new ItemStack(ModContents.alloyBlocks, 1, x));
		}
		for(int x = 0; x < ModArray.alloyBArray.length; x++){
			OreDictionary.registerOre(ModArray.alloyBBlocksOredict[x], new ItemStack(ModContents.alloyBBlocks, 1, x));
		}
		for(int x = 0; x < ModArray.alloyItemsOredict.length; x++){
			OreDictionary.registerOre(ModArray.alloyItemsOredict[x], new ItemStack(ModContents.alloyItems, 1, x));	
		}
		for(int x = 0; x < ModArray.alloyBItemsOredict.length; x++){
			OreDictionary.registerOre(ModArray.alloyBItemsOredict[x], new ItemStack(ModContents.alloyBItems, 1, x));	
		}

	}

}