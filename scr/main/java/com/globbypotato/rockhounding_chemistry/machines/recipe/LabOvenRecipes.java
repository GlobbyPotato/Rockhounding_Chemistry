package com.globbypotato.rockhounding_chemistry.machines.recipe;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class LabOvenRecipes extends BaseRecipes{
	public static ArrayList<LabOvenRecipe> lab_oven_recipes = new ArrayList<LabOvenRecipe>();

	public static void machineRecipes() {
		lab_oven_recipes.add(new LabOvenRecipe(		null,
													sulfur_compound, 		
													ItemStack.EMPTY, 	
													CoreBasics.waterStack(1000), 							
													null, 											
													getFluid(EnumFluid.SULFURIC_ACID, 400), 			
													null ));

		lab_oven_recipes.add(new LabOvenRecipe(		"Sulfuric Acid Plus", 	
													sulfur_compound, 		
													va_catalyst, 		
													CoreBasics.waterStack(1000), 							
													null,
													getFluid(EnumFluid.SULFURIC_ACID, 800), 
													null));

		lab_oven_recipes.add(new LabOvenRecipe(		null,
													salt_compound,
													ItemStack.EMPTY,
													getFluid(EnumFluid.SULFURIC_ACID, 400),
													CoreBasics.waterStack(1000),
													getFluid(EnumFluid.HYDROCHLORIC_ACID, 300),
													getFluid(EnumFluid.TOXIC_WASTE, 10)));

		lab_oven_recipes.add(new LabOvenRecipe(		null,
													fluorite_compound,
													ItemStack.EMPTY,
													getFluid(EnumFluid.SULFURIC_ACID, 500),
													null,
													getFluid(EnumFluid.HYDROFLUORIC_ACID, 400),
													getFluid(EnumFluid.TOXIC_WASTE, 50)));

		lab_oven_recipes.add(new LabOvenRecipe(		null,
													salt_compound,
													ItemStack.EMPTY,
													getFluid(EnumFluid.LIQUID_AMMONIA, 400),
													CoreBasics.waterStack(1000),
													getFluid(EnumFluid.SODIUM_CYANIDE, 300),
													getFluid(EnumFluid.TOXIC_WASTE, 50)));

		lab_oven_recipes.add(new LabOvenRecipe(		"Sodium Cyanide Plus",
													salt_compound,
													gr_catalyst,
													getFluid(EnumFluid.LIQUID_AMMONIA, 400),
													CoreBasics.waterStack(1000),
													getFluid(EnumFluid.SODIUM_CYANIDE, 800),
													getFluid(EnumFluid.TOXIC_WASTE, 50)));

		if(Loader.isModLoaded(ModUtils.thermal_f_id)){
			lab_oven_recipes.add(new LabOvenRecipe(	"Liquid Ammonia by Tar", 
													ModUtils.thermal_f_tar(), 
													ItemStack.EMPTY, 
													CoreBasics.waterStack(1000), 
													null, 
													getFluid(EnumFluid.LIQUID_AMMONIA, 50), 
													ModUtils.creosoteFix()));
		}

		lab_oven_recipes.add(new LabOvenRecipe(		null,
													cracked_lime, 
													ItemStack.EMPTY, 
													getFluid(EnumFluid.COAL_TAR, 200), 
													CoreBasics.waterStack(1000), 
													getFluid(EnumFluid.LIQUID_AMMONIA, 50), 
													getFluid(EnumFluid.COAL_SLURRY, 50)));

		lab_oven_recipes.add(new LabOvenRecipe(		null,
													silicon_compound,
													ItemStack.EMPTY,
													getFluid(EnumFluid.CHLOROMETHANE, 500),
													CoreBasics.waterStack(1000),
													getFluid(EnumFluid.SILICONE, 500),
													getFluid(EnumFluid.TOXIC_WASTE, 100)));

		lab_oven_recipes.add(new LabOvenRecipe(		"IE Creosote",
													coal_tar_compound, 
													ItemStack.EMPTY, 
													CoreBasics.waterStack(1000), 
													null, 
													ModUtils.creosoteFix(), 
													getFluid(EnumFluid.COAL_SLURRY, 50)));

		lab_oven_recipes.add(new LabOvenRecipe(		null,
													ammonium_chloride, 
													ItemStack.EMPTY, 
													CoreBasics.waterStack(1000), 
													null,
													getFluid(EnumFluid.LIQUID_AMMONIA, 50), 
													getFluid(EnumFluid.HYDROCHLORIC_ACID, 50)));
	}
}