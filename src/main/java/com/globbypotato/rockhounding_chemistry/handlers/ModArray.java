package com.globbypotato.rockhounding_chemistry.handlers;

public class ModArray {
//------- NBT Tag Names -------
	//chemical tank
	public static String chemTankName = "Fluid";
	public static String chemTankQuantity = "Quantity";
	public static String toolUses = "Uses";

	//mineCrawler
	public static String tierName = "Tier";
	public static String modeName = "Mode";
	public static String stepName = "Step";
	public static String upgradeName = "Upgrade";
	public static String fillerName = "Filler";
	public static String absorbName = "Absorb";
	public static String tunnelName = "Tunneler";
	public static String lighterName = "Lighter";
	public static String railmakerName = "RailMaker";
	public static String cobbleName = "Cobblestone";
	public static String glassName = "Glass";
	public static String torchName = "Torch";
	public static String railName = "Rails";


//------- OREDICT -------
    public static String[] 	chemicalDustsOredict = new String[] {  "dustCerium", "dustDysprosium", "dustErbium", "dustEuropium", "dustGadolinium", "dustHolmium", "dustLanthanum", "dustLutetium", "dustNeodymium", "dustPraseodymium", 
																   "dustSamarium", "dustScandium", "dustTerbium", "dustThulium", "dustYtterbium", "dustYttrium", "dustIron", "dustCopper", "dustTin", "dustLead", 
																   "dustZinc", "dustChromium", "dustBoron", "dustSilver", "dustAluminum", "dustManganese", "dustNickel", "dustCobalt", "dustMagnesium", "dustTitanium", 
																   "dustSodium", "dustThorium", "dustCalcium", "dustPhosphorus", "dustLithium", "dustPotassium", "dustBeryllium", "dustSulfur", "dustBismuth", "dustNiobium", 
																   "dustTantalum", "dustArsenic", "dustSilicon", "dustUranium", "dustPlatinum", "dustGold", "dustTungsten", "dustOsmium", "dustIridium", "dustCadmium"};
    public static String[] 	chemicalIngotOredict = new String[] {  "ingotCerium", "ingotDysprosium", "ingotErbium", "ingotEuropium", "ingotGadolinium", "ingotHolmium", "ingotLanthanum", "ingotLutetium", "ingotNeodymium", "ingotPraseodymium", 
																   "ingotSamarium", "ingotScandium", "ingotTerbium", "ingotThulium", "ingotYtterbium", "ingotYttrium", "ingotIron", "ingotCopper", "ingotTin", "ingotLead", 
																   "ingotZinc", "ingotChromium", "ingotBoron", "ingotSilver", "ingotAluminum", "ingotManganese", "ingotNickel", "ingotCobalt", "ingotMagnesium", "ingotTitanium", 
																   "ingotSodium", "ingotThorium", "ingotCalcium", "ingotPhosphorus", "ingotLithium", "ingotPotassium", "ingotBeryllium", "ingotSulfur", "ingotBismuth", "ingotNiobium", 
																   "ingotTantalum", "ingotArsenic", "ingotSilicon", "ingotUranium", "ingotPlatinum", "ingotGold", "ingotTungsten", "ingotOsmium", "ingotIridium", "ingotCadmium"};

//------- ORES -------
    public static String[] 	mineralOresArray = new String[]		{"uninspected", "arsenate", "borate", "carbonate", "halide", "native", "oxide", "phosphate", "silicate", "sulfate", "sulfide"};
    public static String[] 	arsenateShardsArray = new String[] 	{"agardite", "fornacite"};
    public static String[] 	borateShardsArray = new String[] 	{"borax", "ericaite", "hulsite", "londonite", "tusionite", "rhodizite"};
    public static String[] 	carbonateShardsArray = new String[] {"ankerite", "gaspeite", "rosasite", "parisite"};
    public static String[] 	halideShardsArray = new String[] 	{"boleite", "carnallite", "rinneite", "griceite", "fluorite"};
    public static String[] 	nativeShardsArray = new String[] 	{"cohenite", "copper", "cupalite", "haxonite", "perryite", "silver", "tulameenite", "niggliite", "maldonite", "auricuprite", "osmium", "iridium"};
    public static String[] 	oxideShardsArray = new String[] 	{"chromite", "cochromite", "columbite",  "euxenite", "mcconnellite", "samarskite", "cassiterite", "boehmite", "ghanite", "hibonite", "senaite", "thorutite", "ixiolite", "tapiolite", "behoite", "bromellite", "tungstite", "wolframite", "ferberite", "monteponite"};
    public static String[] 	phosphateShardsArray = new String[] {"faustite", "lazulite", "monazite", "schoonerite", "triphylite", "wavellite", "xenotime", "zairite", "pretulite", "tavorite", "keyite", "birchite"};
    public static String[] 	silicateShardsArray = new String[] 	{"axinite", "biotite", "gadolinite", "iranite", "jervisite", "magbasite", "moskvinite", "eucryptite", "steacyite", "manandonite", "vistepite", "khristovite"};
    public static String[] 	sulfateShardsArray = new String[] 	{"alunite", "fedotovite", "jarosite", "guarinoite", "bentorite", "aplowite", "bieberite", "scheelite", "stolzite"};
    public static String[] 	sulfideShardsArray = new String[] 	{"abramovite", "aikinite", "balkanite", "galena", "kesterite", "pentlandite", "pyrite", "stannite", "valleriite", "sphalerite", "petrukite", "mawsonite", "tungstenite", "erlichmanite", "malanite", "greenockite", "cernyite"};
    public static String[] 	chemicalDustsArray = new String[] 	{"cerium", "dysprosium", "erbium", "europium", "gadolinium", "holmium", "lanthanum", "lutetium", "neodymium", "praseodymium", 
			 												   	 "samarium", "scandium", "terbium", "thulium", "ytterbium", "yttrium", "iron", "copper", "tin", "lead",
			 												   	 "zinc", "chromium", "boron", "silver", "aluminum", "manganese", "nickel", "cobalt", "magnesium", "titanium",
			 												   	 "sodium", "thorium", "calcium", "phosphorus",  "lithium", "potassium", "beryllium", "sulfur", "bismuth", "niobium", 
			 												   	 "tantalum", "arsenic", "silicon", "uranium", "platinum", "gold", "tungsten", "osmium", "iridium", "cadmium"};

//------- ITEMS -------
    public static String[] 	chemicalItemsArray = new String[] 	{"tank", "salt", "sulfurCompost", "saltCompost", "fluoriteCompost", "carbonCompost", "crackedCoal"};
    public static String[] 	miscItemsArray = new String[] 		{"logicChip", "cylinder", "gear", "cabinet", "ironNugget", "testTube", "bowBarrel", "bowWheel", "fluidCan", "memoryChip", 
    															 "coringChip", "setupChip", "crawlerCase", "crawlerHead", "crawlerArm", "wrench", "ingotPattern", "yagRod", "resonator", "heater", 
    															 "inductor"};
    public static String[] 	saltMakerArray = new String[] 	 	{"empty", "water", "stepa", "stepb", "stepc", "stepd", "salt"};
    public static String[] 	booksArray = new String[] 	 		{"chemistry", "alloys"};

//------- ALLOYS -------
    public static String[] 	alloyArray = new String[] 	 		{"cube", "scal", "bam", "yag", "cupronickel", "nimonic", "hastelloy", "nichrome"};
    public static String[] 	alloyItemArray = new String[] 	 	{"cubeDust", "cubeIngot", "cubeNugget", "scalDust", "scalIngot", "scalNugget", "bamDust", "bamIngot", "bamNugget", "yagDust", "yagIngot", "yagNugget", "cupronickelDust", "cupronickelIngot", "cupronickelNugget", "nimonicDust", "nimonicIngot", "nimonicNugget", "hastelloyDust", "hastelloyIngot", "hastelloyNugget", "nichromeDust", "nichromeIngot", "nichromeNugget"};
    public static String[] 	alloyItemsOredict = new String[] 	{"dustCube", "ingotCube", "nuggetCube", "dustScal", "ingotScal", "nuggetScal", "dustBam", "ingotBam", "nuggetBam", "dustYag", "gemYag", "nuggetYag", "dustCupronickel", "ingotCupronickel", "nuggetCupronickel", "dustNimonic", "ingotNimonic", "nuggetNimonic", "dustHastelloy", "ingotHastelloy", "nuggetHastelloy", "dustNichrome", "ingotNichrome", "nuggetNichrome"};
    public static String[] 	alloyBlocksOredict = new String[] 	{"blockCube", "blockScal", "blockBam", "blockYag", "blockCupronickel", "blockNimonic", "blockHastelloy", "blockNichrome"};
    public static String[] 	alloyDustsOredict = new String[] 	{"dustCube", "dustScal", "dustBam", "dustYag", "dustCupronickel", "dustNimonic", "dustHastelloy", "dustNichrome"};
    public static String[] 	alloyIngotsOredict = new String[] 	{"ingotCube", "ingotScal", "ingotBam", "gemYag", "ingotCupronickel", "ingotNimonic", "ingotHastelloy", "ingotNichrome"};
    public static String[] 	alloyNuggetsOredict = new String[]	{"nuggetCube", "nuggetScal", "nuggetBam", "nuggetYag", "nuggetCupronickel", "nuggetNimonic", "nuggetHastelloy", "nuggetNichrome"};

    public static String[] 	alloyBArray = new String[] 	 		{"mischmetal", "rosegold", "greengold", "whitegold", "shibuichi", "tombak", "pewter"};
    public static String[] 	alloyBItemArray = new String[] 	 	{"mischmetalDust", "mischmetalIngot", "mischmetalNugget", "rosegoldDust", "rosegoldIngot", "rosegoldNugget", "greengoldDust", "greengoldIngot", "greengoldNugget", "whitegoldDust", "whitegoldIngot", "whitegoldNugget", "shibuichiDust", "shibuichiIngot", "shibuichiNugget", "tombakDust", "tombakIngot", "tombakNugget", "pewterDust", "pewterIngot", "pewterNugget"};
    public static String[] 	alloyBItemsOredict = new String[] 	{"dustMischmetal", "ingotMischmetal", "nuggetMischmetal", "dustRosegold", "ingotRosegold", "nuggetRosegold", "dustGreengold", "ingotGreengold", "nuggetGreengold", "dustWhitegold", "ingotWhitegold", "nuggetWhitegold", "dustShibuichi", "ingotShibuichi", "nuggetShibuichi", "dustTombak", "ingotTombak", "nuggetTombak", "dustPewter", "ingotPewter", "nuggetPewter"};
    public static String[] 	alloyBBlocksOredict = new String[] 	{"blockMischmetal", "blockRosegold", "blockGreengold", "blockWhitegold", "blockShibuichi", "blockTombak", "blockPewter"};
    public static String[] 	alloyBDustsOredict = new String[] 	{"dustMischmetal", "dustRosegold", "dustGreengold", "dustWhitegold", "dustShibuichi", "dustTombak", "dustPewter"};
    public static String[] 	alloyBIngotsOredict = new String[] 	{"ingotMischmetal", "ingotRosegold", "ingotGreengold", "ingotWhitegold", "ingotShibuichi", "ingotTombak", "ingotPewter"};
    public static String[] 	alloyBNuggetsOredict = new String[] {"nuggetMischmetal", "nuggetRosegold", "nuggetGreengold", "nuggetWhitegold", "nuggetShibuichi", "nuggetTombak", "nuggetPewter"};

    public static String[] 	laserArray = new String[] 	 		{"off", "on"};

	public static void loadArray() {}

}