package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumMetalItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

@ObjectHolder(Reference.MODID)
public class ModRecipes extends BaseRecipes{

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RecipesHandler {

		/**
		 * @param event  
		 */
		@SubscribeEvent
		public static void registerRecipes(final RegistryEvent.Register<IRecipe> event){
			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, "guide_book"), new ResourceLocation(Reference.MODID, "utils"), rh_book(), new Ingredient[] { new OreIngredient("dustRedstone"), Ingredient.fromStacks(test_tube), Ingredient.fromStacks(new ItemStack(Items.PAPER)), Ingredient.fromStacks(new ItemStack(Items.PAPER)) });
			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, "guide_quest"), new ResourceLocation(Reference.MODID, "utils"), rh_quest(), new Ingredient[] { new OreIngredient("dustRedstone"), Ingredient.fromStacks(graduated_cylinder), Ingredient.fromStacks(new ItemStack(Items.PAPER)), Ingredient.fromStacks(new ItemStack(Items.PAPER)) });

			//tech alloy parts
	 		for(int x = 0; x < EnumAlloyTech.size(); x++){
	 			if(ModConfig.dictSmelt){
	 				for(ItemStack ore : OreDictionary.getOres(EnumAlloyTech.getDust(x))) {
		 				if(!ore.isEmpty() && ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
		 					GameRegistry.addSmelting(ore, alloy_tech_ingot(1, EnumAlloyTech.values()[x]), 1.0F);
		 				}
		 			}
	 			}else{
					GameRegistry.addSmelting(alloy_tech_dust(1, EnumAlloyTech.values()[x]), alloy_tech_ingot(1, EnumAlloyTech.values()[x]), 2.0F);

	 			}
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyTech.getAlloy(x) + "_ingot_from_nugget"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_ingot(1, EnumAlloyTech.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyTech.getNugget(x) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyTech.getAlloy(x) + "_block_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_block(1, EnumAlloyTech.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyTech.getIngot(x) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyTech.getAlloy(x) + "_ingots_from_block"),	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_ingot(9, EnumAlloyTech.values()[x]), 		new Ingredient[] { getTechBlockOredict(EnumAlloyTech.values()[x]) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyTech.getAlloy(x) + "_nuggets_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_nugget(9, EnumAlloyTech.values()[x]), 		new Ingredient[] { getTechIngotOredict(EnumAlloyTech.values()[x]) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyTech.getAlloy(x) + "_bricks"),				new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_bricks(4, EnumAlloyTech.values()[x]), 		new Object[] {"XX","XX",'X', EnumAlloyTech.getBlock(x) });
	 		}

			//tech alloy parts B
	 		for(int x = 0; x < EnumAlloyTechB.size(); x++){
	 			if(ModConfig.dictSmelt){
	 				for(ItemStack ore : OreDictionary.getOres(EnumAlloyTechB.getDust(x))) {
		 				if(!ore.isEmpty() && ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
		 					GameRegistry.addSmelting(ore, alloy_tech_ingot_b(1, EnumAlloyTechB.values()[x]), 1.0F);
		 				}
		 			}
	 			}else{
					GameRegistry.addSmelting(alloy_tech_dust_b(1, EnumAlloyTechB.values()[x]), alloy_tech_ingot_b(1, EnumAlloyTechB.values()[x]), 2.0F);

	 			}
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyTechB.getAlloy(x) + "_ingot_from_nugget"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_ingot_b(1, EnumAlloyTechB.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyTechB.getNugget(x) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyTechB.getAlloy(x) + "_block_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_block_b(1, EnumAlloyTechB.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyTechB.getIngot(x) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyTechB.getAlloy(x) + "_ingots_from_block"),	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_ingot_b(9, EnumAlloyTechB.values()[x]), 		new Ingredient[] { getTechBlockOredictB(EnumAlloyTechB.values()[x]) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyTechB.getAlloy(x) + "_nuggets_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_nugget_b(9, EnumAlloyTechB.values()[x]), 		new Ingredient[] { getTechIngotOredictB(EnumAlloyTechB.values()[x]) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyTechB.getAlloy(x) + "_bricks"),				new ResourceLocation(Reference.MODID, "alloys"), alloy_tech_bricks_b(4, EnumAlloyTechB.values()[x]), 		new Object[] {"XX","XX",'X', EnumAlloyTechB.getBlock(x) });
	 		}

	 		//deco alloy parts
	 		for(int x = 0; x < EnumAlloyDeco.size(); x++){
	 			if(ModConfig.dictSmelt){
		 			for(ItemStack ore : OreDictionary.getOres(EnumAlloyDeco.getDust(x))) {
		 				if(!ore.isEmpty() && ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE){
	 						GameRegistry.addSmelting(ore, alloy_deco_ingot(1, EnumAlloyDeco.values()[x]), 1.0F);
		 				}
		 			}
	 			}else{
					GameRegistry.addSmelting(alloy_deco_dust(1, EnumAlloyDeco.values()[x]), alloy_deco_ingot(1, EnumAlloyDeco.values()[x]), 2.0F);
	 			}
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyDeco.getAlloy(x) + "_ingot_from_nugget"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_deco_ingot(1, EnumAlloyDeco.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyDeco.getNugget(x) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyDeco.getAlloy(x) + "_block_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_deco_block(1, EnumAlloyDeco.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyDeco.getIngot(x) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyDeco.getAlloy(x) + "_ingots_from_block"),	new ResourceLocation(Reference.MODID, "alloys"), alloy_deco_ingot(9, EnumAlloyDeco.values()[x]), 		new Ingredient[] { getDecoBlockOredict(EnumAlloyDeco.values()[x]) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyDeco.getAlloy(x) + "_nuggets_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_deco_nugget(9, EnumAlloyDeco.values()[x]), 		new Ingredient[] { getDecoIngotOredict(EnumAlloyDeco.values()[x]) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyDeco.getAlloy(x) + "_bricks"),				new ResourceLocation(Reference.MODID, "alloys"), alloy_deco_bricks(4, EnumAlloyDeco.values()[x]), 		new Object[] {"XX","XX",'X', EnumAlloyDeco.getBlock(x) });
	 		}

			//gems alloy parts
	 		for(int x = 0; x < EnumAlloyGems.size(); x++){
	 			if(ModConfig.dictSmelt){
	 				for(ItemStack ore : OreDictionary.getOres(EnumAlloyGems.getDust(x))) {
		 				if(!ore.isEmpty() && ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
		 					GameRegistry.addSmelting(ore, alloy_gems_ingot(1, EnumAlloyGems.values()[x]), 1.0F);
		 				}
	 				}
	 			}else{
					GameRegistry.addSmelting(alloy_gems_dust(1, EnumAlloyGems.values()[x]), alloy_gems_ingot(1, EnumAlloyGems.values()[x]), 2.0F);
	 			}
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyGems.getAlloy(x) + "_ingot_from_nugget"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_gems_ingot(1, EnumAlloyGems.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyGems.getNugget(x) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyGems.getAlloy(x) + "_block_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_gems_block(1, EnumAlloyGems.values()[x]), 		new Object[] {"XXX","XXX","XXX",'X', EnumAlloyGems.getIngot(x) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyGems.getAlloy(x) + "_ingots_from_block"),	new ResourceLocation(Reference.MODID, "alloys"), alloy_gems_ingot(9, EnumAlloyGems.values()[x]), 		new Ingredient[] { getGemsBlockOredict(EnumAlloyGems.values()[x]) });
	 			GameRegistry.addShapelessRecipe(new ResourceLocation(Reference.MODID, EnumAlloyGems.getAlloy(x) + "_nuggets_from_ingot"), 	new ResourceLocation(Reference.MODID, "alloys"), alloy_gems_nugget(9, EnumAlloyGems.values()[x]), 		new Ingredient[] { getGemsIngotOredict(EnumAlloyGems.values()[x]) });
	 			GameRegistry.addShapedRecipe(	new ResourceLocation(Reference.MODID, EnumAlloyGems.getAlloy(x) + "_bricks"),				new ResourceLocation(Reference.MODID, "alloys"), alloy_gems_bricks(4, EnumAlloyGems.values()[x]), 		new Object[] {"XX","XX",'X', EnumAlloyGems.getBlock(x) });
	 		}

			//metal parts dust to ingot
 			if(ModConfig.dictSmelt){
 				for(int x = 0; x < EnumMetalItems.size(); x++){
		 			for(ItemStack ore : OreDictionary.getOres(EnumMetalItems.getSmeltDict(x))) {
		 				if(!ore.isEmpty() && ore.getItemDamage() != -1 || ore.getItemDamage() != OreDictionary.WILDCARD_VALUE){
	 						GameRegistry.addSmelting(ore, metal_items(1, EnumMetalItems.values()[x]), 2.0F);
		 				}
		 			}
	 			}
 			}else{
				GameRegistry.addSmelting(elements(1, EnumElements.VANADIUM), metal_items(1, EnumMetalItems.VANADIUM_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.ZIRCONIUM), metal_items(1, EnumMetalItems.ZIRCONIUM_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.OSMIUM), metal_items(1, EnumMetalItems.OSMIUM_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.ALUMINUM), metal_items(1, EnumMetalItems.ALUMINUM_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.TITANIUM), metal_items(1, EnumMetalItems.TITANIUM_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.LEAD), metal_items(1, EnumMetalItems.LEAD_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.PLATINUM), metal_items(1, EnumMetalItems.PLATINUM_INGOT), 2.0F);
				GameRegistry.addSmelting(chemicals(1, EnumChemicals.GRAPHITE_COMPOUND), metal_items(1, EnumMetalItems.GRAPHITE_INGOT), 2.0F);
				GameRegistry.addSmelting(chemicals(1, EnumChemicals.ZEOLITE_COMPOUND), metal_items(1, EnumMetalItems.ZEOLITE_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.COBALT), metal_items(1, EnumMetalItems.COBALT_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.COPPER), metal_items(1, EnumMetalItems.COPPER_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.MOLYBDENUM), metal_items(1, EnumMetalItems.MOLYBDENUM_INGOT), 2.0F);
				GameRegistry.addSmelting(elements(1, EnumElements.NICKEL), metal_items(1, EnumMetalItems.NICKEL_INGOT), 2.0F);
	 		}
			GameRegistry.addSmelting(elements(1, EnumElements.IRON), new ItemStack(Items.IRON_INGOT), 2.0F);
			GameRegistry.addSmelting(elements(1, EnumElements.GOLD), new ItemStack(Items.GOLD_INGOT), 2.0F);

			//silicone gun
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID, "silicone_cartridge"),   new ResourceLocation(Reference.MODID, "tools"), new ItemStack(ModItems.SILICONE_CARTRIDGE), new Object[] { " B ", "IIS"," NI", 'S', "stickWood", 'I', "ingotIron", 'N', "nuggetIron", 'B', CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.SILICONE)) });

			//dry waterlock
			GameRegistry.addSmelting(new ItemStack(ModBlocks.WATERLOCK, 1, 1), new ItemStack(ModBlocks.WATERLOCK, 1, 0), 2.0F);

			//refractory brick
			GameRegistry.addSmelting(misc_items(1, EnumMiscItems.FLYASH_BALL), misc_items(1, EnumMiscItems.FLYASH_BRICK), 2.0F);

			//machines recipes
			MachineRecipes.init();

		}

	}
}