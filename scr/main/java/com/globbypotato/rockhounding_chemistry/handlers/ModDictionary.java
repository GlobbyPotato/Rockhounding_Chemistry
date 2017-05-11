package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModDictionary {

	public static void loadDictionary()  {
		for(int x = 0; x < EnumElement.size(); x++){
			OreDictionary.registerOre(EnumElement.getDust(x), new ItemStack(ModItems.chemicalDusts, 1, x));	
		}
		OreDictionary.registerOre("dustSalt", new ItemStack(ModItems.chemicalItems, 1, 1));	
		OreDictionary.registerOre("itemFluorite", new ItemStack(ModItems.halideShards, 1, 4));	
		OreDictionary.registerOre("itemPyrite", new ItemStack(ModItems.sulfideShards, 1, 6));	
		OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.miscItems, 1, 2));	
		OreDictionary.registerOre("ingotCopper", new ItemStack(ModItems.miscItems, 1, 25));	
		OreDictionary.registerOre("ingotLead", new ItemStack(ModItems.miscItems, 1, 27));	
		OreDictionary.registerOre("ingotTitanium", new ItemStack(ModItems.miscItems, 1, 36));	
		OreDictionary.registerOre("slimeball", new ItemStack(ModItems.siliconeCartridge, 1, OreDictionary.WILDCARD_VALUE));	


		for(int x = 0; x < EnumAlloy.size(); x++){
			OreDictionary.registerOre(EnumAlloy.getBlock(x), new ItemStack(ModBlocks.alloyBlocks, 1, x));
		}
		for(int x = 0; x < EnumAlloyB.size(); x++){
			OreDictionary.registerOre(EnumAlloyB.getBlock(x), new ItemStack(ModBlocks.alloyBBlocks, 1, x));
		}

		for(int x = 0; x < EnumAlloy.size(); x++){
			OreDictionary.registerOre(EnumAlloy.getDust(x),   new ItemStack(ModItems.alloyItems, 1, (x*3)));	
			OreDictionary.registerOre(EnumAlloy.getIngot(x),  new ItemStack(ModItems.alloyItems, 1, (x*3) + 1));	
			OreDictionary.registerOre(EnumAlloy.getNugget(x), new ItemStack(ModItems.alloyItems, 1, (x*3) + 2));	
		}
		for(int x = 0; x < EnumAlloyB.size(); x++){
			OreDictionary.registerOre(EnumAlloyB.getDust(x),   new ItemStack(ModItems.alloyBItems, 1, (x*3)));	
			OreDictionary.registerOre(EnumAlloyB.getIngot(x),  new ItemStack(ModItems.alloyBItems, 1, (x*3) + 1));	
			OreDictionary.registerOre(EnumAlloyB.getNugget(x), new ItemStack(ModItems.alloyBItems, 1, (x*3) + 2));	
		}
	}
}