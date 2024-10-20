package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumMinerals;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryPondRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SlurryPondRecipes extends BaseRecipes{
	public static ArrayList<SlurryPondRecipe> slurry_pond_recipes = new ArrayList<SlurryPondRecipe>();

	public static void machineRecipes() {
		slurry_pond_recipes.add(new SlurryPondRecipe(	"compoundCoal", 				
														CoreBasics.waterStack(1000), 				
														getFluid(EnumFluid.COAL_SLURRY, 1000)));

		slurry_pond_recipes.add(new SlurryPondRecipe(	new ItemStack(Items.ROTTEN_FLESH), 			
														CoreBasics.waterStack(200), 				
														getFluid(EnumFluid.ORGANIC_SLURRY, 200)));

		if(!ModUtils.mekanism_biofuel().isEmpty()){
			slurry_pond_recipes.add(new SlurryPondRecipe(	ModUtils.mekanism_biofuel(), 				
															CoreBasics.waterStack(500), 		
															getFluid(EnumFluid.ORGANIC_SLURRY, 500)));
		}

		if(!ModUtils.actually_biomass().isEmpty()){
			slurry_pond_recipes.add(new SlurryPondRecipe(	ModUtils.actually_biomass(), 			
															CoreBasics.waterStack(300), 				
															getFluid(EnumFluid.ORGANIC_SLURRY, 300)));
		}

		if(!ModUtils.forestry_compost().isEmpty()){
			slurry_pond_recipes.add(new SlurryPondRecipe(	ModUtils.forestry_compost(), 			
															CoreBasics.waterStack(200), 				
															getFluid(EnumFluid.ORGANIC_SLURRY, 200)));
		}

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.ANTIMONATE), 	
															getFluid(EnumFluid.HYDROCHLORIC_ACID, 250), 	
															getFluid(EnumFluid.LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.ARSENATE), 	
															getFluid(EnumFluid.SODIUM_HYDROXIDE, 250), 	
															getFluid(EnumFluid.HIGH_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.BORATE), 	
															getFluid(EnumFluid.HYDROCHLORIC_ACID, 250), 	
															getFluid(EnumFluid.LOW_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.CARBONATE), 	
															getFluid(EnumFluid.SULFURIC_ACID, 250), 	
															getFluid(EnumFluid.LOW_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.CHROMATE), 	
															getFluid(EnumFluid.SODIUM_HYDROXIDE, 250), 	
															getFluid(EnumFluid.LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.HALIDE), 	
															getFluid(EnumFluid.HYDROCHLORIC_ACID, 250), 	
															getFluid(EnumFluid.LOW_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.NATIVE), 	
															getFluid(EnumFluid.HYDROFLUORIC_ACID, 250), 	
															getFluid(EnumFluid.LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.OXIDE), 	
															getFluid(EnumFluid.HYDROFLUORIC_ACID, 250), 	
															getFluid(EnumFluid.HIGH_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.PHOSPHATE), 	
															getFluid(EnumFluid.SULFURIC_ACID, 250), 	
															getFluid(EnumFluid.LOW_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.SILICATE), 	
															getFluid(EnumFluid.HYDROCHLORIC_ACID, 250), 	
															getFluid(EnumFluid.HIGH_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.SULFATE), 	
															getFluid(EnumFluid.HYDROCHLORIC_ACID, 250), 	
															getFluid(EnumFluid.LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.SULFIDE), 	
															getFluid(EnumFluid.HYDROCHLORIC_ACID, 250), 	
															getFluid(EnumFluid.HIGH_LEACHATE, 250)));

		slurry_pond_recipes.add(new SlurryPondRecipe(		mineral_ores(1, EnumMinerals.VANADATE), 	
															getFluid(EnumFluid.SULFURIC_ACID, 250), 	
															getFluid(EnumFluid.LEACHATE, 250)));

	}

}