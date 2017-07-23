package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ShapedNbtRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes extends BaseRecipes {

	public static void init() {
		chemicalRecipes();
		inductionRecipes();
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
		ganRecipes();
	}

	private static void ganRecipes() {
		//compessor
 		GameRegistry.addRecipe(new ShapedOreRecipe(compressor, new Object[] { "cPc", "c c", "cRc", 'R', "dustRedstone", 'P', Blocks.PISTON, 'c', ironCasing }));
 		//spiral
 		GameRegistry.addRecipe(new ShapedOreRecipe(spirals, new Object[] { "CCC", "C C", "I I", 'I', "ingotIron", 'C', "ingotCopper" }));
 		//cupronickel casing
		GameRegistry.addRecipe(new ShapedOreRecipe(cupronickelCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetIron", 'I', "ingotCupronickel", 'B', "blockCupronickel" }));

		//vessel
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(0), new Object[] { "IXI", "c c", "ccc", 'I', "ingotIron", 'X', compressor, 'c', ironCasing }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(6), new Object[] { "cOc", "cSc", "ccc", 'O', gan(0), 'c', hastelloyCasing, 'S', spiral }));
		//chiller
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(1), new Object[] { "cLc", "c c", "cBc", 'c', ironCasing, 'B', Items.WATER_BUCKET, 'L', logicChip}));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(7), new Object[] { "SOS", "SSS", "ccc", 'c', cupronickelCasing, 'O', gan(1), 'S', spiral }));
		//condenser
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(2), new Object[] { "cCc", "cLc", "cCc", 'c', ironCasing, 'L', logicChip, 'C', compressor }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(8), new Object[] { "cOc", "SSS", "ccc", 'c', cupronickelCasing, 'S', spiral, 'O', gan(2) }));
		//turbine
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(3), new Object[] { "cPc", "cLc", "cPc", 'c', ironCasing, 'P', Blocks.PISTON, 'L', logicChip }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(9), new Object[] { "cOc", "cTc", "cRc", 'c', nimonicCasing, 'R', owcRotor, 'O', gan(3), 'T', owcFan }));
		//tank
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(4), new Object[] { "GcG", "G G", "GcG", 'G', "blockGlass", 'c', ironCasing }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(10),new Object[] { "cSc", "cOc", "ccc", 'S', spiral, 'c', hydronaliumCasing, 'O', gan(4) }));
		//tower
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(5), new Object[] { "c c", "c c", "c c", 'c', ironCasing }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(11),new Object[] { "cSc", "cOc", "cSc", 'c', nimonicCasing, 'S', spiral, 'O', gan(5) }));
 		//controller
 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.ganController),new Object[] { "XFX", "FLF", "FCF", 'L', advancedChip, 'F', ironCasing, 'C', Items.COMPARATOR, 'X', logicChip }));
	}

	private static void metallurgyRecipes() {
	 //alloy parts
 		for(int x = 0; x < EnumAlloy.size(); x++){
 			for(ItemStack ore : OreDictionary.getOres(EnumAlloy.getDust(x))) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, alloys(1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(alloys(1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloy.getNugget(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(alloys(9, (x*3)+1), new Object[] { EnumAlloy.getBlock(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(alloys(9, (x*3)+2), new Object[] { EnumAlloy.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloy.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBricks, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloy.getBlock(x) }));
 		}

 	//alloy parts
 		for(int x = 0; x < EnumAlloyB.size(); x++){
 			for(ItemStack ore : OreDictionary.getOres(EnumAlloyB.getDust(x))) {if(ore != null)  {if(ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {GameRegistry.addSmelting(ore, alloysB(1, (x*3) + 1), 1.0F);}}}
 	 		GameRegistry.addRecipe(new ShapedOreRecipe(alloysB(1, (x*3)+1), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloyB.getNugget(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(alloysB(9, (x*3)+1), new Object[] { EnumAlloyB.getBlock(x) }));
 			GameRegistry.addRecipe(new ShapelessOreRecipe(alloysB(9, (x*3)+2), new Object[] { EnumAlloyB.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBBlocks, 1, x), new Object[] { "XXX", "XXX", "XXX", 'X', EnumAlloyB.getIngot(x) }));
 			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alloyBBricks, 4, x), new Object[] { "XX", "XX", 'X', EnumAlloyB.getBlock(x) }));
 		}
	}

	private static void machineryRecipes() {
	//lab oven
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.labOven), new Object[] { "GCG", "IcI", "IGI", 'I', ironCasing, 'C', cabinet, 'c', logicChip, 'G', "blockGlass" }));
	//mineral sizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralSizer), new Object[] { "IHI", "IcI", "ICI", 'C', cabinet, 'I', ironCasing, 'c', logicChip, 'H', Blocks.HOPPER }));
	//leaching vat
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralAnalyzer), new Object[] { "IcI", "GGG", "ICI", 'C', cabinet, 'I', ironCasing, 'c', logicChip, 'G', "blockGlass" }));
	//chemical extractor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chemicalExtractor), new Object[] { "GCI", "GcI", "GII", 'C', cabinet, 'I', ironCasing, 'c', logicChip, 'G', "blockGlass" }));
	//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.metalAlloyer), new Object[] { "ICI", "IHI", "IcI", 'C', cabinet, 'I', ironCasing, 'H', Blocks.HOPPER, 'c', logicChip}));
	//salt maker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.saltMaker), new Object[] { "S S", "sss", 's', "stone", 'S', "ingotBrick" }));
	//salt seasoner
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.saltSeasoner), new Object[] { "SSS", "SSS", "PIP", 'S', "slabWood", 'I', "ingotIron", 'P', "plankWood" }));
	//petrographer table
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.petrographerTable), new Object[] { " G ", "SSS", " P ", 'P', "plankWood", 'S', "slabWood", 'G', "blockGlass" }));
	//cabinet
		GameRegistry.addRecipe(new ShapedOreRecipe(cabinet, new Object[] { "GGG","GIG","GGG", 'I', "ingotIron", 'G', "blockGlass" }));
	//logic chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(logicChip, new Object[] { "ingotIron", "ingotIron", "nuggetGold", "dustRedstone" }));
	//flask
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.flask, new Object[] { " G ","G G","GGG", 'G', "blockGlass"}));
	//iron Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(ironCasings, new Object[] { "NIN", "INI", "NIN", 'N', "nuggetIron", 'I', "ingotIron" }));
	//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.cylinder, new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
	//gear
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.gear, new Object[] { " N ","NIN"," N ", 'I', "ingotIron", 'N', "nuggetIron" }));
	//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.testTube, new Object[] { "  G"," G ","N  ", 'N', "nuggetIron", 'G', "blockGlass" }));
	//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.pattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
	//agitator
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.agitator, new Object[] { " I ","NIN","NIN", 'I', "ingotIron", 'N', "nuggetIron" }));
	//feCatalyst
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.feCatalyst, new Object[] { "  N"," II","NII", 'I', "ingotIron", 'N', "nuggetIron" }));
	//platinum ingot
		GameRegistry.addSmelting(chemicals(1,10), platinumIngot, 1.0F);
	//ptCatalyst
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.ptCatalyst, new Object[] { "  N"," II","NII", 'I', "ingotPlatinum", 'N', "nuggetIron" }));
	}

	private static void inductionRecipes() {
	//nichrome rod
		GameRegistry.addRecipe(new ShapedOreRecipe(nichromeRods, new Object[] { "N", "N", 'N', "ingotNichrome"}));
	//heater
		GameRegistry.addRecipe(new ShapedOreRecipe(heatingElement, new Object[] { "NNN", "N N", "I I", 'I', "nuggetIron", 'N', nichromeRod}));
	}

	private static void genericRecipes() {
	//iron ingot
		GameRegistry.addRecipe(new ShapedOreRecipe(ironIngot, new Object[] { "NNN","NNN","NNN", 'N', "nuggetIron" }));
	//iron nugget
		GameRegistry.addRecipe(new ShapelessOreRecipe(ironNuggets, new Object[] { "ingotIron" }));
	//wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(modWrench, new Object[] { " N "," SN", 'S', "stickWood", 'N', "nuggetIron"}));
	//gold ingot
   		GameRegistry.addSmelting(elements(1,45), goldIngot, 1.0F);
	//iron ingot
   		GameRegistry.addSmelting(elements(1,16), ironIngot, 1.0F);
	//copper ingot
   		GameRegistry.addSmelting(elements(1,17), copperIngot, 1.0F);
	//lead ingot
   		GameRegistry.addSmelting(elements(1,19), leadIngot, 1.0F);
   	//titanium ingot
   		GameRegistry.addSmelting(elements(1,29), titaniumIngot, 1.0F);
   	//platinum ingot
		GameRegistry.addSmelting(elements(1,44), platinumIngot, 1.0F);
   	//aluminum ingot
   		GameRegistry.addSmelting(elements(1,24), aluminumIngot, 1.0F);
		GameRegistry.addRecipe(new ShapedOreRecipe(aluminumIngot, new Object[] { "NNN","NNN","NNN", 'N', "nuggetAluminum" }));
   	//aluminum nugget
		GameRegistry.addRecipe(new ShapelessOreRecipe(aluminumNuggets, new Object[] { "ingotAluminum" }));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,0), new Object[] { "III", " S ", "III", 'I', cupronickelFoil, 'S', "stone" }));
	//concrete
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(4,1), new Object[] { "GSG", "SCS", "GWG", 'G', "gravel", 'S', "sand", 'C', Items.CLAY_BALL, 'W', Items.WATER_BUCKET }));
	//duct
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,2), new Object[] { "FGF", "CGC", "FGF", 'C', nimonicCasing, 'F', nimonicFoil, 'G', "blockGlass" }));
	//turbine
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,3), new Object[] { "FAF", "CTC", "FAF", 'C', nimonicCasing, 'A', owcArm, 'T', owcFan, 'F', nimonicFoil }));
	//valve
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,4), new Object[] { "GFG", "FDF", "G G", 'G', "ingotGold", 'D', owc(1,2), 'F', nimonicFoil }));
	//generator
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,5), new Object[] { "FSF", "CRC", "FAF", 'S', owcStator, 'C', nimonicCasing, 'R', owcRotor, 'A', owcArm, 'F', nimonicFoil }));
	//storage
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,6), new Object[] { "ECE", "MEM", "ECE", 'C', copperCoil, 'E', energyCell, 'M', nimonicCasing }));
	//inverter
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,7), new Object[] { "MCI", "cCI", "MCI", 'C', copperCoil, 'I', "ingotIron", 'c', Items.COMPARATOR, 'M', nimonicCasing }));
	//deflector
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,8), new Object[] { "FIF", " I ", "FIF", 'F', cupronickelFoil, 'I', "ingotCupronickel" }));
	//owc assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcAssembler), new Object[] { " I ", "ICI", " I ", 'C', "workbench", 'I', nimonicFoil }));
	//owc controller
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcController), new Object[] { "XFX", "FAF", "FCF", 'A', advancedChip, 'F', nimonicCasing, 'C', Items.COMPARATOR, 'X', logicChip }));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(owcFan, new Object[] { " I ", "IAI", " I ", 'I', nimonicFoil, 'A', owcArm }));
	//nimonic foils
		GameRegistry.addRecipe(new ShapelessOreRecipe(nimonicFoils, new Object[] { "ingotNimonic", "ingotNimonic" }));
	//energy Cell
		GameRegistry.addRecipe(new ShapedOreRecipe(energyCell, new Object[] { "NMN", "LML", "LXL", 'N', "nuggetIron", 'L', "ingotLead", 'X', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID)), 'M', ironCasing }));
	//machine Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(nimonicCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetIron", 'I', "ingotNimonic", 'B', "blockNimonic" }));
	}

	private static void laserRecipes() {
	//Laser TX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneTx), new Object[] { "RE ", " I ", "III", 'I', aluminumCasing, 'E', laserResonator, 'R', Items.REPEATER }));
	//Laser RX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 0), new Object[] { "ERE", "PCP", "EIE", 'I', aluminumCasing, 'E', laserResonator, 'C', Items.COMPARATOR, 'P', "paneGlass", 'R', "blockRedstone" }));
	//Laser Node
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserSplitter), new Object[] { "EEE", "CER", "III", 'I', aluminumCasing, 'E', laserResonator, 'C', Items.COMPARATOR, 'R', Items.REPEATER }));
	//Laser Pin Tx
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 4), new Object[] { "EEE", "ERE", "III", 'I', aluminumCasing, 'E', laserResonator, 'R', Items.REPEATER}));
	//Laser Pin Rx
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 5), new Object[] { "EEE", "ECE", "III", 'I', aluminumCasing, 'E', laserResonator, 'C', Items.COMPARATOR}));
	//yag rod
		GameRegistry.addRecipe(new ShapedOreRecipe(yagRods, new Object[] { "Y", "Y", 'Y', "ingotYag"}));
	//yag emitter
		GameRegistry.addRecipe(new ShapedOreRecipe(laserResonator, new Object[] { "TTT", "RRP", "TTT", 'T', Blocks.REDSTONE_TORCH, 'R', yagRod, 'P', "paneGlass"}));
	//ElectroLaser
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.electroLaser), new Object[] { "LGE", "cCc", "III", 'G', "blockGlass", 'I', aluminumCasing, 'E', laserResonator, 'C', compressor, 'c', copperCoil, 'L', ModBlocks.laserRedstoneTx }));
	//Laser stabilizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserStabilizer), new Object[] { "E E", " S ", "III", 'I', aluminumCasing, 'E', laserResonator, 'S', owcStator }));
	//Laser amplifier
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserAmplifier), new Object[] { "EGE", "RSR", "III", 'I', aluminumCasing, 'E', laserResonator, 'S', owcStator, 'R', Items.REPEATER }));
	//aluminum Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(aluminumCasings, new Object[] { "NIN", "INI", "NIN", 'N', "nuggetAluminum", 'I', "ingotAluminum" }));
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

	private static void chemicalRecipes() {
	//sulfur compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(8,2), new Object[] { ToolUtils.flask, "dustSulfur", "dustSulfur", "dustSulfur" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,2), new Object[] { ToolUtils.flask, "itemPyrite", "itemPyrite", "itemPyrite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,2), new Object[] { ToolUtils.flask, "itemAnthracite", "itemAnthracite", "itemAnthracite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,2), new Object[] { ToolUtils.flask, "itemBituminous", "itemBituminous", "itemBituminous" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,2), new Object[] { ToolUtils.flask, Items.COAL, Items.COAL, Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,2), new Object[] { ToolUtils.flask, "itemLignite", "itemLignite", "itemLignite" }));
	//sodium chloride compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,3), new Object[] { ToolUtils.flask, "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,3), new Object[] { ToolUtils.flask, "itemSalt", "itemSalt", "itemSalt" }));
	//fluorite compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,4), new Object[] { ToolUtils.flask, "dustFluorite", "dustFluorite", "dustFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,4), new Object[] { ToolUtils.flask, "itemFluorite", "itemFluorite", "itemFluorite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,4), new Object[] { ToolUtils.flask, "gemApatite", "gemApatite", "gemApatite" }));
	//cracked coal
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(8,6), new Object[] { ToolUtils.flask, "fuelCoke"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,6), new Object[] { ToolUtils.flask, "itemAnthracite"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(3,6), new Object[] { ToolUtils.flask, "itemBituminous"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,6), new Object[] { ToolUtils.flask, Items.COAL}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,6), new Object[] { ToolUtils.flask, "itemLignite"}));
	//carbon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,5), new Object[] { crackedCoal, crackedCoal, crackedCoal, crackedCoal}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,5), new Object[] { ToolUtils.flask, "fuelCoke", "fuelCoke"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,5), new Object[] { ToolUtils.flask, "dustCarbon", "dustCarbon"}));
	//sodium polyacrilate
		GameRegistry.addRecipe(new ShapedOreRecipe(chemicals(8,0), new Object[] { "SSS","SBS", "SSS", 'S', "dustSodium", 'B', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.ACRYLIC_ACID)) }));
	//silicon compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,8), new Object[] { ToolUtils.flask, "dustSilicon", "dustSilicon", "dustSilicon" }));
	//silicone gun
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.siliconeCartridge), new Object[] { " B ", "IIS"," NI", 'S', "stickWood", 'I', "ingotIron", 'N', "nuggetIron", 'B', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.SILICONE)) }));
	//slime ball
		if(ModConfig.forceSilicone){
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.SLIME_BALL), new Object[] { new ItemStack(ModItems.siliconeCartridge, 1, OreDictionary.WILDCARD_VALUE) }));
		}
	//ferrous catalyst
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,9), new Object[] { ToolUtils.flask, "dustIron", "dustIron", "dustIron" }));
	//platinum catalyst
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(3,10), new Object[] { ToolUtils.flask, platinumShard, platinumShard, platinumShard, platinumShard }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,10), new Object[] { ToolUtils.flask, tulaShard, tulaShard, tulaShard, tulaShard }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,10), new Object[] { ToolUtils.flask, niggliiteShard, niggliiteShard, niggliiteShard, niggliiteShard }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,10), new Object[] { ToolUtils.flask, malaniteShard, malaniteShard, malaniteShard, malaniteShard }));
	//cracked charchoal
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(2,14), new Object[] { ToolUtils.flask, charcoal, charcoal, charcoal }));
	//ash compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,11), new Object[] { ToolUtils.flask, crackedCharcoal, crackedCharcoal, crackedCharcoal }));
	//potassium carbonate
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,12), new Object[] { Items.WATER_BUCKET, ashCompost, ashCompost, ashCompost }));
	//potassium nitrate
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(3,13), new Object[] { CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID)), potCarbonate, potCarbonate, potCarbonate }));
	//gunpowder
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.GUNPOWDER, 9), new Object[] { potNitrate, potNitrate, potNitrate, potNitrate, potNitrate, potNitrate, crackedCharcoal, sulfCompost, crackedCharcoal}));
	//rutile compost
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(4,15), new Object[] { ToolUtils.flask, rutileShard, rutileShard, rutileShard }));
	//throwable screen smoke
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.splashSmoke, 4), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.TITANIUM_TETRACHLORIDE)), Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE }));
	//fire compounds
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 0), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), borax, borax, borax, borax, borax, borax, borax, borax }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 1), new Object[] {ToolUtils.flask, "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic", "dustArsenic" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 2), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 3), new Object[] {ToolUtils.flask, "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 4), new Object[] {ToolUtils.flask, "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt", "dustSalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 5), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 6), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 7), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 8), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID)), "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 9), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese" }));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.depositionChamber), new Object[] { "CcC", "ILI", "CXC", 'I', nimonicCasing, 'L', ModBlocks.labOven, 'C', hastelloyCasing, 'c', logicChip, 'X', CoreUtils.inductor }));
	//hastelloy Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(hastelloyCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetIron", 'I', "ingotHastelloy", 'B', "blockHastelloy" }));
	//chamber upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(chamberUpgrade, new Object[] { "NIN", "I I", "NIN", 'N', crawlerArm, 'I', hastelloyCasing }));
	//insulation upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(insulationUpgrade, new Object[] { "NIN", "I I", "NIN", 'N', owcArm, 'I', nimonicCasing }));
	}

	private static void boundaryRecipes() {
	//boundary breaker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.earthBreaker), new Object[] { "BCB", "ATA", "BHB", 'H', boundaryHead, 'T', tiniteArm, 'A', crawlerArm, 'C', boundaryChip, 'B', hydronaliumCasing }));
	//boundary chip
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryChip, new Object[] { "FIF","GCG","FIF", 'C', logicChip, 'I', "gemQuartz", 'G', "nuggetGold", 'F', "ingotHydronalium" }));
	//Boundary Head
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryHead, new Object[] { "AHA", "NTN", "CTC", 'A', hydronaliumCasing, 'H', "blockHastelloy", 'N', "ingotSiena", 'C', "ingotCarborundum", 'T', "ingotTinite" }));
	//Siena bearing
		GameRegistry.addRecipe(new ShapedOreRecipe(sienaBearing, new Object[] { "SSS", "SBS", "SSS", 'S', "ingotSiena", 'B', "blockIron" }));
	//breaker arm
		GameRegistry.addRecipe(new ShapedOreRecipe(tiniteArm, new Object[] { "SBS", "IBI", "IBI", 'S', sienaBearing, 'B', "blockTinite", 'I', hydronaliumCasing }));
	//boundary Casing
		GameRegistry.addRecipe(new ShapedOreRecipe(hydronaliumCasings, new Object[] { "NIN", "IBI", "NIN", 'N', "nuggetIron", 'I', "ingotHydronalium", 'B', "blockHydronalium" }));
	}

}