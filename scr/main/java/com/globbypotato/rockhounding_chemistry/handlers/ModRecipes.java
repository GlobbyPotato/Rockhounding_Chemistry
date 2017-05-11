package com.globbypotato.rockhounding_chemistry.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.enums.EnumOres;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumArsenate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumBorate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumCarbonate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumHalide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumOxide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumPhosphate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSilicate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfide;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SaltSeasonerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
	public static ArrayList<MineralSizerRecipe> sizerRecipes = new ArrayList<MineralSizerRecipe>();
	public static ArrayList<LabOvenRecipe> labOvenRecipes = new ArrayList<LabOvenRecipe>();
	public static ArrayList<MineralAnalyzerRecipe> analyzerRecipes = new ArrayList<MineralAnalyzerRecipe>();
	public static ArrayList<ChemicalExtractorRecipe> extractorRecipes = new ArrayList<ChemicalExtractorRecipe>();
	public static ArrayList<SaltSeasonerRecipe> seasonerRecipes = new ArrayList<SaltSeasonerRecipe>();
	public static ArrayList<MetalAlloyerRecipe> alloyerRecipes = new ArrayList<MetalAlloyerRecipe>();
	public static ArrayList<DepositionChamberRecipe> depositionRecipes = new ArrayList<DepositionChamberRecipe>();

	public static ArrayList<String> inhibitedElements = new ArrayList<String>();

//													A  B  C  H  N   O   p   S   S  S
//	public static int[] categoryProb = new int[]{	4, 7, 7, 5, 11, 20, 11, 10, 9, 16};

//													0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
//	public static int[] arsenateProb = new int[]{	40,10,25,15,10};
//	public static int[] borateProb = new int[]{		36,15,10,15,11,13};
//	public static int[] carbonateProb = new int[]{	42,14,16,8, 5, 9, 6};
//	public static int[] halideProb = new int[]{		9, 14,7, 12,36,13,9};
//	public static int[] nativeProb = new int[]{		3, 8, 9, 3, 7, 9, 5, 3, 5, 4, 6, 5, 4, 6, 4, 4, 7, 7};
//	public static int[] oxideProb = new int[]{		7, 2, 4, 3, 2, 3, 8, 8, 5, 3, 5, 3, 4, 3, 4, 3, 2, 4, 2, 3, 4, 2, 4, 3, 5, 4};
//	public static int[] phosphateProb = new int[]{	7, 5, 12,9, 6, 8, 10,6, 9, 7, 4, 7, 5, 5};
//	public static int[] silicateProb = new int[]{	8, 7, 9, 11,9, 8, 6, 5, 8, 9, 5, 9, 6};
//	public static int[] sulfateProb = new int[]{	9, 6, 9, 11,6, 10,8, 11,6, 8, 9, 7};
//	public static int[] sulfideProb = new int[]{	4, 6, 4, 9, 3, 8, 9, 4, 5, 6, 5, 3, 6, 3, 5, 6, 6, 4, 4};

	static ItemStack testTube = new ItemStack(ModItems.testTube);
	static ItemStack gear = new ItemStack(ModItems.gear);
	static ItemStack ingotPattern = new ItemStack(ModItems.ingotPattern);
	static ItemStack cylinder = new ItemStack(ModItems.cylinder);

	static ItemStack logicChip = misc(0);
	static ItemStack cabinet = misc(1);
	//2 iron nugget
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
	static ItemStack hastelloyFoil = misc(13);
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
	static ItemStack copperIngot = misc(25);
	static ItemStack energyCell = misc(26);
	static ItemStack leadIngot = misc(27);
	static ItemStack machineCasing = misc(28);
	static ItemStack nichromeRod = misc(29);
	static ItemStack cunifeCoil = misc(30);
	static ItemStack cathode = misc(31);
	static ItemStack cathodeSet = misc(32);
	static ItemStack hastelloyCasing = misc(33);
	static ItemStack chamberUpgrade = misc(34);
	static ItemStack insulationUpgrade = misc(35);
	static ItemStack titaniumIngot = misc(36);
	static ItemStack boundaryHead = misc(37);
	static ItemStack sienaBearing = misc(38);
	static ItemStack tiniteArm = misc(39);
	static ItemStack boundaryChip = misc(40);
	static ItemStack boundaryCasing = misc(41);

	static ItemStack ironNuggets = new ItemStack(ModItems.miscItems,9,2);
	static ItemStack flask = new ItemStack(ModItems.chemFlask);
	static ItemStack cupronickelFoils = new ItemStack(ModItems.miscItems, 8, 18);
	static ItemStack yagRods = new ItemStack(ModItems.miscItems, 4, 14);
	static ItemStack nichromeRods = new ItemStack(ModItems.miscItems, 4, 29);
	static ItemStack hastelloyFoils = new ItemStack(ModItems.miscItems, 8, 13);
	static ItemStack nimonicFoils = new ItemStack(ModItems.miscItems, 8, 24);
	static ItemStack crawlerArms = new ItemStack(ModItems.miscItems, 4, 11);
	static ItemStack owcArms = new ItemStack(ModItems.miscItems, 4, 22);
	static ItemStack machineCasings = new ItemStack(ModItems.miscItems, 4, 28);
	static ItemStack copperCoils = new ItemStack(ModItems.miscItems, 8, 19);
	static ItemStack cunifeCoils = new ItemStack(ModItems.miscItems, 8, 30);
	static ItemStack sulfCompost = chemicals(2);
	static ItemStack crackedCoal = chemicals(6);
	static ItemStack owcDuct = new ItemStack(ModBlocks.owcBlocks, 1, 2);
	static ItemStack ashCompost = chemicals(11);
	static ItemStack potCarbonate = chemicals(12);
	static ItemStack potNitrate = chemicals(13);
	static ItemStack crackedCharcoal = chemicals(14);
	static ItemStack sandPile = chemicals(16);
	static ItemStack hastelloyCasings = new ItemStack(ModItems.miscItems, 4, 33);
	static ItemStack boundaryCasings = new ItemStack(ModItems.miscItems, 4, 41);

	static ItemStack platinumShard = new ItemStack(ModItems.nativeShards, 1, 17);
	static ItemStack rutileShard = new ItemStack(ModItems.oxideShards, 1, 24);
	static ItemStack borax = new ItemStack(ModItems.borateShards, 1, 0);
	static ItemStack charcoal = new ItemStack(Items.COAL, 1, 1);

	private static ItemStack misc(int meta){return new ItemStack(ModItems.miscItems,1,meta);}
	private static ItemStack chemicals(int meta){return new ItemStack(ModItems.chemicalItems,1,meta);}

	public static void init() {
		chemicalRecipes();
		inductionRecipes();
		machineRecipes();
		laserRecipes();
		owcRecipes();
		crawlerRecipes();
		dekatronRecipes();
		toolsRecipes();
		genericRecipes();
		depositionRecipes();
		boundaryRecipes();
		machineryRecipes();
		metallurgyRecipes();
	}

	private static void metallurgyRecipes() {
	 //alloy parts
 		for(int x = 0; x < EnumAlloy.size(); x++){
 			for(ItemStack ore : OreDictionary.getOres(EnumAlloy.getDust(x))) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, new ItemStack(ModItems.alloyItems, 1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alloyItems, 1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloy.getNugget(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyItems, 9, (x*3)+1), new Object[] { EnumAlloy.getBlock(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyItems, 9, (x*3)+2), new Object[] { EnumAlloy.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloy.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBricks, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloy.getBlock(x) }));
 		}

 	//alloy parts
 		for(int x = 0; x < EnumAlloyB.size(); x++){
 			for(ItemStack ore : OreDictionary.getOres(EnumAlloyB.getDust(x))) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, new ItemStack(ModItems.alloyBItems, 1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alloyBItems, 1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloyB.getNugget(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyBItems, 9, (x*3)+1), new Object[] { EnumAlloyB.getBlock(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.alloyBItems, 9, (x*3)+2), new Object[] { EnumAlloyB.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloyB.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBBricks, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloyB.getBlock(x) }));
 		}
	}

	private static void machineryRecipes() {
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
	//salt seasoner
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.saltSeasoner), new Object[] { "SSS", "SSS", "PIP", 'S', "slabWood", 'I', "ingotIron", 'P', "plankWood" }));
	//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.metalAlloyer), new Object[] { "bFb", "III", "ITI", 'I', "ingotIron", 'F', Blocks.FURNACE, 'b', Items.BOWL, 'T', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE }));
	//petrographer table
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.petrographerTable), new Object[] { " G ", "SSS", " P ", 'P', "plankWood", 'S', "slabWood", 'G', "blockGlass" }));
	//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(cylinder, new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
	//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(gear, new Object[] { " N ","NIN"," N ", 'I', "ingotIron", 'N', "nuggetIron" }));
	//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(cabinet, new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
	//logic chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(logicChip, new Object[] { "ingotIron", "ingotIron", "nuggetGold", "dustRedstone" }));
	//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(testTube, new Object[] { "  G"," G ","N  ", 'N', "nuggetIron", 'G', "blockGlass" }));
	//flask
		GameRegistry.addRecipe(new ShapedOreRecipe(flask, new Object[] { " G ","G G","GGG", 'G', "blockGlass"}));
	//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ingotPattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
	}

	private static void inductionRecipes() {
	//nichrome rod
		GameRegistry.addRecipe(new ShapedOreRecipe(nichromeRods, new Object[] { "N", "N", 'N', "ingotNichrome"}));
	//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(heatingElement, new Object[] { "NNN", "N N", "I I", 'I', "nuggetIron", 'N', nichromeRod}));
	//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(inductor, new Object[] { "III", "HHH", "N N", 'I', "ingotIron", 'H', heatingElement, 'N', "nuggetIron"}));
	}

	private static void genericRecipes() {
	//Book
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemBook), new Object[] { "paper", "paper", "paper", "dustRedstone" }));
	//iron ingot
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.IRON_INGOT, new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
	//iron nugget
		GameRegistry.addRecipe(new ShapelessOreRecipe(ironNuggets, new Object[] { "ingotIron" }));
	//wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(modWrench, new Object[] { " N "," SN", 'S', "stickWood", 'N', "nuggetIron"}));
	//gold ingot
   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 45), new ItemStack(Items.GOLD_INGOT), 1.0F);
	//iron ingot
   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 16), new ItemStack(Items.IRON_INGOT), 1.0F);
	//copper ingot
   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 17), copperIngot, 1.0F);
	//lead ingot
   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 19), leadIngot, 1.0F);
   	//titanium ingot
   		GameRegistry.addSmelting(new ItemStack(ModItems.chemicalDusts, 1, 29), titaniumIngot, 1.0F);
	}

	private static void dekatronRecipes() {
	//Dekatron
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dekatron), new Object[] { "GGG", "KRK", "ICI", 'R', "dustRedstone", 'I', "ingotIron", 'K', cathodeSet, 'C', Items.COMPARATOR, 'G', "blockGlass" }));
	//cunife wire
		GameRegistry.addRecipe(new ShapelessOreRecipe(cunifeCoils, new Object[] { "ingotCunife", "ingotCunife" }));
	//cathode
		GameRegistry.addRecipe(new ShapedOreRecipe(cathode, new Object[] { "PPP", "WWW", "I I", 'I', "nuggetIron", 'W', cunifeCoil, 'P', Items.PAPER}));
	//cathode Set
		GameRegistry.addRecipe(new ShapedOreRecipe(cathodeSet, new Object[] {"CWC", "WCW", "CWC", 'C', cathode, 'W', copperCoil }));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcAssembler), new Object[] { " I ", "ICI", " I ", 'C', "workbench", 'I', nimonicFoil }));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(owcArms, new Object[] { "I  "," I ","  I", 'I', "ingotNimonic" }));
	//owc fan
		GameRegistry.addRecipe(new ShapedOreRecipe(owcFan, new Object[] { " I ", "IBI", " I ", 'I', nimonicFoil, 'B', "blockNimonic" }));
	//nimonic foils
		GameRegistry.addRecipe(new ShapelessOreRecipe(nimonicFoils, new Object[] { "ingotNimonic", "ingotNimonic" }));
	//beaker
		if( !FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapedOreRecipe(ModFluids.beaker, new Object[] { "P P", "GGG", 'P', "paneGlass", 'G', "blockGlass" }));
		}
	//energy Cell
		if( !FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapedOreRecipe(energyCell, new Object[] { "NMN", "LML", "LXL", 'N', "nuggetIron", 'L', "ingotLead", 'X', ModFluids.sulfuricAcidBeaker, 'M', machineCasing }));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(energyCell, new Object[] { "NMN", "LML", "LXL", 'N', "nuggetIron", 'L', "ingotLead", 'X', ToolUtils.getFluidBucket(ModFluids.SULFURIC_ACID), 'M', machineCasing }));
		}
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
		GameRegistry.addRecipe(new ShapedOreRecipe(yagRods, new Object[] { "Y", "Y", 'Y', "ingotYag"}));
	//yag emitter
		GameRegistry.addRecipe(new ShapedOreRecipe(laserResonator, new Object[] { "TTT", "CRP", "TTT", 'T', Blocks.REDSTONE_TORCH, 'C', Items.COMPARATOR, 'R', yagRod, 'P', "paneGlass"}));
	}

	private static void crawlerRecipes() {
	//crawler assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crawlerAssembler), new Object[] {" I ", "ICI", " I ", 'C', "workbench", 'I', hastelloyFoil }));
	//memory chip
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerMemory, new Object[] { "FIF","GCG","FIF", 'C', logicChip, 'I', "ingotIron", 'G', "nuggetGold", 'F', hastelloyFoil }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(crawlerMemory, new Object[] { crawlerMemory }));
	//advanced chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(advancedChip, new Object[] { logicChip, "gemQuartz", "nuggetGold", "dustRedstone" }));
	//setup chip
		GameRegistry.addRecipe(new ShapedOreRecipe(setupChip, new Object[] { "III","NCN","III", 'C', advancedChip, 'I', hastelloyFoil, 'N', "nuggetGold" }));
	//hastelloy foils
		GameRegistry.addRecipe(new ShapelessOreRecipe(hastelloyFoils, new Object[] { "ingotHastelloy", "ingotHastelloy" }));
	//crawler casing
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerCasing, new Object[] { "RFR","FCF","IBI", 'C', crawlerMemory, 'I', "ingotHastelloy", 'B', "blockHastelloy", 'F', hastelloyFoil, 'R', "dustRedstone" }));
	//crawler head
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerHead, new Object[] { "III", "FPF", 'P', Blocks.STICKY_PISTON, 'I', "ingotHastelloy", 'F', hastelloyFoil }));
	//crawler arms
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerArms, new Object[] { "I  "," I ","  I", 'I', "ingotHastelloy"}));
	}

	public static void machineRecipes(){
		sizerRecipes.add(new MineralSizerRecipe(minerals(0), 								Arrays.asList(minerals(1), minerals(2), minerals(3), minerals(4), minerals(5), minerals(6), minerals(7), minerals(8), minerals(9), minerals(10)), 		Arrays.asList(4, 7, 7, 5, 11, 20, 11, 10, 9, 16)));
		sizerRecipes.add(new MineralSizerRecipe(new ItemStack(Items.IRON_INGOT), 			Arrays.asList(new ItemStack(ModItems.chemicalDusts, 1, 16)), 																							Arrays.asList(100))); //iron dust
		sizerRecipes.add(new MineralSizerRecipe(new ItemStack(Items.GOLD_INGOT), 			Arrays.asList(new ItemStack(ModItems.chemicalDusts, 1, 45)), 																							Arrays.asList(100))); //gold dust
		sizerRecipes.add(new MineralSizerRecipe(copperIngot, 								Arrays.asList(new ItemStack(ModItems.chemicalDusts, 1, 17)), 																							Arrays.asList(100))); //copper dust
		sizerRecipes.add(new MineralSizerRecipe(leadIngot, 									Arrays.asList(new ItemStack(ModItems.chemicalDusts, 1, 19)), 																							Arrays.asList(100))); //lead dust
		sizerRecipes.add(new MineralSizerRecipe(titaniumIngot, 								Arrays.asList(new ItemStack(ModItems.chemicalDusts, 1, 29)), 																							Arrays.asList(100))); //titanium dust
		sizerRecipes.add(new MineralSizerRecipe(new ItemStack(Blocks.STONE,1,1), 			Arrays.asList(new ItemStack(ModItems.chemicalItems, 4, 4)), 																							Arrays.asList(100))); //fuorite dust
		for(int i = 0; i <= EnumAlloy.getItemNames().length - 2; i++){
			if((i - 1) % 3 == 0 || i == 1){
				sizerRecipes.add(new MineralSizerRecipe(new ItemStack(ModItems.alloyItems, 1, i), 	Arrays.asList(new ItemStack(ModItems.alloyItems, 1, i - 1)), Arrays.asList(100)));
			}
		}
		for(int i = 0; i <= EnumAlloyB.getItemNames().length - 2; i++){
			if((i - 1) % 3 == 0 || i == 1){
				sizerRecipes.add(new MineralSizerRecipe(new ItemStack(ModItems.alloyBItems, 1, i), 	Arrays.asList(new ItemStack(ModItems.alloyBItems, 1, i - 1)), Arrays.asList(100)));
			}
		}
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 2),  new FluidStack(FluidRegistry.WATER,1000), 			null,												new FluidStack(ModFluids.SULFURIC_ACID,500)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 3),  new FluidStack(ModFluids.SULFURIC_ACID,500), 		null,												new FluidStack(ModFluids.HYDROCHLORIC_ACID,300)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 4),  new FluidStack(ModFluids.SULFURIC_ACID,500), 		new FluidStack(FluidRegistry.WATER,1000),			new FluidStack(ModFluids.PHOSPHORIC_ACID,500)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 4),  new FluidStack(ModFluids.SULFURIC_ACID,500), 		null,												new FluidStack(ModFluids.HYDROFLUORIC_ACID,300)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 3),  new FluidStack(ModFluids.AMMONIA,500), 				new FluidStack(ModFluids.CHLOROMETHANE,500),		new FluidStack(ModFluids.SODIUM_CYANIDE,300)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 10), new FluidStack(ModFluids.AMMONIA,750), 				new FluidStack(FluidRegistry.WATER,1000),			new FluidStack(ModFluids.NITRIC_ACID,400)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 5),  new FluidStack(FluidRegistry.WATER,1000), 			null,												new FluidStack(ModFluids.SYNGAS,400)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 3),  new FluidStack(ModFluids.SULFURIC_ACID,750), 		new FluidStack(ModFluids.SYNGAS,500),				new FluidStack(ModFluids.CHLOROMETHANE,300)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 9),  new FluidStack(ModFluids.SYNGAS,500), 				new FluidStack(FluidRegistry.WATER,1000),			new FluidStack(ModFluids.AMMONIA,300)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 6),  new FluidStack(FluidRegistry.WATER,1000), 			null,												new FluidStack(ModFluids.ACRYLIC_ACID,250)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 8),  new FluidStack(ModFluids.CHLOROMETHANE,500),		 	new FluidStack(FluidRegistry.WATER,1000),			new FluidStack(ModFluids.SILICONE,200)));
		labOvenRecipes.add(new LabOvenRecipe(new ItemStack(ModItems.chemicalItems, 1, 15), new FluidStack(ModFluids.HYDROCHLORIC_ACID,1000),	null,												new FluidStack(ModFluids.TITANIUM_TETRACHLORIDE,100)));

		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 1),  Arrays.asList(arsenateShards(0), arsenateShards(1), arsenateShards(2), arsenateShards(3), arsenateShards(4)), Arrays.asList(40,10,25,15,10)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 2),  Arrays.asList(borateShards(0), borateShards(1), borateShards(2), borateShards(3), borateShards(4), borateShards(5)), Arrays.asList(36,15,10,15,11,13)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 3),  Arrays.asList(carbonateShards(0), carbonateShards(1), carbonateShards(2), carbonateShards(3), carbonateShards(4), carbonateShards(5), carbonateShards(6)), Arrays.asList(42,14,16,8, 5, 9, 6)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 4),  Arrays.asList(halideShards(0), halideShards(1), halideShards(2), halideShards(3), halideShards(4), halideShards(5), halideShards(6)), Arrays.asList(9, 14,7, 12,36,13,9)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 5),  Arrays.asList(nativeShards(0), nativeShards(1), nativeShards(2), nativeShards(3), nativeShards(4), nativeShards(5), nativeShards(6), nativeShards(7), nativeShards(8), nativeShards(9), nativeShards(10), nativeShards(11), nativeShards(12), nativeShards(13), nativeShards(14), nativeShards(15), nativeShards(16), nativeShards(17)), Arrays.asList(3, 8, 9, 3, 7, 9, 5, 3, 5, 4, 6, 5, 4, 6, 4, 4, 7, 7)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 6),  Arrays.asList(oxideShards(0), oxideShards(1), oxideShards(2), oxideShards(3), oxideShards(4), oxideShards(5), oxideShards(6), oxideShards(7), oxideShards(8), oxideShards(9), oxideShards(10), oxideShards(11), oxideShards(12), oxideShards(13), oxideShards(14), oxideShards(15), oxideShards(16), oxideShards(17), oxideShards(18), oxideShards(19), oxideShards(20), oxideShards(21), oxideShards(22), oxideShards(23), oxideShards(24), oxideShards(25)), Arrays.asList(7, 2, 4, 3, 2, 3, 8, 8, 5, 3, 5, 3, 4, 3, 4, 3, 2, 4, 2, 3, 4, 2, 4, 3, 5, 4)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 7),  Arrays.asList(phosphateShards(0), phosphateShards(1), phosphateShards(2), phosphateShards(3), phosphateShards(4), phosphateShards(5), phosphateShards(6), phosphateShards(7), phosphateShards(8), phosphateShards(9), phosphateShards(10), phosphateShards(11), phosphateShards(12), phosphateShards(13)), Arrays.asList(7, 5, 12,9, 6, 8, 10,6, 9, 7, 4, 7, 5, 5)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 8),  Arrays.asList(silicateShards(0), silicateShards(1), silicateShards(2), silicateShards(3), silicateShards(4), silicateShards(5), silicateShards(6), silicateShards(7), silicateShards(8), silicateShards(9), silicateShards(10), silicateShards(11), silicateShards(12)), Arrays.asList(8, 7, 9, 11,9, 8, 6, 5, 8, 9, 5, 9, 6)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 9),  Arrays.asList(sulfateShards(0), sulfateShards(1), sulfateShards(2), sulfateShards(3), sulfateShards(4), sulfateShards(5), sulfateShards(6), sulfateShards(7), sulfateShards(8), sulfateShards(9), sulfateShards(10), sulfateShards(11)), Arrays.asList(9, 6, 9, 11,6, 10,8, 11,6, 8, 9, 7)));
		analyzerRecipes.add(new MineralAnalyzerRecipe(new ItemStack(ModBlocks.mineralOres, 1, 10), Arrays.asList(sulfideShards(0), sulfideShards(1), sulfideShards(2), sulfideShards(3), sulfideShards(4), sulfideShards(5), sulfideShards(6), sulfideShards(7), sulfideShards(8), sulfideShards(9), sulfideShards(10), sulfideShards(11), sulfideShards(12), sulfideShards(13), sulfideShards(14), sulfideShards(15), sulfideShards(16), sulfideShards(17), sulfideShards(18)), Arrays.asList(4, 6, 4, 9, 3, 8, 9, 4, 5, 6, 5, 3, 6, 3, 5, 6, 6, 4, 4)));

		alloyerRecipes.add(new MetalAlloyerRecipe("CuBe", 			Arrays.asList("dustCopper", "dustBeryllium"), 																Arrays.asList(7, 2), 				new ItemStack(ModItems.alloyItems, 9, 1), 	new ItemStack(ModItems.alloyItems, 1, 2)));  //cube
		alloyerRecipes.add(new MetalAlloyerRecipe("ScAl", 			Arrays.asList("dustAluminum", "dustScandium"), 																Arrays.asList(7, 2), 				new ItemStack(ModItems.alloyItems, 9, 4), 	new ItemStack(ModItems.alloyItems, 1, 5)));  //scal
		alloyerRecipes.add(new MetalAlloyerRecipe("BAM", 			Arrays.asList("dustBoron", "dustAluminum", "dustMagnesium"), 												Arrays.asList(6, 2, 1), 			new ItemStack(ModItems.alloyItems, 9, 7), 	new ItemStack(ModItems.alloyItems, 1, 8)));  //bam
		alloyerRecipes.add(new MetalAlloyerRecipe("YAG", 			Arrays.asList("dustYttrium", "dustAluminum", "dustNeodymium", "dustChromium"), 								Arrays.asList(4, 2, 2, 1), 			new ItemStack(ModItems.alloyItems, 9, 10), 	new ItemStack(ModItems.alloyItems, 1, 11))); //yag
		alloyerRecipes.add(new MetalAlloyerRecipe("Cupronickel",	Arrays.asList("dustCopper", "dustNickel", "dustManganese", "dustIron"), 									Arrays.asList(5, 2, 1, 1), 			new ItemStack(ModItems.alloyItems, 9, 13), 	new ItemStack(ModItems.alloyItems, 1, 14))); //Cupronickel
		alloyerRecipes.add(new MetalAlloyerRecipe("Nimonic",		Arrays.asList("dustNickel", "dustCobalt", "dustChromium"), 													Arrays.asList(5, 2, 2), 			new ItemStack(ModItems.alloyItems, 9, 16), 	new ItemStack(ModItems.alloyItems, 1, 17))); //Nimonic
		alloyerRecipes.add(new MetalAlloyerRecipe("Hastelloy",		Arrays.asList("dustIron", "dustNickel", "dustChromium"), 													Arrays.asList(5, 3, 1), 			new ItemStack(ModItems.alloyItems, 9, 19), 	new ItemStack(ModItems.alloyItems, 1, 20))); //Hastelloy
		alloyerRecipes.add(new MetalAlloyerRecipe("Nichrome",		Arrays.asList("dustNickel", "dustChromium", "dustIron"), 													Arrays.asList(6, 2, 1), 			new ItemStack(ModItems.alloyItems, 9, 22), 	new ItemStack(ModItems.alloyItems, 1, 23))); //Nichrome
		alloyerRecipes.add(new MetalAlloyerRecipe("CuNiFe",			Arrays.asList("dustCopper", "dustNickel", "dustIron", "dustCobalt"), 										Arrays.asList(5, 2, 2, 1), 			new ItemStack(ModItems.alloyItems, 9, 25), 	new ItemStack(ModItems.alloyItems, 1, 26))); //CuNiFe
		alloyerRecipes.add(new MetalAlloyerRecipe("Hydronalium",	Arrays.asList("dustAluminum", "dustMagnesium", "dustManganese"), 											Arrays.asList(6, 2, 1), 			new ItemStack(ModItems.alloyItems, 9, 37), 	new ItemStack(ModItems.alloyItems, 1, 38))); //hydronalium
		alloyerRecipes.add(new MetalAlloyerRecipe("Mischmetall",	Arrays.asList("dustCerium", "dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustIron"), 				Arrays.asList(4, 2, 1, 1, 1), 		new ItemStack(ModItems.alloyBItems,9, 1), 	new ItemStack(ModItems.alloyBItems,1, 2)));  //Mischmetall
		alloyerRecipes.add(new MetalAlloyerRecipe("Rose Gold",		Arrays.asList("dustGold", "dustCopper", "dustSilver"), 														Arrays.asList(5, 3, 1), 			new ItemStack(ModItems.alloyBItems,9, 4), 	new ItemStack(ModItems.alloyBItems,1, 5)));  //Rose Gold
		alloyerRecipes.add(new MetalAlloyerRecipe("Green Gold",		Arrays.asList("dustGold", "dustSilver", "dustCopper", "dustCadmium"),										Arrays.asList(5, 2, 1, 1), 			new ItemStack(ModItems.alloyBItems,9, 7), 	new ItemStack(ModItems.alloyBItems,1, 8)));  //Green Gold
		alloyerRecipes.add(new MetalAlloyerRecipe("White Gold",		Arrays.asList("dustGold", "dustSilver", "dustCopper", "dustManganese"),										Arrays.asList(5, 2, 1, 1), 			new ItemStack(ModItems.alloyBItems,9, 10), 	new ItemStack(ModItems.alloyBItems,1, 11))); //White Gold
		alloyerRecipes.add(new MetalAlloyerRecipe("Shibuichi",		Arrays.asList("dustCopper", "dustSilver", "dustGold"),														Arrays.asList(7, 2, 1), 			new ItemStack(ModItems.alloyBItems,9, 13), 	new ItemStack(ModItems.alloyBItems,1, 14))); //Shibuichi
		alloyerRecipes.add(new MetalAlloyerRecipe("Tombak",			Arrays.asList("dustCopper", "dustZinc", "dustArsenic"),														Arrays.asList(6, 2, 1), 			new ItemStack(ModItems.alloyBItems,9, 16), 	new ItemStack(ModItems.alloyBItems,1, 17))); //tombak
		alloyerRecipes.add(new MetalAlloyerRecipe("Pewter",			Arrays.asList("dustTin", "dustCopper", "dustBismuth", "dustLead"),											Arrays.asList(5, 1, 1, 1), 			new ItemStack(ModItems.alloyBItems,9, 19), 	new ItemStack(ModItems.alloyBItems,1, 20))); //Pewter
		alloyerRecipes.add(new MetalAlloyerRecipe("Corten Steel",	Arrays.asList("dustNickel", "dustSilicon", "dustChromium", "dustPhosphorus", "dustManganese", "dustCopper"),Arrays.asList(2, 2, 2, 1, 1, 1), 	new ItemStack(ModItems.alloyBItems,9, 22), 	new ItemStack(ModItems.alloyBItems,1, 23))); //Corten Steel
		alloyerRecipes.add(new MetalAlloyerRecipe("Shakudo",		Arrays.asList("dustCopper", "dustGold"),																	Arrays.asList(8, 1), 				new ItemStack(ModItems.alloyBItems,9, 25), 	new ItemStack(ModItems.alloyBItems,1, 26))); //shakudo
		alloyerRecipes.add(new MetalAlloyerRecipe("Purple Gold",	Arrays.asList("dustGold", "dustAluminum"),																	Arrays.asList(7, 2), 				new ItemStack(ModItems.alloyBItems,9, 28), 	new ItemStack(ModItems.alloyBItems,1, 29))); //purple gold

		extractorRecipes.add(new ChemicalExtractorRecipe(getText(1), arsenateStack(EnumArsenate.AGARDITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.ARSENIC.name().toLowerCase(), EnumElement.LEAD.name().toLowerCase(), EnumElement.DYSPROSIUM.name().toLowerCase(), EnumElement.YTTRIUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.LANTHANUM.name().toLowerCase(), EnumElement.NEODYMIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.EUROPIUM.name().toLowerCase(), EnumElement.GADOLINIUM.name().toLowerCase(), EnumElement.SAMARIUM.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase()), Arrays.asList(36,22,6,5,4,4,3,3,2,1,1,1,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(1), arsenateStack(EnumArsenate.FORNACITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.ARSENIC.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.CHROMIUM.name().toLowerCase()), Arrays.asList(55,10,9,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(1), arsenateStack(EnumArsenate.SCHULTENITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.ARSENIC.name().toLowerCase()), Arrays.asList(60,22)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(1), arsenateStack(EnumArsenate.PITTICITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.ARSENIC.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(31,21,9)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(1), arsenateStack(EnumArsenate.ZALESIITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.ARSENIC.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.YTTRIUM.name().toLowerCase()), Arrays.asList(38,22,3,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(2), borateStack(EnumBorate.BORAX), Arrays.asList(EnumElement.SODIUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase()), Arrays.asList(12,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(2), borateStack(EnumBorate.ERICAITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.BORON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase()), Arrays.asList(22,17,5,4)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(2), borateStack(EnumBorate.HULSITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase(), EnumElement.TIN.name().toLowerCase()), Arrays.asList(46,10,5,5)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(2), borateStack(EnumBorate.LONDONITE), Arrays.asList(EnumElement.BORON.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.BERYLLIUM.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.LITHIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(15,13,6,2,1,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(2), borateStack(EnumBorate.TUSIONITE), Arrays.asList(EnumElement.TIN.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.BORON.name().toLowerCase()), Arrays.asList(40,19,8)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(2), borateStack(EnumBorate.RHODIZITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase(), EnumElement.BERYLLIUM.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(14,13,8,4)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.ANKERITE), Arrays.asList(EnumElement.CALCIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase()), Arrays.asList(19,16,12,4,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.GASPEITE), Arrays.asList(EnumElement.NICKEL.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(33,11,7,5)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.ROSASITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase()), Arrays.asList(43,15,5)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.PARISITE), Arrays.asList(EnumElement.NEODYMIUM.name().toLowerCase(), EnumElement.LANTHANUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase()), Arrays.asList(23,21,19,7,7,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.OTAVITE), Arrays.asList(EnumElement.CADMIUM.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase()), Arrays.asList(65,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.SMITHSONITE), Arrays.asList(EnumElement.ZINC.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase()), Arrays.asList(52,10)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(3), carbonateStack(EnumCarbonate.HUNTITE), Arrays.asList(EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(21,14,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(4), halideStack(EnumHalide.BOLEITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.SILVER.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(49,14,9,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(4), halideStack(EnumHalide.CARNALLITE), Arrays.asList(EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase()), Arrays.asList(14,9)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(4), halideStack(EnumHalide.RINNEITE), Arrays.asList(EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase()), Arrays.asList(28,14,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(4), halideStack(EnumHalide.GRICEITE), Arrays.asList(EnumElement.LITHIUM.name().toLowerCase()), Arrays.asList(27)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(4), halideStack(EnumHalide.HEKLAITE), Arrays.asList(EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase()), Arrays.asList(19,14,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(4), halideStack(EnumHalide.CREEDITE), Arrays.asList(EnumElement.CALCIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(24,11,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.COHENITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.NICKEL.name().toLowerCase(), EnumElement.COBALT.name().toLowerCase(),EnumElement.CARBON.name().toLowerCase()), Arrays.asList(55,29,10,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.COPPER), Arrays.asList(EnumElement.COPPER.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.CUPALITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(60,26,7,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.HAXONITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.NICKEL.name().toLowerCase(), EnumElement.CARBON.name().toLowerCase()), Arrays.asList(82,13,5)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.PERRYITE), Arrays.asList(EnumElement.NICKEL.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase()), Arrays.asList(60,20,11,4)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.SILVER), Arrays.asList(EnumElement.SILVER.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.TULAMEENITE), Arrays.asList(EnumElement.PLATINUM.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(73,13,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.NIGGLIITE), Arrays.asList(EnumElement.PLATINUM.name().toLowerCase(), EnumElement.TIN.name().toLowerCase()), Arrays.asList(62,38)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.MALDONITE), Arrays.asList(EnumElement.GOLD.name().toLowerCase(), EnumElement.BISMUTH.name().toLowerCase()), Arrays.asList(65,35)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.AURICUPRITE), Arrays.asList(EnumElement.GOLD.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase()), Arrays.asList(51,49)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.OSMIUM), Arrays.asList(EnumElement.OSMIUM.name().toLowerCase(), EnumElement.IRIDIUM.name().toLowerCase()), Arrays.asList(75,25)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.IRIDIUM), Arrays.asList(EnumElement.IRIDIUM.name().toLowerCase(), EnumElement.OSMIUM.name().toLowerCase(), EnumElement.PLATINUM.name().toLowerCase()), Arrays.asList(52,31,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.KHATYRKITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase()), Arrays.asList(46,40,14)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.NICKEL), Arrays.asList(EnumElement.NICKEL.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.FULLERITE), Arrays.asList(EnumElement.CARBON.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.CHAOITE), Arrays.asList(EnumElement.CARBON.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.GRAPHITE), Arrays.asList(EnumElement.CARBON.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.PLATINUM), Arrays.asList(EnumElement.PLATINUM.name().toLowerCase()), Arrays.asList(100)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.CHROMITE), Arrays.asList(EnumElement.CHROMIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(47,25)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.COCHROMITE), Arrays.asList(EnumElement.CHROMIUM.name().toLowerCase(), EnumElement.COBALT.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.NICKEL.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase()), Arrays.asList(35,14,8,6,5,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.COLUMBITE), Arrays.asList(EnumElement.NIOBIUM.name().toLowerCase(), EnumElement.TANTALUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.TITANIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(42,24,8,7,5,2,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.EUXENITE), Arrays.asList(EnumElement.NIOBIUM.name().toLowerCase(), EnumElement.YTTRIUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.TITANIUM.name().toLowerCase()), Arrays.asList(33,16,3,2,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.MCCONNELLITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.CHROMIUM.name().toLowerCase()), Arrays.asList(43,35)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.SAMARSKITE), Arrays.asList(EnumElement.NIOBIUM.name().toLowerCase(), EnumElement.TANTALUM.name().toLowerCase(), EnumElement.THORIUM.name().toLowerCase(), EnumElement.URANIUM.name().toLowerCase(), EnumElement.YTTERBIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.YTTRIUM.name().toLowerCase(), EnumElement.LUTETIUM.name().toLowerCase(), EnumElement.THULIUM.name().toLowerCase(), EnumElement.HOLMIUM.name().toLowerCase(), EnumElement.DYSPROSIUM.name().toLowerCase(), EnumElement.ERBIUM.name().toLowerCase(), EnumElement.EUROPIUM.name().toLowerCase(), EnumElement.TERBIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(24,12,10,10,5,4,4,4,4,4,4,4,4,4,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.CASSITERITE), Arrays.asList(EnumElement.TIN.name().toLowerCase()), Arrays.asList(79)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.BOEHMITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(45)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.GHANITE), Arrays.asList(EnumElement.ZINC.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(36,30)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.HIBONITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.TITANIUM.name().toLowerCase(), EnumElement.LANTHANUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase()), Arrays.asList(40,6,5,3,2,2,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.SENAITE), Arrays.asList(EnumElement.TITANIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.LEAD.name().toLowerCase()), Arrays.asList(25,21,12,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.THORUTITE), Arrays.asList(EnumElement.TITANIUM.name().toLowerCase(), EnumElement.THORIUM.name().toLowerCase(), EnumElement.URANIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(25,24,24,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.IXIOLITE), Arrays.asList(EnumElement.TANTALUM.name().toLowerCase(), EnumElement.TIN.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.NIOBIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(56,7,7,6,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.TAPIOLITE), Arrays.asList(EnumElement.TANTALUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.NIOBIUM.name().toLowerCase()), Arrays.asList(66,6,6,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.BEHOITE), Arrays.asList(EnumElement.BERYLLIUM.name().toLowerCase()), Arrays.asList(21)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.BROMELLITE), Arrays.asList(EnumElement.BERYLLIUM.name().toLowerCase()), Arrays.asList(36)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.TUNGSTITE), Arrays.asList(EnumElement.TUNGSTEN.name().toLowerCase()), Arrays.asList(75)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.WOLFRAMITE), Arrays.asList(EnumElement.TUNGSTEN.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase()), Arrays.asList(61,9,9)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.FERBERITE), Arrays.asList(EnumElement.TUNGSTEN.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(61,18)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.MONTEPONITE), Arrays.asList(EnumElement.CADMIUM.name().toLowerCase()), Arrays.asList(88)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.LOPARITE), Arrays.asList(EnumElement.TITANIUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.NIOBIUM.name().toLowerCase(), EnumElement.LANTHANUM.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(23,17,11,8,8,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.BUNSENITE), Arrays.asList(EnumElement.NICKEL.name().toLowerCase()), Arrays.asList(78)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.LASALITE), Arrays.asList(EnumElement.VANADIUM.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(35,3,3,1,1,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.VANOXITE), Arrays.asList(EnumElement.VANADIUM.name().toLowerCase()), Arrays.asList(46)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.RUTILE), Arrays.asList(EnumElement.TITANIUM.name().toLowerCase()), Arrays.asList(60)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.URANINITE), Arrays.asList(EnumElement.URANIUM.name().toLowerCase()), Arrays.asList(88)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.FAUSTITE), Arrays.asList(EnumElement.VANADIUM.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase()), Arrays.asList(20,15,6,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.LAZULITE), Arrays.asList(EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase()), Arrays.asList(20,18,8)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.MONAZITE), Arrays.asList(EnumElement.NEODYMIUM.name().toLowerCase(), EnumElement.LANTHANUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.GADOLINIUM.name().toLowerCase(), EnumElement.SAMARIUM.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.THORIUM.name().toLowerCase()), Arrays.asList(21,18,16,14,13,11,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.SCHOONERITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase()), Arrays.asList(22,12,8,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.TRIPHYLITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.LITHIUM.name().toLowerCase()), Arrays.asList(35,20,5)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.WAVELLITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase()), Arrays.asList(20,15)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.XENOTIME), Arrays.asList(EnumElement.YTTERBIUM.name().toLowerCase(), EnumElement.YTTRIUM.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase()), Arrays.asList(48,38,14)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.ZAIRITE), Arrays.asList(EnumElement.BISMUTH.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(32,20,10,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.PRETULITE), Arrays.asList(EnumElement.SCANDIUM.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase()), Arrays.asList(32,22)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.TAVORITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.LITHIUM.name().toLowerCase()), Arrays.asList(32,18,4)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.KEYITE), Arrays.asList(EnumElement.ARSENIC.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.CADMIUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(29,15,14,13,1,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.BIRCHITE), Arrays.asList(EnumElement.CADMIUM.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase()), Arrays.asList(32,17,9,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.ZIESITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.VANADIUM.name().toLowerCase()), Arrays.asList(37,30)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(7), posphateStack(EnumPhosphate.SCHODERITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.VANADIUM.name().toLowerCase(), EnumElement.PHOSPHORUS.name().toLowerCase()), Arrays.asList(13,13,8)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.AXINITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase()), Arrays.asList(20,14,10,10,10,5,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.BIOTITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(19,14,10,6,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.GADOLINITE), Arrays.asList(EnumElement.YTTRIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase(), EnumElement.LANTHANUM.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.PRASEODYMIUM.name().toLowerCase(), EnumElement.SAMARIUM.name().toLowerCase(), EnumElement.BERYLLIUM.name().toLowerCase(), EnumElement.EUROPIUM.name().toLowerCase(), EnumElement.HOLMIUM.name().toLowerCase(), EnumElement.LUTETIUM.name().toLowerCase(), EnumElement.TERBIUM.name().toLowerCase(), EnumElement.THULIUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase()), Arrays.asList(22,10,10,6,6,6,6,4,3,3,3,3,3,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.IRANITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.CHROMIUM.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase()), Arrays.asList(68,10,2,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.JERVISITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.SCANDIUM.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase()), Arrays.asList(25,12,6,5,5,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.MAGBASITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.SCANDIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(18,12,10,4,2,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.MOSKVINITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.YTTRIUM.name().toLowerCase(), EnumElement.TITANIUM.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.DYSPROSIUM.name().toLowerCase(), EnumElement.GADOLINIUM.name().toLowerCase(), EnumElement.HOLMIUM.name().toLowerCase(), EnumElement.TERBIUM.name().toLowerCase(), EnumElement.SAMARIUM.name().toLowerCase()), Arrays.asList(28,11,7,6,3,1,1,1,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.EUCRYPTITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.LITHIUM.name().toLowerCase()), Arrays.asList(22,22,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.STEACYITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.THORIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase()), Arrays.asList(27,25,4,2,2,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.MANANDONITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.SILICON.name().toLowerCase(), EnumElement.LITHIUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase()), Arrays.asList(26,11,3,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.VISTEPITE), Arrays.asList(EnumElement.MANGANESE.name().toLowerCase(), EnumElement.TIN.name().toLowerCase(), EnumElement.PLATINUM.name().toLowerCase(), EnumElement.BORON.name().toLowerCase()), Arrays.asList(29,16,15,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.KHRISTOVITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.CERIUM.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.EUROPIUM.name().toLowerCase(), EnumElement.LUTETIUM.name().toLowerCase(), EnumElement.THULIUM.name().toLowerCase(), EnumElement.TERBIUM.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.TITANIUM.name().toLowerCase(), EnumElement.CHROMIUM.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(14,12,9,5,5,4,4,4,4,2,1,1,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(8), silicateStack(EnumSilicate.CAVANSITE), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.VANADIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(25,11,9)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.ALUNITE), Arrays.asList(EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(20,15,10)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.FEDOTOVITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(33,17,14)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.JAROSITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(33,13,8)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.GUARINOITE), Arrays.asList(EnumElement.ZINC.name().toLowerCase(), EnumElement.COBALT.name().toLowerCase(), EnumElement.NICKEL.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(34,8,8,4)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.BENTORITE), Arrays.asList(EnumElement.CALCIUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.CHROMIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(19,7,6,1)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.APLOWITE), Arrays.asList(EnumElement.COBALT.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.MANGANESE.name().toLowerCase(), EnumElement.NICKEL.name().toLowerCase()), Arrays.asList(16,14,7,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.BIEBERITE), Arrays.asList(EnumElement.COBALT.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(21,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.SCHEELITE), Arrays.asList(EnumElement.TUNGSTEN.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase()), Arrays.asList(64,14)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.STOLZITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.TUNGSTEN.name().toLowerCase()), Arrays.asList(46,40)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.LOPEZITE), Arrays.asList(EnumElement.CHROMIUM.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(35,26)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.CROCOITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.CHROMIUM.name().toLowerCase()), Arrays.asList(64,16)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(9), sulfateStack(EnumSulfate.KAMCHATKITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.POTASSIUM.name().toLowerCase()), Arrays.asList(40,13,8)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.ABRAMOVITE), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.BISMUTH.name().toLowerCase(), EnumElement.TIN.name().toLowerCase()), Arrays.asList(37,21,17,12)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.AIKINITE), Arrays.asList(EnumElement.BISMUTH.name().toLowerCase(), EnumElement.LEAD.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase()), Arrays.asList(36,36,17,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.BALKANITE), Arrays.asList(EnumElement.BISMUTH.name().toLowerCase(), EnumElement.SILVER.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(36,35,16)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.GALENA), Arrays.asList(EnumElement.LEAD.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(88,12)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.KESTERITE), Arrays.asList(EnumElement.TIN.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(32,27,27,10,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.PENTLANDITE), Arrays.asList(EnumElement.NICKEL.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(37,33,33)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.PYRITE), Arrays.asList(EnumElement.SULFUR.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(53,47)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.STANNITE), Arrays.asList(EnumElement.SULFUR.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.TIN.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase()), Arrays.asList(30,30,28,10,2)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.VALLERIITE), Arrays.asList(EnumElement.IRON.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.MAGNESIUM.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(26,26,24,9,7)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.SPHALERITE), Arrays.asList(EnumElement.ZINC.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(64,33,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.PETRUKITE), Arrays.asList(EnumElement.SULFUR.name().toLowerCase(), EnumElement.TIN.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.IRON.name().toLowerCase(), EnumElement.ZINC.name().toLowerCase(), EnumElement.SILVER.name().toLowerCase()), Arrays.asList(34,25,20,9,4,3)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.MAWSONITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.TIN.name().toLowerCase(), EnumElement.IRON.name().toLowerCase()), Arrays.asList(44,29,14,13)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.TUNGSTENITE), Arrays.asList(EnumElement.TUNGSTEN.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(74,26)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.ERLICHMANITE), Arrays.asList(EnumElement.OSMIUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(75,25)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.MALANITE), Arrays.asList(EnumElement.PLATINUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.IRIDIUM.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase()), Arrays.asList(50,22,17,11)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.GREENOCKITE), Arrays.asList(EnumElement.CADMIUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(78,22)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.CERNYITE), Arrays.asList(EnumElement.SULFUR.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase(), EnumElement.TIN.name().toLowerCase(), EnumElement.CADMIUM.name().toLowerCase()), Arrays.asList(27,26,24,23)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.SULVANITE), Arrays.asList(EnumElement.COPPER.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase(), EnumElement.VANADIUM.name().toLowerCase()), Arrays.asList(51,35,14)));
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(10), sulfideStack(EnumSulfide.PATRONITE), Arrays.asList(EnumElement.SULFUR.name().toLowerCase(), EnumElement.VANADIUM.name().toLowerCase()), Arrays.asList(71,28)));

		extractorRecipes.add(new ChemicalExtractorRecipe("Silicate", new ItemStack(Items.DYE, 1, 4), Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(17,16,14,8,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe("Carbon", new ItemStack(ModItems.chemicalItems, 1, 6), Arrays.asList(EnumElement.CARBON.name().toLowerCase()), Arrays.asList(50)));

		seasonerRecipes.add(new SaltSeasonerRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER)));

		depositionRecipes.add(new DepositionChamberRecipe(new ItemStack(ModItems.chemicalDusts,1,42), new ItemStack(ModItems.alloyItems, 1, 28), new FluidStack(ModFluids.AMMONIA,5000), 1600, 10000));
		depositionRecipes.add(new DepositionChamberRecipe(new ItemStack(ModItems.chemicalDusts,1,42), new ItemStack(ModItems.alloyItems, 1, 31), new FluidStack(ModFluids.SYNGAS,8000), 2200, 24000));
		depositionRecipes.add(new DepositionChamberRecipe(titaniumIngot, new ItemStack(ModItems.alloyItems, 1, 34), new FluidStack(ModFluids.AMMONIA,4000), 1200, 30000));
	}

	private static ItemStack arsenateShards(int i) {	return new ItemStack(ModItems.arsenateShards,1,i);}
	private static ItemStack borateShards(int i) {		return new ItemStack(ModItems.borateShards,1,i);}
	private static ItemStack carbonateShards(int i) {	return new ItemStack(ModItems.carbonateShards,1,i);}
	private static ItemStack halideShards(int i) {		return new ItemStack(ModItems.halideShards,1,i);}
	private static ItemStack nativeShards(int i) {		return new ItemStack(ModItems.nativeShards,1,i);}
	private static ItemStack oxideShards(int i) {		return new ItemStack(ModItems.oxideShards,1,i);}
	private static ItemStack phosphateShards(int i) {	return new ItemStack(ModItems.phosphateShards,1,i);}
	private static ItemStack silicateShards(int i) {	return new ItemStack(ModItems.silicateShards,1,i);}
	private static ItemStack sulfateShards(int i) {		return new ItemStack(ModItems.sulfateShards,1,i);}
	private static ItemStack sulfideShards(int i) {		return new ItemStack(ModItems.sulfideShards,1,i);}
	private static ItemStack dyeShards(int i) {			return new ItemStack(Items.DYE,1,i);}

	private static String getText(int i) {return EnumOres.getName(i).toUpperCase().substring(0, 1) + EnumOres.getName(i).substring(1);}

	private static ItemStack arsenateStack(EnumArsenate mineral) {return new ItemStack(ModItems.arsenateShards, 1, mineral.ordinal());}
	private static ItemStack borateStack(EnumBorate mineral) {return new ItemStack(ModItems.borateShards, 1, mineral.ordinal());}
	private static ItemStack carbonateStack(EnumCarbonate mineral) {return new ItemStack(ModItems.carbonateShards, 1, mineral.ordinal());}
	private static ItemStack halideStack(EnumHalide mineral) {return new ItemStack(ModItems.halideShards, 1, mineral.ordinal());}
	private static ItemStack nativeStack(EnumNative mineral) {return new ItemStack(ModItems.nativeShards, 1, mineral.ordinal());}
	private static ItemStack oxideStack(EnumOxide mineral) {return new ItemStack(ModItems.oxideShards, 1, mineral.ordinal());}
	private static ItemStack posphateStack(EnumPhosphate mineral) {return new ItemStack(ModItems.phosphateShards, 1, mineral.ordinal());}
	private static ItemStack silicateStack(EnumSilicate mineral) {return new ItemStack(ModItems.silicateShards, 1, mineral.ordinal());}
	private static ItemStack sulfateStack(EnumSulfate mineral) {return new ItemStack(ModItems.sulfateShards, 1, mineral.ordinal());}
	private static ItemStack sulfideStack(EnumSulfide mineral) {return new ItemStack(ModItems.sulfideShards, 1, mineral.ordinal());}

	private static ItemStack minerals(int type){return new ItemStack(ModBlocks.mineralOres,1,type);}

	private static void chemicalRecipes() {
	//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { flask, "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 2), new Object[] { flask, "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 2), new Object[] { flask, "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { flask, "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { flask, Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 2), new Object[] { flask, "itemLignite", "itemLignite", "itemLignite" }));
	//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 3), new Object[] { flask, "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 3), new Object[] { flask, "itemSalt", "itemSalt", "itemSalt" }));
	//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { flask, "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { flask, "itemFluorite", "itemFluorite", "itemFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 4), new Object[] { flask, "gemApatite", "gemApatite", "gemApatite" }));
	//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 6), new Object[] { flask, "fuelCoke"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 6), new Object[] { flask, "itemAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 3, 6), new Object[] { flask, "itemBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 6), new Object[] { flask, Items.COAL}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 6), new Object[] { flask, "itemLignite"}));
	//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 5), new Object[] { crackedCoal, crackedCoal, crackedCoal, crackedCoal}));
	//sodium polyacrilate
		if( !FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 0), new Object[] { "SSS","SBS", "SSS", 'S', "dustSodium", 'B', ModFluids.acrylicAcidBeaker}));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chemicalItems, 8, 0), new Object[] { "SSS","SBS", "SSS", 'S', "dustSodium", 'B', ToolUtils.getFluidBucket(ModFluids.ACRYLIC_ACID) }));
		}
	//silicon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 8), new Object[] { flask, "dustSilicon", "dustSilicon", "dustSilicon" }));
	//silicone gun
		if( !FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.siliconeCartridge), new Object[] { " B ", "IIS"," NI", 'S', "stickWood", 'I', "ingotIron", 'N', "nuggetIron", 'B', ModFluids.siliconeBeaker }));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.siliconeCartridge), new Object[] { " B ", "IIS"," NI", 'S', "stickWood", 'I', "ingotIron", 'N', "nuggetIron", 'B', ToolUtils.getFluidBucket(ModFluids.SILICONE) }));
		}
	//slime ball
		if(ModConfig.forceSilicone){
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.SLIME_BALL), new Object[] { new ItemStack(ModItems.siliconeCartridge, 1, OreDictionary.WILDCARD_VALUE) }));
		}
	//ferrous catalyst
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 9), new Object[] { flask, "dustIron", "dustIron", "dustIron" }));
	//platinum catalyst
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 10), new Object[] { flask, "dustPlatinum", "dustPlatinum", "dustPlatinum" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 10), new Object[] { flask, platinumShard, platinumShard, platinumShard }));
	//cracked charchoal
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 2, 14), new Object[] { flask, charcoal, charcoal, charcoal }));
	//ash compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 11), new Object[] { flask, crackedCharcoal, crackedCharcoal, crackedCharcoal }));
	//potassium carbonate
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 1, 12), new Object[] { Items.WATER_BUCKET, ashCompost, ashCompost, ashCompost }));
	//potassium nitrate
		if( !FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 3, 13), new Object[] { ModFluids.nitricAcidBeaker, potCarbonate, potCarbonate, potCarbonate }));
		}else{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 3, 13), new Object[] { ToolUtils.getFluidBucket(ModFluids.NITRIC_ACID), potCarbonate, potCarbonate, potCarbonate }));
		}
	//gunpowder
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.GUNPOWDER, 9), new Object[] { potNitrate, potNitrate, potNitrate, potNitrate, potNitrate, potNitrate, crackedCharcoal, sulfCompost, crackedCharcoal}));
	//rutile compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalItems, 4, 15), new Object[] { flask, rutileShard, rutileShard, rutileShard }));
	//throwable screen smoke
		if(!FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.splashSmoke, 4), new Object[] {ModFluids.titaniumTetrachlorideBeaker, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE  }));
		}else{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.splashSmoke, 4), new Object[] {ToolUtils.getFluidBucket(ModFluids.TITANIUM_TETRACHLORIDE), Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE }));
		}
	//fire compounds
		if(!FluidRegistry.isUniversalBucketEnabled() ){
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 0), new Object[] {ModFluids.hydrochloricAcidBeaker, borax, borax, borax, borax, borax, borax, borax, borax  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 1), new Object[] {flask, "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 2), new Object[] {ModFluids.hydrochloricAcidBeaker, "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 3), new Object[] {flask, "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 4), new Object[] {flask, "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 5), new Object[] {ModFluids.hydrochloricAcidBeaker, "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 5), new Object[] {ModFluids.hydrochloricAcidBeaker, "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 6), new Object[] {ModFluids.hydrochloricAcidBeaker, "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 7), new Object[] {ModFluids.hydrochloricAcidBeaker, "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 8), new Object[] {ModFluids.nitricAcidBeaker, "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead"  }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 9), new Object[] {ModFluids.hydrochloricAcidBeaker, "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese"  }));
		}else{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 0), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), borax, borax, borax, borax, borax, borax, borax, borax }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 1), new Object[] {flask, "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 2), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 3), new Object[] {flask, "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 4), new Object[] {flask, "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 4), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium", "dustSodium" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 5), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 6), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 7), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 8), new Object[] {ToolUtils.getFluidBucket(ModFluids.NITRIC_ACID), "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead" }));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.chemicalFires, 8, 9), new Object[] {ToolUtils.getFluidBucket(ModFluids.HYDROCHLORIC_ACID), "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese" }));
		}
	}

	private static void toolsRecipes() {
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
	//bam sword
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bamSword), new Object[] { "I", "I", "S", 'I', "ingotBam", 'S', "stickWood"}));
	//bam shear
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bamShears), new Object[] { " I", "I ", 'I', "ingotBam"}));
	}

	private static void depositionRecipes() {
	//deposition chamber
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.depositionChamber), new Object[] { "CcC", "ILI", "CXC", 'I', machineCasing, 'L', ModBlocks.labOven, 'C', hastelloyCasing, 'c', logicChip, 'X', inductor }));
	//hastelloy Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(hastelloyCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetHastelloy", 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//chamber upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(chamberUpgrade, new Object[] { "NIN", "I I", "NIN", 'N', crawlerArm, 'I', hastelloyCasing }));
	//insulation upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(insulationUpgrade, new Object[] { "NIN", "I I", "NIN", 'N', owcArm, 'I', machineCasing }));
	}

	private static void boundaryRecipes() {
	//boundary breaker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.earthBreaker), new Object[] { "BCB", "ATA", "BHB", 'H', boundaryHead, 'T', tiniteArm, 'A', crawlerArm, 'C', boundaryChip, 'B', boundaryCasing }));
	//boundary chip
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryChip, new Object[] { "FIF","GCG","FIF", 'C', logicChip, 'I', "gemQuartz", 'G', "nuggetGold", 'F', "ingotHydronalium" }));
	//Boundary Head
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryHead, new Object[] { "AHA", "NTN", "CTC", 'A', boundaryCasing, 'H', "blockHastelloy", 'N', "ingotSiena", 'C', "ingotCarborundum", 'T', "ingotTinite" }));
	//Siena bearing
		GameRegistry.addRecipe(new ShapedOreRecipe(sienaBearing, new Object[] { "SSS", "SBS", "SSS", 'S', "ingotSiena", 'B', "blockIron" }));
	//breaker arm
		GameRegistry.addRecipe(new ShapedOreRecipe(tiniteArm, new Object[] { "SBS", "IBI", "IBI", 'S', sienaBearing, 'B', "blockTinite", 'I', boundaryCasing }));
	//boundary Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetHydronalium", 'I', "ingotHydronalium", 'B', "blockHydronalium" }));
	}

}