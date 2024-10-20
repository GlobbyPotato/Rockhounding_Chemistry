package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumAntimonate;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PowderMixerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PowderMixerRecipes extends BaseRecipes{
	public static ArrayList<PowderMixerRecipe> powder_mixer_recipes = new ArrayList<PowderMixerRecipe>();

	public static void machineRecipes() {
		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList(	"dustAluminum", "dustYttrium"), 
																Arrays.asList(	80, 20),
																chemicals(1, EnumChemicals.YAG_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList(	"dustSulfur"), 
																Arrays.asList(	100),
																chemicals(1, EnumChemicals.SULFUR_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList(	"compoundCarbon", "compoundTar"), 
																Arrays.asList(	70, 30),
																chemicals(1, EnumChemicals.GRAPHITE_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList(	"dustSilicon"), 
																Arrays.asList(	100),
																chemicals(1, EnumChemicals.SILICON_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList(	"dustLead"), 
																Arrays.asList(	100),
																chemicals(1, EnumChemicals.LEAD_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList("dustSand", "dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustSamarium", "dustGadolinium"),
																Arrays.asList(40, 23, 17, 7, 3, 2),
																chemicals(1, EnumChemicals.DIDYMIUM_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList("compoundNitrate", "compoundCharcoal", "compoundSulfur"),
																Arrays.asList(60, 30, 10),
																new ItemStack(Items.GUNPOWDER)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList(	"dustSalt"), 
																Arrays.asList(	100),
																chemicals(1, EnumChemicals.CHLORIDE_COMPOUND)));

		powder_mixer_recipes.add(new PowderMixerRecipe(			Arrays.asList("compoundQuartz", "dustAluminum", "compoundZeolite"),
																Arrays.asList(65, 27, 8),
																chemicals(1, EnumChemicals.ZEOLITE_COMPOUND)));

	
	}
}