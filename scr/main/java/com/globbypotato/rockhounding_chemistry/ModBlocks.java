package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.blocks.AlloyBlocksDeco;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBlocksGems;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBlocksTech;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBlocksTechB;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBricksDeco;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBricksGems;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBricksTech;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBricksTechB;
import com.globbypotato.rockhounding_chemistry.blocks.DidymiumGlass;
import com.globbypotato.rockhounding_chemistry.blocks.MineralOres;
import com.globbypotato.rockhounding_chemistry.blocks.MiscBlocksA;
import com.globbypotato.rockhounding_chemistry.blocks.ToxicCloud;
import com.globbypotato.rockhounding_chemistry.blocks.UninspectedMineral;
import com.globbypotato.rockhounding_chemistry.blocks.Waterlock;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTech;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;
import com.globbypotato.rockhounding_chemistry.enums.EnumDidymium;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesC;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesE;
import com.globbypotato.rockhounding_chemistry.enums.EnumMinerals;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumWaterlock;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.handlers.RegistryHandler;
import com.globbypotato.rockhounding_chemistry.machines.GaslineDuct;
import com.globbypotato.rockhounding_chemistry.machines.GaslineHalt;
import com.globbypotato.rockhounding_chemistry.machines.GaslinePump;
import com.globbypotato.rockhounding_chemistry.machines.MachinesA;
import com.globbypotato.rockhounding_chemistry.machines.MachinesB;
import com.globbypotato.rockhounding_chemistry.machines.MachinesC;
import com.globbypotato.rockhounding_chemistry.machines.MachinesD;
import com.globbypotato.rockhounding_chemistry.machines.MachinesE;
import com.globbypotato.rockhounding_chemistry.machines.PipelineDuct;
import com.globbypotato.rockhounding_chemistry.machines.PipelineHalt;
import com.globbypotato.rockhounding_chemistry.machines.PipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.PipelineValve;
import com.globbypotato.rockhounding_core.blocks.itemblocks.BaseMetaIB;
import com.globbypotato.rockhounding_core.blocks.itemblocks.PoweredMetaIB;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Reference.MODID)
public class ModBlocks {

	// initialize the block
	public static final Block UNINSPECTED_MINERAL = new UninspectedMineral("uninspected_mineral");
	public static final Block MINERAL_ORES = new MineralOres("mineral_ores");
	public static final Block MISC_BLOCKS_A = new MiscBlocksA("misc_blocks_a");
	public static final Block MACHINES_A = new MachinesA("machines_a");
	public static final Block MACHINES_B = new MachinesB("machines_b");
	public static final Block MACHINES_C = new MachinesC("machines_c");
	public static final Block MACHINES_D = new MachinesD("machines_d");
	public static final Block MACHINES_E = new MachinesE("machines_e");

	public static final Block ALLOY_BLOCKS_TECH = new AlloyBlocksTech("alloy_blocks_tech");
	public static final Block ALLOY_BRICKS_TECH = new AlloyBricksTech("alloy_bricks_tech");
	public static final Block ALLOY_BLOCKS_DECO = new AlloyBlocksDeco("alloy_blocks_deco");
	public static final Block ALLOY_BRICKS_DECO = new AlloyBricksDeco("alloy_bricks_deco");
	public static final Block ALLOY_BLOCKS_GEMS = new AlloyBlocksGems("alloy_blocks_gems");
	public static final Block ALLOY_BRICKS_GEMS = new AlloyBricksGems("alloy_bricks_gems");
	public static final Block ALLOY_BLOCKS_TECH_B = new AlloyBlocksTechB("alloy_blocks_tech_b");
	public static final Block ALLOY_BRICKS_TECH_B = new AlloyBricksTechB("alloy_bricks_tech_b");

	public static final Block PIPELINE_DUCT = new PipelineDuct("pipeline_duct");
	public static final Block PIPELINE_PUMP = new PipelinePump("pipeline_pump");
	public static final Block PIPELINE_VALVE = new PipelineValve("pipeline_valve");
	public static final Block GASLINE_DUCT = new GaslineDuct("gasline_duct");
	public static final Block GASLINE_PUMP = new GaslinePump("gasline_pump");
	public static final Block PIPELINE_HALT = new PipelineHalt("pipeline_halt");
	public static final Block GASLINE_HALT = new GaslineHalt("gasline_halt");

	public static final Block DIDYMIUM_GLASS = new DidymiumGlass("didymium_glass");

	public static final Block WATERLOCK = new Waterlock("waterlock");

	public static final Block TOXIC_CLOUD = new ToxicCloud("toxic_cloud");

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

		// register the block block
		@SubscribeEvent
		public static void registerBlock(final RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();
			registry.register(UNINSPECTED_MINERAL);
			registry.register(MINERAL_ORES);
			registry.register(MISC_BLOCKS_A);
			registry.register(MACHINES_A);
			registry.register(MACHINES_B);
			registry.register(MACHINES_C);
			registry.register(MACHINES_D);
			registry.register(MACHINES_E);

			registry.register(ALLOY_BLOCKS_TECH);
			registry.register(ALLOY_BRICKS_TECH);
			registry.register(ALLOY_BLOCKS_DECO);
			registry.register(ALLOY_BRICKS_DECO);
			registry.register(ALLOY_BLOCKS_GEMS);
			registry.register(ALLOY_BRICKS_GEMS);
			registry.register(ALLOY_BLOCKS_TECH_B);
			registry.register(ALLOY_BRICKS_TECH_B);

			registry.register(PIPELINE_DUCT);
			registry.register(PIPELINE_PUMP);
			registry.register(PIPELINE_VALVE);
			registry.register(GASLINE_DUCT);
			registry.register(GASLINE_PUMP);
			registry.register(PIPELINE_HALT);
			registry.register(GASLINE_HALT);
			
			registry.register(DIDYMIUM_GLASS);

			registry.register(WATERLOCK);

			registry.register(TOXIC_CLOUD);

		}

		// register the itemblock
		@SubscribeEvent
		public static void registerItemBlock(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(new ItemBlock(UNINSPECTED_MINERAL).setRegistryName(UNINSPECTED_MINERAL.getRegistryName()));
			registry.register(new BaseMetaIB(MINERAL_ORES, EnumMinerals.getNames()).setRegistryName(MINERAL_ORES.getRegistryName()));
			registry.register(new BaseMetaIB(MISC_BLOCKS_A, EnumMiscBlocksA.getNames()).setRegistryName(MISC_BLOCKS_A.getRegistryName()));
			registry.register(new PoweredMetaIB(MACHINES_A, EnumMachinesA.getNames()).setRegistryName(MACHINES_A.getRegistryName()));
			registry.register(new PoweredMetaIB(MACHINES_B, EnumMachinesB.getNames()).setRegistryName(MACHINES_B.getRegistryName()));
			registry.register(new PoweredMetaIB(MACHINES_C, EnumMachinesC.getNames()).setRegistryName(MACHINES_C.getRegistryName()));
			registry.register(new PoweredMetaIB(MACHINES_D, EnumMachinesD.getNames()).setRegistryName(MACHINES_D.getRegistryName()));
			registry.register(new PoweredMetaIB(MACHINES_E, EnumMachinesE.getNames()).setRegistryName(MACHINES_E.getRegistryName()));

			registry.register(new BaseMetaIB(ALLOY_BLOCKS_TECH, EnumAlloyTech.getAlloys()).setRegistryName(ALLOY_BLOCKS_TECH.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BRICKS_TECH, EnumAlloyTech.getAlloys()).setRegistryName(ALLOY_BRICKS_TECH.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BLOCKS_DECO, EnumAlloyDeco.getAlloys()).setRegistryName(ALLOY_BLOCKS_DECO.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BRICKS_DECO, EnumAlloyDeco.getAlloys()).setRegistryName(ALLOY_BRICKS_DECO.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BLOCKS_GEMS, EnumAlloyGems.getAlloys()).setRegistryName(ALLOY_BLOCKS_GEMS.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BRICKS_GEMS, EnumAlloyGems.getAlloys()).setRegistryName(ALLOY_BRICKS_GEMS.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BLOCKS_TECH_B, EnumAlloyTechB.getAlloys()).setRegistryName(ALLOY_BLOCKS_TECH_B.getRegistryName()));
			registry.register(new BaseMetaIB(ALLOY_BRICKS_TECH_B, EnumAlloyTechB.getAlloys()).setRegistryName(ALLOY_BRICKS_TECH_B.getRegistryName()));

			registry.register(new ItemBlock(PIPELINE_DUCT).setRegistryName(PIPELINE_DUCT.getRegistryName()));
			registry.register(new ItemBlock(PIPELINE_PUMP).setRegistryName(PIPELINE_PUMP.getRegistryName()));
			registry.register(new ItemBlock(PIPELINE_VALVE).setRegistryName(PIPELINE_VALVE.getRegistryName()));
			registry.register(new ItemBlock(GASLINE_DUCT).setRegistryName(GASLINE_DUCT.getRegistryName()));
			registry.register(new ItemBlock(GASLINE_PUMP).setRegistryName(GASLINE_PUMP.getRegistryName()));
			registry.register(new ItemBlock(PIPELINE_HALT).setRegistryName(PIPELINE_HALT.getRegistryName()));
			registry.register(new ItemBlock(GASLINE_HALT).setRegistryName(GASLINE_HALT.getRegistryName()));
			
			registry.register(new BaseMetaIB(DIDYMIUM_GLASS, EnumDidymium.getNames()).setRegistryName(DIDYMIUM_GLASS.getRegistryName()));
			
			registry.register(new BaseMetaIB(WATERLOCK, EnumWaterlock.getNames()).setRegistryName(WATERLOCK.getRegistryName()));

			registry.register(new ItemBlock(TOXIC_CLOUD).setRegistryName(TOXIC_CLOUD.getRegistryName()));

		}

		// register the item model
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event){
			RegistryHandler.registerSingleModel(UNINSPECTED_MINERAL);
			RegistryHandler.registerMetaModel(MINERAL_ORES, EnumMinerals.getNames());
			RegistryHandler.registerMetaModel(MISC_BLOCKS_A, EnumMiscBlocksA.getNames());
			RegistryHandler.registerMetaModel(MACHINES_A, EnumMachinesA.getNames());
			RegistryHandler.registerMetaModel(MACHINES_B, EnumMachinesB.getNames());
			RegistryHandler.registerMetaModel(MACHINES_C, EnumMachinesC.getNames());
			RegistryHandler.registerMetaModel(MACHINES_D, EnumMachinesD.getNames());
			RegistryHandler.registerMetaModel(MACHINES_E, EnumMachinesE.getNames());

			RegistryHandler.registerMetaModel(ALLOY_BLOCKS_TECH, EnumAlloyTech.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BRICKS_TECH, EnumAlloyTech.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BLOCKS_DECO, EnumAlloyDeco.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BRICKS_DECO, EnumAlloyDeco.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BLOCKS_GEMS, EnumAlloyGems.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BRICKS_GEMS, EnumAlloyGems.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BLOCKS_TECH_B, EnumAlloyTechB.getAlloys());
			RegistryHandler.registerMetaModel(ALLOY_BRICKS_TECH_B, EnumAlloyTechB.getAlloys());

			RegistryHandler.registerSingleModel(PIPELINE_DUCT);
			RegistryHandler.registerSingleModel(PIPELINE_PUMP);
			RegistryHandler.registerSingleModel(PIPELINE_VALVE);
			RegistryHandler.registerSingleModel(GASLINE_DUCT);
			RegistryHandler.registerSingleModel(GASLINE_PUMP);
			RegistryHandler.registerSingleModel(PIPELINE_HALT);
			RegistryHandler.registerSingleModel(GASLINE_HALT);
			
			RegistryHandler.registerMetaModel(DIDYMIUM_GLASS, EnumDidymium.getNames());

			RegistryHandler.registerMetaModel(WATERLOCK, EnumWaterlock.getNames());

			RegistryHandler.registerSingleModel(TOXIC_CLOUD);

		}

	}
}