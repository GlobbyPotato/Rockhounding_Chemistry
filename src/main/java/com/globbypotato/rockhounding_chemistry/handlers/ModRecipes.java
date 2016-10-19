package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.items.ModItems;

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
	
	static ItemStack testTube = new ItemStack(ModItems.testTube);
	static ItemStack cylinder = new ItemStack(ModItems.cylinder);
	static ItemStack gear = new ItemStack(ModItems.gear);
	static ItemStack inductor = new ItemStack(ModItems.inductor);
	static ItemStack logicChip = misc(0);
	static ItemStack cabinet = misc(1);
	static ItemStack ironNuggets = new ItemStack(ModItems.miscItems,9,2);
	static ItemStack bowBarrel = misc(3);
	static ItemStack bowWheel = misc(4);
	static ItemStack fluidContainer = misc(5);
	static ItemStack crawlerMemory = misc(6);
	static ItemStack advancedChip = misc(7);
	static ItemStack setupChip = misc(8);
	static ItemStack crawlerCasing = misc(9);
	static ItemStack crawlerHead = misc(10);
	static ItemStack crawlerArm = misc(11);
	static ItemStack modWrench = misc(12);
	static ItemStack ingotPattern = misc(13);
	static ItemStack yagRod = misc(14);
	static ItemStack laserResonator = misc(15);
	static ItemStack heatingElement = misc(16);

	
	private static ItemStack misc(int meta){
		return new ItemStack(ModItems.miscItems,1,meta);
	}

	public static void init() {
		ModRecipes.chemRecipes();
		ModRecipes.machinesRecipes();
		ModRecipes.alloyRecipes();
		ModRecipes.toolsRecipes();
	}

	private static void toolsRecipes() {
	//bow barrel
		GameRegistry.addRecipe(new ShapedOreRecipe(bowBarrel, new Object[] { "III"," PP", 'I', "ingotCube", 'P', "plankWood" }));
	//cube crossbow
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cubeCrossbow), new Object[] { " IS", "IBS", " IS",  'I', "ingotCube", 'B', bowBarrel, 'S', "string" }));

	//bow wheel
		GameRegistry.addRecipe(new ShapedOreRecipe(bowWheel, new Object[] { " I ","ISI", " I " , 'I', "nuggetScal", 'S', "string" }));
	//scal bow
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.scalBow), new Object[] { " IW", "I S", " IW",  'I', "ingotScal", 'W', bowWheel, 'S', "string" }));
	//scal bat
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.scalBat), new Object[] { "  I", " I ", "I  ",  'I', "ingotScal"}));
	}

	private static void alloyRecipes() {
 	//alloy parts
 		for(int x = 0; x < ModArray.alloyArray.length; x++){
 			for(ItemStack ore : OreDictionary.getOres(ModArray.alloyDustsOredict[x])) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, new ItemStack(ModItems.alloyItems, 1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alloyItems, 1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyNuggetsOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyItems, 9, (x*3)+1), new Object[] { ModArray.alloyBlocksOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyItems, 9, (x*3)+2), new Object[] { ModArray.alloyIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBricks, 4, x), new Object[] { "XX", "XX", 'X', ModArray.alloyBlocksOredict[x] }));
 		}

 	//alloy parts
 		for(int x = 0; x < ModArray.alloyBArray.length; x++){
 			for(ItemStack ore : OreDictionary.getOres(ModArray.alloyBDustsOredict[x])) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, new ItemStack(ModItems.alloyBItems, 1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alloyBItems, 1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyBNuggetsOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyBItems, 9, (x*3)+1), new Object[] { ModArray.alloyBBlocksOredict[x] }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyBItems, 9, (x*3)+2), new Object[] { ModArray.alloyBIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', ModArray.alloyBIngotsOredict[x] }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBBricks, 4, x), new Object[] { "XX", "XX", 'X', ModArray.alloyBBlocksOredict[x] }));
 		}
	}

	private static void machinesRecipes() {
	//Book
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemBook), new Object[] { "paper", "paper", "paper", "dustRedstone" }));

	//lab oven
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.labOven), new Object[] { "IBI", "IGI", "IFI", 'B', new ItemStack(ModItems.cylinder), 'I', "ingotIron", 'F', Blocks.FURNACE, 'G', "blockGlass" }));
	//mineral sizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralSizer), new Object[] { "I I", "GcG", "ICI", 'C', cabinet, 'I', "ingotIron", 'G', gear, 'c', logicChip }));
	//mineral analyzer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralAnalyzer), new Object[] { "ICC", "IcI", "III", 'C', cabinet, 'I', "ingotIron", 'c', logicChip }));
	//chemical extractor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chemicalExtractor), new Object[] { "CCC", "IcI", "III", 'C', cabinet, 'I', "ingotIron", 'c', logicChip }));
	//salt maker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.saltMaker), new Object[] { "S S", "sss", 's', "stone", 'S', "ingotBrick" }));
	//crawler assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crawlerAssembler), new Object[] { "C", "B", 'C', "workbench", 'B', "blockHastelloy" }));
	//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.metalAlloyer), new Object[] { "bFb", "III", "ITI", 'I', "ingotIron", 'F', Blocks.FURNACE, 'b', Items.BOWL, 'T', Blocks.IRON_TRAPDOOR }));
	//Laser TX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneTx), new Object[] { " E ", "III", "I I", 'I', "ingotIron", 'E', laserResonator }));
	//Laser RX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx), new Object[] { "PEP", "ICI", "IRI", 'I', "ingotIron", 'E', laserResonator, 'C', Items.COMPARATOR, 'P', "paneGlass", 'R', "blockRedstone" }));
	//Laser Splitter
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserSplitter), new Object[] { "EEE", "CER", "IBI", 'B', "blockIron", 'I', "ingotIron", 'E', laserResonator, 'C', Items.COMPARATOR, 'R', Items.REPEATER }));


	//chemical tank
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 0), new Object[] { "GCG", "G G", "GGG", 'G', "blockGlass", 'C', new ItemStack(ModItems.cylinder) }));
	//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cylinder, 16), new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
	//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(gear, new Object[] { " N ","NIN"," N ", 'I', "ingotIron", 'N', "nuggetIron" }));
	//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(cabinet, new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
	//iron nugget
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.IRON_INGOT), new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ironNuggets, new Object[] { "ingotIron" }));
	//logic chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(logicChip, new Object[] { "ingotIron", "ingotIron", "nuggetGold", "dustRedstone" }));
	//memory chip
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerMemory, new Object[] { "IGI","GCG","IGI", 'C', logicChip, 'I', "ingotIron", 'G', "ingotGold" }));
	//advanced chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(advancedChip, new Object[] { logicChip, "gemQuartz", "gemQuartz", "gemQuartz" }));
	//setup chip
		GameRegistry.addRecipe(new ShapedOreRecipe(setupChip, new Object[] { "III","ICI","III", 'C', advancedChip, 'I', "ingotHastelloy" }));
	//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(testTube, new Object[] { "  G"," G ","N  ", 'N', "nuggetIron", 'G', "blockGlass" }));
	//crawler case
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerCasing, new Object[] { "IAI","ICI","BBB", 'A', crawlerArm, 'C', setupChip, 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//crawler head
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerHead, new Object[] { "III"," B "," P ", 'P', new ItemStack(Blocks.STICKY_PISTON), 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//crawler head //arm?
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerArm, new Object[] { "I I","III","I I", 'I', "ingotHastelloy"}));
	//wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(modWrench, new Object[] { " N "," IN","I  ", 'I', "ingotIron", 'N', "nuggetIron"}));
	//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ingotPattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
	//yag rod
		GameRegistry.addRecipe(new ShapedOreRecipe(yagRod, new Object[] { "YYY", "   ", "YYY", 'Y', "gemYag"}));
	//yag emitter
		GameRegistry.addRecipe(new ShapedOreRecipe(laserResonator, new Object[] { "TTT", "CRP", "TTT", 'T', Blocks.REDSTONE_TORCH, 'C', Items.COMPARATOR, 'R', yagRod, 'P', "paneGlass"}));
	//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(heatingElement, new Object[] { "NNN", "N N", "I I", 'I', "ingotIron", 'N', "ingotNichrome"}));
	//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(inductor, new Object[] { "III", "HHH", "III", 'I', "ingotIron", 'H', heatingElement}));
	}

	private static void chemRecipes() {

	//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { cylinder, "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { cylinder, "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { cylinder, "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { cylinder, "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { cylinder, Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { cylinder, "itemLignite", "itemLignite", "itemLignite" }));
	//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 3), new Object[] { cylinder, "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 3), new Object[] { cylinder, "itemSalt", "itemSalt", "itemSalt" }));
	//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { cylinder, "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { cylinder, "itemFluorite", "itemFluorite", "itemFluorite" }));
	//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 6), new Object[] { cylinder, "blockAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 3, 6), new Object[] { cylinder, "blockBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 6), new Object[] { cylinder, "blockcCoal"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { cylinder, "blockLignite"}));
	//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 5), new Object[] { new ItemStack(ModItems.chemicalItems, 1, 6), new ItemStack(ModItems.chemicalItems, 1, 6), new ItemStack(ModItems.chemicalItems, 1, 6), new ItemStack(ModItems.chemicalItems, 1, 6)}));
	//force smelting
		if(ModConfig.forceSmelting){
			for(int x = 0; x < ModArray.chemicalDustsOredict.length; x++){
				ingotforcedstack = null;
    			for(ItemStack ingot : OreDictionary.getOres(ModArray.chemicalIngotOredict[x])) {
    	            if(ingot != null){ingotforcedstack = ingot;}
    			}
				if(ingotforcedstack != null){ 
					GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, x), ingotforcedstack, 1.0F);
				}
			}
		}else{
    	//gold ingot
	   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 45), new ItemStack(Items.GOLD_INGOT), 1.0F);
    	//iron ingot
	   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 16), new ItemStack(Items.IRON_INGOT), 1.0F);
		}
	}


}
