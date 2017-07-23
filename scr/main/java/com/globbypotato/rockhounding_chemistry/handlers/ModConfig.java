package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String CATEGORY_MINERAL = "Mineral";
	public static final String CATEGORY_SALT = "Salt";
	public static final String CATEGORY_TOOLS = "Tools";
	public static final String CATEGORY_MISC = "Miscellaneous";

	//config
	public static int speedLabOven;
	public static int speedExtractor;
	public static int factorExtractor;
	public static int speedAnalyzer;
	public static int speedSizer;
    public static int alloyingSpeed;
    public static int speedSeasoner;
    public static int speedAssembling;
	public static int speedDeposition;
    public static int machineTank;

	public static int maxMineral;
	public static int gearUses;
	public static int tubeUses;
	public static int patternUses;
	public static int agitatorUses;
	public static int catalystUses;

	public static boolean enableRainRefill;
	public static int evaporationBase;
	public static int evaporationMultiplier;
    public static int saltAmount;

	public static boolean forceSilicone;

	public static int consumedSulf = 10;
	public static int consumedChlo = 30;
	public static int consumedFluo = 20;

	public static int consumedNitr = 10;
	public static int consumedPhos = 30;
	public static int consumedCyan = 20;

	public static int[] dimensions = new int[]{};

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

	//GENERIC
		config.addCustomCategoryComment("Miscellaneous", "Generic parameters settings.");
		forceSilicone = config.get(									CATEGORY_MISC, "SlimeFromSilicone",			true,	"Obtain slime balls from the silicone cartridge instead to use the cartridge itself").getBoolean();

	//ORES
		config.addCustomCategoryComment("Mineral", "These settings handle the Mineral spawning parameters.");
		ChemOresGenerator.mineralFrequency = config.get(			CATEGORY_MINERAL, "MineralFrequency", 		12, 	"Frequency of the Uninspected Mineral spawning").getInt();
		ChemOresGenerator.mineralMaxVein = config.get(				CATEGORY_MINERAL, "MineralMaxVein", 		20, 	"Highest mineral vein size").getInt();
		ChemOresGenerator.mineralMinVein = config.get(				CATEGORY_MINERAL, "MineralMinVein", 		10,		"Lowest mineral vein size").getInt();
		ChemOresGenerator.mineralMaxLevel = config.get(				CATEGORY_MINERAL, "MineralMaxLevel", 		200, 	"Highest mineral level").getInt();
		ChemOresGenerator.mineralMinLevel = config.get(				CATEGORY_MINERAL, "MineralMinLevel", 		27,		"Lowest mineral level").getInt();
		dimensions = config.get(									CATEGORY_MINERAL, "dimension whitelist", dimensions, "Allows in these dimensions IDs the generation of the Uninspected Mineral").getIntList();

	//SALT
		config.addCustomCategoryComment("Salt", "These settings handle the making of Salt.");
		evaporationMultiplier = config.get(							CATEGORY_SALT, "EvaporationMultiplier", 	3, 		"Multiply factor for the EvaporationBaseInterval").getInt();
		evaporationBase = config.get(								CATEGORY_SALT, "EvaporationBaseInterval", 	1000, 	"Base ticks required to advance in the evaporation process").getInt();
		enableRainRefill = config.get(								CATEGORY_SALT, "RefillTankFromRain", 		true,	"Wether the rain can automatically refill the tank when empty").getBoolean();
		saltAmount = config.get(									CATEGORY_SALT, "SaltQuantity", 				4, 		"Max quantity of salt items a single tank can produce").getInt();

	//TOOLS
		config.addCustomCategoryComment("Tools", "These settings handle the settings of machines and tools.");
		speedLabOven = config.get(									CATEGORY_TOOLS, "SpeedLabOven", 			200,	"Ticks required to produce acids in the Lab Oven").getInt();
		speedSizer = config.get(									CATEGORY_TOOLS, "SpeedMineralSizer", 		300,	"Ticks required to crush minerals in the Mineral Sizer").getInt();
		speedAnalyzer = config.get(									CATEGORY_TOOLS, "SpeedMineralAnalyzer", 	300,	"Ticks required to analyze minerals in the Leaching Vat").getInt();
		speedExtractor = config.get(								CATEGORY_TOOLS, "SpeedChemicalExtractor", 	400,	"Ticks required to extract elements in the Chemical Extractor").getInt();
		speedAssembling = config.get(								CATEGORY_TOOLS, "SpeedAssemblyTables", 		100,	"Ticks required to assembly Devices in their Assembly Tables").getInt();
		alloyingSpeed = config.get(									CATEGORY_TOOLS, "SpeedMetalAlloyer", 		200,	"Ticks required to cast an alloy in the Metal Alloyer").getInt();
		speedSeasoner = config.get(									CATEGORY_TOOLS, "SpeedSaltSeasoner", 		600,	"Ticks required to finalize salt in the Salt Seasoning Rack").getInt();
		speedDeposition = config.get(								CATEGORY_TOOLS, "SpeedDepositionChamber",	800,	"Ticks required to process in the Deposition Chamber").getInt();
		factorExtractor = config.get(								CATEGORY_TOOLS, "ExtractingFactor", 		100,	"Percentage of element required to produce one regular dust").getInt();
		gearUses = config.get(										CATEGORY_TOOLS, "UsesGear", 				250,	"Max uses for the Crushing Gear in the Mineral Sizer").getInt();
		tubeUses = config.get(										CATEGORY_TOOLS, "UsesTube", 				300,	"Max uses for the Test Tube in the Chemical Extractor").getInt();
		agitatorUses = config.get(									CATEGORY_TOOLS, "UsesAgitator", 			400,	"Max uses for the Agitator in the Leaching Vat").getInt();
		patternUses = config.get(									CATEGORY_TOOLS, "UsesPattern", 				200,	"Max uses for the Ingot Pattern in the Metal Alloyer").getInt();
		catalystUses = config.get(									CATEGORY_TOOLS, "UsesCatalyst", 			200,	"Max uses for any Catalyst in the Lab Oven").getInt();
		maxMineral = config.get(									CATEGORY_TOOLS, "MaxMineralShards", 		4,		"Max amount of mineral shards obtainable from the Mineral Analyzer").getInt();
		machineTank = config.get(									CATEGORY_TOOLS, "MachineTankCapacity", 		9000,	"Max capacity for machine fluid tanks. This value is increased by 1000 by default").getInt();
		TileEntityEarthBreaker.dropBedrock = config.get(			CATEGORY_TOOLS, "DropBedrock",				false,	"Allow the Boundary Breaker to drop the mined bedrock").getBoolean();

        if (config.hasChanged()) {
        	config.save();
        }

	}
}