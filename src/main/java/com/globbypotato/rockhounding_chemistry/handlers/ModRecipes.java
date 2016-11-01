package com.globbypotato.rockhounding_chemistry.handlers;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;


public class ModRecipes {
	public static final ArrayList<MineralSizerRecipe> sizerRecipes = new ArrayList<MineralSizerRecipe>();
	public static final ArrayList<LabOvenRecipe> labOvenRecipes = new ArrayList<LabOvenRecipe>();
	
	private static ItemStack dustforcedstack;
	private static ItemStack ingotforcedstack;
	
	static ItemStack testTube = new ItemStack(ModItems.testTube);
	static ItemStack gear = new ItemStack(ModItems.gear);
	static ItemStack ingotPattern = new ItemStack(ModItems.ingotPattern);
	static ItemStack cylinder = new ItemStack(ModItems.cylinder);
	
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
	static ItemStack flask = misc(13);
	static ItemStack yagRod = misc(14);
	static ItemStack laserResonator = misc(15);
	static ItemStack heatingElement = misc(16);
	static ItemStack inductor = misc(17);
	static ItemStack cupronickelFoil = misc(18);
	static ItemStack copperCoil = misc(19);
	static ItemStack owcStator = misc(20);
	static ItemStack owcRotor = misc(21);
	static ItemStack owcArm = misc(22);
	static ItemStack owcFan = misc(23);
	static ItemStack nimonicFoil = misc(24);
	//25 copper ingot
	static ItemStack energyCell = misc(26);
	//27 lead ingot
	static ItemStack machineCasing = misc(28);
	static ItemStack sulfuricBeaker = misc(29);

	static ItemStack flasks = new ItemStack(ModItems.miscItems, 16, 13);
	static ItemStack cupronickelFoils = new ItemStack(ModItems.miscItems, 16, 18);
	static ItemStack nimonicFoils = new ItemStack(ModItems.miscItems, 16, 24);
	static ItemStack machineCasings = new ItemStack(ModItems.miscItems, 4, 28);
	static ItemStack copperCoils = new ItemStack(ModItems.miscItems, 8, 19);
	static ItemStack crackedCoal = new ItemStack(ModItems.chemicalItems, 1, 6);
	static ItemStack chemicalTank = new ItemStack(ModItems.chemicalItems, 1, 0);
	static ItemStack owcDuct = new ItemStack(ModBlocks.owcBlocks, 1, 2);

	private static ItemStack misc(int meta){
		return new ItemStack(ModItems.miscItems,1,meta);
	}

	public static void init() {
		craftingRecipes();
		machineRecipes();
		laserRecipes();
		owcRecipes();
	}


	private static void owcRecipes() {
//bulkhead
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 0), new Object[] { "III", " S ", "III", 'I', cupronickelFoil, 'S', "stone" }));
//concrete
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 4, 1), new Object[] { "GSG", "SCS", "GWG", 'G', "gravel", 'S', "sand", 'C', Items.CLAY_BALL, 'W', Items.WATER_BUCKET }));
//duct
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 2), new Object[] { "FGF", "CGC", "FGF", 'C', machineCasing, 'F', nimonicFoil, 'G', "blockGlass" }));
//turbine
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 3), new Object[] { "FAF", "CTC", "FAF", 'C', machineCasing, 'A', owcArm, 'T', owcFan, 'F', nimonicFoil }));
//valve
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 4), new Object[] { "GFG", "FDF", "G G", 'G', "ingotGold", 'D', owcDuct, 'F', nimonicFoil }));
//generator
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 5), new Object[] { "FSF", "CRC", "FAF", 'S', owcStator, 'C', machineCasing, 'R', owcRotor, 'A', owcArm, 'F', nimonicFoil }));
//storage
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 6), new Object[] { "ECE", "MEM", "ECE", 'C', copperCoil, 'E', energyCell, 'M', machineCasing }));
//inverter
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 7), new Object[] { "MCI", "cCI", "MCI", 'C', copperCoil, 'I', "blockIron", 'c', Items.COMPARATOR, 'M', machineCasing }));
//deflector
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcBlocks, 1, 8), new Object[] { "FIF", " I ", "FIF", 'F', cupronickelFoil, 'I', "ingotCupronickel" }));
//owc assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcAssembler), new Object[] { "ICI", " I ", 'C', "workbench", 'I', "ingotNimonic" }));
//owc controller
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcController), new Object[] { "XFX", "FLF", "FCF", 'L', Blocks.LEVER, 'F', machineCasing, 'C', Items.COMPARATOR, 'X', logicChip }));

//cupronickel foils
		GameRegistry.addRecipe(new ShapelessOreRecipe(cupronickelFoils, new Object[] { "ingotCupronickel", "ingotCupronickel" }));
//copper coil
		GameRegistry.addRecipe(new ShapelessOreRecipe(copperCoils, new Object[] { "ingotCopper", "ingotCopper" }));
//owc stator
		GameRegistry.addRecipe(new ShapedOreRecipe(owcStator, new Object[] { "III", "I I", "III", 'I', copperCoil }));
//owc rotor
		GameRegistry.addRecipe(new ShapedOreRecipe(owcRotor, new Object[] { "CCA", 'C', copperCoil, 'A', owcArm }));
//owc arm
		GameRegistry.addRecipe(new ShapedOreRecipe(owcArm, new Object[] { "I I", "III", "I I", 'I', "ingotNimonic" }));
//owc fan
		GameRegistry.addRecipe(new ShapedOreRecipe(owcFan, new Object[] { " I ", "IBI", " I ", 'I', nimonicFoil, 'B', "blockNimonic" }));
//nimonic foils
		GameRegistry.addRecipe(new ShapelessOreRecipe(nimonicFoils, new Object[] { "ingotNimonic", "ingotNimonic" }));
//energy Cell
		GameRegistry.addRecipe(new ShapedOreRecipe(energyCell, new Object[] { "NMN", "LML", "LXL", 'N', "nuggetIron", 'L', "ingotLead", 'X', sulfuricBeaker, 'M', machineCasing }));
//machine Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(machineCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetIron", 'I', "ingotIron", 'B', "blockNimonic" }));
	}

	private static void laserRecipes() {
	//Laser TX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneTx), new Object[] { "RE ", " I ", "III", 'I', "ingotIron", 'E', laserResonator, 'R', Items.REPEATER }));
	//Laser RX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 0), new Object[] { "ERE", "PCP", "EIE", 'I', "ingotIron", 'E', laserResonator, 'C', Items.COMPARATOR, 'P', "paneGlass", 'R', "blockRedstone" }));
	//Laser Node
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserSplitter), new Object[] { "EEE", "CER", "III", 'I', "ingotIron", 'E', laserResonator, 'C', Items.COMPARATOR, 'R', Items.REPEATER }));
	//Laser Pin Tx
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 4), new Object[] { "EEE", "ERE", "III", 'I', "ingotIron", 'E', laserResonator, 'R', Items.REPEATER}));
	//Laser Pin Rx
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 5), new Object[] { "EEE", "ECE", "III", 'I', "ingotIron", 'E', laserResonator, 'C', Items.COMPARATOR}));

	//yag rod
		GameRegistry.addRecipe(new ShapedOreRecipe(yagRod, new Object[] { "YYY", "   ", "YYY", 'Y', "gemYag"}));
	//yag emitter
		GameRegistry.addRecipe(new ShapedOreRecipe(laserResonator, new Object[] { "TTT", "CRP", "TTT", 'T', Blocks.REDSTONE_TORCH, 'C', Items.COMPARATOR, 'R', yagRod, 'P', "paneGlass"}));
	}

	public static void machineRecipes(){
		sizerRecipes.add(new MineralSizerRecipe(ModBlocks.mineralOres,null));
		sizerRecipes.add(new MineralSizerRecipe(Items.IRON_INGOT, 0, ModItems.chemicalDusts,16)); //iron dust
		sizerRecipes.add(new MineralSizerRecipe(Items.GOLD_INGOT,0, ModItems.chemicalDusts,45)); //gold dust
		sizerRecipes.add(new MineralSizerRecipe(Blocks.STONE,1, ModItems.chemicalItems,4)); //fuorite dust
		for(int i=0;i<22;i++){
			if((i - 1) % 3 == 0 || i == 1){
				sizerRecipes.add(new MineralSizerRecipe(ModItems.alloyItems,i, ModItems.alloyItems,i-1));
			}
		}
		for(int i=0;i<20;i++){
			if((i - 1) % 3 == 0 || i == 1){
				sizerRecipes.add(new MineralSizerRecipe(ModItems.alloyBItems,i, ModItems.alloyBItems,i-1));
			}
		}

		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,2, EnumFluid.WATER, EnumFluid.SULFURIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,3, EnumFluid.SULFURIC_ACID, EnumFluid.HYDROCHLORIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,4, EnumFluid.SULFURIC_ACID, EnumFluid.HYDROFLUORIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,5, EnumFluid.WATER, EnumFluid.SYNGAS));

	}
	
	public static EnumFluid getLabOvenSolvent(ItemStack input){
		for(LabOvenRecipe recipe: labOvenRecipes){
			if(ItemStack.areItemsEqual(input, recipe.getSolute())){
				return recipe.getSolvent();
			}
		}
		return EnumFluid.EMPTY;
	}
	
	private static void craftingRecipes() {
	//petrographer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.petrographer), new Object[] { " DD", "DS ", " S ", 'D', "gemDiamond", 'S', "stickWood" }));
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

	//Book
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemBook), new Object[] { "paper", "paper", "paper", "dustRedstone" }));

	//lab oven
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.labOven), new Object[] { "IBI", "IGI", "IFI", 'B', flask, 'I', "ingotIron", 'F', Blocks.FURNACE, 'G', "blockGlass" }));
	//mineral sizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralSizer), new Object[] { "IHI", "GcG", "ICI", 'C', cabinet, 'I', "ingotIron", 'G', gear, 'c', logicChip, 'H', Blocks.HOPPER }));
	//mineral analyzer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralAnalyzer), new Object[] { "ICC", "IcI", "III", 'C', cabinet, 'I', "ingotIron", 'c', logicChip }));
	//chemical extractor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chemicalExtractor), new Object[] { "CCC", "IcI", "III", 'C', cabinet, 'I', "ingotIron", 'c', logicChip }));
	//salt maker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.saltMaker), new Object[] { "S S", "sss", 's', "stone", 'S', "ingotBrick" }));
	//crawler assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crawlerAssembler), new Object[] { "ICI", " I ", 'C', "workbench", 'I', "ingotHastelloy" }));
	//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.metalAlloyer), new Object[] { "bFb", "III", "ITI", 'I', "ingotIron", 'F', Blocks.FURNACE, 'b', Items.BOWL, 'T', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE }));
	//petrographer table
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.petrographerTable), new Object[] { " G ", "SSS", " P ", 'P', "plankWood", 'S', "slabWood", 'G', "blockGlass" }));


	//chemical tank
		GameRegistry.addRecipe(new ShapedOreRecipe(chemicalTank, new Object[] { "GCG", "G G", "GGG", 'G', "blockGlass", 'C', flask }));
	//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(cylinder, new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
	//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(gear, new Object[] { " N ","NIN"," N ", 'I', "ingotIron", 'N', "nuggetIron" }));
	//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(cabinet, new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
	//iron ingot
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.IRON_INGOT, new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
	//iron nugget
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
	//crawler casing
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerCasing, new Object[] { "IAI","ICI","BBB", 'A', crawlerArm, 'C', crawlerMemory, 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//crawler head
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerHead, new Object[] { "III"," B "," P ", 'P', Blocks.STICKY_PISTON, 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//crawler arms
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerArm, new Object[] { "I I","III","I I", 'I', "ingotHastelloy"}));
	//wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(modWrench, new Object[] { " N "," IN","I  ", 'I', "ingotIron", 'N', "nuggetIron"}));
	//flask
		GameRegistry.addRecipe(new ShapedOreRecipe(flasks, new Object[] { " G ","G G","GGG", 'G', "blockGlass"}));
	//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ingotPattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
	//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(heatingElement, new Object[] { "NNN", "N N", "I I", 'I', "ingotIron", 'N', "ingotNichrome"}));
	//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(inductor, new Object[] { "III", "HHH", "III", 'I', "ingotIron", 'H', heatingElement}));
	
	//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { flask, "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { flask, "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { flask, "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { flask, "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { flask, Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { flask, "itemLignite", "itemLignite", "itemLignite" }));
	//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 3), new Object[] { flask, "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 3), new Object[] { flask, "itemSalt", "itemSalt", "itemSalt" }));
	//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { flask, "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { flask, "itemFluorite", "itemFluorite", "itemFluorite" }));
	//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 6), new Object[] { flask, "blockAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 3, 6), new Object[] { flask, "blockBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 6), new Object[] { flask, "blockcCoal"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { flask, "blockLignite"}));
	//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 5), new Object[] { crackedCoal, crackedCoal, crackedCoal, crackedCoal}));
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
    	//copper ingot
	   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 17), new ItemStack(ModItems.miscItems, 1, 25), 1.0F);
    	//lead ingot
	   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 19), new ItemStack(ModItems.miscItems, 1, 27), 1.0F);
		}
	}


}
