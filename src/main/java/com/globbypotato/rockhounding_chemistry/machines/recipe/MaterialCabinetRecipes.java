package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class MaterialCabinetRecipes extends BaseRecipes{
	public static ArrayList<MaterialCabinetRecipe> material_cabinet_recipes = new ArrayList<MaterialCabinetRecipe>();
	public static ArrayList<String> inhibited_material = new ArrayList<String>();

	public static void machineRecipes() {
		material_cabinet_recipes.add(new MaterialCabinetRecipe("FS", "dustSand", "Filtered Sand"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("WI", "compoundWidia", "Widia Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("CT", "compoundTar", "Coal Tar Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("SU", "compoundSulfur", "Sulfur Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("FA", "compoundFlyash", "Fly Ash Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("CK", "compoundCoke", "Coke Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("QU", "compoundQuartz", "Quartz Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("LE", "compoundLead", "Lead Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("FL", "compoundFluorite", "Fluorite Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("ZE", "dustZeolite", "Zeolite Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("GR", "dustGraphite", "Graphite Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("DD", "dustDidymium", "Didymium Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("CH", "compoundCharcoal", "Charcoal Compound"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("PN", "compoundNitrate", "Potassium Nitrate"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("SA", "dustSalt", "Salt"));
		material_cabinet_recipes.add(new MaterialCabinetRecipe("CX", "compoundCarbon", "Carbon Compound"));
	}
}