package com.globbypotato.rockhounding_chemistry.handlers;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyPart;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.EnumMetalItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;

@ObjectHolder(Reference.MODID)
public class ModDictionary extends BaseRecipes {

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void registerOreDictEntries(@Nonnull RegistryEvent.Register<Item> event) {

			//ores
			OreDictionary.registerOre("oreUninspected", new ItemStack(ModBlocks.UNINSPECTED_MINERAL));

			//blocks
			OreDictionary.registerOre("blockRawsalt", raw_salt_block);
			OreDictionary.registerOre("blockCharcoal", charcoal_block);

			//dusts
			OreDictionary.registerOre("compoundCoal", chemicals(1, EnumChemicals.CRACKED_COAL));
			OreDictionary.registerOre("compoundFlyash", chemicals(1, EnumChemicals.FLYASH_COMPOUND));
			OreDictionary.registerOre("compoundYag", chemicals(1, EnumChemicals.YAG_COMPOUND));
			OreDictionary.registerOre("compoundPureYag", chemicals(1, EnumChemicals.PURE_YAG_COMPOUND));
			OreDictionary.registerOre("compoundZeolite", chemicals(1, EnumChemicals.ZEOLITE_COMPOUND));
			OreDictionary.registerOre("compoundWidia", chemicals(1, EnumChemicals.WIDIA_COMPOUND));

			OreDictionary.registerOre("dustSalt", chemicals(1, EnumChemicals.SALT));
			OreDictionary.registerOre("dustGraphite", chemicals(1, EnumChemicals.GRAPHITE_COMPOUND));
			OreDictionary.registerOre("dustZeolite", chemicals(1, EnumChemicals.ZEOLITE_PELLET));
			OreDictionary.registerOre("dustSand", chemicals(1, EnumChemicals.SAND_COMPOUND));
			OreDictionary.registerOre("dustCoke", chemicals(1, EnumChemicals.COKE_COMPOUND));
			for(int x = 0; x < EnumElements.size(); x++){OreDictionary.registerOre(EnumElements.getDust(x), elements(1, EnumElements.values()[x]));}

			OreDictionary.registerOre("compoundGraphite", native_stack(1, EnumNative.CHAOITE));
			OreDictionary.registerOre("compoundGraphite", native_stack(1, EnumNative.GRAPHITE));
			OreDictionary.registerOre("compoundGraphite", native_stack(1, EnumNative.FULLERITE));

			//items
			OreDictionary.registerOre("itemNichromeHeater", misc_items(1, EnumMiscItems.HEATER));
			OreDictionary.registerOre("slimeball", new ItemStack(ModItems.SILICONE_CARTRIDGE, 1, OreDictionary.WILDCARD_VALUE));	

			//alloys
			for(int x = 0; x < EnumAlloyTech.size(); x++){
				OreDictionary.registerOre(EnumAlloyTech.getBlock(x), alloy_tech_block(1, EnumAlloyTech.values()[x]));
			}
			for(int x = 0; x < EnumAlloyTechB.size(); x++){
				OreDictionary.registerOre(EnumAlloyTechB.getBlock(x), alloy_tech_block_b(1, EnumAlloyTechB.values()[x]));
			}
			for(int x = 0; x < EnumAlloyDeco.size(); x++){
				OreDictionary.registerOre(EnumAlloyDeco.getBlock(x), alloy_deco_block(1, EnumAlloyDeco.values()[x]));
			}
			for(int x = 0; x < EnumAlloyGems.size(); x++){
				OreDictionary.registerOre(EnumAlloyGems.getBlock(x), alloy_gems_block(1, EnumAlloyGems.values()[x]));
			}
			for(int x = 0; x < EnumAlloyTech.size(); x++){
				OreDictionary.registerOre(EnumAlloyTech.getDust(x),   alloy_tech_dust(1, EnumAlloyTech.values()[x]));	
				OreDictionary.registerOre(EnumAlloyTech.getIngot(x),  alloy_tech_ingot(1, EnumAlloyTech.values()[x]));	
				OreDictionary.registerOre(EnumAlloyTech.getNugget(x), alloy_tech_nugget(1, EnumAlloyTech.values()[x]));	
			}
			for(int x = 0; x < EnumAlloyTechB.size(); x++){
				OreDictionary.registerOre(EnumAlloyTechB.getDust(x),   alloy_tech_dust_b(1, EnumAlloyTechB.values()[x]));	
				OreDictionary.registerOre(EnumAlloyTechB.getIngot(x),  alloy_tech_ingot_b(1, EnumAlloyTechB.values()[x]));	
				OreDictionary.registerOre(EnumAlloyTechB.getNugget(x), alloy_tech_nugget_b(1, EnumAlloyTechB.values()[x]));	
			}
			for(int x = 0; x < EnumAlloyDeco.size(); x++){
				OreDictionary.registerOre(EnumAlloyDeco.getDust(x),   alloy_deco_dust(1, EnumAlloyDeco.values()[x]));	
				OreDictionary.registerOre(EnumAlloyDeco.getIngot(x),  alloy_deco_ingot(1, EnumAlloyDeco.values()[x]));	
				OreDictionary.registerOre(EnumAlloyDeco.getNugget(x), alloy_deco_nugget(1, EnumAlloyDeco.values()[x]));	
			}
			for(int x = 0; x < EnumAlloyGems.size(); x++){
				OreDictionary.registerOre(EnumAlloyGems.getDust(x),   alloy_gems_dust(1, EnumAlloyGems.values()[x]));	
				OreDictionary.registerOre(EnumAlloyGems.getIngot(x),  alloy_gems_ingot(1, EnumAlloyGems.values()[x]));	
				OreDictionary.registerOre(EnumAlloyGems.getNugget(x), alloy_gems_nugget(1, EnumAlloyGems.values()[x]));	
			}
			for(int x = 0; x < EnumAlloyPart.size(); x++){
				OreDictionary.registerOre(EnumAlloyPart.getGear(x),   alloy_part_gear(1, EnumAlloyPart.values()[x]));	
				OreDictionary.registerOre(EnumAlloyPart.getPlate(x),  alloy_part_plate(1, EnumAlloyPart.values()[x]));	
				OreDictionary.registerOre(EnumAlloyPart.getCoin(x),	  alloy_part_coin(1, EnumAlloyPart.values()[x]));	
			}
			for(int x = 0; x < EnumMetalItems.size(); x++){
				OreDictionary.registerOre(EnumMetalItems.getOredict(x),   metal_items(1, EnumMetalItems.values()[x]));	
			}
		}
	}
}