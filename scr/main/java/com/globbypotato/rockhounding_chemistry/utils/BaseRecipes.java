package com.globbypotato.rockhounding_chemistry.utils;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyPart;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumMetalItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMobItems;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesC;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesE;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
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
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;

public class BaseRecipes {
	public static ItemStack mineral_ores(int num, EnumMinerals meta){	return new ItemStack(ModBlocks.MINERAL_ORES, num, meta.ordinal());}
	public static ItemStack patterns(int num, EnumCasting meta) {		return new ItemStack(ModItems.PATTERN_ITEMS, num, meta.ordinal());}
	public static ItemStack chemicals(int num, EnumChemicals meta) {	return new ItemStack(ModItems.CHEMICAL_ITEMS, num, meta.ordinal());}
	public static ItemStack elements(int num, EnumElements meta) {		return new ItemStack(ModItems.CHEMICAL_DUSTS, num, meta.ordinal());}
	public static ItemStack misc_items(int num, EnumMiscItems meta) {	return new ItemStack(ModItems.MISC_ITEMS, num, meta.ordinal());}
	public static ItemStack mob_items(int num, EnumMobItems meta) {		return new ItemStack(ModItems.MOB_ITEMS, num, meta.ordinal());}
	public static ItemStack blocks_a(int num, EnumMiscBlocksA meta) {	return new ItemStack(ModBlocks.MISC_BLOCKS_A, num, meta.ordinal());}
	public static ItemStack machines_a(int num, EnumMachinesA meta) {	return new ItemStack(ModBlocks.MACHINES_A, num, meta.ordinal());}
	public static ItemStack machines_b(int num, EnumMachinesB meta) {	return new ItemStack(ModBlocks.MACHINES_B, num, meta.ordinal());}
	public static ItemStack machines_c(int num, EnumMachinesC meta) {	return new ItemStack(ModBlocks.MACHINES_C, num, meta.ordinal());}
	public static ItemStack machines_d(int num, EnumMachinesD meta) {	return new ItemStack(ModBlocks.MACHINES_D, num, meta.ordinal());}
	public static ItemStack machines_e(int num, EnumMachinesE meta) {	return new ItemStack(ModBlocks.MACHINES_E, num, meta.ordinal());}
	public static ItemStack metal_items(int num, EnumMetalItems meta){	return new ItemStack(ModItems.METAL_ITEMS, num, meta.ordinal());}

	public static ItemStack alloy_tech(int num, EnumAlloyTech meta){		return new ItemStack(ModItems.ALLOY_ITEMS_TECH, num, meta.ordinal());}
	public static ItemStack alloy_tech_block(int num, EnumAlloyTech meta){	return new ItemStack(ModBlocks.ALLOY_BLOCKS_TECH, num, meta.ordinal());}
	public static ItemStack alloy_tech_bricks(int num, EnumAlloyTech meta){	return new ItemStack(ModBlocks.ALLOY_BRICKS_TECH, num, meta.ordinal());}
	public static ItemStack alloy_tech_dust(int num, EnumAlloyTech meta){	return new ItemStack(ModItems.ALLOY_ITEMS_TECH, num, (meta.ordinal() * 3));}
	public static ItemStack alloy_tech_ingot(int num, EnumAlloyTech meta){	return new ItemStack(ModItems.ALLOY_ITEMS_TECH, num, (meta.ordinal() * 3) + 1);}
	public static ItemStack alloy_tech_nugget(int num, EnumAlloyTech meta){	return new ItemStack(ModItems.ALLOY_ITEMS_TECH, num, (meta.ordinal() * 3) + 2);}

	public static ItemStack alloy_tech_b(int num, EnumAlloyTechB meta){			return new ItemStack(ModItems.ALLOY_ITEMS_TECH_B, num, meta.ordinal());}
	public static ItemStack alloy_tech_block_b(int num, EnumAlloyTechB meta){	return new ItemStack(ModBlocks.ALLOY_BLOCKS_TECH_B, num, meta.ordinal());}
	public static ItemStack alloy_tech_bricks_b(int num, EnumAlloyTechB meta){	return new ItemStack(ModBlocks.ALLOY_BRICKS_TECH_B, num, meta.ordinal());}
	public static ItemStack alloy_tech_dust_b(int num, EnumAlloyTechB meta){	return new ItemStack(ModItems.ALLOY_ITEMS_TECH_B, num, (meta.ordinal() * 3));}
	public static ItemStack alloy_tech_ingot_b(int num, EnumAlloyTechB meta){	return new ItemStack(ModItems.ALLOY_ITEMS_TECH_B, num, (meta.ordinal() * 3) + 1);}
	public static ItemStack alloy_tech_nugget_b(int num, EnumAlloyTechB meta){	return new ItemStack(ModItems.ALLOY_ITEMS_TECH_B, num, (meta.ordinal() * 3) + 2);}

	public static ItemStack alloy_deco(int num, EnumAlloyDeco meta){		return new ItemStack(ModItems.ALLOY_ITEMS_DECO, num, meta.ordinal());}
	public static ItemStack alloy_deco_block(int num, EnumAlloyDeco meta){	return new ItemStack(ModBlocks.ALLOY_BLOCKS_DECO, num, meta.ordinal());}
	public static ItemStack alloy_deco_bricks(int num, EnumAlloyDeco meta){	return new ItemStack(ModBlocks.ALLOY_BRICKS_DECO, num, meta.ordinal());}
	public static ItemStack alloy_deco_dust(int num, EnumAlloyDeco meta){	return new ItemStack(ModItems.ALLOY_ITEMS_DECO, num, (meta.ordinal() * 3));}
	public static ItemStack alloy_deco_ingot(int num, EnumAlloyDeco meta){	return new ItemStack(ModItems.ALLOY_ITEMS_DECO, num, (meta.ordinal() * 3) + 1);}
	public static ItemStack alloy_deco_nugget(int num, EnumAlloyDeco meta){	return new ItemStack(ModItems.ALLOY_ITEMS_DECO, num, (meta.ordinal() * 3) + 2);}

	public static ItemStack alloy_gems(int num, EnumAlloyGems meta){		return new ItemStack(ModItems.ALLOY_ITEMS_GEMS, num, meta.ordinal());}
	public static ItemStack alloy_gems_block(int num, EnumAlloyGems meta){	return new ItemStack(ModBlocks.ALLOY_BLOCKS_GEMS, num, meta.ordinal());}
	public static ItemStack alloy_gems_bricks(int num, EnumAlloyGems meta){	return new ItemStack(ModBlocks.ALLOY_BRICKS_GEMS, num, meta.ordinal());}
	public static ItemStack alloy_gems_dust(int num, EnumAlloyGems meta){	return new ItemStack(ModItems.ALLOY_ITEMS_GEMS, num, (meta.ordinal() * 3));}
	public static ItemStack alloy_gems_ingot(int num, EnumAlloyGems meta){	return new ItemStack(ModItems.ALLOY_ITEMS_GEMS, num, (meta.ordinal() * 3) + 1);}
	public static ItemStack alloy_gems_nugget(int num, EnumAlloyGems meta){	return new ItemStack(ModItems.ALLOY_ITEMS_GEMS, num, (meta.ordinal() * 3) + 2);}

	public static ItemStack alloy_part_gear(int num, EnumAlloyPart meta){	return new ItemStack(ModItems.ALLOY_PARTS, num, (meta.ordinal() * 3));}
	public static ItemStack alloy_part_plate(int num, EnumAlloyPart meta){	return new ItemStack(ModItems.ALLOY_PARTS, num, (meta.ordinal() * 3) + 1);}
	public static ItemStack alloy_part_coin(int num, EnumAlloyPart meta){	return new ItemStack(ModItems.ALLOY_PARTS, num, (meta.ordinal() * 3) + 2);}

	public static OreIngredient getTechBlockOredict(EnumAlloyTech item) {	return new OreIngredient(EnumAlloyTech.getBlock(item.ordinal()));}
	public static OreIngredient getTechIngotOredict(EnumAlloyTech item) {	return new OreIngredient(EnumAlloyTech.getIngot(item.ordinal()));}
	public static OreIngredient getTechBlockOredictB(EnumAlloyTechB item) {	return new OreIngredient(EnumAlloyTechB.getBlock(item.ordinal()));}
	public static OreIngredient getTechIngotOredictB(EnumAlloyTechB item) {	return new OreIngredient(EnumAlloyTechB.getIngot(item.ordinal()));}
	public static OreIngredient getDecoBlockOredict(EnumAlloyDeco item) {	return new OreIngredient(EnumAlloyDeco.getBlock(item.ordinal()));}
	public static OreIngredient getDecoIngotOredict(EnumAlloyDeco item) {	return new OreIngredient(EnumAlloyDeco.getIngot(item.ordinal()));}
	public static OreIngredient getGemsBlockOredict(EnumAlloyGems item) {	return new OreIngredient(EnumAlloyGems.getBlock(item.ordinal()));}
	public static OreIngredient getGemsIngotOredict(EnumAlloyGems item) {	return new OreIngredient(EnumAlloyGems.getIngot(item.ordinal()));}

	public static ItemStack antimonate_stack(int num, EnumAntimonate mineral) {	return new ItemStack(ModItems.ANTIMONATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack arsenate_stack(int num, EnumArsenate mineral) {		return new ItemStack(ModItems.ARSENATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack borate_stack(int num, EnumBorate mineral) {			return new ItemStack(ModItems.BORATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack carbonate_stack(int num, EnumCarbonate mineral) {	return new ItemStack(ModItems.CARBONATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack chromate_stack(int num, EnumChromate mineral) {		return new ItemStack(ModItems.CHROMATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack halide_stack(int num, EnumHalide mineral) {			return new ItemStack(ModItems.HALIDE_SHARDS, num, mineral.ordinal());}
	public static ItemStack native_stack(int num, EnumNative mineral) {			return new ItemStack(ModItems.NATIVE_SHARDS, num, mineral.ordinal());}
	public static ItemStack oxide_stack(int num, EnumOxide mineral) {			return new ItemStack(ModItems.OXIDE_SHARDS, num, mineral.ordinal());}
	public static ItemStack phosphate_stack(int num, EnumPhosphate mineral) {	return new ItemStack(ModItems.PHOSPHATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack silicate_stack(int num, EnumSilicate mineral) {		return new ItemStack(ModItems.SILICATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack sulfate_stack(int num, EnumSulfate mineral) {		return new ItemStack(ModItems.SULFATE_SHARDS, num, mineral.ordinal());}
	public static ItemStack sulfide_stack(int num, EnumSulfide mineral) {		return new ItemStack(ModItems.SULFIDE_SHARDS, num, mineral.ordinal());}
	public static ItemStack vanadate_stack(int num, EnumVanadate mineral) {		return new ItemStack(ModItems.VANADATE_SHARDS, num, mineral.ordinal());}

	public static String getText(EnumMinerals mineral) {	return EnumMinerals.getName(mineral.ordinal()).toUpperCase().substring(0, 1) + EnumMinerals.getName(mineral.ordinal()).substring(1);}
	public static String element(EnumElements element) {	return EnumElements.getDust(element.ordinal());}

	public static ItemStack crushing_gear = 		new ItemStack(ModItems.CRUSHING_GEAR);
	public static ItemStack slurry_agitator = 		new ItemStack(ModItems.SLURRY_AGITATOR);
	public static ItemStack test_tube = 			new ItemStack(ModItems.TEST_TUBE);
	public static ItemStack graduated_cylinder = 	new ItemStack(ModItems.GRADUATED_CYLINDER);
	public static ItemStack ingot_pattern = 		new ItemStack(ModItems.INGOT_PATTERN);
	public static ItemStack sampling_ampoule = 		new ItemStack(ModItems.SAMPLING_AMPOULE);
	public static ItemStack tile_nullifier = 		new ItemStack(ModItems.TILE_NULLIFIER);

	public static ItemStack va_catalyst = new ItemStack(ModItems.VA_CATALYST);
	public static ItemStack fe_catalyst = new ItemStack(ModItems.FE_CATALYST);
	public static ItemStack gr_catalyst = new ItemStack(ModItems.GR_CATALYST);
	public static ItemStack pt_catalyst = new ItemStack(ModItems.PT_CATALYST);
	public static ItemStack wg_catalyst = new ItemStack(ModItems.WG_CATALYST);
	public static ItemStack os_catalyst = new ItemStack(ModItems.OS_CATALYST);
	public static ItemStack ze_catalyst = new ItemStack(ModItems.ZE_CATALYST);
	public static ItemStack zn_catalyst = new ItemStack(ModItems.ZN_CATALYST);
	public static ItemStack co_catalyst = new ItemStack(ModItems.CO_CATALYST);
	public static ItemStack ni_catalyst = new ItemStack(ModItems.NI_CATALYST);
	public static ItemStack nl_catalyst = new ItemStack(ModItems.NL_CATALYST);
	public static ItemStack au_catalyst = new ItemStack(ModItems.AU_CATALYST);
	public static ItemStack mo_catalyst = new ItemStack(ModItems.MO_CATALYST);
	public static ItemStack in_catalyst = new ItemStack(ModItems.IN_CATALYST);

	public static ItemStack silicone_cartridge = 	new ItemStack(ModItems.SILICONE_CARTRIDGE);
	public static ItemStack toxic_slimeball = 		new ItemStack(ModItems.MOB_ITEMS, 1, EnumMobItems.TOXIC_SLIMEBALL.ordinal());

	public static FluidStack getFluid(EnumFluid fluid, int amount) {	return new FluidStack(EnumFluid.pickFluid(fluid), amount);}

	public static ItemStack rh_book() { 	return GameRegistry.makeItemStack("gbook:guidebook",0,1,"{Book:\"rockhounding_chemistry:xml/chembook.xml\"}");}
	public static ItemStack rh_quest() { 	return GameRegistry.makeItemStack("gbook:guidebook",0,1,"{Book:\"rockhounding_chemistry:xml/chemquest.xml\"}");}

}