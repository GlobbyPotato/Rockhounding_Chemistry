package com.globbypotato.rockhounding_chemistry.handlers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String ABSOLUTE = "absolute";
	public static final String ABSOLUTE_INTEGRATIONS = "absolute_integrations";
	public static final String ORES = "ores";
	public static final String MINERAL_SIZER = "mineral_sizer";
	public static final String LEACHING_VAT = "leaching_vat";
	public static final String EVAPORATION_TANK = "evaporation_tank";
	public static final String SEASONING_RACK = "seasoning_rack";
	public static final String CHEMICAL_EXTRACTOR = "chemical_extractor";
	public static final String METAL_ALLOYER = "metal_alloyer";
	public static final String GASIFICATION_PLANT = "gasification_plant";
	public static final String GAS_PURIFIER = "gas_purifier";
	public static final String TOXIC_WASTE = "toxic_waste";
	public static final String MUTATION = "toxic_mutation";
	public static final String WATER_PUMP = "water_pump";
	public static final String CABINETS = "cabinets";
	public static final String POWER_STATION = "power_station";
	public static final String ORBITER = "orbiter";

	//ore
	public static int mineralFrequency; 
	public static int mineralMinVein; 
	public static int mineralMaxVein;
	public static int mineralMinLevel; 
	public static int mineralMaxLevel;
	public static int[] dimensions;

	//uses
	public static int gearUses;
	public static int agitatorUses;
	public static int tubeUses;
	public static int cylinderUses;
	public static int patternUses;
	public static int fe_catalystUses;
	public static int va_catalystUses;
	public static int gr_catalystUses;
	public static int pt_catalystUses;
	public static int wg_catalystUses;
	public static int os_catalystUses;
	public static int ze_catalystUses;
	public static int zn_catalystUses;
	public static int co_catalystUses;
	public static int ni_catalystUses;
	public static int nl_catalystUses;
	public static int au_catalystUses;
	public static int mo_catalystUses;
	public static int in_catalystUses;

	//factors
	public static int maxSizeable;
	public static int maxLeachable;
	public static int extractorFactor;
	public static int burner_main_slag;
	public static int burner_secondary_slag;
	public static int purifier_main_slag;
	public static int purifier_secondary_slag;
	public static int extractorCap;

	//speeds
	public static int speedSizer = 100;
	public static int speedOven = 200;
	public static int speedBlender = 60;
	public static int speedPond = 20;
	public static int speedSeasoning;
	public static int speedLeaching = 200;
	public static int speedExtractor = 400;
	public static int speedAlloyer = 400;
	public static int speedDeposition = 500;
	public static int speedPulling = 300;
	public static int speedCstr = 400;
	public static int speedBedReactor = 500;

	//consumes
	public static int consumedHydr = 30;
	public static int consumedChlo = 20;
	public static int consumedFluo = 10;
	public static int consumedNitr = 10;
	public static int consumedCyan = 20;

	//generic
	public static boolean consumeWater;
	public static boolean fluidDamage;
	public static boolean speedMultiplier;
	public static int basePower = 1;
	public static int catalystMultiplier = 1;
	public static int consumeWaterChance;
	public static boolean dictSmelt;
	public static int cabinetTiming = 10;
	public static int gasBurntime;
	public static int rfToFuelFactor;
	public static int gasEnergizer;
	public static int recycleChance;
	public static int wasteConsumed;
	public static int infusingFactor;

	//toxic waste
	public static boolean xpDrop;
	public static boolean enableHazard;
	public static boolean leakingType;
	public static int leakingRadius;
	public static int hazardChance;
	public static int pressureTolerance;
	public static int exhaustRate;
	public static int toxicCloudSize;
	public static int slimeChance;
	public static boolean enableMutation;
	public static int tankSubstance = 4000;
	public static double kinematicPar = 1.5;

	//integrations
	public static boolean hasRhRocks;

	//salt
	public static int speedEvaporation;
	public static int meltingTime;
	public static int[] salt_dim_blacklist = new int[]{};
	public static int[] salt_dim_space = new int[]{};

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		config.addCustomCategoryComment(ABSOLUTE, "Generic configurations");
		fluidDamage = config.get(									ABSOLUTE, "fluid damage", 							true,					"Chemical products cause damage").getBoolean();
		catalystMultiplier = config.get(							ABSOLUTE, "catalyst multiplier", 					1, 						"Multiplies all the catalysts duration").getInt();
		dictSmelt = config.get(										ABSOLUTE, "oredict smelting", 						true,					"Smelt metals by using oredicted ingredients").getBoolean();

		config.addCustomCategoryComment(ABSOLUTE_INTEGRATIONS, "Mod Integrations");
		hasRhRocks = config.get(									ABSOLUTE_INTEGRATIONS, "Integrate Rockhounding:Rocks", 						false,					"Includes Rockhounding Rocks into the mineral processing chain").getBoolean();

		config.addCustomCategoryComment(MUTATION, "Generic configurations");
		enableMutation = config.get(								MUTATION, "enable toxic mutation", 					true,					"Enable the toxic mutation procedure").getBoolean();

		config.addCustomCategoryComment(TOXIC_WASTE, "Hazard and pollution configuration");
		xpDrop = config.get(										TOXIC_WASTE, "xp from toxic Waste", 				true,					"Toxic Waste drops xp when killing entities").getBoolean();
		enableHazard = config.get(									TOXIC_WASTE, "enable hazard", 						false,					"Enable the chance to have pollution with blocks handling Toxic Waste").getBoolean();
		leakingType = config.get(									TOXIC_WASTE, "safer pollution", 					true,					"Enable a safer pollution leaking only in empty spots. By setting True replaceable blocks can be eroded").getBoolean();
		leakingRadius = config.get(									TOXIC_WASTE, "pollution leaking radius", 			4, 						"Distance in each direction in which pollution may appear").getInt();
		hazardChance = config.get(									TOXIC_WASTE, "hazard chance", 						1000, 					"1/N chance the block containing Toxic Waste causes pollution").getInt();
		pressureTolerance = config.get(								TOXIC_WASTE, "pressure tolerance", 					10, 					"tolerance level of a vessel before collapsing").getInt();
		exhaustRate = config.get(									TOXIC_WASTE, "exhaustion amount", 					20, 					"percentage of gas purged when tolerance is reached. Max 50%").getInt();
		toxicCloudSize = config.get(								TOXIC_WASTE, "toxic cloud size", 					128, 					"size of the toxic cloud").getInt();
		slimeChance = config.get(									TOXIC_WASTE, "toxic slime spawn", 					150, 					"1/N chance for the Toxic Sludge to spawn a Toxic Slime").getInt();

		config.addCustomCategoryComment(ORES, "Configuration of the ores generation");
		mineralFrequency = config.get(								ORES, "ore frequency", 								15, 					"Frequency of the Uninspected Mineral spawning").getInt();
		mineralMaxVein = config.get(								ORES, "max vein", 									20, 					"Highest mineral vein size").getInt();
		mineralMinVein = config.get(								ORES, "min vein", 									10,						"Lowest mineral vein size").getInt();
		mineralMaxLevel = config.get(								ORES, "max level", 									240,	 				"Highest mineral level").getInt();
		mineralMinLevel = config.get(								ORES, "min level", 									20,						"Lowest mineral level").getInt();
		dimensions = config.get(									ORES, "dimension whitelist", 						new int[]{0}, 			"Allows in these dimensions IDs the generation of the Uninspected Mineral").getIntList();

		config.addCustomCategoryComment(MINERAL_SIZER, "Configuration of the Mineral Sizer");
		gearUses = config.get(										MINERAL_SIZER, "crushing gear uses", 				500,					"Max uses for the crushing gear").getInt();
		maxSizeable = config.get(									MINERAL_SIZER, "max sizeable output", 				4,						"Max amount of output obtainable from the Mineral Sizer").getInt();

		config.addCustomCategoryComment(LEACHING_VAT, "Configuration of the Leaching Vat");
		agitatorUses = config.get(									LEACHING_VAT, "slurry agitator uses", 				300,					"Max uses for the slurry agitator").getInt();
		maxLeachable = config.get(									LEACHING_VAT, "max leachable output", 				4,						"Max amount of output obtainable from the Leaching Vat").getInt();

		config.addCustomCategoryComment(EVAPORATION_TANK, "Configuration of the Evaporation Tank");
		meltingTime = config.get(									EVAPORATION_TANK, "melting interval", 				500,					"Ticks before the process is lost under rain").getInt();
		speedEvaporation = config.get(								EVAPORATION_TANK, "evaporation speed", 				1000,					"Ticks required to advance stages in the Evaporation Tank").getInt();
		salt_dim_blacklist = config.get(							EVAPORATION_TANK, "dimension blacklist", 			salt_dim_blacklist, 	"Deny in these dimensions IDs the production of salt").getIntList();
		salt_dim_space = config.get(								EVAPORATION_TANK, "space dimensions", 	  			salt_dim_space, 		"Dimensions in which evaporation is overridden by desublimation").getIntList();

		config.addCustomCategoryComment(SEASONING_RACK, "Configuration of the Seasoning Rack");
		speedSeasoning = config.get(								SEASONING_RACK, "seasoning rack speed", 			1000,					"Ticks required to advance improve an object in the Seasoning Rack").getInt();

		config.addCustomCategoryComment(CHEMICAL_EXTRACTOR, "Configuration of the Chemical Extractor");
		tubeUses = config.get(										CHEMICAL_EXTRACTOR, "test tube uses", 				300,					"Max uses for the test tube").getInt();
		cylinderUses = config.get(									CHEMICAL_EXTRACTOR, "graduated cylinder uses", 		200,					"Max uses for the graduated cylinder").getInt();

		config.addCustomCategoryComment(METAL_ALLOYER, "Configuration of the Metal Alloyer");
		patternUses = config.get(									METAL_ALLOYER, "ingot pattern uses", 				500,					"Max uses for the ingot pattern").getInt();

		config.addCustomCategoryComment(POWER_STATION, "Configuration of the Power Station");
		gasBurntime = config.get(									POWER_STATION, "gas burntime factor", 				2000,					"Burntime value for 0.010cu of Syngas").getInt();
		gasEnergizer = config.get(									POWER_STATION, "gas turbine factor", 				2000,					"RF value for 0.010cu of Syngas").getInt();
		basePower = config.get(										POWER_STATION, "power multiplier", 					1, 						"Multiplies the base RF consumed by all the machines").getInt();
		speedMultiplier = config.get(								POWER_STATION, "speed multiplier", 					true,					"Multiplies the consumed power to balance the speed upgrades").getBoolean();
		rfToFuelFactor = config.get(								POWER_STATION, "rf to fuel factor", 				1,				  		"How many fuel ticks 1RF can produce via induction").getInt();
		
		config.addCustomCategoryComment(CABINETS, "Configuration of the Cabinets");
		extractorFactor = config.get(								CABINETS, "extraction factor", 						100,					"How much element is required for 1 regular dust").getInt();
		extractorCap = config.get(									CABINETS, "cabinets cap", 							6400,					"Max quantity of element that can be inserted into the cabinets from the loader").getInt();

		config.addCustomCategoryComment(ORBITER, "Configuration of the Orbiter");
		recycleChance = config.get(									ORBITER, "waste recycling chance", 					3,						"1/N chance to infuse an Exp Orb by recycling Toxic Waste for double XP").getInt();
		wasteConsumed = config.get(									ORBITER, "waste consumed by recycling",				10,						"Millibuckets of Toxic Waste infused in the Exp Orb").getInt();
		infusingFactor = config.get(								ORBITER, "XP infusing factor",						20,						"Millibuckets of any fluidXP from 1 XP").getInt();

		config.addCustomCategoryComment(GASIFICATION_PLANT, "Configuration of the Gasification Plant");
		burner_main_slag = config.get(								GASIFICATION_PLANT, "main slag chance", 			25,						"n% chance to recover the main slag when available (10ppc)").getInt();
		burner_secondary_slag = config.get(							GASIFICATION_PLANT, "secondary slag chance", 		10,						"n% chance to recover the secondary slag when available (5ppc)").getInt();

		config.addCustomCategoryComment(GAS_PURIFIER, "Configuration of the Gas Purifier");
		purifier_main_slag = config.get(							GAS_PURIFIER, "main slag chance", 					25,						"n% chance to recover the main slag when available (10ppc)").getInt();
		purifier_secondary_slag = config.get(						GAS_PURIFIER, "secondary slag chance", 				10,						"n% chance to recover the secondary slag when available (5ppc)").getInt();

		config.addCustomCategoryComment(WATER_PUMP, "Water Pump configuration");
		consumeWater = config.get(									WATER_PUMP, "consume water tile", 					true,					"Chance to consume the source water tile").getBoolean();
		consumeWaterChance = config.get(							WATER_PUMP, "consumption chance", 					100,					"1/n chance to consume the source water tile").getInt();

		if (config.hasChanged()) {
        	config.save();
        }

		fe_catalystUses = 1000 * catalystMultiplier;
		va_catalystUses = 400 * catalystMultiplier;
		gr_catalystUses = 2000 * catalystMultiplier;
		pt_catalystUses = 2000 * catalystMultiplier;
		wg_catalystUses = 3000 * catalystMultiplier;
		os_catalystUses = 2000 * catalystMultiplier;
		ze_catalystUses = 300 * catalystMultiplier;
		zn_catalystUses = 400 * catalystMultiplier;
		co_catalystUses = 1600 * catalystMultiplier;
		nl_catalystUses = 1200 * catalystMultiplier;
		au_catalystUses = 1600 * catalystMultiplier;
		ni_catalystUses = 1400 * catalystMultiplier;
		mo_catalystUses = 1200 * catalystMultiplier;
		in_catalystUses = 1500 * catalystMultiplier;

		if(exhaustRate > 50) {exhaustRate = 50;}

	}

}