package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModDictionary {

	public static void loadDictionary()  {
		for(int x = 0; x < EnumElement.size(); x++){
			OreDictionary.registerOre(EnumElement.getDust(x), BaseRecipes.elements(1, x));	
		}
		OreDictionary.registerOre("slimeball", new ItemStack(ModItems.siliconeCartridge, 1, OreDictionary.WILDCARD_VALUE));	

		OreDictionary.registerOre("dustSalt", BaseRecipes.saltStack);	
		OreDictionary.registerOre("itemFluorite", BaseRecipes.fluorite);	
		OreDictionary.registerOre("itemPyrite", BaseRecipes.pyrite);	
		OreDictionary.registerOre("nuggetIron", BaseRecipes.ironNugget);	
		OreDictionary.registerOre("ingotPlatinum", BaseRecipes.platinumIngot);	
		OreDictionary.registerOre("ingotCopper", BaseRecipes.copperIngot);	
		OreDictionary.registerOre("ingotLead", BaseRecipes.leadIngot);	
		OreDictionary.registerOre("ingotTitanium", BaseRecipes.titaniumIngot);	
		OreDictionary.registerOre("ingotAluminum", BaseRecipes.aluminumIngot);	
		OreDictionary.registerOre("nuggetAluminum", BaseRecipes.aluminumNugget);	
		OreDictionary.registerOre("itemNichromeHeater", BaseRecipes.heatingElement);	
		OreDictionary.registerOre("dustLeadDioxide", BaseRecipes.leadDioxide);	
		OreDictionary.registerOre("ingotCarbon", BaseRecipes.carbonIngot);	
		OreDictionary.registerOre("itemFusedGlass", BaseRecipes.fusedGlass);	

		for(int x = 0; x < EnumAlloy.size(); x++){
			OreDictionary.registerOre(EnumAlloy.getBlock(x), new ItemStack(ModBlocks.alloyBlocks, 1, x));
		}
		for(int x = 0; x < EnumAlloyB.size(); x++){
			OreDictionary.registerOre(EnumAlloyB.getBlock(x), new ItemStack(ModBlocks.alloyBBlocks, 1, x));
		}

		for(int x = 0; x < EnumAlloy.size(); x++){
			OreDictionary.registerOre(EnumAlloy.getDust(x),   BaseRecipes.alloys(1, (x*3)));	
			OreDictionary.registerOre(EnumAlloy.getIngot(x),  BaseRecipes.alloys(1, (x*3) + 1));	
			OreDictionary.registerOre(EnumAlloy.getNugget(x), BaseRecipes.alloys(1, (x*3) + 2));	
		}
		for(int x = 0; x < EnumAlloyB.size(); x++){
			OreDictionary.registerOre(EnumAlloyB.getDust(x),   BaseRecipes.alloysB(1, (x*3)));	
			OreDictionary.registerOre(EnumAlloyB.getIngot(x),  BaseRecipes.alloysB(1, (x*3) + 1));	
			OreDictionary.registerOre(EnumAlloyB.getNugget(x), BaseRecipes.alloysB(1, (x*3) + 2));	
		}
	}
}