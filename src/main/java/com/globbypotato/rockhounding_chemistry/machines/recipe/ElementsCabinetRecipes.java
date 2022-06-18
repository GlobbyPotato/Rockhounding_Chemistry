package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ElementsCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

public class ElementsCabinetRecipes extends BaseRecipes{
	public static ArrayList<ElementsCabinetRecipe> elements_cabinet_recipes = new ArrayList<ElementsCabinetRecipe>();
	public static ArrayList<String> inhibited_elements = new ArrayList<String>();

	public static void machineRecipes() {
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Al", "dustAluminum", "Aluminum"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Sb", "dustAntimony", "Antimony"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("As", "dustArsenic", "Arsenic"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Be", "dustBeryllium", "Beryllim"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Bi", "dustBismuth", "Bismuth"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("B",  "dustBoron", "Boron"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Cd", "dustCadmium", "Cadmium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ca", "dustCalcium", "Calcium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("C",  "dustCarbon", "Carbon"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ce", "dustCerium", "Cerium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Cr", "dustChromium", "Chromium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Co", "dustCobalt", "Cobalt"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Cu", "dustCopper", "Copper"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Dy", "dustDysprosium", "Dysprosium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Er", "dustErbium", "Erbium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Eu", "dustEuropium", "Europium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Gd", "dustGadolinium", "Gadolinium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Au", "dustGold", "Gold"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ho", "dustHolmium", "Holmium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ir", "dustIridium", "Iridium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Fe", "dustIron", "Iron"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("La", "dustLanthanum", "Lanthanum"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Pb", "dustLead", "Lead"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Li", "dustLithium", "Lithium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Lu", "dustLutetium", "Lutetium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Mg", "dustMagnesium", "Magnesium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Mn", "dustManganese", "Manganese"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Mo", "dustMolybdenum", "Molybdenum"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Nd", "dustNeodymium", "Neodymium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ni", "dustNickel", "Nickel"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Nb", "dustNiobium", "Niobium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Os", "dustOsmium", "Osmium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Pd", "dustPalladium", "Palladium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("P",  "dustPhosphorus", "Phosphorus"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Pt", "dustPlatinum", "Platinum"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("K",  "dustPotassium", "Potassium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Pr", "dustPraseodymium", "Praseodymium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Sa", "dustSamarium", "Samarium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Sc", "dustScandium", "Scandium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Si", "dustSilicon", "Silicon"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ag", "dustSilver", "Silver"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("So", "dustSodium", "Sodium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("S",  "dustSulfur", "Sulfur"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ta", "dustTantalum", "Tantalum"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Te", "dustTellurium", "Tellurium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Tb", "dustTerbium", "Terbium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Th", "dustThorium", "Thorium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Tm", "dustThulium", "Thulium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Sn", "dustTin", "Tin"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Ti", "dustTitanium", "Titanium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("W",  "dustTungsten", "Tungsten"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("V",  "dustVanadium", "Vanadium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("U",  "dustYellowcake", "Yellowcake"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Yt", "dustYtterbium", "Ytterbium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Y",  "dustYttrium", "Yttrium"));
		elements_cabinet_recipes.add(new ElementsCabinetRecipe("Zn", "dustZinc", "Zinc"));

	}
}