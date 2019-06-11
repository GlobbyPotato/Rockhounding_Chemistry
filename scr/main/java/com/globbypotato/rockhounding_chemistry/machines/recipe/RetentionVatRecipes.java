package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
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
		retention_vat_recipes.add(new RetentionVatRecipe(getFluid(EnumFluid.LEACHATE, 200), 
									Arrays.asList(	
													antimonate_stack(1, EnumAntimonate.PARTZITE),
													antimonate_stack(1, EnumAntimonate.CAMEROLAITE),
													
													arsenate_stack(1, EnumArsenate.AGARDITE), 
													arsenate_stack(1, EnumArsenate.SCHULTENITE),
													arsenate_stack(1, EnumArsenate.ZALESIITE),
													arsenate_stack(1, EnumArsenate.MIXITE),

													borate_stack(1, EnumBorate.TUSIONITE),
													
													carbonate_stack(1, EnumCarbonate.ANKERITE),
													carbonate_stack(1, EnumCarbonate.PARISITE),
													carbonate_stack(1, EnumCarbonate.OTAVITE), 

													chromate_stack(1, EnumChromate.FORNACITE),
													chromate_stack(1, EnumChromate.MACQUARTITE),
													
													halide_stack(1, EnumHalide.GRICEITE),

													native_stack(1, EnumNative.COHENITE),
													native_stack(1, EnumNative.PERRYITE),

													oxide_stack(1, EnumOxide.COCHROMITE),
													oxide_stack(1, EnumOxide.EUXENITE),
													oxide_stack(1, EnumOxide.MCCONNELLITE),
													oxide_stack(1, EnumOxide.HIBONITE),
													oxide_stack(1, EnumOxide.TAPIOLITE),
													oxide_stack(1, EnumOxide.MONTEPONITE),
													oxide_stack(1, EnumOxide.BIEHLITE),
													oxide_stack(1, EnumOxide.MOURITE),
													
													phosphate_stack(1, EnumPhosphate.MONAZITE),
													phosphate_stack(1, EnumPhosphate.KOSNARITE),
													
													silicate_stack(1, EnumSilicate.GADOLINITE), 
													silicate_stack(1, EnumSilicate.JERVISITE),
													silicate_stack(1, EnumSilicate.MOSKVINITE),
													silicate_stack(1, EnumSilicate.KHRISTOVITE),
													silicate_stack(1, EnumSilicate.KELDYSHITE),
													
													sulfate_stack(1, EnumSulfate.GUARINOITE),
													sulfate_stack(1, EnumSulfate.KAMCHATKITE),
													sulfate_stack(1, EnumSulfate.CHILUITE),
													sulfate_stack(1, EnumSulfate.SEDOVITE),

													sulfide_stack(1, EnumSulfide.ABRAMOVITE),
													sulfide_stack(1, EnumSulfide.SPHALERITE), 
													sulfide_stack(1, EnumSulfide.MAWSONITE),
													sulfide_stack(1, EnumSulfide.VAVRINITE),
													sulfide_stack(1, EnumSulfide.MASLOVITE),
													sulfide_stack(1, EnumSulfide.JORDISITE),
													
													vanadate_stack(1, EnumVanadate.DUGGANITE)
													
									),
					
									Arrays.asList(	
													5.95F,
													3.10F,
													
													3.72F, 
											 		5.94F,
											 		3.49F,
											 		3.80F,
											 		
											 		4.73F,
											 		
											 		3.05F,
													4.36F,
													5.03F, 
													
													6.27F, 
													5.49F,
													
													2.62F,
													
											 		7.42F,
											 		7.88F,
											 		
											 		5.22F,
											 		4.84F,
											 		5.49F,
											 		3.84F,
											 		7.82F,
											 		8.14F,
											 		5.24F,
											 		4.23F,
											 		
											 		5.15F,
											 		3.19F,
											 		
											 		4.20F,
											 		3.22F,
											 		2.91F,
											 		4.08F,
											 		3.30F,
											 		
											 		2.80F,
											 		3.48F,
											 		3.65F,
											 		4.20F,
											 		
											 		9.00F,
											 		4.05F,
											 		4.66F,
											 		7.80F,
											 		11.0F,
											 		4.97F,
											 		
											 		6.3F
											 		
									)));
	}

}