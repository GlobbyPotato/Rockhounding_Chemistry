package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraftforge.fml.common.Loader;

public class GasifierPlantRecipes extends BaseRecipes{
	public static ArrayList<GasifierPlantRecipe> gasifier_plant_recipes = new ArrayList<GasifierPlantRecipe>();

	public static void machineRecipes() {
		gasifier_plant_recipes.add(new GasifierPlantRecipe(		getFluid(EnumFluid.COAL_SLURRY, 120), 		
																CoreBasics.waterStack(300), 			
																getFluid(EnumFluid.RAW_SYNGAS, 100), 
																Arrays.asList(	"compoundTar", "compoundSulfur", "compoundFlyash"), 
																Arrays.asList(	15, 10, 7),
																800));

		gasifier_plant_recipes.add(new GasifierPlantRecipe(		getFluid(EnumFluid.ORGANIC_SLURRY, 140), 
																CoreBasics.waterStack(400), 			
																getFluid(EnumFluid.RAW_SYNGAS, 100), 	
																Arrays.asList(), 
																Arrays.asList(),
																600));

		gasifier_plant_recipes.add(new GasifierPlantRecipe(		CoreBasics.waterStack(100), 	
																CoreBasics.waterStack(200), 		
																getFluid(EnumFluid.WATER_VAPOUR, 200), 	
																Arrays.asList(), 
																Arrays.asList(),
																400));

		gasifier_plant_recipes.add(new GasifierPlantRecipe(		getFluid(EnumFluid.TOXIC_WASTE, 130), 	
																CoreBasics.waterStack(500), 			
																getFluid(EnumFluid.RAW_FLUE_GAS, 150), 	
																Arrays.asList(	"compoundFlyash", "compoundSulfur"), 
																Arrays.asList(	10, 8),
																1000));

		gasifier_plant_recipes.add(new GasifierPlantRecipe(		getFluid(EnumFluid.TOXIC_SLUDGE, 130), 	
																CoreBasics.waterStack(500), 			
																getFluid(EnumFluid.RAW_FLUE_GAS, 100), 	
																Arrays.asList(	"compoundFlyash", "compoundSulfur"), 
																Arrays.asList(	8, 5),
																1000));

		if(Loader.isModLoaded(ModUtils.forestry_id)){
			if(CoreUtils.fluidExists(ModUtils.forestry_biomass)){
				gasifier_plant_recipes.add(new GasifierPlantRecipe(		CoreUtils.getFluid(ModUtils.forestry_id, 120),
																		CoreBasics.waterStack(300), 			
																		getFluid(EnumFluid.RAW_SYNGAS, 100), 	
																		Arrays.asList(), 
																		Arrays.asList(),
																		600));
			}
		}

	}

}