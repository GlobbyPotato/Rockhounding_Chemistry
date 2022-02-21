package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class MetalAlloyerRecipes extends BaseRecipes{
	public static ArrayList<MetalAlloyerRecipe> metal_alloyer_recipes = new ArrayList<MetalAlloyerRecipe>();

	public static void machineRecipes() {
//TECH ALLOYS
		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCopper", "dustBeryllium", "dustCobalt"), 
															Arrays.asList(96, 3, 1), 
															alloy_tech_ingot(1, EnumAlloyTech.CUBE)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustAluminum", "dustScandium"), 
															Arrays.asList(98, 2),
															alloy_tech_ingot(1, EnumAlloyTech.SCAL)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustAluminum", "dustMagnesium", "dustBoron", "dustLithium", "dustSilicon"),
															Arrays.asList(76, 15, 3, 3, 3), 
															alloy_tech_ingot(1, EnumAlloyTech.BAM)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCobalt", "dustChromium", "dustTungsten", "dustCarbon", "dustSilicon", "dustMolybdenum"),
															Arrays.asList(57, 28, 11, 2, 1, 1), 
															alloy_tech_ingot(1, EnumAlloyTech.STELLITE)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCopper", "dustNickel", "dustChromium", "dustSilicon", "dustManganese", "dustIron", "dustTitanium", "dustZirconium"),
															Arrays.asList(63, 30, 2, 1, 1, 1, 1, 1),
															alloy_tech_ingot(1, EnumAlloyTech.CUPRONICKEL)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustNickel", "dustChromium", "dustCobalt", "dustMolybdenum", "dustTitanium", "dustAluminum"),
															Arrays.asList(50, 21, 20, 6, 2, 1),
															alloy_tech_ingot(1, EnumAlloyTech.NIMONIC)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustNickel", "dustMolybdenum", "dustChromium", "dustIron", "dustTungsten", "dustCobalt", "dustManganese", "dustNiobium"),
															Arrays.asList(55, 16, 15, 5, 4, 3, 1, 1),
															alloy_tech_ingot(1, EnumAlloyTech.HASTELLOY)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustNickel", "dustChromium", "dustIron", "dustSilicon", "dustManganese"),
															Arrays.asList(74, 20, 1, 2, 3),
															alloy_tech_ingot(1, EnumAlloyTech.NICHROME)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCopper", "dustNickel", "dustIron", "dustManganese"),
															Arrays.asList(86, 11, 2, 1),
															alloy_tech_ingot(1, EnumAlloyTech.CUNIFE)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustAluminum", "dustMagnesium", "dustManganese"),
															Arrays.asList(77, 12, 1),
															alloy_tech_ingot(1, EnumAlloyTech.HYDRONALIUM)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustIron", "dustChromium", "dustCarbon", "dustVanadium", "dustSilicon"),
															Arrays.asList(95, 2, 1, 1, 1),
															alloy_tech_ingot(1, EnumAlloyTech.VANASTEEL)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("compoundWidia", "dustCobalt", "dustCarbon"),
															Arrays.asList(82, 13, 5),
															alloy_tech_ingot(1, EnumAlloyTech.WIDIA)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustTantalum", "dustTungsten", "dustNiobium"),
															Arrays.asList(94, 5, 1),
															alloy_tech_ingot(1, EnumAlloyTech.TANTALOY)));

// TECH ALLOYS B
		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustNickel", "dustAluminum"),
															Arrays.asList(70, 30),
															alloy_tech_ingot_b(1, EnumAlloyTechB.NIAL)));
		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustNickel", "dustChromium", "dustCobalt", "dustTitanium", "dustTungsten", "dustAluminum", "dustTantalum", "dustNiobium"),
															Arrays.asList(48, 22, 19, 4, 2, 2, 2, 1),
															alloy_tech_ingot_b(1, EnumAlloyTechB.INCONEL)));
		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustZirconium", "dustTin", "dustNiobium", "dustIron", "dustChromium"),
															Arrays.asList(98, 4, 1, 1, 1),
															alloy_tech_ingot_b(1, EnumAlloyTechB.ZIRCALOY)));

// DECO ALLOYS
		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCerium", "dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustIron"),
															Arrays.asList(50, 29, 15, 5, 1),
															alloy_deco_ingot(1, EnumAlloyDeco.MISCHMETAL)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustGold", "dustCopper", "dustSilver"),
															Arrays.asList(75, 22, 3),
															alloy_deco_ingot(1, EnumAlloyDeco.ROSEGOLD)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustGold", "dustSilver", "dustCopper", "dustCadmium"),
															Arrays.asList(75, 18, 5, 2),
															alloy_deco_ingot(1, EnumAlloyDeco.GREENGOLD)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustGold", "dustPlatinum", "dustNickel", "dustZinc"),
															Arrays.asList(75, 10, 10, 5),
															alloy_deco_ingot(1, EnumAlloyDeco.WHITEGOLD)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCopper", "dustSilver", "dustGold"),
															Arrays.asList(70, 20, 10),
															alloy_deco_ingot(1, EnumAlloyDeco.SHIBUICHI)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCopper", "dustZinc", "dustArsenic"),
															Arrays.asList(75, 20, 5),
															alloy_deco_ingot(1, EnumAlloyDeco.TOMBAK)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustTin", "dustAntimony", "dustCopper", "dustBismuth",  "dustLead"),
															Arrays.asList(90, 6, 2, 1, 1),
															alloy_deco_ingot(1, EnumAlloyDeco.PEWTER)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustIron", "dustNickel", "dustSilicon", "dustChromium", "dustPhosphorus", "dustManganese", "dustCopper", "dustVanadium"),
															Arrays.asList(93, 1, 1, 1, 1, 1, 1, 1),
															alloy_deco_ingot(1, EnumAlloyDeco.CORTEN)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCopper", "dustGold"),
															Arrays.asList(90, 10),
															alloy_deco_ingot(1, EnumAlloyDeco.SHAKUDO)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustGold", "dustAluminum"),
															Arrays.asList(80, 20),
															alloy_deco_ingot(1, EnumAlloyDeco.PURPLEGOLD)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustIron", "dustNeodymium", "dustBoron", "dustNiobium", "dustDysprosium", "dustPraseodymium"),
															Arrays.asList(65, 30, 2, 1, 1, 1),
															alloy_deco_ingot(1, EnumAlloyDeco.NIB)));

		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustCobalt", "dustSamarium", "dustIron", "dustCopper", "dustZirconium"),
															Arrays.asList(50, 25, 18, 5, 2),
															alloy_deco_ingot(1, EnumAlloyDeco.COSM)));

// MISC ALLOYS
		metal_alloyer_recipes.add(new MetalAlloyerRecipe(	Arrays.asList("dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustSamarium", "dustGadolinium", "dustSand"),
															Arrays.asList(46, 34, 11, 5, 4, 60),
															misc_items(8, EnumMiscItems.DIDYMIUM_PANEL)));

	}
}