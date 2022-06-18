package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesC;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesE;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesF;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumStructure;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PlanningTableRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class PlanningTableRecipes extends BaseRecipes{
	public static ArrayList<PlanningTableRecipe> planning_table_recipes = new ArrayList<PlanningTableRecipe>();

	public static void machineRecipes() {
		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.OVEN.ordinal(),
															Arrays.asList(	machines_a(1, EnumMachinesA.LAB_OVEN_CHAMBER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_f(4, EnumMachinesF.FLUID_ROUTER),
																			blocks_a(1, EnumMiscBlocksA.SEPARATOR),
																			machines_d(2, EnumMachinesD.FLOTATION_TANK), 		
																			machines_e(2, EnumMachinesE.BUFFER_TANK),
																			machines_b(2, EnumMachinesB.AUXILIARY_ENGINE), 		
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.EXTRACTOR.ordinal(),
															Arrays.asList(	machines_c(1, EnumMachinesC.EXTRACTOR_CONTROLLER),
																			machines_c(1, EnumMachinesC.EXTRACTOR_REACTOR),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_c(1, EnumMachinesC.EXTRACTOR_GLASSWARE),
																			machines_c(1, EnumMachinesC.EXTRACTOR_STABILIZER),
																			machines_d(2, EnumMachinesD.FLOTATION_TANK), 		
																			blocks_a(1, EnumMiscBlocksA.EXTRACTOR_CHARGER),
																			blocks_a(2, EnumMiscBlocksA.SEPARATOR),
																			machines_f(2, EnumMachinesF.FLUID_ROUTER),
																			machines_b(1, EnumMachinesB.AUXILIARY_ENGINE), 		
																			machines_a(2, EnumMachinesA.CENTRIFUGE),
																			machines_c(1, EnumMachinesC.ELEMENTS_CABINET_BASE),
																			machines_d(1, EnumMachinesD.MATERIAL_CABINET_BASE),
																			machines_c(2, EnumMachinesC.EXTRACTOR_BALANCE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.PRECIPITATOR.ordinal(),
															Arrays.asList(	machines_e(1, EnumMachinesE.PRECIPITATION_CHAMBER),
																			machines_e(2, EnumMachinesE.PRECIPITATION_REACTOR),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_d(1, EnumMachinesD.FLOTATION_TANK), 		
																			machines_e(1, EnumMachinesE.BUFFER_TANK),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.LEACHING.ordinal(),
															Arrays.asList(	machines_c(1, EnumMachinesC.LEACHING_VAT_CONTROLLER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_c(3, EnumMachinesC.LEACHING_VAT_TANK),
																			machines_c(1, EnumMachinesC.SPECIMEN_COLLECTOR),
																			machines_e(1, EnumMachinesE.BUFFER_TANK),
																			machines_a(2, EnumMachinesA.CENTRIFUGE),
																			machines_d(2, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.RETENTION.ordinal(),
															Arrays.asList(	machines_c(1, EnumMachinesC.RETENTION_VAT_CONTROLLER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_c(2, EnumMachinesC.LEACHING_VAT_TANK),
																			machines_c(1, EnumMachinesC.SPECIMEN_COLLECTOR),
																			machines_d(1, EnumMachinesD.FLOTATION_TANK), 		
																			machines_e(1, EnumMachinesE.BUFFER_TANK),
																			machines_a(1, EnumMachinesA.CENTRIFUGE),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.CONDENSER.ordinal(),
															Arrays.asList(	machines_c(1, EnumMachinesC.GAS_CONDENSER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_e(1, EnumMachinesE.BUFFER_TANK),
																			machines_a(1, EnumMachinesA.CENTRIFUGE),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.EXPANDER.ordinal(),
															Arrays.asList(	machines_a(1, EnumMachinesA.GAS_EXPANDER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_d(1, EnumMachinesD.FLOTATION_TANK), 		
																			machines_a(1, EnumMachinesA.CENTRIFUGE),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.BLENDER.ordinal(),
															Arrays.asList(	machines_a(1, EnumMachinesA.LAB_BLENDER_CONTROLLER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.MIXER.ordinal(),
															Arrays.asList(	machines_e(1, EnumMachinesE.POWDER_MIXER_CONTROLLER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_a(2, EnumMachinesA.CENTRIFUGE),
																			machines_c(1, EnumMachinesC.ELEMENTS_CABINET_BASE),
																			machines_d(1, EnumMachinesD.MATERIAL_CABINET_BASE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.COMPRESSOR.ordinal(),
															Arrays.asList(	machines_b(1, EnumMachinesB.AIR_COMPRESSOR),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.PROFILING.ordinal(),
															Arrays.asList(	machines_a(1, EnumMachinesA.PROFILING_BENCH),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.POND.ordinal(),
															Arrays.asList(	machines_b(1, EnumMachinesB.SLURRY_POND),
																			machines_d(1, EnumMachinesD.FLOTATION_TANK),
																			machines_e(1, EnumMachinesE.WASHING_TANK))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.ALLOYER.ordinal(),
															Arrays.asList(	machines_d(1, EnumMachinesD.METAL_ALLOYER),
																			machines_b(1, EnumMachinesB.FLUID_CISTERN),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			blocks_a(1, EnumMiscBlocksA.SEPARATOR),
																			machines_a(3, EnumMachinesA.CENTRIFUGE),
																			machines_c(1, EnumMachinesC.ELEMENTS_CABINET_BASE),
																			machines_d(1, EnumMachinesD.MATERIAL_CABINET_BASE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.ORBITER.ordinal(),
															Arrays.asList(	machines_d(1, EnumMachinesD.ORBITER),
																			machines_e(1, EnumMachinesE.WASHING_TANK),
																			machines_b(1, EnumMachinesB.FLUID_CISTERN))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.GASIFIER.ordinal(),
															Arrays.asList(	machines_b(1, EnumMachinesB.GASIFIER_CONTROLLER),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_b(1, EnumMachinesB.REINFORCED_CISTERN),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_b(1, EnumMachinesB.FLUID_CISTERN),
																			machines_a(2, EnumMachinesA.CENTRIFUGE),
																			machines_d(1, EnumMachinesD.MATERIAL_CABINET_BASE),
																			machines_c(1, EnumMachinesC.EXTRACTOR_BALANCE),
																			machines_b(1, EnumMachinesB.AUXILIARY_ENGINE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.SIZER.ordinal(),
															Arrays.asList(	machines_f(1, EnumMachinesF.SIZER_TRANSMISSION),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_a(4, EnumMachinesA.SIZER_TANK),
																			machines_a(1, EnumMachinesA.SIZER_CABINET),
																			machines_a(1, EnumMachinesA.SIZER_COLLECTOR),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.EXCHANGER.ordinal(),
															Arrays.asList(	machines_b(1, EnumMachinesB.HEAT_EXCHANGER_BASE),
																			machines_b(1, EnumMachinesB.CYCLONE_SEPARATOR_BASE),
																			machines_b(1, EnumMachinesB.CYCLONE_SEPARATOR_CAP),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_b(1, EnumMachinesB.FLUID_CISTERN),
																			machines_d(2, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(2, EnumMachinesA.CENTRIFUGE),
																			machines_b(2, EnumMachinesB.AUXILIARY_ENGINE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.CSTR.ordinal(),
															Arrays.asList(	machines_e(1, EnumMachinesE.STIRRED_TANK_BASE),
																			machines_e(1, EnumMachinesE.STIRRED_TANK_OUT),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			blocks_a(1, EnumMiscBlocksA.EXTRACTOR_CHARGER),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_d(2, EnumMachinesD.FLOTATION_TANK),
																			machines_f(2, EnumMachinesF.FLUID_ROUTER),
																			machines_a(1, EnumMachinesA.CENTRIFUGE),
																			machines_b(1, EnumMachinesB.AUXILIARY_ENGINE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.PULLING.ordinal(),
															Arrays.asList(	machines_d(1, EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER),
																			blocks_a(1, EnumMiscBlocksA.PULLING_CRUCIBLE_CAP),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			blocks_a(1, EnumMiscBlocksA.EXTRACTOR_CHARGER),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(1, EnumMachinesA.CENTRIFUGE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_b(1, EnumMachinesB.AUXILIARY_ENGINE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.SHREDDER.ordinal(),
															Arrays.asList(	machines_f(1, EnumMachinesF.SHREDDER_BASE),
																			machines_f(3, EnumMachinesF.SHREDDER_TABLE),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_b(1, EnumMachinesB.FLUID_CISTERN),
																			machines_e(1, EnumMachinesE.WASHING_TANK),
																			machines_d(1, EnumMachinesD.MATERIAL_CABINET_BASE),
																			machines_a(1, EnumMachinesA.CENTRIFUGE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_c(1, EnumMachinesC.EXTRACTOR_BALANCE),
																			machines_b(2, EnumMachinesB.AUXILIARY_ENGINE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.DEPOSITION.ordinal(),
															Arrays.asList(	machines_d(1, EnumMachinesD.DEPOSITION_CHAMBER_BASE),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			blocks_a(2, EnumMiscBlocksA.SEPARATOR),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_d(3, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(3, EnumMachinesA.CENTRIFUGE),
																			machines_b(2, EnumMachinesB.AUXILIARY_ENGINE),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.PURIFIER.ordinal(),
															Arrays.asList(	machines_b(1, EnumMachinesB.GAS_PURIFIER),
																			machines_b(1, EnumMachinesB.CYCLONE_SEPARATOR_BASE),
																			machines_b(1, EnumMachinesB.CYCLONE_SEPARATOR_CAP),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_d(2, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(3, EnumMachinesA.CENTRIFUGE),
																			blocks_a(1, EnumMiscBlocksA.SEPARATOR),
																			machines_b(3, EnumMachinesB.AUXILIARY_ENGINE),
																			machines_c(1, EnumMachinesC.EXTRACTOR_BALANCE),
																			machines_d(1, EnumMachinesD.MATERIAL_CABINET_BASE))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.REFORMER.ordinal(),
															Arrays.asList(	machines_c(1, EnumMachinesC.REFORMER_CONTROLLER),
																			blocks_a(3, EnumMiscBlocksA.REFORMER_TOWER),
																			blocks_a(1, EnumMiscBlocksA.REFORMER_TOWER_TOP),
																			machines_b(1, EnumMachinesB.CYCLONE_SEPARATOR_BASE),
																			machines_b(1, EnumMachinesB.CYCLONE_SEPARATOR_CAP),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_d(4, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(4, EnumMachinesA.CENTRIFUGE),
																			blocks_a(2, EnumMiscBlocksA.SEPARATOR),
																			blocks_a(2, EnumMiscBlocksA.GAS_ROUTER),
																			machines_b(6, EnumMachinesB.AUXILIARY_ENGINE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_e(1, EnumMachinesE.BUFFER_TANK),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.GAN.ordinal(),
															Arrays.asList(	machines_c(1, EnumMachinesC.GAN_CONTROLLER),
																			blocks_a(2, EnumMiscBlocksA.GAN_INJECTOR),
																			blocks_a(8, EnumMiscBlocksA.GAN_TOWER),
																			blocks_a(2, EnumMiscBlocksA.GAN_TOWER_TOP),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_b(1, EnumMachinesB.GAN_TURBOEXPANDER_BASE),
																			machines_d(3, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(3, EnumMachinesA.CENTRIFUGE),
																			blocks_a(2, EnumMiscBlocksA.SEPARATOR),
																			machines_b(5, EnumMachinesB.AUXILIARY_ENGINE),
																			machines_c(1, EnumMachinesC.MULTIVESSEL))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.TUBULAR.ordinal(),
															Arrays.asList(	machines_f(1, EnumMachinesF.TUBULAR_BED_TANK),
																			machines_f(1, EnumMachinesF.TUBULAR_BED_BASE),
																			machines_f(1, EnumMachinesF.TUBULAR_BED_MID),
																			machines_a(1, EnumMachinesA.POWER_GENERATOR),
																			machines_d(6, EnumMachinesD.GAS_HOLDER_BASE),
																			blocks_a(2, EnumMiscBlocksA.GAS_ROUTER),
																			machines_a(6, EnumMachinesA.CENTRIFUGE),
																			blocks_a(2, EnumMiscBlocksA.SEPARATOR),
																			machines_b(3, EnumMachinesB.AUXILIARY_ENGINE),
																			machines_a(1, EnumMachinesA.UNLOADER),
																			machines_a(1, EnumMachinesA.SERVER))));

		planning_table_recipes.add(new PlanningTableRecipe(	EnumStructure.REGEN.ordinal(),
															Arrays.asList(	machines_e(1, EnumMachinesE.CATALYST_REGEN),
																			machines_b(1, EnumMachinesB.FLUID_CISTERN),
																			machines_d(1, EnumMachinesD.GAS_HOLDER_BASE),
																			machines_a(1, EnumMachinesA.CENTRIFUGE))));

		
		
	}
}