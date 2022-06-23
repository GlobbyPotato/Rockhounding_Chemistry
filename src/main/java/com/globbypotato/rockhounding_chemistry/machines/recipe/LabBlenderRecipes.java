package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraftforge.fml.common.Loader;

public class LabBlenderRecipes extends BaseRecipes{
	public static ArrayList<LabBlenderRecipe> lab_blender_recipes = new ArrayList<LabBlenderRecipe>();

	public static void machineRecipes() {
		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"gemFluorite"), 
															Arrays.asList(	3),
															chemicals(1, EnumChemicals.FLUORITE_COMPOUND)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"compoundTar", "compoundGraphite"), 
															Arrays.asList(	1, 3),
															chemicals(1, EnumChemicals.GRAPHITE_COMPOUND)));

		if(Loader.isModLoaded(ModUtils.thermal_f_id)){
			lab_blender_recipes.add(new LabBlenderRecipe(	Arrays.asList(	"compoundTar", "compoundGraphite"), 
															Arrays.asList(	1, 3),
															chemicals(1, EnumChemicals.GRAPHITE_COMPOUND)));
		}

		for(int i = 0; i < EnumAlloyTech.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	Arrays.asList(	EnumAlloyTech.getIngot(i)), 
															Arrays.asList(	1),
															alloy_tech_dust(1, EnumAlloyTech.values()[i])));
		}
		for(int i = 0; i < EnumAlloyTechB.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	Arrays.asList(	EnumAlloyTechB.getIngot(i)), 
															Arrays.asList(	1),
															alloy_tech_dust_b(1, EnumAlloyTechB.values()[i])));
		}
		for(int i = 0; i < EnumAlloyDeco.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	Arrays.asList(	EnumAlloyDeco.getIngot(i)), 
															Arrays.asList(	1),
															alloy_deco_dust(1, EnumAlloyDeco.values()[i])));
		}
		for(int i = 0; i < EnumAlloyGems.size(); i++){
			lab_blender_recipes.add(new LabBlenderRecipe(	Arrays.asList(	EnumAlloyGems.getIngot(i)), 
															Arrays.asList(	1),
															alloy_gems_dust(1, EnumAlloyGems.values()[i])));
		}

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"sand"), 
															Arrays.asList(	4),
															chemicals(3, EnumChemicals.SAND_COMPOUND)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"gemQuartz"), 
															Arrays.asList(	2),
															chemicals(1, EnumChemicals.QUARTZ_COMPOUND)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotAluminum"), 
															Arrays.asList(	1),
															elements(1, EnumElements.ALUMINUM)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotGraphite"), 
															Arrays.asList(	1),
															chemicals(1, EnumChemicals.CRACKED_COAL)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotLead"), 
															Arrays.asList(	1),
															elements(1, EnumElements.LEAD)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotOsmium"), 
															Arrays.asList(	1),
															elements(1, EnumElements.OSMIUM)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotPlatinum"), 
															Arrays.asList(	1),
															elements(1, EnumElements.PLATINUM)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotTitanium"), 
															Arrays.asList(	1),
															elements(1, EnumElements.TITANIUM)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotVanadium"), 
															Arrays.asList(	1),
															elements(1, EnumElements.VANADIUM)));

		lab_blender_recipes.add(new LabBlenderRecipe(		Arrays.asList(	"ingotZeolite"), 
															Arrays.asList(	1),
															chemicals(1, EnumChemicals.ZEOLITE_PELLET)));
	}

}