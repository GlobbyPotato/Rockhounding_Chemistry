package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModContents;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
	private static ItemStack dustforcedstack;
	private static ItemStack ingotforcedstack;

	public static void init() {
		ModRecipes.chemRecipes();
		ModRecipes.machinesRecipes();
		ModRecipes.alloyRecipes();
		ModRecipes.toolsRecipes();
	}

	private static void toolsRecipes() {
	//bow barrel
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 6), new Object[] { "III"," PP", 'I', "ingotCube", 'P', "plankWood" }));
	//cube crossbow
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.cubeCrossbow), new Object[] { " IS", "IBS", " IS",  'I', "ingotCube", 'B', new ItemStack(ModContents.miscItems, 1, 6), 'S', "string" }));

	//bow wheel
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 7), new Object[] { " I ","ISI", " I " , 'I', "nuggetScal", 'S', "string" }));
	//scal bow
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.scalBow), new Object[] { " IW", "I S", " IW",  'I', "ingotScal", 'W', new ItemStack(ModContents.miscItems, 1, 7), 'S', "string" }));
	//scal bat
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.scalBat), new Object[] { "  I", " I ", "I  ",  'I', "ingotScal"}));
	}

	private static void alloyRecipes() {
 	//alloy parts
 		for(int x = 0; x < ModArray.alloyArray.length; x++){
 			for(ItemStack ore : OreDictionary.getOres(ModArray.alloyDustsOredict[x])) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, new ItemStack(ModContents.alloyItems, 1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.alloyItems, 1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyNuggetsOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.alloyItems, 9, (x*3)+1), new Object[] { ModArray.alloyBlocksOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.alloyItems, 9, (x*3)+2), new Object[] { ModArray.alloyIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.alloyBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.alloyBricks, 4, x), new Object[] { "XX", "XX", 'X', ModArray.alloyBlocksOredict[x] }));
 		}

 	//alloy parts
 		for(int x = 0; x < ModArray.alloyBArray.length; x++){
 			for(ItemStack ore : OreDictionary.getOres(ModArray.alloyBDustsOredict[x])) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, new ItemStack(ModContents.alloyBItems, 1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.alloyBItems, 1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyBNuggetsOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.alloyBItems, 9, (x*3)+1), new Object[] { ModArray.alloyBBlocksOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.alloyBItems, 9, (x*3)+2), new Object[] { ModArray.alloyBIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.alloyBBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyBIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.alloyBBricks, 4, x), new Object[] { "XX", "XX", 'X', ModArray.alloyBBlocksOredict[x] }));
 		}
	}

	private static void machinesRecipes() {
	//Book
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemBook, 1, 0), new Object[] { "paper", "paper", "paper", "dustRedstone" }));

	//lab oven
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.labOven), new Object[] { "IBI", "IGI", "IFI", 'B', new ItemStack(ModContents.miscItems, 1, 1), 'I', "ingotIron", 'F', Blocks.FURNACE, 'G', "blockGlass" }));
	//mineral sizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.mineralSizer), new Object[] { "I I", "GcG", "ICI", 'C', new ItemStack(ModContents.miscItems, 1, 3), 'I', "ingotIron", 'G', new ItemStack(ModContents.miscItems, 1, 2), 'c', new ItemStack(ModContents.miscItems, 1, 0) }));
	//mineral analyzer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.mineralAnalyzer), new Object[] { "ICC", "IcI", "III", 'C', new ItemStack(ModContents.miscItems, 1, 3), 'I', "ingotIron", 'c', new ItemStack(ModContents.miscItems, 1, 0) }));
	//chemical extractor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.chemicalExtractor), new Object[] { "CCC", "IcI", "III", 'C', new ItemStack(ModContents.miscItems, 1, 3), 'I', "ingotIron", 'c', new ItemStack(ModContents.miscItems, 1, 0) }));
	//salt maker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.saltMaker), new Object[] { "S S", "sss", 's', "stone", 'S', "ingotBrick" }));
	//crawler assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.crawlerAssembler), new Object[] { "C", "B", 'C', "workbench", 'B', "blockHastelloy" }));
	//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.metalAlloyer), new Object[] { "bFb", "III", "ITI", 'I', "ingotIron", 'F', Blocks.FURNACE, 'b', Items.BOWL, 'T', Blocks.IRON_TRAPDOOR }));
	//Laser TX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.laserRedstoneTx), new Object[] { " E ", "III", "I I", 'I', "ingotIron", 'E', new ItemStack(ModContents.miscItems, 1, 18) }));
	//Laser RX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.laserRedstoneRx), new Object[] { "PEP", "ICI", "IRI", 'I', "ingotIron", 'E', new ItemStack(ModContents.miscItems, 1, 18), 'C', Items.COMPARATOR, 'P', "paneGlass", 'R', "blockRedstone" }));
	//Laser Splitter
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.laserSplitter), new Object[] { "EEE", "CER", "IBI", 'B', "blockIron", 'I', "ingotIron", 'E', new ItemStack(ModContents.miscItems, 1, 18), 'C', Items.COMPARATOR, 'R', Items.REPEATER }));


	//chemical tank
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 0), new Object[] { "GCG", "G G", "GGG", 'G', "blockGlass", 'C', new ItemStack(ModContents.miscItems, 1, 1) }));
	//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 16, 1), new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
	//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 2), new Object[] { " N ","NIN"," N ", 'I', "ingotIron", 'N', "nuggetIron" }));
	//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 3), new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
	//iron nugget
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.IRON_INGOT), new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.miscItems, 9, 4), new Object[] { "ingotIron" }));
	//logic chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.miscItems, 1, 0), new Object[] { "ingotIron", "ingotIron", "nuggetGold", "dustRedstone" }));
	//memory chip
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 9), new Object[] { "IGI","GCG","IGI", 'C', new ItemStack(ModContents.miscItems, 1, 0), 'I', "ingotIron", 'G', "ingotGold" }));
	//advanced chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.miscItems, 1, 10), new Object[] { new ItemStack(ModContents.miscItems, 1, 0), "gemQuartz", "gemQuartz", "gemQuartz" }));
	//setup chip
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 11), new Object[] { "III","ICI","III", 'C', new ItemStack(ModContents.miscItems, 1, 10), 'I', "ingotHastelloy" }));
	//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 5), new Object[] { "  G"," G ","N  ", 'N', "nuggetIron", 'G', "blockGlass" }));
	//crawler case
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 12), new Object[] { "IAI","ICI","BBB", 'A', new ItemStack(ModContents.miscItems, 1, 14), 'C', new ItemStack(ModContents.miscItems, 1, 11), 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//crawler head
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 13), new Object[] { "III"," B "," P ", 'P', new ItemStack(Blocks.STICKY_PISTON), 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//crawler head
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 14), new Object[] { "I I","III","I I", 'I', "ingotHastelloy"}));
	//wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 15), new Object[] { " N "," IN","I  ", 'I', "ingotIron", 'N', "nuggetIron"}));
	//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 16), new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
	//yag rod
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 17), new Object[] { "YYY", "   ", "YYY", 'Y', "gemYag"}));
	//yag emitter
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 18), new Object[] { "TTT", "CRP", "TTT", 'T', Blocks.REDSTONE_TORCH, 'C', Items.COMPARATOR, 'R', new ItemStack(ModContents.miscItems, 1, 17), 'P', "paneGlass"}));
	//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 19), new Object[] { "NNN", "N N", "I I", 'I', "ingotIron", 'N', "ingotNichrome"}));
	//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModContents.miscItems, 1, 20), new Object[] { "III", "HHH", "III", 'I', "ingotIron", 'H', new ItemStack(ModContents.miscItems, 1, 19)}));
	}

	private static void chemRecipes() {
	//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 2), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 2), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 2), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 2), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 2), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 2), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "itemLignite", "itemLignite", "itemLignite" }));
	//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 3), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 3), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "itemSalt", "itemSalt", "itemSalt" }));
	//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 4), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 4), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "itemFluorite", "itemFluorite", "itemFluorite" }));
	//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 4, 6), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "blockAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 3, 6), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "blockBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 2, 6), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "blokcCoal"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 6), new Object[] { new ItemStack(ModContents.miscItems, 1, 1), "blockLignite"}));
	//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModContents.chemicalItems, 1, 5), new Object[] { new ItemStack(ModContents.chemicalItems, 1, 6), new ItemStack(ModContents.chemicalItems, 1, 6), new ItemStack(ModContents.chemicalItems, 1, 6), new ItemStack(ModContents.chemicalItems, 1, 6)}));
	//force smelting
		if(ModContents.forceSmelting){
			for(int x = 0; x < ModArray.chemicalDustsOredict.length; x++){
				ingotforcedstack = null;
    			for(ItemStack ingot : OreDictionary.getOres(ModArray.chemicalIngotOredict[x])) {
    	            if(ingot != null){ingotforcedstack = ingot;}
    			}
				if(ingotforcedstack != null){ 
					GameRegistry.addSmelting(new ItemStack(ModContents.chemicalDusts, 1, x), ingotforcedstack, 1.0F);
				}
			}
		}else{
    	//gold ingot
	   		GameRegistry.addSmelting(new ItemStack(ModContents.chemicalDusts, 1, 45), new ItemStack(Items.GOLD_INGOT), 1.0F);
    	//iron ingot
	   		GameRegistry.addSmelting(new ItemStack(ModContents.chemicalDusts, 1, 16), new ItemStack(Items.IRON_INGOT), 1.0F);
		}
	}


}
