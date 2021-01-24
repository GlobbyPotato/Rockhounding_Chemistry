package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyPart;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumMetalItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMobItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumAntimonate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumArsenate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumBorate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumCarbonate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumChromate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumHalide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumOxide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumPhosphate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSilicate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumVanadate;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumFilters;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumProbes;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSpeeds;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.handlers.RegistryHandler;
import com.globbypotato.rockhounding_chemistry.items.FilterItems;
import com.globbypotato.rockhounding_chemistry.items.MiscItems;
import com.globbypotato.rockhounding_chemistry.items.ProbeItems;
import com.globbypotato.rockhounding_chemistry.items.SamplingAmpoule;
import com.globbypotato.rockhounding_chemistry.items.SiliconCartridge;
import com.globbypotato.rockhounding_chemistry.items.SodiumPolyacrylate;
import com.globbypotato.rockhounding_chemistry.items.SpeedItems;
import com.globbypotato.rockhounding_chemistry.items.io.ArrayIO;
import com.globbypotato.rockhounding_chemistry.items.io.ConsumableIO;
import com.globbypotato.rockhounding_chemistry.items.io.ItemIO;
import com.globbypotato.rockhounding_chemistry.utils.OredictUtils;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Reference.MODID)
public class ModItems {

	// initialize the item
	public static final Item CRUSHING_GEAR = new ConsumableIO("crushing_gear", ModConfig.gearUses);
	public static final Item SLURRY_AGITATOR = new ConsumableIO("slurry_agitator", ModConfig.agitatorUses);
	public static final Item TEST_TUBE = new ConsumableIO("test_tube", ModConfig.tubeUses);
	public static final Item INGOT_PATTERN = new ConsumableIO("ingot_pattern", ModConfig.patternUses);
	public static final Item GRADUATED_CYLINDER = new ConsumableIO("graduated_cylinder", ModConfig.cylinderUses);
	public static final Item FE_CATALYST = new ConsumableIO("fe_catalyst", ModConfig.fe_catalystUses);
	public static final Item VA_CATALYST = new ConsumableIO("va_catalyst", ModConfig.va_catalystUses);
	public static final Item GR_CATALYST = new ConsumableIO("gr_catalyst", ModConfig.gr_catalystUses);
	public static final Item PT_CATALYST = new ConsumableIO("pt_catalyst", ModConfig.pt_catalystUses);
	public static final Item WG_CATALYST = new ConsumableIO("wg_catalyst", ModConfig.wg_catalystUses);
	public static final Item OS_CATALYST = new ConsumableIO("os_catalyst", ModConfig.os_catalystUses);
	public static final Item ZE_CATALYST = new ConsumableIO("ze_catalyst", ModConfig.ze_catalystUses);
	public static final Item ZN_CATALYST = new ConsumableIO("zn_catalyst", ModConfig.zn_catalystUses);
	public static final Item CO_CATALYST = new ConsumableIO("co_catalyst", ModConfig.co_catalystUses);
	public static final Item NI_CATALYST = new ConsumableIO("ni_catalyst", ModConfig.ni_catalystUses);
	public static final Item NL_CATALYST = new ConsumableIO("nl_catalyst", ModConfig.nl_catalystUses);
	public static final Item AU_CATALYST = new ConsumableIO("au_catalyst", ModConfig.au_catalystUses);
	public static final Item MO_CATALYST = new ConsumableIO("mo_catalyst", ModConfig.mo_catalystUses);
	public static final Item IN_CATALYST = new ConsumableIO("in_catalyst", ModConfig.in_catalystUses);
	public static final Item TILE_NULLIFIER = new ItemIO("tile_nullifier");
	public static final Item SILICONE_CARTRIDGE = new SiliconCartridge("silicone_cartridge", 100);
	public static final Item SODIUM_POLYACRYLATE = new SodiumPolyacrylate("sodium_polyacrylate");
	public static final Item SAMPLING_AMPOULE = new SamplingAmpoule("sampling_ampoule");

	public static final Item ALLOY_ITEMS_TECH = new ArrayIO("alloy_items_tech", EnumAlloyTech.getItemNames());
	public static final Item ALLOY_ITEMS_TECH_B = new ArrayIO("alloy_items_tech_b", EnumAlloyTechB.getItemNames());
	public static final Item ALLOY_ITEMS_DECO = new ArrayIO("alloy_items_deco", EnumAlloyDeco.getItemNames());
	public static final Item ALLOY_ITEMS_GEMS = new ArrayIO("alloy_items_gems", EnumAlloyGems.getItemNames());
	public static final Item METAL_ITEMS = new ArrayIO("metal_items", EnumMetalItems.getNames());
	public static final Item ALLOY_PARTS = new ArrayIO("alloy_parts", EnumAlloyPart.getItemNames());

	public static final Item ANTIMONATE_SHARDS = new ArrayIO("antimonate_shards", EnumAntimonate.getNames());
	public static final Item ARSENATE_SHARDS = new ArrayIO("arsenate_shards", EnumArsenate.getNames());
	public static final Item BORATE_SHARDS = new ArrayIO("borate_shards", EnumBorate.getNames());
	public static final Item CARBONATE_SHARDS = new ArrayIO("carbonate_shards", EnumCarbonate.getNames());
	public static final Item CHROMATE_SHARDS = new ArrayIO("chromate_shards", EnumChromate.getNames());
	public static final Item HALIDE_SHARDS = new ArrayIO("halide_shards", EnumHalide.getNames());
	public static final Item NATIVE_SHARDS = new ArrayIO("native_shards", EnumNative.getNames());
	public static final Item OXIDE_SHARDS = new ArrayIO("oxide_shards", EnumOxide.getNames());
	public static final Item PHOSPHATE_SHARDS = new ArrayIO("phosphate_shards", EnumPhosphate.getNames());
	public static final Item SILICATE_SHARDS = new ArrayIO("silicate_shards", EnumSilicate.getNames());
	public static final Item SULFATE_SHARDS = new ArrayIO("sulfate_shards", EnumSulfate.getNames());
	public static final Item SULFIDE_SHARDS = new ArrayIO("sulfide_shards", EnumSulfide.getNames());
	public static final Item VANADATE_SHARDS = new ArrayIO("vanadate_shards", EnumVanadate.getNames());
	public static final Item CHEMICAL_DUSTS = new ArrayIO("chemical_dusts", EnumElements.getNames());

	public static final Item PATTERN_ITEMS = new ArrayIO("pattern_items", EnumCasting.getNames());
	public static final Item CHEMICAL_ITEMS = new ArrayIO("chemical_items", EnumChemicals.getNames());
	public static final Item MISC_ITEMS = new MiscItems("misc_items", EnumMiscItems.getNames());
	public static final Item SPEED_ITEMS = new SpeedItems("speed_items", EnumSpeeds.getNames());
	public static final Item FILTER_ITEMS = new FilterItems("filter_items", EnumFilters.getNames());
	public static final Item PROBE_ITEMS = new ProbeItems("probe_items", EnumProbes.getNames());

	public static final Item MOB_ITEMS = new ArrayIO("mob_items", EnumMobItems.getNames());

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

		// register the item
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(TILE_NULLIFIER);
			registry.register(CRUSHING_GEAR);
			registry.register(SLURRY_AGITATOR);
			registry.register(TEST_TUBE);
			registry.register(INGOT_PATTERN);
			registry.register(GRADUATED_CYLINDER);
			registry.register(SILICONE_CARTRIDGE);
			registry.register(SODIUM_POLYACRYLATE);
			registry.register(SAMPLING_AMPOULE);
			registry.register(FE_CATALYST);
			registry.register(VA_CATALYST);
			registry.register(GR_CATALYST);
			registry.register(PT_CATALYST);
			registry.register(WG_CATALYST);
			registry.register(OS_CATALYST);
			registry.register(ZE_CATALYST);
			registry.register(ZN_CATALYST);
			registry.register(CO_CATALYST);
			registry.register(NI_CATALYST);
			registry.register(NL_CATALYST);
			registry.register(AU_CATALYST);
			registry.register(MO_CATALYST);
			registry.register(IN_CATALYST);
			registry.register(ANTIMONATE_SHARDS);
			registry.register(ARSENATE_SHARDS);
			registry.register(BORATE_SHARDS);
			registry.register(CARBONATE_SHARDS);
			registry.register(CHROMATE_SHARDS);
			registry.register(HALIDE_SHARDS);
			registry.register(NATIVE_SHARDS);
			registry.register(OXIDE_SHARDS);
			registry.register(PHOSPHATE_SHARDS);
			registry.register(SILICATE_SHARDS);
			registry.register(SULFATE_SHARDS);
			registry.register(SULFIDE_SHARDS);
			registry.register(VANADATE_SHARDS);
			registry.register(CHEMICAL_DUSTS);
			registry.register(PATTERN_ITEMS);
			registry.register(CHEMICAL_ITEMS);
			registry.register(MISC_ITEMS);
			registry.register(ALLOY_ITEMS_TECH);
			registry.register(ALLOY_ITEMS_TECH_B);
			registry.register(ALLOY_ITEMS_DECO);
			registry.register(ALLOY_ITEMS_GEMS);
			registry.register(METAL_ITEMS);
			registry.register(ALLOY_PARTS);
			registry.register(SPEED_ITEMS);
			registry.register(FILTER_ITEMS);
			registry.register(PROBE_ITEMS);
			registry.register(MOB_ITEMS);
			
			OredictUtils.registerOreDictItems();
		}

		// register the model
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event){
			RegistryHandler.registerSingleModel(TILE_NULLIFIER);
			RegistryHandler.registerSingleModel(CRUSHING_GEAR);
			RegistryHandler.registerSingleModel(SLURRY_AGITATOR);
			RegistryHandler.registerSingleModel(TEST_TUBE);
			RegistryHandler.registerSingleModel(INGOT_PATTERN);
			RegistryHandler.registerSingleModel(GRADUATED_CYLINDER);
			RegistryHandler.registerSingleModel(FE_CATALYST);
			RegistryHandler.registerSingleModel(VA_CATALYST);
			RegistryHandler.registerSingleModel(GR_CATALYST);
			RegistryHandler.registerSingleModel(PT_CATALYST);
			RegistryHandler.registerSingleModel(WG_CATALYST);
			RegistryHandler.registerSingleModel(OS_CATALYST);
			RegistryHandler.registerSingleModel(ZE_CATALYST);
			RegistryHandler.registerSingleModel(ZN_CATALYST);
			RegistryHandler.registerSingleModel(CO_CATALYST);
			RegistryHandler.registerSingleModel(NI_CATALYST);
			RegistryHandler.registerSingleModel(NL_CATALYST);
			RegistryHandler.registerSingleModel(AU_CATALYST);
			RegistryHandler.registerSingleModel(MO_CATALYST);
			RegistryHandler.registerSingleModel(IN_CATALYST);
			RegistryHandler.registerSingleModel(SILICONE_CARTRIDGE);
			RegistryHandler.registerSingleModel(SODIUM_POLYACRYLATE);
			RegistryHandler.registerSingleModel(SAMPLING_AMPOULE);
			RegistryHandler.registerMetaModel(ANTIMONATE_SHARDS, EnumAntimonate.getNames());
			RegistryHandler.registerMetaModel(ARSENATE_SHARDS, EnumArsenate.getNames());
			RegistryHandler.registerMetaModel(BORATE_SHARDS, EnumBorate.getNames());
			RegistryHandler.registerMetaModel(CARBONATE_SHARDS, EnumCarbonate.getNames());
			RegistryHandler.registerMetaModel(CHROMATE_SHARDS, EnumChromate.getNames());
			RegistryHandler.registerMetaModel(HALIDE_SHARDS, EnumHalide.getNames());
			RegistryHandler.registerMetaModel(NATIVE_SHARDS, EnumNative.getNames());
			RegistryHandler.registerMetaModel(OXIDE_SHARDS, EnumOxide.getNames());
			RegistryHandler.registerMetaModel(PHOSPHATE_SHARDS, EnumPhosphate.getNames());
			RegistryHandler.registerMetaModel(SILICATE_SHARDS, EnumSilicate.getNames());
			RegistryHandler.registerMetaModel(SULFATE_SHARDS, EnumSulfate.getNames());
			RegistryHandler.registerMetaModel(SULFIDE_SHARDS, EnumSulfide.getNames());
			RegistryHandler.registerMetaModel(VANADATE_SHARDS, EnumVanadate.getNames());
			RegistryHandler.registerMetaModel(CHEMICAL_ITEMS, EnumChemicals.getNames());
			RegistryHandler.registerMetaModel(CHEMICAL_DUSTS, EnumElements.getNames());
			RegistryHandler.registerMetaModel(PATTERN_ITEMS, EnumCasting.getNames());
			RegistryHandler.registerMetaModel(MISC_ITEMS, EnumMiscItems.getNames());
			RegistryHandler.registerMetaModel(ALLOY_ITEMS_TECH_B, EnumAlloyTechB.getItemNames());
			RegistryHandler.registerMetaModel(ALLOY_ITEMS_TECH, EnumAlloyTech.getItemNames());
			RegistryHandler.registerMetaModel(ALLOY_ITEMS_DECO, EnumAlloyDeco.getItemNames());
			RegistryHandler.registerMetaModel(ALLOY_ITEMS_GEMS, EnumAlloyGems.getItemNames());
			RegistryHandler.registerMetaModel(METAL_ITEMS, EnumMetalItems.getNames());
			RegistryHandler.registerMetaModel(ALLOY_PARTS, EnumAlloyPart.getItemNames());
			RegistryHandler.registerMetaModel(SPEED_ITEMS, EnumSpeeds.getNames());
			RegistryHandler.registerMetaModel(FILTER_ITEMS, EnumFilters.getNames());
			RegistryHandler.registerMetaModel(PROBE_ITEMS, EnumProbes.getNames());
			RegistryHandler.registerMetaModel(MOB_ITEMS, EnumMobItems.getNames());
		}

	}

}