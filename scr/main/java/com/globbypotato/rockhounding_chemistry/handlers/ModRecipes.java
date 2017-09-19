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
		profilersRecipes();
		disposerRecipes();
		ultraBattery();
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
		pipeRecipes();
	}

	private static void pipeRecipes() {
		//duct
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.pipelineDuct, 4), new Object[] { "IGI", "IGI", "IGI", 'I', ironFoil, 'G', "blockGlass" }));
		//pump
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.pipelinePump), new Object[] { "IGI", "FDF", "IPI", 'F', ironFoil, 'I', ironCasing, 'G', "blockGlass", 'D', pipeline, 'P', piston }));
		//valve
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.pipelineValve), new Object[] { "IDI", "FGF", "IDI", 'F', ironFoil, 'I', ironCasing, 'G', "blockGlass", 'D', pipeline }));
		//pipeline upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(pipelineUpgrade, new Object[] { "XCX", "DPD", "XCX", 'X', hastelloyCasing, 'D', pump, 'C', compressor, 'P', pipeline }));
	}

	private static void profilersRecipes() {
		// casting bench
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.castingBench), new Object[] { "IPI", "I I", "SSS", 'I', "ingotIron", 'S', stoneSlab, 'P', piston }));
		// lab blender
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.labBlender), new Object[] { "PIU", "C  ", "SSS", 'C', cabinet, 'I', "ingotIron", 'S', stoneSlab, 'U', blendUnit, 'P', piston }));
		// blend unit
		GameRegistry.addRecipe(new ShapedOreRecipe(blendUnit, new Object[] { "GCG","CRC","GCG", 'C', ironCasing, 'G', "blockGlass" , 'R', ToolUtils.gear }));
	}

	private static void ultraBattery() {
		//ultrabattery
		GameRegistry.addRecipe(new ShapedOreRecipe(battery(0), new Object[] { 	"N N", "LOI", "BOC", 'N', "nuggetIron", 'L', leadElectrode, 'C', carbonElectrode, 'I', "ingotLead", 'O', fiberglass, 'B', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID)) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(battery(1), new Object[] { 	"BCB", "CcC", "BCB", 'B', battery(0), 'c', ironCasing, 'C', copperCoil }));
		GameRegistry.addRecipe(new ShapedOreRecipe(battery(2), new Object[] { 	"BCB", "CcC", "BCB", 'B', battery(1), 'c', ironCasing, 'C', copperCoil }));
		GameRegistry.addRecipe(new ShapedOreRecipe(battery(3), new Object[] { 	"BCB", "CcC", "BCB", 'B', battery(2), 'c', ironCasing, 'C', copperCoil }));
	}

	private static void disposerRecipes() {
		//clockwork
		GameRegistry.addRecipe(new ShapedOreRecipe(clockwork, new Object[] { "GCG", "cGr", "GCG", 'G', vanaGear, 'C', ironCasing, 'c', comparator, 'r', repeater }));
		//disposer
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.disposer, new Object[] { "CLC", "xIx", "CcC", 'L', advancedChip, 'C', clockwork, 'I', cabinet, 'c', comparator, 'x', ironCasing }));
	}

	private static void ganRecipes() {
		//compessor
 		GameRegistry.addRecipe(new ShapedOreRecipe(compressor, new Object[] { "cPc", "cLc", "cRc", 'R', "dustRedstone", 'P', piston, 'c', ironCasing, 'L', logicChip }));
 		//spiral
 		GameRegistry.addRecipe(new ShapedOreRecipe(spirals, new Object[] { "CCC", "C C", "I I", 'I', "ingotIron", 'C', "ingotCopper" }));
		//vessel
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(0), new Object[] { "IXI", "c c", "ccc", 'I', "ingotIron", 'X', compressor, 'c', ironCasing }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(6), new Object[] { "cOc", "cSc", "ccc", 'O', gan(0), 'c', hastelloyCasing, 'S', spiral }));
		//chiller
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(1), new Object[] { "cLc", "c c", "cBc", 'c', ironCasing, 'B', waterBucket, 'L', logicChip}));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(7), new Object[] { "SOS", "SSS", "ccc", 'c', cupronickelCasing, 'O', gan(1), 'S', spiral }));
		//condenser
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(2), new Object[] { "cCc", "cLc", "cCc", 'c', ironCasing, 'L', logicChip, 'C', compressor }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(8), new Object[] { "cOc", "SSS", "ccc", 'c', cupronickelCasing, 'S', spiral, 'O', gan(2) }));
		//turbine
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(3), new Object[] { "cPc", "cLc", "cPc", 'c', ironCasing, 'P', piston, 'L', logicChip }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(9), new Object[] { "cOc", "cTc", "cRc", 'c', nimonicCasing, 'R', owcRotor, 'O', gan(3), 'T', owcFan }));
		//tank
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(4), new Object[] { "GcG", "G G", "GcG", 'G', "blockGlass", 'c', ironCasing }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(10),new Object[] { "cSc", "cOc", "ccc", 'S', spiral, 'c', hydronaliumCasing, 'O', gan(4) }));
		//tower
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(5), new Object[] { "cGc", "xGx", "cGc", 'c', ironCasing, 'x', copperCoil, 'G', "blockGlass" }));
 		GameRegistry.addRecipe(new ShapedNbtRecipe(gan(11),new Object[] { "cSc", "xOx", "cSc", 'c', nimonicCasing, 'S', spiral, 'O', gan(5), 'x', copperCoil }));
 		//controller
 		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.ganController),new Object[] { "XFX", "FLF", "FCF", 'L', advancedChip, 'F', ironCasing, 'C', comparator, 'X', logicChip }));
		//tower cap
 		GameRegistry.addRecipe(new ShapedOreRecipe(gan(15),new Object[] { "cCc", "xGx", "cGc", 'c', ironCasing, 'C', compressor, 'G', "blockGlass", 'x', copperCoil }));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralSizer), new Object[] { "IHI", "IcI", "ICI", 'C', cabinet, 'I', ironCasing, 'c', logicChip, 'H', hopper }));
	//leaching vat
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mineralAnalyzer), new Object[] { "IcI", "GGG", "ICI", 'C', cabinet, 'I', ironCasing, 'c', logicChip, 'G', "blockGlass" }));
	//chemical extractor
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chemicalExtractor), new Object[] { "GCI", "GcI", "GII", 'C', cabinet, 'I', ironCasing, 'c', logicChip, 'G', "blockGlass" }));
	//metal alloyer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.metalAlloyer), new Object[] { "ICI", "IHI", "IcI", 'C', cabinet, 'I', ironCasing, 'H', hopper, 'c', logicChip}));
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
	//advanced chip
		GameRegistry.addRecipe(new ShapelessOreRecipe(advancedChip, new Object[] { logicChip, "gemQuartz", "nuggetGold", "dustRedstone" }));
	//flask
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.flask, new Object[] { " G ","G G","GGG", 'G', "blockGlass"}));
	//cylinder
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.cylinder, new Object[] { " G "," G ","GGG", 'G', "blockGlass" }));
	//test tube
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.testTube, new Object[] { "  G"," G ","N  ", 'N', "nuggetIron", 'G', "blockGlass" }));
	//ingot pattern
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.pattern, new Object[] { "T","P", 'T', Blocks.IRON_TRAPDOOR, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE}));
	//agitator
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolUtils.agitator, new Object[] { " I ","NIN","NIN", 'I', "ingotIron", 'N', "nuggetIron" }));
	}

	private static void inductionRecipes() {
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
		GameRegistry.addRecipe(new ShapelessOreRecipe(aluminumIngots, new Object[] { "blockAluminum" }));
   	//aluminum nugget
		GameRegistry.addRecipe(new ShapelessOreRecipe(aluminumNuggets, new Object[] { "ingotAluminum" }));
   	//aluminum block
		GameRegistry.addRecipe(new ShapedOreRecipe(aluminumBlock, new Object[] { "NNN","NNN","NNN", 'N', "ingotAluminum" }));
	//platinum ingot
		GameRegistry.addSmelting(chemicals(1,10), platinumIngot, 1.0F);
	}

	private static void dekatronRecipes() {
	//Dekatron
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dekatron), new Object[] { "GGG", "KRK", "ICI", 'R', "dustRedstone", 'I', "ingotIron", 'K', cathodeSet, 'C', comparator, 'G', "blockGlass" }));
	//cathode
		GameRegistry.addRecipe(new ShapedOreRecipe(cathode, new Object[] { "PPP", "WWW", "I I", 'I', "nuggetIron", 'W', cunifeCoil, 'P', Items.PAPER}));
	//cathode Set
		GameRegistry.addRecipe(new ShapedOreRecipe(cathodeSet, new Object[] {"CWC", "WCW", "CWC", 'C', cathode, 'W', copperCoil }));
	}

	private static void owcRecipes() {
	//bulkhead
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,0), new Object[] { "III", " S ", "III", 'I', cupronickelFoil, 'S', "stone" }));
	//concrete
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(4,1), new Object[] { "GSG", "SCS", "GWG", 'G', "gravel", 'S', "sand", 'C', Items.CLAY_BALL, 'W', waterBucket }));
	//duct
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,2), new Object[] { "FGF", "CGC", "FGF", 'C', nimonicCasing, 'F', nimonicFoil, 'G', "blockGlass" }));
	//turbine
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,3), new Object[] { "FAF", "CTC", "FAF", 'C', nimonicCasing, 'A', nimonicArm, 'T', owcFan, 'F', nimonicFoil }));
	//valve
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,4), new Object[] { "GFG", "FDF", "G G", 'G', "ingotGold", 'D', owc(1,2), 'F', nimonicFoil }));
	//generator
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,5), new Object[] { "FSF", "CRC", "FAF", 'S', owcStator, 'C', nimonicCasing, 'R', owcRotor, 'A', nimonicArm, 'F', nimonicFoil }));
	//storage
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,6), new Object[] { "ECE", "MEM", "ECE", 'C', copperCoil, 'E', energyCell, 'M', nimonicCasing }));
	//inverter
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,7), new Object[] { "MCI", "cCI", "MCI", 'C', copperCoil, 'I', "ingotIron", 'c', comparator, 'M', nimonicCasing }));
	//deflector
		GameRegistry.addRecipe(new ShapedOreRecipe(owc(1,8), new Object[] { "FIF", " I ", "FIF", 'F', cupronickelFoil, 'I', cupronickelArm }));
	//owc assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcAssembler), new Object[] { " I ", "ICI", " I ", 'C', "workbench", 'I', nimonicFoil }));
	//owc controller
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.owcController), new Object[] { "XFX", "FAF", "FCF", 'A', advancedChip, 'F', nimonicCasing, 'C', comparator, 'X', logicChip }));
	//owc stator
		GameRegistry.addRecipe(new ShapedOreRecipe(owcStator, new Object[] { "III", "I I", "III", 'I', copperCoil }));
	//owc rotor
		GameRegistry.addRecipe(new ShapedOreRecipe(owcRotor, new Object[] { "CCA", 'C', copperCoil, 'A', nimonicArm }));
	//owc fan
		GameRegistry.addRecipe(new ShapedOreRecipe(owcFan, new Object[] { " I ", "IAI", " I ", 'I', nimonicFoil, 'A', nimonicArm }));
	//energy Cell
		GameRegistry.addRecipe(new ShapedOreRecipe(energyCell, new Object[] { "NMN", "LML", "LXL", 'N', "nuggetIron", 'L', "ingotLead", 'X', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID)), 'M', fiberglass }));
	}

	private static void laserRecipes() {
	//Laser TX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneTx), new Object[] { "RE ", " I ", "III", 'I', aluminumCasing, 'E', laserResonator, 'R', repeater }));
	//Laser RX
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 0), new Object[] { "ERE", "PCP", "EIE", 'I', aluminumCasing, 'E', laserResonator, 'C', comparator, 'P', "paneGlass", 'R', "blockRedstone" }));
	//Laser Node
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserSplitter), new Object[] { "EEE", "CER", "III", 'I', aluminumCasing, 'E', laserResonator, 'C', comparator, 'R', repeater }));
	//Laser Pin Tx
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 4), new Object[] { "EEE", "ERE", "III", 'I', aluminumCasing, 'E', laserResonator, 'R', repeater}));
	//Laser Pin Rx
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserRedstoneRx, 1, 5), new Object[] { "EEE", "ECE", "III", 'I', aluminumCasing, 'E', laserResonator, 'C', comparator}));
	//yag emitter
		GameRegistry.addRecipe(new ShapedOreRecipe(laserResonator, new Object[] { "TTT", "RRP", "TTT", 'T', Blocks.REDSTONE_TORCH, 'R', yagRod, 'P', "paneGlass"}));
	//ElectroLaser
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.electroLaser), new Object[] { "LGE", "cCc", "III", 'G', "blockGlass", 'I', aluminumCasing, 'E', laserResonator, 'C', compressor, 'c', copperCoil, 'L', ModBlocks.laserRedstoneTx }));
	//Laser stabilizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserStabilizer), new Object[] { "E E", " S ", "III", 'I', aluminumCasing, 'E', laserResonator, 'S', owcStator }));
	//Laser amplifier
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.laserAmplifier), new Object[] { "EGE", "RSR", "III", 'I', aluminumCasing, 'E', laserResonator, 'S', owcStator, 'R', repeater }));
	}

	private static void crawlerRecipes() {
	//crawler assembler
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crawlerAssembler), new Object[] {" I ", "ICI", " I ", 'C', "workbench", 'I', widiaFoil }));
	//memory chip
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerMemory, new Object[] { "FIF","GCG","FIF", 'C', logicChip, 'I', "ingotIron", 'G', "nuggetGold", 'F', widiaFoil }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(crawlerMemory, new Object[] { crawlerMemory }));
	//setup chip
		GameRegistry.addRecipe(new ShapedOreRecipe(setupChip, new Object[] { "III","NCN","III", 'C', advancedChip, 'I', widiaFoil, 'N', "nuggetGold" }));
	//crawler casing
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerCasing, new Object[] { "LFL","FCF","FFF", 'C', crawlerMemory, 'F', widiaCasing, 'L', logicChip }));
	//crawler head
		GameRegistry.addRecipe(new ShapedOreRecipe(crawlerHead, new Object[] { "CPC", "CCC", 'P', Blocks.STICKY_PISTON, 'C', widiaFoil }));
	}

	private static void chemicalRecipes() {
	//sodium polyacrilate
		GameRegistry.addRecipe(new ShapedOreRecipe(chemicals(8,0), new Object[] { "SSS","SBS", "SSS", 'S', "dustSodium", 'B', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.ACRYLIC_ACID)) }));//TODO in tank
	//silicone gun
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.siliconeCartridge), new Object[] { " B ", "IIS"," NI", 'S', "stickWood", 'I', "ingotIron", 'N', "nuggetIron", 'B', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.SILICONE)) }));
	//slime ball
		if(ModConfig.forceSilicone){
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.SLIME_BALL), new Object[] { new ItemStack(ModItems.siliconeCartridge, 1, OreDictionary.WILDCARD_VALUE) }));
		}
	//potassium carbonate
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(1,12), new Object[] { waterBucket, ashCompost, ashCompost, ashCompost })); //TODO in tank
	//potassium nitrate
		GameRegistry.addRecipe(new ShapelessOreRecipe(chemicals(3,13), new Object[] { CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID)), potCarbonate, potCarbonate, potCarbonate })); //TODO in tank
	//throwable screen smoke
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.splashSmoke, 4), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.TITANIUM_TETRACHLORIDE)), bottle, bottle, bottle, bottle }));
	//fire compounds
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 0), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), borax, borax, borax, borax, borax, borax, borax, borax })); //TODO in tank
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 2), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper", "dustCopper" })); //TODO in tank
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 5), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium", "dustCalcium" })); //TODO in tank
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 6), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium", "dustPotassium" }));//TODO in tank
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 7), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium", "dustLithium" }));//TODO in tank
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 8), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID)), "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead", "dustLead" }));//TODO in tank
		GameRegistry.addRecipe(new ShapelessOreRecipe(fires(8, 9), new Object[] {CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)), "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese", "dustManganese" }));//TODO in tank
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
	//chamber upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(chamberUpgrade, new Object[] { "NIN", "I I", "NIN", 'N', hastelloyArm, 'I', hastelloyCasing }));
	//insulation upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(insulationUpgrade, new Object[] { "NIN", "I I", "NIN", 'N', nimonicArm, 'I', nimonicCasing }));
	}

	private static void boundaryRecipes() {
	//boundary breaker
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.earthBreaker), new Object[] { "BCB", "ATA", "BHB", 'H', boundaryHead, 'T', tiniteArm, 'A', hastelloyArm, 'C', boundaryChip, 'B', hydronaliumCasing }));
	//boundary chip
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryChip, new Object[] { "FIF","GCG","FIF", 'C', logicChip, 'I', "gemQuartz", 'G', "nuggetGold", 'F', "ingotHydronalium" }));
	//Boundary Head
		GameRegistry.addRecipe(new ShapedOreRecipe(boundaryHead, new Object[] { "AHA", "NTN", "CTC", 'A', hydronaliumCasing, 'H', "blockHastelloy", 'N', "ingotSiena", 'C', "ingotCarborundum", 'T', "ingotTinite" }));
	//Siena bearing
		GameRegistry.addRecipe(new ShapedOreRecipe(sienaBearing, new Object[] { "SSS", "SBS", "SSS", 'S', "ingotSiena", 'B', "ingotIron" }));
	//breaker arm
		GameRegistry.addRecipe(new ShapedOreRecipe(tiniteArm, new Object[] { "SBS", "IBI", "IBI", 'S', sienaBearing, 'B', "blockTinite", 'I', hydronaliumCasing }));
	}

}