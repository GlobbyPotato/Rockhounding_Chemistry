package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.enums.EnumFires;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
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
import com.globbypotato.rockhounding_chemistry.integration.ModIntegration;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MachineRecipes extends BaseRecipes {
													//	A  B  C  H  N   O   p   S   S  S
//public static int[] categoryProb = new int[]{			4, 7, 7, 5, 11, 20, 11, 10, 9, 16};

													//	0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
//public static int[] arsenateProb = new int[]{			40,10,25,15,10};
//public static int[] borateProb = new int[]{			36,15,10,15,11,13};
//public static int[] carbonateProb = new int[]{		42,14,16,8, 5, 9, 6};
//public static int[] halideProb = new int[]{			9, 14,7, 12,36,13,9};
//public static int[] nativeProb = new int[]{			3, 8, 9, 3, 7, 9, 5, 3, 5, 4, 6, 5, 4, 6, 4, 4, 7, 7};
//public static int[] oxideProb = new int[]{			7, 2, 4, 3, 2, 3, 8, 8, 5, 3, 5, 3, 4, 3, 4, 3, 2, 4, 2, 3, 4, 2, 4, 3, 5, 4};
//public static int[] phosphateProb = new int[]{		7, 5, 12,9, 6, 8, 10,6, 9, 7, 4, 7, 5, 5};
//public static int[] silicateProb = new int[]{			8, 7, 9, 11,9, 8, 6, 5, 8, 9, 5, 9, 6};
//public static int[] sulfateProb = new int[]{			9, 6, 9, 11,6, 10,8, 11,6, 8, 9, 7};
//public static int[] sulfideProb = new int[]{			4, 6, 4, 9, 3, 8, 9, 4, 5, 6, 5, 3, 6, 3, 5, 6, 6, 4, 4};

	public static ArrayList<MineralSizerRecipe> sizerRecipes = new ArrayList<MineralSizerRecipe>();
	public static ArrayList<LabOvenRecipe> labOvenRecipes = new ArrayList<LabOvenRecipe>();
	public static ArrayList<MineralAnalyzerRecipe> analyzerRecipes = new ArrayList<MineralAnalyzerRecipe>();
	public static ArrayList<ChemicalExtractorRecipe> extractorRecipes = new ArrayList<ChemicalExtractorRecipe>();
	public static ArrayList<SaltSeasonerRecipe> seasonerRecipes = new ArrayList<SaltSeasonerRecipe>();
	public static ArrayList<MetalAlloyerRecipe> alloyerRecipes = new ArrayList<MetalAlloyerRecipe>();
	public static ArrayList<DepositionChamberRecipe> depositionRecipes = new ArrayList<DepositionChamberRecipe>();
	public static ArrayList<DistillationTowerRecipe> distillationRecipes = new ArrayList<DistillationTowerRecipe>();
	public static ArrayList<FlameTestRecipe> flamesRecipes = new ArrayList<FlameTestRecipe>();
	public static ArrayList<CastingRecipe> castingRecipes = new ArrayList<CastingRecipe>();
	public static ArrayList<LabBlenderRecipe> blenderRecipes = new ArrayList<LabBlenderRecipe>();

	public static ArrayList<String> inhibitedElements = new ArrayList<String>();

	public static void machineRecipes(){
		sizerRecipes.add(new MineralSizerRecipe(minerals(0), 								Arrays.asList(minerals(8), minerals(3), minerals(10), minerals(6), minerals(5), minerals(9), minerals(7), minerals(2), minerals(4), minerals(1))));
		sizerRecipes.add(new MineralSizerRecipe(ironIngot, 									elements(1,16))); //iron dust
		sizerRecipes.add(new MineralSizerRecipe(goldIngot, 									elements(1,45))); //gold dust
		sizerRecipes.add(new MineralSizerRecipe(copperIngot, 								elements(1,17))); //copper dust
		sizerRecipes.add(new MineralSizerRecipe(leadIngot, 									elements(1,19))); //lead dust
		sizerRecipes.add(new MineralSizerRecipe(titaniumIngot, 								elements(1,29))); //titanium dust
		sizerRecipes.add(new MineralSizerRecipe(aluminumIngot, 								elements(1,24))); //aluminum dust
		sizerRecipes.add(new MineralSizerRecipe(platinumIngot, 								elements(1,44))); //platinum dust
		sizerRecipes.add(new MineralSizerRecipe(new ItemStack(Blocks.STONE,1,1), 			fluorite)); //fuorite
		for(int i = 0; i <= EnumAlloy.getItemNames().length - 2; i++){
			if((i - 1) % 3 == 0 || i == 1){
				sizerRecipes.add(new MineralSizerRecipe(alloys(1, i), 						alloys(1, i - 1)));
			}
		}
		for(int i = 0; i <= EnumAlloyB.getItemNames().length - 2; i++){
			if((i - 1) % 3 == 0 || i == 1){
				sizerRecipes.add(new MineralSizerRecipe(alloysB(1, i), 						alloysB(1, i - 1)));
			}
		}

		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,2),  				 	false,		new FluidStack(FluidRegistry.WATER,1000), 								null,														new FluidStack(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID),500)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,3),  				 	false,	 	new FluidStack(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID), 500), 		null,														new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID),300)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,4),  				 	false,		new FluidStack(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID),500), 		null,														new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROFLUORIC_ACID),300)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,4),  				 	false,		new FluidStack(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID),500), 		new FluidStack(FluidRegistry.WATER,1000),					new FluidStack(EnumFluid.pickFluid(EnumFluid.PHOSPHORIC_ACID),400)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,3),  				 	false,		new FluidStack(EnumFluid.pickFluid(EnumFluid.AMMONIA),500), 			new FluidStack(EnumFluid.pickFluid(EnumFluid.SYNGAS),500),	new FluidStack(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE),300)));
		labOvenRecipes.add(new LabOvenRecipe(ToolUtils.ptCatalyst,				true, 		new FluidStack(EnumFluid.pickFluid(EnumFluid.AMMONIA),750), 			new FluidStack(FluidRegistry.WATER,1000),					new FluidStack(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID),400)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,5),  				 	false,		new FluidStack(FluidRegistry.WATER,1000), 								null,														new FluidStack(EnumFluid.pickFluid(EnumFluid.SYNGAS),400)));
		labOvenRecipes.add(new LabOvenRecipe(ToolUtils.feCatalyst,				true,		new FluidStack(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN),500), 	new FluidStack(EnumFluid.pickFluid(EnumFluid.SYNGAS),300),	new FluidStack(EnumFluid.pickFluid(EnumFluid.AMMONIA),300)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,3),  					false,		new FluidStack(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID),750), 		new FluidStack(EnumFluid.pickFluid(EnumFluid.SYNGAS),500),	new FluidStack(EnumFluid.pickFluid(EnumFluid.CHLOROMETHANE),300)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,6),  					false,		new FluidStack(FluidRegistry.WATER,1000), 								null,														new FluidStack(EnumFluid.pickFluid(EnumFluid.ACRYLIC_ACID),250)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,8),  					false,		new FluidStack(EnumFluid.pickFluid(EnumFluid.CHLOROMETHANE),500),		new FluidStack(FluidRegistry.WATER,1000),					new FluidStack(EnumFluid.pickFluid(EnumFluid.SILICONE),200)));
		labOvenRecipes.add(new LabOvenRecipe(chemicals(1,15), 					false,		new FluidStack(EnumFluid.pickFluid(EnumFluid.CHLOROMETHANE),1000),		null,														new FluidStack(EnumFluid.pickFluid(EnumFluid.TITANIUM_TETRACHLORIDE),100)));

		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(1),  Arrays.asList(arsenateShards(1,0), arsenateShards(1,1), arsenateShards(1,2), arsenateShards(1,3), arsenateShards(1,4)), Arrays.asList(arsenateGravity(0),arsenateGravity(1),arsenateGravity(2),arsenateGravity(3),arsenateGravity(4)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(2),  Arrays.asList(borateShards(1,0), borateShards(1,1), borateShards(1,2), borateShards(1,3), borateShards(1,4), borateShards(1,5)), Arrays.asList(borateGravity(0),borateGravity(1),borateGravity(2),borateGravity(3),borateGravity(4),borateGravity(5)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(3),  Arrays.asList(carbonateShards(1,0), carbonateShards(1,1), carbonateShards(1,2), carbonateShards(1,3), carbonateShards(1,4), carbonateShards(1,5), carbonateShards(1,6)), Arrays.asList(carbonateGravity(0),carbonateGravity(1),carbonateGravity(2),carbonateGravity(3), carbonateGravity(4), carbonateGravity(5), carbonateGravity(6)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(4),  Arrays.asList(halideShards(1,0), halideShards(1,1), halideShards(1,2), halideShards(1,3), halideShards(1,4), halideShards(1,5), halideShards(1,6)), Arrays.asList(halideGravity(0), halideGravity(1),halideGravity(2), halideGravity(3),halideGravity(4),halideGravity(5),halideGravity(6)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(5),  Arrays.asList(nativeShards(1,0), nativeShards(1,1), nativeShards(1,2), nativeShards(1,3), nativeShards(1,4), nativeShards(1,5), nativeShards(1,6), nativeShards(1,7), nativeShards(1,8), nativeShards(1,9), nativeShards(1,10), nativeShards(1,11), nativeShards(1,12), nativeShards(1,13), nativeShards(1,14), nativeShards(1,15), nativeShards(1,16), nativeShards(1,17)), Arrays.asList(nativeGravity(0), nativeGravity(1), nativeGravity(2), nativeGravity(3), nativeGravity(4), nativeGravity(5), nativeGravity(6), nativeGravity(7), nativeGravity(8), nativeGravity(9), nativeGravity(10), nativeGravity(11), nativeGravity(12), nativeGravity(13), nativeGravity(14), nativeGravity(15), nativeGravity(16), nativeGravity(17)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(6),  Arrays.asList(oxideShards(1,0), oxideShards(1,1), oxideShards(1,2), oxideShards(1,3), oxideShards(1,4), oxideShards(1,5), oxideShards(1,6), oxideShards(1,7), oxideShards(1,8), oxideShards(1,9), oxideShards(1,10), oxideShards(1,11), oxideShards(1,12), oxideShards(1,13), oxideShards(1,14), oxideShards(1,15), oxideShards(1,16), oxideShards(1,17), oxideShards(1,18), oxideShards(1,19), oxideShards(1,20), oxideShards(1,21), oxideShards(1,22), oxideShards(1,23), oxideShards(1,24), oxideShards(1,25)), Arrays.asList(oxideGravity(0), oxideGravity(1), oxideGravity(2), oxideGravity(3), oxideGravity(4), oxideGravity(5), oxideGravity(6), oxideGravity(7), oxideGravity(8), oxideGravity(9), oxideGravity(10), oxideGravity(11), oxideGravity(12), oxideGravity(13), oxideGravity(14), oxideGravity(15), oxideGravity(16), oxideGravity(17), oxideGravity(18), oxideGravity(19), oxideGravity(20), oxideGravity(21), oxideGravity(22), oxideGravity(23), oxideGravity(24), oxideGravity(25)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(7),  Arrays.asList(phosphateShards(1,0), phosphateShards(1,1), phosphateShards(1,2), phosphateShards(1,3), phosphateShards(1,4), phosphateShards(1,5), phosphateShards(1,6), phosphateShards(1,7), phosphateShards(1,8), phosphateShards(1,9), phosphateShards(1,10), phosphateShards(1,11), phosphateShards(1,12), phosphateShards(1,13)), Arrays.asList(phosphateGravity(0), phosphateGravity(1), phosphateGravity(2),phosphateGravity(3), phosphateGravity(4), phosphateGravity(5), phosphateGravity(6),phosphateGravity(7), phosphateGravity(8), phosphateGravity(9), phosphateGravity(10), phosphateGravity(11), phosphateGravity(12), phosphateGravity(13)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(8),  Arrays.asList(silicateShards(1,0), silicateShards(1,1), silicateShards(1,2), silicateShards(1,3), silicateShards(1,4), silicateShards(1,5), silicateShards(1,6), silicateShards(1,7), silicateShards(1,8), silicateShards(1,9), silicateShards(1,10), silicateShards(1,11), silicateShards(1,12)), Arrays.asList(silicateGravity(0), silicateGravity(1), silicateGravity(2), silicateGravity(3),silicateGravity(4), silicateGravity(5), silicateGravity(6), silicateGravity(7), silicateGravity(8), silicateGravity(9), silicateGravity(10), silicateGravity(11), silicateGravity(12)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(9),  Arrays.asList(sulfateShards(1,0), sulfateShards(1,1), sulfateShards(1,2), sulfateShards(1,3), sulfateShards(1,4), sulfateShards(1,5), sulfateShards(1,6), sulfateShards(1,7), sulfateShards(1,8), sulfateShards(1,9), sulfateShards(1,10), sulfateShards(1,11)), Arrays.asList(sulfateGravity(0), sulfateGravity(1), sulfateGravity(2), sulfateGravity(3),sulfateGravity(4), sulfateGravity(5),sulfateGravity(6), sulfateGravity(7),sulfateGravity(8), sulfateGravity(9), sulfateGravity(10), sulfateGravity(11)), true));
		analyzerRecipes.add(new MineralAnalyzerRecipe(minerals(10), Arrays.asList(sulfideShards(1,0), sulfideShards(1,1), sulfideShards(1,2), sulfideShards(1,3), sulfideShards(1,4), sulfideShards(1,5), sulfideShards(1,6), sulfideShards(1,7), sulfideShards(1,8), sulfideShards(1,9), sulfideShards(1,10), sulfideShards(1,11), sulfideShards(1,12), sulfideShards(1,13), sulfideShards(1,14), sulfideShards(1,15), sulfideShards(1,16), sulfideShards(1,17), sulfideShards(1,18)), Arrays.asList(sulfideGravity(0), sulfideGravity(1), sulfideGravity(2), sulfideGravity(3), sulfideGravity(4), sulfideGravity(5), sulfideGravity(6), sulfideGravity(7), sulfideGravity(8), sulfideGravity(9), sulfideGravity(10), sulfideGravity(11), sulfideGravity(12), sulfideGravity(13), sulfideGravity(14), sulfideGravity(15), sulfideGravity(16), sulfideGravity(17), sulfideGravity(18)), true));

		alloyerRecipes.add(new MetalAlloyerRecipe("CuBe", 				Arrays.asList("dustCopper", "dustBeryllium"), 																Arrays.asList(7, 2), 				alloys(9, 1), 	alloys(1, 2)));   //cube
		alloyerRecipes.add(new MetalAlloyerRecipe("ScAl", 				Arrays.asList("dustAluminum", "dustScandium"), 																Arrays.asList(7, 2), 				alloys(9, 4), 	alloys(1, 5)));   //scal
		alloyerRecipes.add(new MetalAlloyerRecipe("BAM", 				Arrays.asList("dustBoron", "dustAluminum", "dustMagnesium"), 												Arrays.asList(6, 2, 1), 			alloys(9, 7), 	alloys(1, 8)));   //bam
		alloyerRecipes.add(new MetalAlloyerRecipe("YAG", 				Arrays.asList("dustYttrium", "dustAluminum", "dustNeodymium", "dustChromium"), 								Arrays.asList(4, 2, 2, 1), 			alloys(9, 10), 	alloys(1, 11)));  //yag
		alloyerRecipes.add(new MetalAlloyerRecipe("Cupronickel",		Arrays.asList("dustCopper", "dustNickel", "dustManganese", "dustIron"), 									Arrays.asList(5, 2, 1, 1), 			alloys(9, 13), 	alloys(1, 14)));  //Cupronickel
		alloyerRecipes.add(new MetalAlloyerRecipe("Nimonic",			Arrays.asList("dustNickel", "dustCobalt", "dustChromium"), 													Arrays.asList(5, 2, 2), 			alloys(9, 16), 	alloys(1, 17)));  //Nimonic
		alloyerRecipes.add(new MetalAlloyerRecipe("Hastelloy",			Arrays.asList("dustIron", "dustNickel", "dustChromium"), 													Arrays.asList(5, 3, 1), 			alloys(9, 19), 	alloys(1, 20)));  //Hastelloy
		alloyerRecipes.add(new MetalAlloyerRecipe("Nichrome",			Arrays.asList("dustNickel", "dustChromium", "dustIron"), 													Arrays.asList(6, 2, 1), 			alloys(9, 22), 	alloys(1, 23)));  //Nichrome
		alloyerRecipes.add(new MetalAlloyerRecipe("CuNiFe",				Arrays.asList("dustCopper", "dustNickel", "dustIron", "dustCobalt"), 										Arrays.asList(5, 2, 2, 1), 			alloys(9, 25), 	alloys(1, 26)));  //CuNiFe
		alloyerRecipes.add(new MetalAlloyerRecipe("Hydronalium",		Arrays.asList("dustAluminum", "dustMagnesium", "dustManganese"), 											Arrays.asList(6, 2, 1), 			alloys(9, 37), 	alloys(1, 38)));  //hydronalium
		alloyerRecipes.add(new MetalAlloyerRecipe("Vanasteel",			Arrays.asList("dustIron", "dustCarbon", "dustVanadium", "dustChromium", "dustTungsten"), 					Arrays.asList(4, 2, 1, 1, 1), 		alloys(9, 40), 	alloys(1, 41)));  //vanasteel
		alloyerRecipes.add(new MetalAlloyerRecipe("Mischmetall",		Arrays.asList("dustCerium", "dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustIron"), 				Arrays.asList(4, 2, 1, 1, 1), 		alloysB(9, 1), 	alloysB(1, 2)));  //Mischmetall
		alloyerRecipes.add(new MetalAlloyerRecipe("Rose Gold",			Arrays.asList("dustGold", "dustCopper", "dustSilver"), 														Arrays.asList(5, 3, 1), 			alloysB(9, 4), 	alloysB(1, 5)));  //Rose Gold
		alloyerRecipes.add(new MetalAlloyerRecipe("Green Gold",			Arrays.asList("dustGold", "dustSilver", "dustCopper", "dustCadmium"),										Arrays.asList(5, 2, 1, 1), 			alloysB(9, 7), 	alloysB(1, 8)));  //Green Gold
		alloyerRecipes.add(new MetalAlloyerRecipe("White Gold",			Arrays.asList("dustGold", "dustSilver", "dustCopper", "dustManganese"),										Arrays.asList(5, 2, 1, 1), 			alloysB(9, 10), alloysB(1, 11))); //White Gold
		alloyerRecipes.add(new MetalAlloyerRecipe("Shibuichi",			Arrays.asList("dustCopper", "dustSilver", "dustGold"),														Arrays.asList(7, 2, 1), 			alloysB(9, 13), alloysB(1, 14))); //Shibuichi
		alloyerRecipes.add(new MetalAlloyerRecipe("Tombak",				Arrays.asList("dustCopper", "dustZinc", "dustArsenic"),														Arrays.asList(6, 2, 1), 			alloysB(9, 16), alloysB(1, 17))); //tombak
		alloyerRecipes.add(new MetalAlloyerRecipe("Pewter",				Arrays.asList("dustTin", "dustCopper", "dustBismuth", "dustLead"),											Arrays.asList(5, 1, 1, 1), 			alloysB(9, 19), alloysB(1, 20))); //Pewter
		alloyerRecipes.add(new MetalAlloyerRecipe("Corten Steel",		Arrays.asList("dustNickel", "dustSilicon", "dustChromium", "dustPhosphorus", "dustManganese", "dustCopper"),Arrays.asList(2, 2, 2, 1, 1, 1), 	alloysB(9, 22), alloysB(1, 23))); //Corten Steel
		alloyerRecipes.add(new MetalAlloyerRecipe("Shakudo",			Arrays.asList("dustCopper", "dustGold"),																	Arrays.asList(8, 1), 				alloysB(9, 25), alloysB(1, 26))); //shakudo
		alloyerRecipes.add(new MetalAlloyerRecipe("Purple Gold",		Arrays.asList("dustGold", "dustAluminum"),																	Arrays.asList(7, 2), 				alloysB(9, 28), alloysB(1, 29))); //purple gold
		alloyerRecipes.add(new MetalAlloyerRecipe("Carbon Ingot",		Arrays.asList("dustCarbon"),																				Arrays.asList(4), 					miscs(4, 55))); 				  //carbon ingot
		alloyerRecipes.add(new MetalAlloyerRecipe("Fused Glass",		Arrays.asList("blockGlass", "blockSand"),																	Arrays.asList(4, 1), 				miscs(1, 57))); 				  //fused glass

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
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(5), nativeStack(EnumNative.AURICUPRIDE), Arrays.asList(EnumElement.GOLD.name().toLowerCase(), EnumElement.COPPER.name().toLowerCase()), Arrays.asList(51,49)));
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
		extractorRecipes.add(new ChemicalExtractorRecipe(getText(6), oxideStack(EnumOxide.GAHNITE), Arrays.asList(EnumElement.ZINC.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase()), Arrays.asList(36,30)));
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

		extractorRecipes.add(new ChemicalExtractorRecipe("Silicate", lapis, Arrays.asList(EnumElement.SILICON.name().toLowerCase(), EnumElement.ALUMINUM.name().toLowerCase(), EnumElement.SODIUM.name().toLowerCase(), EnumElement.CALCIUM.name().toLowerCase(), EnumElement.SULFUR.name().toLowerCase()), Arrays.asList(17,16,14,8,6)));
		extractorRecipes.add(new ChemicalExtractorRecipe("Fossil", crackedCoal, Arrays.asList(EnumElement.CARBON.name().toLowerCase()), Arrays.asList(50)));

		seasonerRecipes.add(new SaltSeasonerRecipe(BaseRecipes.saltRaw, BaseRecipes.saltStack));
		seasonerRecipes.add(new SaltSeasonerRecipe(rottenFlesh, leather));

		depositionRecipes.add(new DepositionChamberRecipe(elements(1,42), 	alloys(1, 28), 	new FluidStack(EnumFluid.pickFluid(EnumFluid.AMMONIA),	 			5000),	1900, 	10000));
		depositionRecipes.add(new DepositionChamberRecipe(elements(1,42), 	alloys(1, 31), 	new FluidStack(EnumFluid.pickFluid(EnumFluid.SYNGAS), 				8000), 	2500, 	24000));
		depositionRecipes.add(new DepositionChamberRecipe(titaniumIngot, 	alloys(1, 34), 	new FluidStack(EnumFluid.pickFluid(EnumFluid.AMMONIA), 				4000), 	1500, 	30000));
		depositionRecipes.add(new DepositionChamberRecipe(elements(1,19), 	leadDioxide, 	new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID), 	1000), 	800, 	4000));
		depositionRecipes.add(new DepositionChamberRecipe(elements(1,46), 	alloys(1, 42), 	new FluidStack(EnumFluid.pickFluid(EnumFluid.CHLOROMETHANE), 		2000), 	1200, 	2000));

		distillationRecipes.add(new DistillationTowerRecipe(chemicals(1,10), new FluidStack(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN), 100)));

		for(int x = 0; x < EnumFires.size(); x++){
			flamesRecipes.add(new FlameTestRecipe(fires(1, x), fireBlocks(1, x)));
		}

		//generic
		castingRecipes.add(new CastingRecipe("ingotLeadDioxide", 	leadElectrode, 0));
		castingRecipes.add(new CastingRecipe("ingotIron", 			ToolUtils.feCatalyst, 0));
		castingRecipes.add(new CastingRecipe("ingotPlatinum", 		ToolUtils.ptCatalyst, 0));
		//coils
		castingRecipes.add(new CastingRecipe("ingotCopper", 		miscs(4, 19), 1));
		castingRecipes.add(new CastingRecipe("ingotCunife", 		miscs(4, 30), 1));
		//rods
		castingRecipes.add(new CastingRecipe("ingotNichrome", 		miscs(2, 29), 2));
		castingRecipes.add(new CastingRecipe("ingotYag", 			miscs(2, 14), 2));
		castingRecipes.add(new CastingRecipe("ingotCarbon", 		miscs(2, 50), 2));
		//foils
		castingRecipes.add(new CastingRecipe("ingotCupronickel", 	miscs(3, 18), 3));
		castingRecipes.add(new CastingRecipe("ingotHastelloy", 		miscs(3, 13), 3));
		castingRecipes.add(new CastingRecipe("ingotNimonic", 		miscs(3, 24), 3));
		castingRecipes.add(new CastingRecipe("itemFusedGlass", 		fiberglass, 3));
		castingRecipes.add(new CastingRecipe("ingotWidia", 			miscs(3, 61), 3));
		castingRecipes.add(new CastingRecipe("ingotIron", 			miscs(3, 64), 3));
		//arms
		castingRecipes.add(new CastingRecipe("ingotNimonic", 		nimonicArm, 4));
		castingRecipes.add(new CastingRecipe("ingotHastelloy", 		hastelloyArm, 4));
		castingRecipes.add(new CastingRecipe("ingotCupronickel", 	cupronickelArm, 4));
		//casings
		castingRecipes.add(new CastingRecipe("blockNimonic", 		miscs(8, 28), 5));
		castingRecipes.add(new CastingRecipe("blockAluminum", 		miscs(8, 48), 5));
		castingRecipes.add(new CastingRecipe("blockCupronickel", 	miscs(8, 45), 5));
		castingRecipes.add(new CastingRecipe("blockHastelloy", 		miscs(8, 33), 5));
		castingRecipes.add(new CastingRecipe("blockHydronalium", 	miscs(8, 41), 5));
		castingRecipes.add(new CastingRecipe("blockIron", 			miscs(8, 42), 5));
		castingRecipes.add(new CastingRecipe("blockWidia", 			miscs(8, 62), 5));
		//gear
		castingRecipes.add(new CastingRecipe("ingotVanasteel", 		vanaGear, 6));
		castingRecipes.add(new CastingRecipe("ingotIron", 			ToolUtils.gear, 6));
		//ingot
		castingRecipes.add(new CastingRecipe("dustWidia", 			alloys(1, 43), 7));
		castingRecipes.add(new CastingRecipe("dustLeadDioxide", 	leadDioxideIngot, 7));


		//sulfur compound
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustSulfur"), 												Arrays.asList(3),  		chemicals(8,2)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("itemPyrite"),	 											Arrays.asList(3), 	 	chemicals(4,2)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(coalBlock(1,0)), 													  					chemicals(3,2)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("blockAnthracite"), 											Arrays.asList(1),  		chemicals(4,2)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("blockBituminous"), 											Arrays.asList(1),  		chemicals(3,2)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("blockFuelCoke"), 											Arrays.asList(1),  		chemicals(4,2)));
		//fluorite compound
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("itemFluorite"), 												Arrays.asList(3),		chemicals(4,4)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("gemApatite"), 												Arrays.asList(3),		chemicals(4,4)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustApatite"), 												Arrays.asList(3),		chemicals(4,4)));
		//salt compound
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustSalt"), 													Arrays.asList(3),		chemicals(4,3)));
		//cracked coal
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(coalItem(3,0)), 													  					chemicals(2,6)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("itemAnthracite"), 											Arrays.asList(2),  		chemicals(3,6)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("itemBituminous"), 											Arrays.asList(3),  		chemicals(2,6)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("itemLignite"), 												Arrays.asList(4),  		chemicals(1,6)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustCoal"), 													Arrays.asList(3),  		chemicals(2,6)));
		//carbon compost
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustCarbon"), 												Arrays.asList(3),  		chemicals(2,5)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(chemicals(4,6)), 													  					chemicals(1,5)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("fuelCoke"), 													Arrays.asList(2),  		chemicals(1,5)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustCoke"), 													Arrays.asList(2),  		chemicals(1,5)));
		//silicon compost
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustSilicon"), 												Arrays.asList(3),  		chemicals(4,8)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("itemSilicon"), 												Arrays.asList(3),  		chemicals(4,8)));
		//platinum compost
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(nativeShards(4,17)), 													  				chemicals(3,10)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(nativeShards(3,6)), 													 		 		chemicals(2,10)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(nativeShards(3,7)), 													 				chemicals(2,10)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(sulfideShards(3,14)), 												 		 		chemicals(1,10)));
		//cracked charcoal
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(coalItem(3,1)), 														 				chemicals(2,14)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustCharcoal"), 												Arrays.asList(3),  		chemicals(2,14)));
		//ash compost
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(chemicals(3,14)), 										  							chemicals(1,11)));
		//rutile compost
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(oxideShards(3,24)), 																	chemicals(2,15)));
		//fire composts
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustArsenic"), 												Arrays.asList(8),  		fires(8, 1)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList("dustAluminum"), 												Arrays.asList(8),  		fires(8, 3)));
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(chemicals(4,3)), 														 		 		fires(8, 4)));
		//misc
		blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(chemicals(6,13), chemicals(2,14), chemicals(1,2)),					 		 		new ItemStack(Items.GUNPOWDER, 9)));

		if(ModIntegration.railcraftLoaded()){
			//carbon compost
			blenderRecipes.add(new LabBlenderRecipe(Arrays.asList(ModIntegration.railCokeBlock()), 													chemicals(1,5)));
		}
	}
}