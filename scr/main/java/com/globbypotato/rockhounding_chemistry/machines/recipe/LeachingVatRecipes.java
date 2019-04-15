package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.EnumMinerals;
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
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class LeachingVatRecipes extends BaseRecipes{
	public static ArrayList<LeachingVatRecipe> leaching_vat_recipes = new ArrayList<LeachingVatRecipe>();

	public static void machineRecipes(){
		//antimonate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.ANTIMONATE), 
																		Arrays.asList(	antimonate_stack(1, EnumAntimonate.BAHIANITE),
																						antimonate_stack(1, EnumAntimonate.TRIPUHYITE),
																						antimonate_stack(1, EnumAntimonate.PARWELITE),
																						antimonate_stack(1, EnumAntimonate.ORDONEZITE)), 
																		Arrays.asList(5.17F, 5.82F, 4.62F, 6.63F), getFluid(EnumFluid.LEACHATE, 1000)));
		//arsenate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.ARSENATE), 
																		Arrays.asList(	arsenate_stack(1, EnumArsenate.PITTICITE)), 
																		Arrays.asList(2.5F), getFluid(EnumFluid.LEACHATE, 1000)));
		//borate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.BORATE), 
																		Arrays.asList(	borate_stack(1, EnumBorate.BORAX), 
																						borate_stack(1, EnumBorate.ERICAITE), 
																						borate_stack(1, EnumBorate.HULSITE), 
																						borate_stack(1, EnumBorate.LONDONITE), 
																						borate_stack(1, EnumBorate.RHODIZITE)), 
																		Arrays.asList(1.71F, 3.22F, 4.3F, 3.34F, 3.44F), getFluid(EnumFluid.LEACHATE, 1000)));
		//carbonate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.CARBONATE), 
																		Arrays.asList(	carbonate_stack(1, EnumCarbonate.GASPEITE), 
																						carbonate_stack(1, EnumCarbonate.ROSASITE), 
																						carbonate_stack(1, EnumCarbonate.SMITHSONITE), 
																						carbonate_stack(1, EnumCarbonate.HUNTITE)), 
																		Arrays.asList(3.71F, 4.06F, 4.45F, 2.69F), getFluid(EnumFluid.LEACHATE, 1000)));
		//chromate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.CHROMATE), 
																		Arrays.asList(	chromate_stack(1, EnumChromate.LOPEZITE),
																						chromate_stack(1, EnumChromate.CROCOITE),
																						chromate_stack(1, EnumChromate.CHROMATITE), 
																						chromate_stack(1, EnumChromate.IRANITE)), 
																		Arrays.asList(2.69F, 0.6F, 3.142F, 5.8F), getFluid(EnumFluid.LEACHATE, 1000)));
		//halide vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.HALIDE), 
																		Arrays.asList(	halide_stack(1, EnumHalide.BOLEITE), 
																						halide_stack(1, EnumHalide.CARNALLITE), 
																						halide_stack(1, EnumHalide.RINNEITE), 
																						halide_stack(1, EnumHalide.HEKLAITE), 
																						halide_stack(1, EnumHalide.CREEDITE)), 
																		Arrays.asList(4.94F, 1.6F, 2.3F, 2.69F, 2.71F), getFluid(EnumFluid.LEACHATE, 1000)));
		//native vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.NATIVE),
																		Arrays.asList(	native_stack(1, EnumNative.CUPALITE), 
																						native_stack(1, EnumNative.HAXONITE), 
																						native_stack(1, EnumNative.TULAMEENITE), 
																						native_stack(1, EnumNative.NIGGLIITE), 
																						native_stack(1, EnumNative.MALDONITE), 
																						native_stack(1, EnumNative.AURICUPRIDE), 
																						native_stack(1, EnumNative.KHATYRKITE), 
																						native_stack(1, EnumNative.FULLERITE), 
																						native_stack(1, EnumNative.CHAOITE), 
																						native_stack(1, EnumNative.GRAPHITE),
																						native_stack(1, EnumNative.OSMIRIDIUM)), 
																		Arrays.asList(5.12F, 7.7F, 14.9F, 13.44F, 15.46F, 11.50F, 4.42F, 1.95F, 3.38F, 2.16F, 22F), getFluid(EnumFluid.LEACHATE, 1000)));
		//oxide vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.OXIDE), 
																		Arrays.asList(	oxide_stack(1, EnumOxide.CHROMITE), 
																						oxide_stack(1, EnumOxide.COLUMBITE), 
																						oxide_stack(1, EnumOxide.SAMARSKITE), 
																						oxide_stack(1, EnumOxide.CASSITERITE), 
																						oxide_stack(1, EnumOxide.BOEHMITE),
																						oxide_stack(1, EnumOxide.GAHNITE),
																						oxide_stack(1, EnumOxide.SENAITE), 
																						oxide_stack(1, EnumOxide.THORUTITE),
																						oxide_stack(1, EnumOxide.IXIOLITE), 
																						oxide_stack(1, EnumOxide.BEHOITE), 
																						oxide_stack(1, EnumOxide.BROMELLITE), 
																						oxide_stack(1, EnumOxide.TUNGSTITE), 
																						oxide_stack(1, EnumOxide.WOLFRAMITE), 
																						oxide_stack(1, EnumOxide.FERBERITE), 
																						oxide_stack(1, EnumOxide.LOPARITE), 
																						oxide_stack(1, EnumOxide.BUNSENITE), 
																						oxide_stack(1, EnumOxide.RUTILE), 
																						oxide_stack(1, EnumOxide.URANINITE),
																						oxide_stack(1, EnumOxide.ZIRKELITE),
																						oxide_stack(1, EnumOxide.KAMIOKITE)),
																		Arrays.asList(4.79F, 6.3F, 5.69F, 6.9F, 3.03F, 4.3F, 5.3F, 5.82F, 7.08F, 1.92F, 3.01F, 5.50F, 7.3F, 7.45F, 8.77F, 6.3F, 4.25F, 8.72F, 4.7F, 5.96F), getFluid(EnumFluid.LEACHATE, 1000)));
		//phosphate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.PHOSPHATE), 
																		Arrays.asList(	phosphate_stack(1, EnumPhosphate.FAUSTITE),
																						phosphate_stack(1, EnumPhosphate.LAZULITE), 
																						phosphate_stack(1, EnumPhosphate.SCHOONERITE),
																						phosphate_stack(1, EnumPhosphate.TRIPHYLITE), 
																						phosphate_stack(1, EnumPhosphate.WAVELLITE), 
																						phosphate_stack(1, EnumPhosphate.XENOTIME),
																						phosphate_stack(1, EnumPhosphate.ZAIRITE), 
																						phosphate_stack(1, EnumPhosphate.PRETULITE),
																						phosphate_stack(1, EnumPhosphate.TAVORITE), 
																						phosphate_stack(1, EnumPhosphate.KEYITE), 
																						phosphate_stack(1, EnumPhosphate.BIRCHITE), 
																						phosphate_stack(1, EnumPhosphate.ZIESITE),
																						phosphate_stack(1, EnumPhosphate.SCHODERITE)),
																		Arrays.asList(2.92F, 3.05F, 2.89F, 3.5F, 2.34F, 4.75F, 4.37F, 3.71F, 3.28F, 4.95F, 3.61F, 3.86F, 1.88F), getFluid(EnumFluid.LEACHATE, 1000)));
		//silicate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.SILICATE), 
																		Arrays.asList(	silicate_stack(1, EnumSilicate.AXINITE),
																						silicate_stack(1, EnumSilicate.BIOTITE), 
																						silicate_stack(1, EnumSilicate.MAGBASITE), 
																						silicate_stack(1, EnumSilicate.EUCRYPTITE), 
																						silicate_stack(1, EnumSilicate.STEACYITE),
																						silicate_stack(1, EnumSilicate.MANANDONITE),
																						silicate_stack(1, EnumSilicate.VISTEPITE), 
																						silicate_stack(1, EnumSilicate.CAVANSITE), 
																						silicate_stack(1, EnumSilicate.PHENAKITE),
																						silicate_stack(1, EnumSilicate.CALDERITE), 
																						silicate_stack(1, EnumSilicate.HUTTONITE),
																						silicate_stack(1, EnumSilicate.ZIRCON)), 
																		Arrays.asList(3.82F, 3.09F, 3.41F, 2.67F, 2.95F, 2.89F, 3.67F, 2.25F, 2.98F, 4.08F, 7.1F, 4.65F), getFluid(EnumFluid.LEACHATE, 1000)));
		//sulfate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.SULFATE),
																		Arrays.asList(	sulfate_stack(1, EnumSulfate.ALUNITE), 
																						sulfate_stack(1, EnumSulfate.FEDOTOVITE), 
																						sulfate_stack(1, EnumSulfate.JAROSITE), 
																						sulfate_stack(1, EnumSulfate.BENTORITE), 
																						sulfate_stack(1, EnumSulfate.APLOWITE),
																						sulfate_stack(1, EnumSulfate.BIEBERITE), 
																						sulfate_stack(1, EnumSulfate.SCHEELITE), 
																						sulfate_stack(1, EnumSulfate.STOLZITE),
																						sulfate_stack(1, EnumSulfate.POWELLITE)), 
																		Arrays.asList(2.74F, 3.2F, 3.09F, 2.02F, 2.33F, 1.9F, 6.01F, 8.05F, 4.34F), getFluid(EnumFluid.LEACHATE, 1000)));
		//sulfide vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.SULFIDE)
																		,Arrays.asList(	sulfide_stack(1, EnumSulfide.AIKINITE),
																						sulfide_stack(1, EnumSulfide.BALKANITE), 
																						sulfide_stack(1, EnumSulfide.GALENA),
																						sulfide_stack(1, EnumSulfide.KESTERITE), 
																						sulfide_stack(1, EnumSulfide.PENTLANDITE), 
																						sulfide_stack(1, EnumSulfide.PYRITE), 
																						sulfide_stack(1, EnumSulfide.STANNITE), 
																						sulfide_stack(1, EnumSulfide.VALLERIITE), 
																						sulfide_stack(1, EnumSulfide.PETRUKITE), 
																						sulfide_stack(1, EnumSulfide.TUNGSTENITE),
																						sulfide_stack(1, EnumSulfide.ERLICHMANITE),
																						sulfide_stack(1, EnumSulfide.MALANITE), 
																						sulfide_stack(1, EnumSulfide.GREENOCKITE),
																						sulfide_stack(1, EnumSulfide.CERNYITE), 
																						sulfide_stack(1, EnumSulfide.SULVANITE), 
																						sulfide_stack(1, EnumSulfide.PATRONITE),
																						sulfide_stack(1, EnumSulfide.INAGLYITE),
																						sulfide_stack(1, EnumSulfide.MELONITE)), 
																		Arrays.asList(6.44F, 6.31F, 7.4F, 4.56F, 4.8F, 5.01F, 4.4F, 3.11F, 4.61F, 7.68F, 9.59F, 7.4F, 4.49F, 4.77F, 4F, 2.82F, 5.79F, 7.3F), getFluid(EnumFluid.LEACHATE, 1000)));
		//vanadate vat 1
		leaching_vat_recipes.add(new LeachingVatRecipe(mineral_ores(1,EnumMinerals.VANADATE),
																		Arrays.asList(	vanadate_stack(1, EnumVanadate.LASALITE),
																						vanadate_stack(1, EnumVanadate.VANOXITE),
																						vanadate_stack(1, EnumVanadate.ALVANITE)), 
																		Arrays.asList(2.38F, 2.90F, 2.45F), getFluid(EnumFluid.LEACHATE, 1000)));
	}

}