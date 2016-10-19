package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.SaltMaker;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltMaker;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String CATEGORY_MINERAL = "Mineral";
	public static final String CATEGORY_SALT = "Salt";
	public static final String CATEGORY_TOONS = "Tools";


	//config
	public static int maxMineral;
	public static int gearUses;
	public static int tubeUses;
	public static int patternUses;
	public static boolean enableRainRefill;
	public static boolean forceSmelting;

	
	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
	//ORES
		config.addCustomCategoryComment("Mineral", "These settings handle the Mineral spawning parameters.");
		ChemOresGenerator.mineralFrequency = config.get(			CATEGORY_MINERAL, "MineralFrequency", 		12, 	"Frequency of the Uninspected Mineral spawning").getInt();
		ChemOresGenerator.mineralMaxVein = config.get(				CATEGORY_MINERAL, "MineralMaxVein", 		20, 	"Highest mineral vein size").getInt();
		ChemOresGenerator.mineralMinVein = config.get(				CATEGORY_MINERAL, "MineralMinVein", 		10,		"Lowest mineral vein size").getInt();
		ChemOresGenerator.mineralMaxLevel = config.get(				CATEGORY_MINERAL, "MineralMaxLevel", 		200, 	"Highest mineral level").getInt();
		ChemOresGenerator.mineralMinLevel = config.get(				CATEGORY_MINERAL, "MineralMinLevel", 		27,		"Lowest mineral level").getInt();
		forceSmelting = config.get(						CATEGORY_MINERAL, "ForceSmelting", 			true,	"Force the smelting of dusts into other mod ingots when available").getBoolean();
	//SALT
		config.addCustomCategoryComment("Salt", "These settings handle the making of Salt.");
		TileEntitySaltMaker.evaporationMultiplier = config.get(		CATEGORY_SALT, "EvaporationMultiplier", 	4, 		"Multiply ticks to advance in the evaporation process. Base ticks = 2000").getInt();
		enableRainRefill = config.get(					CATEGORY_SALT, "RefillTankFromRain", 		true,	"Wether the rain can automatically refill the tank when empty").getBoolean();
		SaltMaker.saltAmount = config.get(							CATEGORY_SALT, "SaltQuantity", 				4, 		"Max quantity of salt items a single tank can produce").getInt();

	//TOOLS
		config.addCustomCategoryComment("Tools", "These settings handle the settings of machines and tools.");
		TileEntityLabOven.cookingSpeed = config.get(				CATEGORY_TOONS, "SpeedLabOven", 			200,	"Ticks required to produce acids in the Lab Oven").getInt();
		TileEntityMineralSizer.crushingSpeed = config.get(			CATEGORY_TOONS, "SpeedMineralSizer", 		300,	"Ticks required to crush minerals in the Mineral Sizer").getInt();
		TileEntityMineralAnalyzer.analyzingSpeed = config.get(		CATEGORY_TOONS, "SpeedMineralAnalyzer", 	400,	"Ticks required to analyze minerals in the Mineral Analyzer").getInt();
		TileEntityChemicalExtractor.extractingSpeed = config.get(	CATEGORY_TOONS, "SpeedChemicalExtractor", 	600,	"Ticks required to extract elements in the Chemical Extractor").getInt();
		TileEntityCrawlerAssembler.assemblingSpeed = config.get(	CATEGORY_TOONS, "SpeedCrawlerTable", 		300,	"Ticks required to assembly a Mine Crawler in its Assembly Table").getInt();
		TileEntityMetalAlloyer.alloyingSpeed = config.get(			CATEGORY_TOONS, "SpeedMetalAlloyer", 		200,	"Ticks required to cast an alloy in the Metal Alloyer").getInt();
		TileEntityChemicalExtractor.extractingFactor = config.get(	CATEGORY_TOONS, "ExtractingFactor", 		100,	"Percentage of element required to produce one regular dust").getInt();
		gearUses = config.get(							CATEGORY_TOONS, "UsesGear", 				150,	"Max uses for the Crushing Gear in the Mineral Sizer").getInt();
		tubeUses = config.get(							CATEGORY_TOONS, "UsesTube", 				200,	"Max uses for the Test Tube in the Mineral Analyzer").getInt();
		patternUses = config.get(						CATEGORY_TOONS, "UsesPattern", 				100,	"Max uses for the Ingot Pattern in the Metal Alloyer").getInt();
		maxMineral = config.get(						CATEGORY_TOONS, "MaxMineralShards", 		4,		"Max amount of mineral shards obtainable from the Mineral Analyzer").getInt();

		
		
		config.save();
	}
}
