package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumMetalItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class LabBlenderRecipes extends BaseRecipes{
	public static ArrayList<LabBlenderRecipe> lab_blender_recipes = new ArrayList<LabBlenderRecipe>();

	public static void machineRecipes() {
		lab_blender_recipes.add(new LabBlenderRecipe(		chemicals(3, EnumChemicals.FLUORITE),
															chemicals(1, EnumChemicals.FLUORITE_COMPOUND)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	native_stack(3, EnumNative.GRAPHITE),
																			chemicals(1, EnumChemicals.COAL_TAR_COMPOUND)), 		
															chemicals(1, EnumChemicals.GRAPHITE_COMPOUND)));

		if(Loader.isModLoaded(ModUtils.thermal_f_id)){
			lab_blender_recipes.add(new LabBlenderRecipe(	Arrays.asList(	BaseRecipes.native_stack(3, EnumNative.GRAPHITE), 
																			ModUtils.thermal_f_tar()),
															BaseRecipes.chemicals(1, EnumChemicals.GRAPHITE_COMPOUND)));
		}

		for(int i = 0; i < EnumAlloyTech.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	alloy_tech_ingot(1, EnumAlloyTech.values()[i]),
															alloy_tech_dust(1, EnumAlloyTech.values()[i])));
		}
		for(int i = 0; i < EnumAlloyTechB.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	alloy_tech_ingot_b(1, EnumAlloyTechB.values()[i]),
															alloy_tech_dust_b(1, EnumAlloyTechB.values()[i])));
		}
		for(int i = 0; i < EnumAlloyDeco.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	alloy_deco_ingot(1, EnumAlloyDeco.values()[i]), 	
															alloy_deco_dust(1, EnumAlloyDeco.values()[i])));
		}
		for(int i = 0; i < EnumAlloyGems.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	alloy_gems_ingot(1, EnumAlloyGems.values()[i]), 	
															alloy_gems_dust(1, EnumAlloyGems.values()[i])));
		}

		lab_blender_recipes.add(new LabBlenderRecipe(		new ItemStack(Blocks.SAND, 4), 		
															chemicals(3, EnumChemicals.SAND_COMPOUND)));

		lab_blender_recipes.add(new LabBlenderRecipe(		new ItemStack(Items.QUARTZ, 2), 		
															chemicals(1, EnumChemicals.QUARTZ_COMPOUND)));

		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.ALUMINUM_INGOT),
															elements(1, EnumElements.ALUMINUM)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.GRAPHITE_INGOT), 		
															chemicals(1, EnumChemicals.CRACKED_COAL)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.LEAD_INGOT), 
															elements(1, EnumElements.LEAD)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.OSMIUM_INGOT), 
															elements(1, EnumElements.OSMIUM)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.PLATINUM_INGOT), 
															elements(1, EnumElements.PLATINUM)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.TITANIUM_INGOT), 	
															elements(1, EnumElements.TITANIUM)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.VANADIUM_INGOT), 	
															elements(1, EnumElements.VANADIUM)));
		lab_blender_recipes.add(new LabBlenderRecipe(		metal_items(1, EnumMetalItems.ZEOLITE_INGOT), 
															chemicals(1, EnumChemicals.ZEOLITE_PELLET)));
	}

}