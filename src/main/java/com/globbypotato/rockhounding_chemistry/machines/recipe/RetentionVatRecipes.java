package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
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
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class RetentionVatRecipes extends BaseRecipes{
	public static ArrayList<RetentionVatRecipe> retention_vat_recipes = new ArrayList<RetentionVatRecipe>();

	public static void machineRecipes(){
		retention_vat_recipes.add(new RetentionVatRecipe(getFluid(EnumFluid.LEACHATE, 300), 
									Arrays.asList(	
													antimonate_stack(1, EnumAntimonate.CAMEROLAITE),
													arsenate_stack(1, EnumArsenate.ZALESIITE),
													borate_stack(1, EnumBorate.TUSIONITE),
													carbonate_stack(1, EnumCarbonate.PARISITE),
													chromate_stack(1, EnumChromate.FORNACITE),
													native_stack(1, EnumNative.PERRYITE),
													oxide_stack(1, EnumOxide.MCCONNELLITE),
													oxide_stack(1, EnumOxide.HIBONITE),
													sulfate_stack(1, EnumSulfate.GUARINOITE),
													sulfate_stack(1, EnumSulfate.CHILUITE),
													sulfide_stack(1, EnumSulfide.ABRAMOVITE),
													sulfide_stack(1, EnumSulfide.SPHALERITE), 
													sulfide_stack(1, EnumSulfide.MAWSONITE),
													oxide_stack(1, EnumOxide.ZIRKELITE),
													sulfide_stack(1, EnumSulfide.VAVRINITE)
									),

									Arrays.asList(	
													3.10F,
											 		3.49F,
											 		4.73F,
													4.36F,
													6.27F, 
											 		7.88F,
											 		5.49F,
											 		3.84F,
											 		2.80F,
											 		3.65F,
											 		9.00F,
											 		4.05F,
											 		4.66F,
											 		4.70F,
											 		7.80F
									)));

		retention_vat_recipes.add(new RetentionVatRecipe(getFluid(EnumFluid.HIGH_LEACHATE, 200), 
									Arrays.asList(	
													arsenate_stack(1, EnumArsenate.AGARDITE), 
													carbonate_stack(1, EnumCarbonate.ANKERITE),
													carbonate_stack(1, EnumCarbonate.OTAVITE), 
													chromate_stack(1, EnumChromate.MACQUARTITE),
													native_stack(1, EnumNative.COHENITE),
													oxide_stack(1, EnumOxide.COCHROMITE),
													oxide_stack(1, EnumOxide.SAMARSKITE),
													phosphate_stack(1, EnumPhosphate.MONAZITE),
													silicate_stack(1, EnumSilicate.GADOLINITE), 
													silicate_stack(1, EnumSilicate.JERVISITE),
													silicate_stack(1, EnumSilicate.MOSKVINITE),
													silicate_stack(1, EnumSilicate.KHRISTOVITE),
													sulfate_stack(1, EnumSulfate.SEDOVITE),
													vanadate_stack(1, EnumVanadate.DUGGANITE),
													silicate_stack(1, EnumSilicate.FUCHSITE),
													silicate_stack(1, EnumSilicate.MUSCOVITE)
									),

									Arrays.asList(	
													3.72F, 
											 		3.05F,
													5.03F, 
													5.49F,
											 		7.42F,
											 		5.22F,
											 		5.69F,
											 		5.15F,
											 		4.20F,
											 		3.22F,
											 		2.91F,
											 		4.08F,
											 		4.20F,
											 		6.30F,
											 		2.85F,
											 		2.93F
									)));

		retention_vat_recipes.add(new RetentionVatRecipe(getFluid(EnumFluid.LOW_LEACHATE, 400), 
									Arrays.asList(	
													antimonate_stack(1, EnumAntimonate.PARTZITE),
													arsenate_stack(1, EnumArsenate.SCHULTENITE),
													arsenate_stack(1, EnumArsenate.MIXITE),
													halide_stack(1, EnumHalide.GRICEITE),
													oxide_stack(1, EnumOxide.EUXENITE),
													oxide_stack(1, EnumOxide.TAPIOLITE),
													oxide_stack(1, EnumOxide.MONTEPONITE),
													oxide_stack(1, EnumOxide.BIEHLITE),
													oxide_stack(1, EnumOxide.MOURITE),
													phosphate_stack(1, EnumPhosphate.KOSNARITE),
													phosphate_stack(1, EnumPhosphate.KEYITE),
													silicate_stack(1, EnumSilicate.KELDYSHITE),
													sulfate_stack(1, EnumSulfate.KAMCHATKITE),
													sulfide_stack(1, EnumSulfide.MASLOVITE),
													sulfide_stack(1, EnumSulfide.JORDISITE)
									),

									Arrays.asList(	
													5.95F,
											 		5.94F,
											 		3.80F,
													2.62F,
											 		4.84F,
											 		7.82F,
											 		8.14F,
											 		5.24F,
											 		4.23F,
											 		3.19F,
											 		4.95F,
											 		3.30F,
											 		3.48F,
											 		11.0F,
											 		4.97F
									)));
	}

}