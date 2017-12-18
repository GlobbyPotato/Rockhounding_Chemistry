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
	public static final String CATEGORY_SUPPORT = "Integration";

	//config
	public static int speedLabOven;
	public static int speedExtractor;
	public static int factorExtractor;
	public static int speedAnalyzer;
	public static int speedSizer;
    public static int speedAlloyer;
    public static int speedSeasoner;
    public static int speedAssembling;
	public static int speedDeposition;
	public static int speedBlender;
    public static int machineTank;
	public static int comminutionFactor;

	public static int maxMineral;
	public static int gearUses;
	public static int tubeUses;
	public static int patternUses;
	public static int agitatorUses;
	public static int catalystUses;

	public static boolean enableRainRefill;
	public static int evaporationBase;
	public static int meltingTime;
	public static int evaporationMultiplier;
    public static int saltAmount;

	public static boolean forceSilicone;
	public static boolean ingredientEqualizer;
	public static boolean poisonFluid;

	public static boolean enableTOP;

	public static int consumedSulf = 30;
	public static int consumedChlo = 20;
	public static int consumedFluo = 10;

	public static int consumedNitr = 10;
	public static int consumedPhos = 30;
	public static int consumedCyan = 20;

	public static int[] dimensions = new int[]{};
	public static int[] saltdimensions = new int[]{};
	public static int[] spacesaltdimensions = new int[]{};

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

	//SUPPORT
		config.addCustomCategoryComment("Integration", "Mod support and integration parameters.");
		enableTOP = config.get(										CATEGORY_SUPPORT, "SupportTheOneProbe",		true,	"Enable the additional info for The One Probe").getBoolean();

	//GENERIC
		config.addCustomCategoryComment("Miscellaneous", "Generic parameters settings.");
		forceSilicone = config.get(									CATEGORY_MISC, "SlimeFromSilicone",			true,	"Obtain slime balls from the silicone cartridge instead to use the cartridge itself").getBoolean();
		ingredientEqualizer = config.get(							CATEGORY_MISC, "IngredientEqualizer",		false,	"Allows to uniform oredicted dusts from different mods into a single type when inserted in the Metal Alloyer").getBoolean();
		poisonFluid = config.get(									CATEGORY_MISC, "PoisoningFluids",			true,	"Allows to acids and solutions to cause damage to entities.").getBoolean();

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
		meltingTime = config.get(									CATEGORY_SALT, "MeltingTime", 				1000, 	"Base ticks after which the progress is lost with rain").getInt();
		enableRainRefill = config.get(								CATEGORY_SALT, "RefillTankFromRain", 		true,	"Wether the rain can automatically refill the tank when empty").getBoolean();
		saltAmount = config.get(									CATEGORY_SALT, "SaltQuantity", 				4, 		"Max quantity of salt items a single tank can produce").getInt();
		saltdimensions = config.get(								CATEGORY_SALT, "DimensionBlacklist", saltdimensions, "Deny in these dimensions IDs the production of salt").getIntList();
		spacesaltdimensions = config.get(							CATEGORY_SALT, "SpaceDimensions", 	  spacesaltdimensions, "Dimensions in which evaporation is overridden by desublimation").getIntList();

	//TOOLS
		config.addCustomCategoryComment("Tools", "These settings handle the settings of machines and tools.");
		speedLabOven = config.get(									CATEGORY_TOOLS, "SpeedLabOven", 			200,	"Ticks required to produce acids in the Lab Oven").getInt();
		speedSizer = config.get(									CATEGORY_TOOLS, "SpeedMineralSizer", 		300,	"Ticks required to crush minerals in the Mineral Sizer").getInt();
		comminutionFactor = config.get(								CATEGORY_TOOLS, "ComminutionFactor", 		30,		"Multiplied for the comminution level and added to the base Sizer speed").getInt();
		speedAnalyzer = config.get(									CATEGORY_TOOLS, "SpeedMineralAnalyzer", 	300,	"Ticks required to analyze minerals in the Leaching Vat").getInt();
		speedExtractor = config.get(								CATEGORY_TOOLS, "SpeedChemicalExtractor", 	400,	"Ticks required to extract elements in the Chemical Extractor").getInt();
		speedAssembling = config.get(								CATEGORY_TOOLS, "SpeedAssemblyTables", 		100,	"Ticks required to assembly Devices in their Assembly Tables").getInt();
		speedAlloyer = config.get(									CATEGORY_TOOLS, "SpeedMetalAlloyer", 		200,	"Ticks required to cast an alloy in the Metal Alloyer").getInt();
		speedSeasoner = config.get(									CATEGORY_TOOLS, "SpeedSaltSeasoner", 		600,	"Ticks required to finalize salt in the Salt Seasoning Rack").getInt();
		speedDeposition = config.get(								CATEGORY_TOOLS, "SpeedDepositionChamber",	800,	"Ticks required to process in the Deposition Chamber").getInt();
		speedBlender = config.get(									CATEGORY_TOOLS, "SpeedLabBlender",			30,		"Ticks required to process in the Lab Blender").getInt();
		factorExtractor = config.get(								CATEGORY_TOOLS, "ExtractingFactor", 		100,	"Percentage of element required to produce one regular dust").getInt();
		gearUses = config.get(										CATEGORY_TOOLS, "UsesGear", 				400,	"Max uses for the Crushing Gear in the Mineral Sizer").getInt();
		tubeUses = config.get(										CATEGORY_TOOLS, "UsesTube", 				300,	"Max uses for the Test Tube in the Chemical Extractor").getInt();
		agitatorUses = config.get(									CATEGORY_TOOLS, "UsesAgitator", 			400,	"Max uses for the Agitator in the Leaching Vat").getInt();
		patternUses = config.get(									CATEGORY_TOOLS, "UsesPattern", 				200,	"Max uses for the Ingot Pattern in the Metal Alloyer").getInt();
		catalystUses = config.get(									CATEGORY_TOOLS, "UsesCatalyst", 			200,	"Max uses for any Catalyst in the Lab Oven").getInt();
		maxMineral = config.get(									CATEGORY_TOOLS, "MaxMineralShards", 		4,		"Max amount of mineral shards obtainable from the Leaching Vat").getInt();
		machineTank = config.get(									CATEGORY_TOOLS, "MachineTankCapacity", 		9000,	"Max capacity for machine fluid tanks. This value is increased by 1000 by default").getInt();
		TileEntityEarthBreaker.dropBedrock = config.get(			CATEGORY_TOOLS, "DropBedrock",				false,	"Allow the Boundary Breaker to drop the mined bedrock").getBoolean();

        if (config.hasChanged()) {
        	config.save();
        }

	}
}