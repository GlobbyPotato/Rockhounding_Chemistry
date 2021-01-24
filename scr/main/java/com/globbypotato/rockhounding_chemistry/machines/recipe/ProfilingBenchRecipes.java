package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyPart;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ProfilingBenchRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class ProfilingBenchRecipes extends BaseRecipes {
	public static ArrayList<ProfilingBenchRecipe> profiling_bench_recipes = new ArrayList<ProfilingBenchRecipe>();

	public static void machineRecipes() {
		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotIron", 												
																crushing_gear,
																EnumCasting.GEAR.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotIron", 	
																misc_items(2,  EnumMiscItems.IRON_COIL), 
																EnumCasting.COIL.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotNimonic", 	
																misc_items(2,  EnumMiscItems.NIMONIC_COIL), 
																EnumCasting.COIL.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"blockGlass", 
																misc_items(4,  EnumMiscItems.GLASS_PIPE), 
																EnumCasting.COIL.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotIron", 	
																misc_items(4,  EnumMiscItems.IRON_ROD), 		
																EnumCasting.ROD.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotHastelloy", 	
																misc_items(4,  EnumMiscItems.HASTELLOY_ROD), 		
																EnumCasting.ROD.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotCopper", 	
																misc_items(4,  EnumMiscItems.COPPER_ROD), 		
																EnumCasting.ROD.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotNib", 	
																misc_items(4,  EnumMiscItems.NIB_ROD), 		
																EnumCasting.ROD.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotCosm", 	
																misc_items(4,  EnumMiscItems.COSM_ROD), 		
																EnumCasting.ROD.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotNichrome", 
																misc_items(4,  EnumMiscItems.NICHROME_ROD), 	
																EnumCasting.ROD.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotIron",
																misc_items(8,  EnumMiscItems.IRON_FOIL), 	
																EnumCasting.FOIL.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotAluminum",
																misc_items(8,  EnumMiscItems.ALUMINUM_FOIL), 	
																EnumCasting.FOIL.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotStellite",
																misc_items(8,  EnumMiscItems.STELLITE_FOIL), 	
																EnumCasting.FOIL.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotIron", 
																misc_items(2,  EnumMiscItems.IRON_ARM), 
																EnumCasting.ARM.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"blockIron", 	
																misc_items(20, EnumMiscItems.IRON_CASING), 		
																EnumCasting.CASING.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"blockHydronalium", 
																misc_items(20, EnumMiscItems.HYDRONALIUM_CASING), 
																EnumCasting.CASING.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"blockHastelloy", 	
																misc_items(20, EnumMiscItems.HASTELLOY_CASING), 	
																EnumCasting.CASING.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"blockNimonic",
																misc_items(20, EnumMiscItems.NIMONIC_CASING), 	
																EnumCasting.CASING.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotIron", 		
																fe_catalyst, 	
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotMolybdenum", 		
																mo_catalyst, 	
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotGraphite", 
																gr_catalyst, 			
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotVanadium", 
																va_catalyst, 		
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotPlatinum", 
																pt_catalyst, 				
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotWhitegold", 		
																wg_catalyst, 			
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotNial", 		
																nl_catalyst,			
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotGold", 		
																au_catalyst, 			
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotNickel", 		
																ni_catalyst, 			
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotOsmium", 		
																os_catalyst, 	
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotZeolite", 
																ze_catalyst, 	
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotZinc", 	
																zn_catalyst, 	
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotCobalt", 	
																co_catalyst, 		
																EnumCasting.GAUZE.ordinal()));

		profiling_bench_recipes.add(new ProfilingBenchRecipe(	"ingotInconel", 	
																in_catalyst, 		
																EnumCasting.GAUZE.ordinal()));

		for(int x = 0; x < EnumAlloyPart.size(); x++){
			profiling_bench_recipes.add(new ProfilingBenchRecipe(	EnumAlloyPart.getBlock(x), 												
																	alloy_part_gear(2, EnumAlloyPart.values()[x]),
																	EnumCasting.GEAR.ordinal()));

			profiling_bench_recipes.add(new ProfilingBenchRecipe(	EnumAlloyPart.getIngot(x), 												
																	alloy_part_coin(3, EnumAlloyPart.values()[x]),
																	EnumCasting.COIN.ordinal()));

			profiling_bench_recipes.add(new ProfilingBenchRecipe(	EnumAlloyPart.getIngot(x), 												
																	alloy_part_plate(1, EnumAlloyPart.values()[x]),
																	EnumCasting.PLATE.ordinal()));
		}

	}

}