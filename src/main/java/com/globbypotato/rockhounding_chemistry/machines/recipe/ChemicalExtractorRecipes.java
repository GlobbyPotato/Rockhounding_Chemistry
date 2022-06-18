package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumMinerals;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumAntimonate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumArsenate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumBorate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumCarbonate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumChromate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumHalide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumOxide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumPhosphate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSilicate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumVanadate;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class ChemicalExtractorRecipes extends BaseRecipes{
	public static ArrayList<ChemicalExtractorRecipe> extractor_recipes = new ArrayList<ChemicalExtractorRecipe>();

	public static void machineRecipes() {

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ANTIMONATE), antimonate_stack(1, EnumAntimonate.BAHIANITE), 
																			Arrays.asList(	element(EnumElements.ANTIMONY),
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(48,18)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ANTIMONATE), antimonate_stack(1, EnumAntimonate.PARTZITE), 
																			Arrays.asList(	element(EnumElements.ANTIMONY),
																							element(EnumElements.IRON)), 
																			Arrays.asList(50,23)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ANTIMONATE), antimonate_stack(1, EnumAntimonate.TRIPUHYITE), 
																			Arrays.asList(	element(EnumElements.ANTIMONY),
																							element(EnumElements.IRON)), 
																			Arrays.asList(50,23)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ANTIMONATE), antimonate_stack(1, EnumAntimonate.PARWELITE), 
																			Arrays.asList(	element(EnumElements.MANGANESE),
																							element(EnumElements.ANTIMONY),
																							element(EnumElements.ARSENIC),
																							element(EnumElements.SILICON),
																							element(EnumElements.MAGNESIUM),
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(35,16,11,4,3,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ANTIMONATE), antimonate_stack(1, EnumAntimonate.CAMEROLAITE), 
																			Arrays.asList(	element(EnumElements.COPPER),
																							element(EnumElements.ANTIMONY),
																							element(EnumElements.ALUMINUM),
																							element(EnumElements.CARBON),
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(34,12,7,2,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ANTIMONATE), antimonate_stack(1, EnumAntimonate.ORDONEZITE), 
																			Arrays.asList(	element(EnumElements.ANTIMONY),
																							element(EnumElements.ZINC)), 
																			Arrays.asList(60,16)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ARSENATE), arsenate_stack(1, EnumArsenate.AGARDITE), 		
																			Arrays.asList(	element(EnumElements.COPPER), 		
																							element(EnumElements.ARSENIC), 			
																							element(EnumElements.LEAD), 			
																							element(EnumElements.DYSPROSIUM), 
																							element(EnumElements.YTTRIUM), 
																							element(EnumElements.CERIUM), 
																							element(EnumElements.LANTHANUM), 
																							element(EnumElements.NEODYMIUM), 
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.EUROPIUM), 
																							element(EnumElements.GADOLINIUM), 
																							element(EnumElements.SAMARIUM), 
																							element(EnumElements.SILICON)), 
																			Arrays.asList(36,22,6,5,4,4,3,3,2,1,1,1,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ARSENATE), arsenate_stack(1, EnumArsenate.SCHULTENITE), 		
																			Arrays.asList(	element(EnumElements.LEAD),
																							element(EnumElements.ARSENIC)), 
																			Arrays.asList(60,22)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ARSENATE), arsenate_stack(1, EnumArsenate.PITTICITE), 
																			Arrays.asList(	element(EnumElements.IRON), 			
																							element(EnumElements.ARSENIC), 			
																							element(EnumElements.SULFUR)), 										
																			Arrays.asList(31,21,9)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ARSENATE), arsenate_stack(1, EnumArsenate.ZALESIITE), 		
																			Arrays.asList(	element(EnumElements.COPPER), 		
																							element(EnumElements.ARSENIC), 			
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.YTTRIUM)), 
																			Arrays.asList(38,22,3,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.ARSENATE), arsenate_stack(1, EnumArsenate.MIXITE), 			
																			Arrays.asList(	element(EnumElements.COPPER),
																							element(EnumElements.ARSENIC), 			
																							element(EnumElements.BISMUTH)),
																			Arrays.asList(33,19,18)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.BORATE), borate_stack(1, EnumBorate.BORAX), 
																			Arrays.asList(	element(EnumElements.SODIUM), 
																							element(EnumElements.BORON)), 
																			Arrays.asList(12,11)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.BORATE), borate_stack(1, EnumBorate.ERICAITE), 
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.BORON), 
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.MANGANESE)), 
																			Arrays.asList(22,17,5,4)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.BORATE), borate_stack(1, EnumBorate.HULSITE), 
																			Arrays.asList(	element(EnumElements.IRON),
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.BORON), 
																							element(EnumElements.TIN)), 
																			Arrays.asList(46,10,5,5)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.BORATE), borate_stack(1, EnumBorate.LONDONITE), 
																			Arrays.asList(	element(EnumElements.BORON), 
																							element(EnumElements.ALUMINUM), 
																							element(EnumElements.BERYLLIUM), 
																							element(EnumElements.POTASSIUM),
																							element(EnumElements.LITHIUM), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(15,13,6,2,1,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.BORATE), borate_stack(1, EnumBorate.TUSIONITE), 
																			Arrays.asList(	element(EnumElements.TIN), 
																							element(EnumElements.MANGANESE), 
																							element(EnumElements.BORON)), 
																			Arrays.asList(40,19,8)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.BORATE), borate_stack(1, EnumBorate.RHODIZITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.BORON), 
																							element(EnumElements.BERYLLIUM), 
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(14,13,8,4)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.ANKERITE), 
																			Arrays.asList(	element(EnumElements.CALCIUM), 
																							element(EnumElements.IRON), 
																							element(EnumElements.CARBON), 
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.MANGANESE)), 
																			Arrays.asList(19,16,12,4,3)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.GASPEITE), 
																			Arrays.asList(	element(EnumElements.NICKEL),
																							element(EnumElements.CARBON), 
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(33,11,7,5)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.ROSASITE), 
																			Arrays.asList(	element(EnumElements.COPPER), 
																							element(EnumElements.ZINC), 
																							element(EnumElements.CARBON)), 
																			Arrays.asList(43,15,5)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.PARISITE), 
																			Arrays.asList(	element(EnumElements.NEODYMIUM), 
																							element(EnumElements.LANTHANUM), 
																							element(EnumElements.CERIUM),
																							element(EnumElements.SILICON),
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.CARBON)), 
																			Arrays.asList(23,21,19,7,7,7)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.OTAVITE), 
																			Arrays.asList(	element(EnumElements.CADMIUM),
																							element(EnumElements.CARBON)), 
																			Arrays.asList(65,7)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.SMITHSONITE), 
																			Arrays.asList(	element(EnumElements.ZINC), 
																							element(EnumElements.CARBON)), 
																			Arrays.asList(52,10)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CARBONATE), carbonate_stack(1, EnumCarbonate.HUNTITE), 		
																			Arrays.asList(	element(EnumElements.MAGNESIUM), 
																							element(EnumElements.CARBON),
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(21,14,11)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CHROMATE), chromate_stack(1, EnumChromate.LOPEZITE), 
																			Arrays.asList(	element(EnumElements.CHROMIUM), 
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(35,26)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CHROMATE), chromate_stack(1, EnumChromate.CROCOITE), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.CHROMIUM)), 
																			Arrays.asList(64,16)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CHROMATE), chromate_stack(1, EnumChromate.CHROMATITE), 
																			Arrays.asList(	element(EnumElements.CHROMIUM), 
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(33,26)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CHROMATE), chromate_stack(1, EnumChromate.IRANITE), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.CHROMIUM), 
																							element(EnumElements.COPPER),
																							element(EnumElements.SILICON)), 
																			Arrays.asList(68,10,2,2)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CHROMATE), chromate_stack(1, EnumChromate.FORNACITE), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.ARSENIC), 
																							element(EnumElements.COPPER),
																							element(EnumElements.CHROMIUM)), 
																			Arrays.asList(55,10,9,7)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.CHROMATE), chromate_stack(1, EnumChromate.MACQUARTITE), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.CHROMIUM),
																							element(EnumElements.SILICON)), 
																			Arrays.asList(63,6,5,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.HALIDE), halide_stack(1, EnumHalide.BOLEITE), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.SILVER), 
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(49,14,9,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.HALIDE), halide_stack(1, EnumHalide.CARNALLITE), 
																			Arrays.asList(	element(EnumElements.POTASSIUM), 
																							element(EnumElements.MAGNESIUM)), 
																			Arrays.asList(14,9)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.HALIDE), halide_stack(1, EnumHalide.RINNEITE),
																			Arrays.asList(	element(EnumElements.POTASSIUM), 
																							element(EnumElements.IRON),
																							element(EnumElements.SODIUM)), 
																			Arrays.asList(28,14,6)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.HALIDE), halide_stack(1, EnumHalide.GRICEITE), 
																			Arrays.asList(	element(EnumElements.LITHIUM)), 
																			Arrays.asList(27)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.HALIDE), halide_stack(1, EnumHalide.HEKLAITE), 
																			Arrays.asList(	element(EnumElements.POTASSIUM),
																							element(EnumElements.SILICON), 
																							element(EnumElements.SODIUM)), 
																			Arrays.asList(19,14,11)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.HALIDE), halide_stack(1, EnumHalide.CREEDITE), 
																			Arrays.asList(	element(EnumElements.CALCIUM), 
																							element(EnumElements.ALUMINUM), 
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(24,11,6)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.COHENITE), 
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.NICKEL), 
																							element(EnumElements.COBALT),
																							element(EnumElements.CARBON)), 
																			Arrays.asList(55,29,10,6)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.CUPALITE),
																			Arrays.asList(	element(EnumElements.COPPER), 
																							element(EnumElements.ALUMINUM),
																							element(EnumElements.ZINC), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(60,26,7,7)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.HAXONITE),
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.NICKEL),
																							element(EnumElements.CARBON)), 
																			Arrays.asList(82,13,5)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.PERRYITE),
																			Arrays.asList(	element(EnumElements.NICKEL),
																							element(EnumElements.IRON), 
																							element(EnumElements.SILICON), 
																							element(EnumElements.PHOSPHORUS)), 
																			Arrays.asList(60,20,11,4)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.TULAMEENITE), 
																			Arrays.asList(	element(EnumElements.PLATINUM), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(73,13,11)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.NIGGLIITE),
																			Arrays.asList(	element(EnumElements.PLATINUM), 
																							element(EnumElements.TIN)), 
																			Arrays.asList(62,38)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.MALDONITE), 
																			Arrays.asList(	element(EnumElements.GOLD), 
																							element(EnumElements.BISMUTH)), 
																			Arrays.asList(65,35)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.AURICUPRIDE), 
																			Arrays.asList(	element(EnumElements.GOLD), 
																							element(EnumElements.COPPER)), 
																			Arrays.asList(51,49)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.KHATYRKITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.ZINC)), 
																			Arrays.asList(46,40,14)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.FULLERITE), 
																			Arrays.asList(	element(EnumElements.CARBON)), 
																			Arrays.asList(100)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.CHAOITE),
																			Arrays.asList(	element(EnumElements.CARBON)), 
																			Arrays.asList(100)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.GRAPHITE), 
																			Arrays.asList(	element(EnumElements.CARBON)), 
																			Arrays.asList(100)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.NATIVE), native_stack(1, EnumNative.OSMIRIDIUM), 
																			Arrays.asList(	element(EnumElements.OSMIUM), 
																							element(EnumElements.IRIDIUM)), 
																			Arrays.asList(50,50)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.CHROMITE), 
																			Arrays.asList(	element(EnumElements.CHROMIUM), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(47,25)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.COCHROMITE), 
																			Arrays.asList(	element(EnumElements.CHROMIUM),
																							element(EnumElements.COBALT), 
																							element(EnumElements.IRON), 
																							element(EnumElements.NICKEL),
																							element(EnumElements.ALUMINUM), 
																							element(EnumElements.MAGNESIUM)),
																			Arrays.asList(35,14,8,6,5,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.COLUMBITE),
																			Arrays.asList(	element(EnumElements.NIOBIUM), 
																							element(EnumElements.TANTALUM),
																							element(EnumElements.IRON), 
																							element(EnumElements.MANGANESE), 
																							element(EnumElements.MAGNESIUM),
																							element(EnumElements.TITANIUM), 
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(42,24,8,7,5,2,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.EUXENITE), 
																			Arrays.asList(	element(EnumElements.NIOBIUM),
																							element(EnumElements.YTTRIUM), 
																							element(EnumElements.CERIUM), 
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.TITANIUM)), 
																			Arrays.asList(33,16,3,2,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.MCCONNELLITE),
																			Arrays.asList(	element(EnumElements.COPPER), 
																							element(EnumElements.CHROMIUM)), 
																			Arrays.asList(43,35)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.SAMARSKITE), 
																			Arrays.asList(	element(EnumElements.NIOBIUM), 
																							element(EnumElements.TANTALUM), 
																							element(EnumElements.THORIUM), 
																							element(EnumElements.YELLOWCAKE), 
																							element(EnumElements.YTTERBIUM), 
																							element(EnumElements.IRON),
																							element(EnumElements.YTTRIUM), 
																							element(EnumElements.LUTETIUM), 
																							element(EnumElements.THULIUM), 
																							element(EnumElements.HOLMIUM), 
																							element(EnumElements.DYSPROSIUM), 
																							element(EnumElements.ERBIUM),
																							element(EnumElements.EUROPIUM),
																							element(EnumElements.TERBIUM), 
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(24,12,10,10,5,4,4,4,4,4,4,4,4,4,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.CASSITERITE), 
																			Arrays.asList(	element(EnumElements.TIN)),
																			Arrays.asList(79)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.BOEHMITE),
																			Arrays.asList(	element(EnumElements.ALUMINUM)), 
																			Arrays.asList(45)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.GAHNITE),
																			Arrays.asList(	element(EnumElements.ZINC), 
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(36,30)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.HIBONITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.IRON), 
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.TITANIUM), 
																							element(EnumElements.LANTHANUM), 
																							element(EnumElements.CERIUM), 
																							element(EnumElements.MAGNESIUM)), 
																			Arrays.asList(40,6,5,3,2,2,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.SENAITE), 
																			Arrays.asList(	element(EnumElements.TITANIUM),
																							element(EnumElements.IRON), 
																							element(EnumElements.MANGANESE),
																							element(EnumElements.LEAD)), 
																			Arrays.asList(25,21,12,11)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.THORUTITE), 
																			Arrays.asList(	element(EnumElements.TITANIUM), 
																							element(EnumElements.THORIUM), 
																							element(EnumElements.YELLOWCAKE),
																							element(EnumElements.CALCIUM)),
																			Arrays.asList(25,24,24,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.IXIOLITE), 
																			Arrays.asList(	element(EnumElements.TANTALUM), 
																							element(EnumElements.TIN), 
																							element(EnumElements.MANGANESE), 
																							element(EnumElements.NIOBIUM), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(56,7,7,6,3)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.TAPIOLITE), 
																			Arrays.asList(	element(EnumElements.TANTALUM),
																							element(EnumElements.MANGANESE), 
																							element(EnumElements.IRON), 
																							element(EnumElements.NIOBIUM)),
																			Arrays.asList(66,6,6,6)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.BEHOITE), 
																			Arrays.asList(	element(EnumElements.BERYLLIUM)), 
																			Arrays.asList(21)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.BROMELLITE), 
																			Arrays.asList(	element(EnumElements.BERYLLIUM)), 
																			Arrays.asList(36)));
			
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.TUNGSTITE), 
																			Arrays.asList(	element(EnumElements.TUNGSTEN)), 
																			Arrays.asList(75)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.WOLFRAMITE), 
																			Arrays.asList(	element(EnumElements.TUNGSTEN), 
																							element(EnumElements.IRON), 
																							element(EnumElements.MANGANESE)), 
																			Arrays.asList(61,9,9)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.FERBERITE), 
																			Arrays.asList(	element(EnumElements.TUNGSTEN), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(61,18)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.MONTEPONITE), 
																			Arrays.asList(	element(EnumElements.CADMIUM)), 
																			Arrays.asList(88)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.LOPARITE), 
																			Arrays.asList(	element(EnumElements.TITANIUM), 
																							element(EnumElements.CERIUM), 
																							element(EnumElements.NIOBIUM),
																							element(EnumElements.LANTHANUM), 
																							element(EnumElements.SODIUM),
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(23,17,11,8,8,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.BUNSENITE), 
																			Arrays.asList(	element(EnumElements.NICKEL)), 
																			Arrays.asList(78)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.RUTILE), 
																			Arrays.asList(	element(EnumElements.TITANIUM)), 
																			Arrays.asList(60)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.URANINITE), 
																			Arrays.asList(	element(EnumElements.YELLOWCAKE)), 
																			Arrays.asList(88)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.ZIRKELITE), 
																			Arrays.asList(	element(EnumElements.ZIRCONIUM),
																							element(EnumElements.TITANIUM),
																							element(EnumElements.NIOBIUM),
																							element(EnumElements.THORIUM),
																							element(EnumElements.CALCIUM),
																							element(EnumElements.CERIUM)), 
																			Arrays.asList(24,19,12,6,6,4)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.BIEHLITE), 
																			Arrays.asList(	element(EnumElements.ANTIMONY),
																							element(EnumElements.MOLYBDENUM),
																							element(EnumElements.ARSENIC)), 
																			Arrays.asList(51,22,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.KAMIOKITE), 
																			Arrays.asList(	element(EnumElements.MOLYBDENUM),
																							element(EnumElements.IRON)), 
																			Arrays.asList(54,21)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.OXIDE), oxide_stack(1, EnumOxide.MOURITE), 
																			Arrays.asList(	element(EnumElements.MOLYBDENUM),
																							element(EnumElements.YELLOWCAKE)), 
																			Arrays.asList(44,22)));
															
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.FAUSTITE), 
																			Arrays.asList(	element(EnumElements.VANADIUM), 
																							element(EnumElements.PHOSPHORUS), 
																							element(EnumElements.ZINC), 
																							element(EnumElements.COPPER)), 
																			Arrays.asList(20,15,6,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.LAZULITE), 
																			Arrays.asList(	element(EnumElements.PHOSPHORUS), 
																							element(EnumElements.ALUMINUM),
																							element(EnumElements.MAGNESIUM)), 
																			Arrays.asList(20,18,8)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.MONAZITE), 
																			Arrays.asList(	element(EnumElements.NEODYMIUM), 
																							element(EnumElements.LANTHANUM), 
																							element(EnumElements.CERIUM), 
																							element(EnumElements.GADOLINIUM), 
																							element(EnumElements.SAMARIUM), 
																							element(EnumElements.PHOSPHORUS),
																							element(EnumElements.THORIUM)), 
																			Arrays.asList(21,18,16,14,13,11,7)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.SCHOONERITE), 
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.PHOSPHORUS), 
																							element(EnumElements.ZINC), 
																							element(EnumElements.MANGANESE)), 
																			Arrays.asList(22,12,8,7)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.TRIPHYLITE),
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.PHOSPHORUS),
																							element(EnumElements.LITHIUM)), 
																			Arrays.asList(35,20,5)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.WAVELLITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.PHOSPHORUS)), 
																			Arrays.asList(20,15)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.XENOTIME), 
																			Arrays.asList(	element(EnumElements.YTTERBIUM), 
																							element(EnumElements.YTTRIUM), 
																							element(EnumElements.PHOSPHORUS)),
																			Arrays.asList(48,38,14)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.ZAIRITE), 
																			Arrays.asList(	element(EnumElements.BISMUTH),
																							element(EnumElements.IRON),
																							element(EnumElements.PHOSPHORUS),
																							element(EnumElements.ALUMINUM)),
																			Arrays.asList(32,20,10,3)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.PRETULITE),
																			Arrays.asList(	element(EnumElements.SCANDIUM),
																							element(EnumElements.PHOSPHORUS)),
																			Arrays.asList(32,22)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.TAVORITE), 
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.PHOSPHORUS), 
																							element(EnumElements.LITHIUM)), 
																			Arrays.asList(32,18,4)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.KEYITE),
																			Arrays.asList(	element(EnumElements.ARSENIC),
																							element(EnumElements.COPPER), 
																							element(EnumElements.ZINC), 
																							element(EnumElements.CADMIUM), 
																							element(EnumElements.MANGANESE), 
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(29,15,14,13,1,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.BIRCHITE), 
																			Arrays.asList(	element(EnumElements.CADMIUM),
																							element(EnumElements.COPPER), 
																							element(EnumElements.PHOSPHORUS), 
																							element(EnumElements.ZINC)), 
																			Arrays.asList(32,17,9,1)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.ZIESITE),
																			Arrays.asList(	element(EnumElements.COPPER),
																							element(EnumElements.VANADIUM)), 
																			Arrays.asList(37,30)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.SCHODERITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.VANADIUM),	
																							element(EnumElements.PHOSPHORUS)), 
																			Arrays.asList(13,13,8)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.PHOSPHATE), phosphate_stack(1, EnumPhosphate.KOSNARITE), 
																			Arrays.asList(	element(EnumElements.ZIRCONIUM), 
																							element(EnumElements.PHOSPHORUS),	
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(36,18,8)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.AXINITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.ALUMINUM), 
																							element(EnumElements.MANGANESE), 
																							element(EnumElements.IRON), 
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.BORON)), 
																			Arrays.asList(20,14,10,10,10,5,2)));
		
		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.BIOTITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.MANGANESE),
																							element(EnumElements.POTASSIUM), 
																							element(EnumElements.ALUMINUM), 
																							element(EnumElements.IRON)),
																			Arrays.asList(19,14,10,6,6)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.GADOLINITE),
																			Arrays.asList(	element(EnumElements.YTTRIUM),
																							element(EnumElements.IRON), 
																							element(EnumElements.SILICON),
																							element(EnumElements.LANTHANUM), 
																							element(EnumElements.CERIUM), 
																							element(EnumElements.PRASEODYMIUM),
																							element(EnumElements.SAMARIUM), 
																							element(EnumElements.BERYLLIUM), 
																							element(EnumElements.EUROPIUM),
																							element(EnumElements.HOLMIUM),
																							element(EnumElements.LUTETIUM),
																							element(EnumElements.TERBIUM), 
																							element(EnumElements.THULIUM), 
																							element(EnumElements.BORON)), 
																			Arrays.asList(22,10,10,6,6,6,6,4,3,3,3,3,3,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.JERVISITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.SCANDIUM), 
																							element(EnumElements.SODIUM), 
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.IRON),
																							element(EnumElements.MAGNESIUM)), 
																			Arrays.asList(25,12,6,5,5,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.MAGBASITE), 
																			Arrays.asList(	element(EnumElements.SILICON),
																							element(EnumElements.IRON), 
																							element(EnumElements.MAGNESIUM),
																							element(EnumElements.POTASSIUM),
																							element(EnumElements.SCANDIUM), 
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(18,12,10,4,2,2)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.MOSKVINITE), 
																			Arrays.asList(	element(EnumElements.SILICON),
																							element(EnumElements.YTTRIUM),
																							element(EnumElements.TITANIUM), 
																							element(EnumElements.POTASSIUM), 
																							element(EnumElements.DYSPROSIUM), 
																							element(EnumElements.GADOLINIUM), 
																							element(EnumElements.HOLMIUM),
																							element(EnumElements.TERBIUM), 
																							element(EnumElements.SAMARIUM)), 
																			Arrays.asList(28,11,7,6,3,1,1,1,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.EUCRYPTITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.ALUMINUM), 
																							element(EnumElements.LITHIUM)),
																			Arrays.asList(22,22,6)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.STEACYITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.THORIUM), 
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.POTASSIUM), 
																							element(EnumElements.SODIUM),
																							element(EnumElements.MANGANESE)),
																			Arrays.asList(27,25,4,2,2,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.MANANDONITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.SILICON),
																							element(EnumElements.LITHIUM), 
																							element(EnumElements.BORON)), 
																			Arrays.asList(26,11,3,2)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.VISTEPITE), 
																			Arrays.asList(	element(EnumElements.MANGANESE),
																							element(EnumElements.TIN),
																							element(EnumElements.PLATINUM),
																							element(EnumElements.BORON)), 
																			Arrays.asList(29,16,15,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.KHRISTOVITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.CERIUM),
																							element(EnumElements.MANGANESE),
																							element(EnumElements.ALUMINUM),
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.EUROPIUM),
																							element(EnumElements.LUTETIUM), 
																							element(EnumElements.THULIUM),
																							element(EnumElements.TERBIUM),
																							element(EnumElements.MAGNESIUM),
																							element(EnumElements.TITANIUM),
																							element(EnumElements.CHROMIUM), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(14,12,9,5,5,4,4,4,4,2,1,1,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.CAVANSITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.VANADIUM), 
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(25,11,9)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.PHENAKITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.BERYLLIUM)), 
																			Arrays.asList(25,16)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.CALDERITE), 
																			Arrays.asList(	element(EnumElements.MANGANESE), 
																							element(EnumElements.IRON), 
																							element(EnumElements.SILICON),
																							element(EnumElements.CALCIUM),
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(23,16,16,6,2)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.HUTTONITE), 
																			Arrays.asList(	element(EnumElements.THORIUM), 
																							element(EnumElements.SILICON)), 
																			Arrays.asList(71,9)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.ZIRCON), 
																			Arrays.asList(	element(EnumElements.ZIRCONIUM), 
																							element(EnumElements.SILICON),
																							element(EnumElements.EUROPIUM),
																							element(EnumElements.PRASEODYMIUM),
																							element(EnumElements.SAMARIUM),
																							element(EnumElements.ERBIUM),
																							element(EnumElements.TERBIUM)), 
																			Arrays.asList(43,15,1,1,1,1,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.KELDYSHITE), 
																			Arrays.asList(	element(EnumElements.ZIRCONIUM), 
																							element(EnumElements.SILICON),
																							element(EnumElements.SODIUM)), 
																			Arrays.asList(28,17,10)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.FUCHSITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.ALUMINUM),
																							element(EnumElements.POTASSIUM),
																							element(EnumElements.CHROMIUM)), 
																			Arrays.asList(21,15,10,5)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SILICATE), silicate_stack(1, EnumSilicate.MUSCOVITE), 
																			Arrays.asList(	element(EnumElements.SILICON), 
																							element(EnumElements.ALUMINUM),
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(21,20,10)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.ALUNITE), 
																			Arrays.asList(	element(EnumElements.ALUMINUM), 
																							element(EnumElements.SULFUR),
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(20,15,10)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.FEDOTOVITE), 
																			Arrays.asList(	element(EnumElements.COPPER), 
																							element(EnumElements.SULFUR), 
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(33,17,14)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.JAROSITE), 
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.SULFUR),
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(33,13,8)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.GUARINOITE), 
																			Arrays.asList(	element(EnumElements.ZINC), 
																							element(EnumElements.COBALT), 
																							element(EnumElements.NICKEL), 
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(34,8,8,4)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.BENTORITE), 
																			Arrays.asList(	element(EnumElements.CALCIUM),
																							element(EnumElements.SULFUR), 
																							element(EnumElements.CHROMIUM), 
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(19,7,6,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.APLOWITE), 
																			Arrays.asList(	element(EnumElements.COBALT),
																							element(EnumElements.SULFUR),
																							element(EnumElements.MANGANESE),
																							element(EnumElements.NICKEL)),
																			Arrays.asList(16,14,7,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.BIEBERITE), 
																			Arrays.asList(	element(EnumElements.COBALT), 
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(21,11)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.SCHEELITE), 
																			Arrays.asList(	element(EnumElements.TUNGSTEN), 
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(64,14)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.STOLZITE), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.TUNGSTEN)), 
																			Arrays.asList(46,40)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.KAMCHATKITE), 
																			Arrays.asList(	element(EnumElements.COPPER), 
																							element(EnumElements.SULFUR), 
																							element(EnumElements.POTASSIUM)), 
																			Arrays.asList(40,13,8)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.CHILUITE), 
																			Arrays.asList(	element(EnumElements.BISMUTH), 
																							element(EnumElements.TELLURIUM), 
																							element(EnumElements.MOLYBDENUM)), 
																			Arrays.asList(61,12,10)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.POWELLITE), 
																			Arrays.asList(	element(EnumElements.MOLYBDENUM), 
																							element(EnumElements.CALCIUM)), 
																			Arrays.asList(48,20)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFATE), sulfate_stack(1, EnumSulfate.SEDOVITE), 
																			Arrays.asList(	element(EnumElements.YELLOWCAKE), 
																							element(EnumElements.MOLYBDENUM)), 
																			Arrays.asList(43,34)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.ABRAMOVITE), 
																			Arrays.asList(	element(EnumElements.LEAD),
																							element(EnumElements.SULFUR), 
																							element(EnumElements.BISMUTH), 
																							element(EnumElements.TIN)),
																			Arrays.asList(37,21,17,12)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.AIKINITE), 
																			Arrays.asList(	element(EnumElements.BISMUTH),
																							element(EnumElements.LEAD),
																							element(EnumElements.SULFUR), 
																							element(EnumElements.COPPER)), 
																			Arrays.asList(36,36,17,11)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.BALKANITE),
																			Arrays.asList(	element(EnumElements.BISMUTH),
																							element(EnumElements.SILVER),
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(36,35,16)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.GALENA), 
																			Arrays.asList(	element(EnumElements.LEAD), 
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(88,12)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.KESTERITE), 
																			Arrays.asList(	element(EnumElements.TIN), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.SULFUR), 
																							element(EnumElements.ZINC), 
																							element(EnumElements.IRON)),
																			Arrays.asList(32,27,27,10,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.PENTLANDITE),
																			Arrays.asList(	element(EnumElements.NICKEL), 
																							element(EnumElements.IRON),
																							element(EnumElements.SULFUR)),
																			Arrays.asList(37,33,33)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.PYRITE), 
																			Arrays.asList(	element(EnumElements.SULFUR), 
																							element(EnumElements.IRON)), 
																			Arrays.asList(53,47)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.STANNITE), 
																			Arrays.asList(	element(EnumElements.SULFUR),
																							element(EnumElements.COPPER), 
																							element(EnumElements.TIN), 
																							element(EnumElements.IRON),
																							element(EnumElements.ZINC)), 
																			Arrays.asList(30,30,28,10,2)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.VALLERIITE), 
																			Arrays.asList(	element(EnumElements.IRON), 
																							element(EnumElements.SULFUR), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.ALUMINUM)), 
																			Arrays.asList(26,26,24,9,7)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.SPHALERITE), 
																			Arrays.asList(	element(EnumElements.ZINC), 
																							element(EnumElements.SULFUR),
																							element(EnumElements.IRON)), 
																			Arrays.asList(64,33,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.PETRUKITE), 
																			Arrays.asList(	element(EnumElements.SULFUR),
																							element(EnumElements.TIN), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.IRON), 
																							element(EnumElements.ZINC), 
																							element(EnumElements.SILVER)),
																			Arrays.asList(34,25,20,9,4,3)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.MAWSONITE), 
																			Arrays.asList(	element(EnumElements.COPPER),
																							element(EnumElements.SULFUR), 
																							element(EnumElements.TIN),
																							element(EnumElements.IRON)),
																			Arrays.asList(44,29,14,13)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.TUNGSTENITE), 
																			Arrays.asList(	element(EnumElements.TUNGSTEN),
																							element(EnumElements.SULFUR)), 
																			Arrays.asList(74,26)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.ERLICHMANITE),
																			Arrays.asList(	element(EnumElements.OSMIUM),
																							element(EnumElements.SULFUR)),
																			Arrays.asList(75,25)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.MALANITE),
																			Arrays.asList(	element(EnumElements.PLATINUM),
																							element(EnumElements.SULFUR),
																							element(EnumElements.IRIDIUM),
																							element(EnumElements.COPPER)), 
																			Arrays.asList(50,22,17,11)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.GREENOCKITE),
																			Arrays.asList(	element(EnumElements.CADMIUM),
																							element(EnumElements.SULFUR)),
																			Arrays.asList(78,22)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.CERNYITE), 
																			Arrays.asList(	element(EnumElements.SULFUR), 
																							element(EnumElements.COPPER), 
																							element(EnumElements.TIN), 
																							element(EnumElements.CADMIUM)),
																			Arrays.asList(27,26,24,23)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.SULVANITE),
																			Arrays.asList(	element(EnumElements.COPPER),
																							element(EnumElements.SULFUR),
																							element(EnumElements.VANADIUM)), 
																			Arrays.asList(51,35,14)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.PATRONITE), 
																			Arrays.asList(	element(EnumElements.SULFUR), 
																							element(EnumElements.VANADIUM)), 
																			Arrays.asList(71,28)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.INAGLYITE), 
																			Arrays.asList(	element(EnumElements.IRIDIUM), 
																							element(EnumElements.SULFUR),
																							element(EnumElements.PLATINUM),
																							element(EnumElements.LEAD),
																							element(EnumElements.COPPER)), 
																			Arrays.asList(47,21,16,8,7)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.MELONITE), 
																			Arrays.asList(	element(EnumElements.TELLURIUM), 
																							element(EnumElements.NICKEL)), 
																			Arrays.asList(47,21,16,8,7)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.VAVRINITE), 
																			Arrays.asList(	element(EnumElements.TELLURIUM), 
																							element(EnumElements.ANTIMONY),
																							element(EnumElements.NICKEL),
																							element(EnumElements.IRON),
																							element(EnumElements.BISMUTH)), 
																			Arrays.asList(50,24,23,1,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.MASLOVITE), 
																			Arrays.asList(	element(EnumElements.BISMUTH), 
																							element(EnumElements.PLATINUM), 
																							element(EnumElements.TELLURIUM)),
																			Arrays.asList(39,37,24)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.SULFIDE), sulfide_stack(1, EnumSulfide.JORDISITE), 
																			Arrays.asList(	element(EnumElements.MOLYBDENUM), 
																							element(EnumElements.SULFUR)),
																			Arrays.asList(60,40)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.VANADATE), vanadate_stack(1, EnumVanadate.LASALITE), 
																			Arrays.asList(	element(EnumElements.VANADIUM), 
																							element(EnumElements.MAGNESIUM), 
																							element(EnumElements.SODIUM),
																							element(EnumElements.CALCIUM), 
																							element(EnumElements.SULFUR),
																							element(EnumElements.POTASSIUM)),
																			Arrays.asList(35,3,3,1,1,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.VANADATE), vanadate_stack(1, EnumVanadate.VANOXITE),
																			Arrays.asList(	element(EnumElements.VANADIUM)), 
																			Arrays.asList(46)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.VANADATE), vanadate_stack(1, EnumVanadate.DUGGANITE),
																			Arrays.asList(	element(EnumElements.LEAD),
																							element(EnumElements.ZINC),
																							element(EnumElements.TELLURIUM),
																							element(EnumElements.ARSENIC),
																							element(EnumElements.VANADIUM),
																							element(EnumElements.SILICON)), 
																			Arrays.asList(48,15,10,7,2,1)));

		extractor_recipes.add(new ChemicalExtractorRecipe(getText(EnumMinerals.VANADATE), vanadate_stack(1, EnumVanadate.ALVANITE),
																			Arrays.asList(	element(EnumElements.ALUMINUM),
																							element(EnumElements.VANADIUM),
																							element(EnumElements.ZINC),
																							element(EnumElements.NICKEL)), 
																			Arrays.asList(18,17,8,2)));
	}

}