package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.container.COAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.container.COBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COCatalystRegen;
import com.globbypotato.rockhounding_chemistry.machines.container.COContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.container.CODepositionBase;
import com.globbypotato.rockhounding_chemistry.machines.container.CODepositionController;
import com.globbypotato.rockhounding_chemistry.machines.container.CODisposer;
import com.globbypotato.rockhounding_chemistry.machines.container.COElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.container.COElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.container.COEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COExtractorController;
import com.globbypotato.rockhounding_chemistry.machines.container.COExtractorStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.container.COFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.container.COFluidTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COFluidpedia;
import com.globbypotato.rockhounding_chemistry.machines.container.COGanController;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasCondenserController;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasExpanderController;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasPurifierController;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasifierController;
import com.globbypotato.rockhounding_chemistry.machines.container.COHeatExchangerController;
import com.globbypotato.rockhounding_chemistry.machines.container.COLabBlenderController;
import com.globbypotato.rockhounding_chemistry.machines.container.COLabBlenderTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COLabOvenController;
import com.globbypotato.rockhounding_chemistry.machines.container.COLeachingVatController;
import com.globbypotato.rockhounding_chemistry.machines.container.COLeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.container.COMaterialCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.container.COMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.machines.container.COMineralSizerCabinet;
import com.globbypotato.rockhounding_chemistry.machines.container.COMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.container.COMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.container.COMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.container.COMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.container.COOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.container.COPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.container.COPlanningTable;
import com.globbypotato.rockhounding_chemistry.machines.container.COPowderMixerController;
import com.globbypotato.rockhounding_chemistry.machines.container.COPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.container.COPrecipitationChamber;
import com.globbypotato.rockhounding_chemistry.machines.container.COPrecipitationController;
import com.globbypotato.rockhounding_chemistry.machines.container.COPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.container.COPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.machines.container.COPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.machines.container.COReformerController;
import com.globbypotato.rockhounding_chemistry.machines.container.COReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.container.COReinforcedCistern;
import com.globbypotato.rockhounding_chemistry.machines.container.CORetentionVatController;
import com.globbypotato.rockhounding_chemistry.machines.container.COSeasoningRack;
import com.globbypotato.rockhounding_chemistry.machines.container.COServer;
import com.globbypotato.rockhounding_chemistry.machines.container.COShredderController;
import com.globbypotato.rockhounding_chemistry.machines.container.COSlurryDrum;
import com.globbypotato.rockhounding_chemistry.machines.container.COSlurryPondController;
import com.globbypotato.rockhounding_chemistry.machines.container.COSpecimenCollector;
import com.globbypotato.rockhounding_chemistry.machines.container.COStirredTankController;
import com.globbypotato.rockhounding_chemistry.machines.container.COStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.container.COTransposer;
import com.globbypotato.rockhounding_chemistry.machines.container.COTubularBedController;
import com.globbypotato.rockhounding_chemistry.machines.container.COTubularBedLow;
import com.globbypotato.rockhounding_chemistry.machines.container.COUnloader;
import com.globbypotato.rockhounding_chemistry.machines.container.COWasteDumper;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UICatalystRegen;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIDepositionBase;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIDepositionController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIDisposer;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIExtractorController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIExtractorStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIFluidTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIFluidpedia;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGanController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasCondenserController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasExpanderController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasPurifierController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasifierController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIHeatExchangerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabBlenderController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabBlenderTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabOvenController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILeachingVatController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMaterialCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerCabinet;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPlanningTable;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPowderMixerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPrecipitationChamber;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPrecipitationController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIReformerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIReinforcedCistern;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIRetentionVatController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISeasoningRack;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIServer;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIShredderController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISlurryDrum;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISlurryPondController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISpecimenCollector;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIStirredTankController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITransposer;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITubularBedController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITubularBedLow;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIUnloader;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIWasteDumper;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEDepositionController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGanController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasCondenserController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasExpanderController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasPurifierController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEHeatExchangerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabBlenderController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabOvenController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELeachingVatController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPlanningTable;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPowderMixerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPrecipitationController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TERetentionVatController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEShredderController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TESlurryPondController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEStirredTankController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TETubularBedController;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TECatalystRegen;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEFluidpedia;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEDisposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TESeasoningRack;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TETransposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWasteDumper;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEDepositionBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELabBlenderTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCabinet;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEPrecipitationChamber;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TESpecimenCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedLow;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEReinforcedCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TESlurryDrum;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int mineral_sizer_controller_id = 0;
	public static final int mineral_sizer_tank_id = 1;
	public static final int power_generator_id = 2;
	public static final int mineral_sizer_collector_id = 3;
	public static final int fluid_tank_id = 4;
	public static final int lab_oven_controller_id = 5;
	public static final int mineral_sizer_cabinet_id = 6;
	public static final int unloader_id = 7;
	public static final int lab_blender_controller_id = 8;
	public static final int lab_blender_tank_id = 9;
	public static final int evaporation_tank_id = 10;
	public static final int seasoning_rack_id = 11;
	public static final int server_id = 12;
	public static final int gas_expander_id = 13;

	public static final int slurry_pond_id = 16;
	public static final int gasifier_tank_id = 17;
	public static final int gasifier_controller_id = 18;
	public static final int shredder_controller_id = 19;
	public static final int gasifier_purifier_id = 20;
	public static final int fluid_cistern_id=21;
	public static final int pressure_vessel_id=22;
	public static final int air_compressor_id=23;
	public static final int gan_exchanger_base_id=24;

	public static final int gan_controller_id=32;
	public static final int multivessel_id = 33;
	public static final int gas_condenser_id = 34;
	public static final int leaching_vat_tank_id = 35;
	public static final int leaching_vat_controller_id = 36;
	public static final int leaching_vat_collector_id = 37;
	public static final int retention_vat_id = 38;
	public static final int planning_table_id = 39;
	public static final int extraction_cabinet_id = 40;
	public static final int extraction_controller_id = 41;
	public static final int extraction_stabilizer_id = 42;
	public static final int reformer_controller_id = 43;
	public static final int reformer_reactor_id = 44;
	public static final int extraction_cabinet_injector_id = 45;

	public static final int metal_alloyer_id = 48;
	public static final int material_cabinet_id = 49;
	public static final int material_cabinet_injector_id = 50;
	public static final int deposition_chamber_id = 51;
	public static final int deposition_chamber_top_id = 52;
	public static final int gas_holder_id = 53;
	public static final int pulling_crucible_base_id = 54;
	public static final int pulling_crucible_top_id = 55;
	public static final int orbiter_id = 56;
	public static final int transposer_id = 57;
	public static final int fluidpedia_id = 58;
	public static final int waste_dumper_id = 59;
	public static final int flotation_tank_id = 60;
	public static final int containment_tank_id = 61;

	public static final int catalyst_regen_id = 65;
	public static final int disposer_id = 66;
	public static final int slurry_drum_id = 67;
	public static final int buffer_tank_id = 68;
	public static final int stirred_tank_id = 69;
	public static final int stirred_tank_out_id = 70;
	public static final int precipitation_chamber_id = 71;
	public static final int precipitation_controller_id = 72;
	public static final int powder_mixer_id = 73;

	public static final int tubular_bed_mid_id = 81;
	public static final int tubular_bed_monitor_id = 82;

	public static final int pipeline_valve_ID = 100;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
			default: return null;
			case pipeline_valve_ID:
				if (entity != null && entity instanceof TEPipelineValve){return new COPipelineValve(player.inventory, (TEPipelineValve) entity);}
				break;
			case mineral_sizer_controller_id:
				if (entity != null && entity instanceof TEMineralSizerController){return new COMineralSizerController(player.inventory, (TEMineralSizerController) entity);}
				break;
			case mineral_sizer_tank_id:
				if (entity != null && entity instanceof TEMineralSizerTank){return new COMineralSizerTank(player.inventory, (TEMineralSizerTank) entity);}
				break;
			case power_generator_id:
				if (entity != null && entity instanceof TEPowerGenerator){return new COPowerGenerator(player.inventory, (TEPowerGenerator) entity);}
				break;
			case mineral_sizer_collector_id:
				if (entity != null && entity instanceof TEMineralSizerCollector){return new COMineralSizerCollector(player.inventory, (TEMineralSizerCollector) entity);}
				break;
			case fluid_tank_id:
				if (entity != null && entity instanceof TEFluidTank){return new COFluidTank(player.inventory, (TEFluidTank) entity);}
				break;
			case lab_oven_controller_id:
				if (entity != null && entity instanceof TELabOvenController){return new COLabOvenController(player.inventory, (TELabOvenController) entity);}
				break;
			case mineral_sizer_cabinet_id:
				if (entity != null && entity instanceof TEMineralSizerCabinet){return new COMineralSizerCabinet(player.inventory, (TEMineralSizerCabinet) entity);}
				break;
			case unloader_id:
				if (entity != null && entity instanceof TEUnloader){return new COUnloader(player.inventory, (TEUnloader) entity);}
				break;
			case lab_blender_controller_id:
				if (entity != null && entity instanceof TELabBlenderController){return new COLabBlenderController(player.inventory, (TELabBlenderController) entity);}
				break;
			case lab_blender_tank_id:
				if (entity != null && entity instanceof TELabBlenderTank){return new COLabBlenderTank(player.inventory, (TELabBlenderTank) entity);}
				break;
			case evaporation_tank_id:
				if (entity != null && entity instanceof TEEvaporationTank){return new COEvaporationTank(player.inventory, (TEEvaporationTank) entity);}
				break;
			case seasoning_rack_id:
				if (entity != null && entity instanceof TESeasoningRack){return new COSeasoningRack(player.inventory, (TESeasoningRack) entity);}
				break;
			case server_id:
				if (entity != null && entity instanceof TEServer){return new COServer(player.inventory, (TEServer) entity);}
				break;
			case slurry_pond_id:
				if (entity != null && entity instanceof TESlurryPondController){return new COSlurryPondController(player.inventory, (TESlurryPondController) entity);}
				break;
			case gasifier_tank_id:
				if (entity != null && entity instanceof TEReinforcedCistern){return new COReinforcedCistern(player.inventory, (TEReinforcedCistern) entity);}
				break;
			case gasifier_controller_id:
				if (entity != null && entity instanceof TEGasifierController){return new COGasifierController(player.inventory, (TEGasifierController) entity);}
				break;
			case shredder_controller_id:
				if (entity != null && entity instanceof TEShredderController){return new COShredderController(player.inventory, (TEShredderController) entity);}
				break;
			case gasifier_purifier_id:
				if (entity != null && entity instanceof TEGasPurifierController){return new COGasPurifierController(player.inventory, (TEGasPurifierController) entity);}
				break;
			case fluid_cistern_id:
				if (entity != null && entity instanceof TEFluidCistern){return new COFluidCistern(player.inventory, (TEFluidCistern) entity);}
				break;
			case pressure_vessel_id:
				if (entity != null && entity instanceof TEPressureVessel){return new COPressureVessel(player.inventory, (TEPressureVessel) entity);}
				break;
			case air_compressor_id:
				if (entity != null && entity instanceof TEAirCompressor){return new COAirCompressor(player.inventory, (TEAirCompressor) entity);}
				break;
			case gan_exchanger_base_id:
				if (entity != null && entity instanceof TEHeatExchangerController){return new COHeatExchangerController(player.inventory, (TEHeatExchangerController) entity);}
				break;
			case gan_controller_id:
				if (entity != null && entity instanceof TEGanController){return new COGanController(player.inventory, (TEGanController) entity);}
				break;
			case gas_condenser_id:
				if (entity != null && entity instanceof TEGasCondenserController){return new COGasCondenserController(player.inventory, (TEGasCondenserController) entity);}
				break;
			case multivessel_id:
				if (entity != null && entity instanceof TEMultivessel){return new COMultivessel(player.inventory, (TEMultivessel) entity);}
				break;
			case leaching_vat_controller_id:
				if (entity != null && entity instanceof TELeachingVatController){return new COLeachingVatController(player.inventory, (TELeachingVatController) entity);}
				break;
			case leaching_vat_tank_id:
				if (entity != null && entity instanceof TELeachingVatTank){return new COLeachingVatTank(player.inventory, (TELeachingVatTank) entity);}
				break;
			case leaching_vat_collector_id:
				if (entity != null && entity instanceof TESpecimenCollector){return new COSpecimenCollector(player.inventory, (TESpecimenCollector) entity);}
				break;
			case retention_vat_id:
				if (entity != null && entity instanceof TERetentionVatController){return new CORetentionVatController(player.inventory, (TERetentionVatController) entity);}
				break;
			case extraction_cabinet_id:
				if (entity != null && entity instanceof TEElementsCabinetBase){return new COElementsCabinetBase(player.inventory, (TEElementsCabinetBase) entity);}
				break;
			case extraction_controller_id:
				if (entity != null && entity instanceof TEExtractorController){return new COExtractorController(player.inventory, (TEExtractorController) entity);}
				break;
			case extraction_stabilizer_id:
				if (entity != null && entity instanceof TEExtractorStabilizer){return new COExtractorStabilizer(player.inventory, (TEExtractorStabilizer) entity);}
				break;
			case reformer_controller_id:
				if (entity != null && entity instanceof TEReformerController){return new COReformerController(player.inventory, (TEReformerController) entity);}
				break;
			case reformer_reactor_id:
				if (entity != null && entity instanceof TEReformerReactor){return new COReformerReactor(player.inventory, (TEReformerReactor) entity);}
				break;
			case gas_expander_id:
				if (entity != null && entity instanceof TEGasExpanderController){return new COGasExpanderController(player.inventory, (TEGasExpanderController) entity);}
				break;
			case extraction_cabinet_injector_id:
				if (entity != null && entity instanceof TEElementsCabinetTop){return new COElementsCabinetTop(player.inventory, (TEElementsCabinetTop) entity);}
				break;
			case metal_alloyer_id:
				if (entity != null && entity instanceof TEMetalAlloyerController){return new COMetalAlloyerController(player.inventory, (TEMetalAlloyerController) entity);}
				break;
			case material_cabinet_id:
				if (entity != null && entity instanceof TEMaterialCabinetBase){return new COMaterialCabinetBase(player.inventory, (TEMaterialCabinetBase) entity);}
				break;
			case material_cabinet_injector_id:
				if (entity != null && entity instanceof TEMaterialCabinetTop){return new COMaterialCabinetTop(player.inventory, (TEMaterialCabinetTop) entity);}
				break;
			case deposition_chamber_id:
				if (entity != null && entity instanceof TEDepositionBase){return new CODepositionBase(player.inventory, (TEDepositionBase) entity);}
				break;
			case deposition_chamber_top_id:
				if (entity != null && entity instanceof TEDepositionController){return new CODepositionController(player.inventory, (TEDepositionController) entity);}
				break;
			case gas_holder_id:
				if (entity != null && entity instanceof TEGasHolderBase){return new COGasHolderBase(player.inventory, (TEGasHolderBase) entity);}
				break;
			case pulling_crucible_base_id:
				if (entity != null && entity instanceof TEPullingCrucibleController){return new COPullingCrucibleController(player.inventory, (TEPullingCrucibleController) entity);}
				break;
			case pulling_crucible_top_id:
				if (entity != null && entity instanceof TEPullingCrucibleTop){return new COPullingCrucibleTop(player.inventory, (TEPullingCrucibleTop) entity);}
				break;
			case orbiter_id:
				if (entity != null && entity instanceof TEOrbiter){return new COOrbiter(player.inventory, (TEOrbiter) entity);}
				break;
			case transposer_id:
				if (entity != null && entity instanceof TETransposer){return new COTransposer(player.inventory, (TETransposer) entity);}
				break;
			case fluidpedia_id:
				if (entity != null && entity instanceof TEFluidpedia){return new COFluidpedia(player.inventory, (TEFluidpedia) entity);}
				break;
			case waste_dumper_id:
				if (entity != null && entity instanceof TEWasteDumper){return new COWasteDumper(player.inventory, (TEWasteDumper) entity);}
				break;
			case flotation_tank_id:
				if (entity != null && entity instanceof TEFlotationTank){return new COFlotationTank(player.inventory, (TEFlotationTank) entity);}
				break;
			case containment_tank_id:
				if (entity != null && entity instanceof TEContainmentTank){return new COContainmentTank(player.inventory, (TEContainmentTank) entity);}
				break;
			case catalyst_regen_id:
				if (entity != null && entity instanceof TECatalystRegen){return new COCatalystRegen(player.inventory, (TECatalystRegen) entity);}
				break;
			case powder_mixer_id:
				if (entity != null && entity instanceof TEPowderMixerController){return new COPowderMixerController(player.inventory, (TEPowderMixerController) entity);}
				break;
			case disposer_id:
				if (entity != null && entity instanceof TEDisposer){return new CODisposer(player.inventory, (TEDisposer) entity);}
				break;
			case slurry_drum_id:
				if (entity != null && entity instanceof TESlurryDrum){return new COSlurryDrum(player.inventory, (TESlurryDrum) entity);}
				break;
			case buffer_tank_id:
				if (entity != null && entity instanceof TEBufferTank){return new COBufferTank(player.inventory, (TEBufferTank) entity);}
				break;
			case stirred_tank_id:
				if (entity != null && entity instanceof TEStirredTankController){return new COStirredTankController(player.inventory, (TEStirredTankController) entity);}
				break;
			case stirred_tank_out_id:
				if (entity != null && entity instanceof TEStirredTankOut){return new COStirredTankOut(player.inventory, (TEStirredTankOut) entity);}
				break;
			case precipitation_chamber_id:
				if (entity != null && entity instanceof TEPrecipitationChamber){return new COPrecipitationChamber(player.inventory, (TEPrecipitationChamber) entity);}
				break;
			case precipitation_controller_id:
				if (entity != null && entity instanceof TEPrecipitationController){return new COPrecipitationController(player.inventory, (TEPrecipitationController) entity);}
				break;
			case tubular_bed_mid_id:
				if (entity != null && entity instanceof TETubularBedLow){return new COTubularBedLow(player.inventory, (TETubularBedLow) entity);}
				break;
			case tubular_bed_monitor_id:
				if (entity != null && entity instanceof TETubularBedController){return new COTubularBedController(player.inventory, (TETubularBedController) entity);}
				break;
			case planning_table_id:
				if (entity != null && entity instanceof TEPlanningTable){return new COPlanningTable(player.inventory, (TEPlanningTable) entity);}
				break;
		}
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
			default: return null;
			case pipeline_valve_ID:
				if (entity != null && entity instanceof TEPipelineValve){return new UIPipelineValve(player.inventory, (TEPipelineValve) entity);}
				break;
			case mineral_sizer_controller_id:
				if (entity != null && entity instanceof TEMineralSizerController) {return new UIMineralSizerController(player.inventory, (TEMineralSizerController) entity);}
				break;
			case mineral_sizer_tank_id:
				if (entity != null && entity instanceof TEMineralSizerTank){return new UIMineralSizerTank(player.inventory, (TEMineralSizerTank) entity);}
				break;
			case power_generator_id:
				if (entity != null && entity instanceof TEPowerGenerator){return new UIPowerGenerator(player.inventory, (TEPowerGenerator) entity);}
				break;
			case mineral_sizer_collector_id:
				if (entity != null && entity instanceof TEMineralSizerCollector){return new UIMineralSizerCollector(player.inventory, (TEMineralSizerCollector) entity);}
				break;
			case fluid_tank_id:
				if (entity != null && entity instanceof TEFluidTank){return new UIFluidTank(player.inventory, (TEFluidTank) entity);}
				break;
			case lab_oven_controller_id:
				if (entity != null && entity instanceof TELabOvenController){return new UILabOvenController(player.inventory, (TELabOvenController) entity);}
				break;
			case shredder_controller_id:
				if (entity != null && entity instanceof TEShredderController){return new UIShredderController(player.inventory, (TEShredderController) entity);}
				break;
			case unloader_id:
				if (entity != null && entity instanceof TEUnloader){return new UIUnloader(player.inventory, (TEUnloader) entity);}
				break;
			case lab_blender_controller_id:
				if (entity != null && entity instanceof TELabBlenderController){return new UILabBlenderController(player.inventory, (TELabBlenderController) entity);}
				break;
			case lab_blender_tank_id:
				if (entity != null && entity instanceof TELabBlenderTank){return new UILabBlenderTank(player.inventory, (TELabBlenderTank) entity);}
				break;
			case evaporation_tank_id:
				if (entity != null && entity instanceof TEEvaporationTank){return new UIEvaporationTank(player.inventory, (TEEvaporationTank) entity);}
				break;
			case seasoning_rack_id:
				if (entity != null && entity instanceof TESeasoningRack){return new UISeasoningRack(player.inventory, (TESeasoningRack) entity);}
				break;
			case server_id:
				if (entity != null && entity instanceof TEServer){return new UIServer(player.inventory, (TEServer) entity);}
				break;
			case slurry_pond_id:
				if (entity != null && entity instanceof TESlurryPondController){return new UISlurryPondController(player.inventory, (TESlurryPondController) entity);}
				break;
			case gasifier_tank_id:
				if (entity != null && entity instanceof TEReinforcedCistern){return new UIReinforcedCistern(player.inventory, (TEReinforcedCistern) entity);}
				break;
			case gasifier_controller_id:
				if (entity != null && entity instanceof TEGasifierController){return new UIGasifierController(player.inventory, (TEGasifierController) entity);}
				break;
			case mineral_sizer_cabinet_id:
				if (entity != null && entity instanceof TEMineralSizerCabinet){return new UIMineralSizerCabinet(player.inventory, (TEMineralSizerCabinet) entity);}
				break;
			case gasifier_purifier_id:
				if (entity != null && entity instanceof TEGasPurifierController){return new UIGasPurifierController(player.inventory, (TEGasPurifierController) entity);}
				break;
			case fluid_cistern_id:
				if (entity != null && entity instanceof TEFluidCistern){return new UIFluidCistern(player.inventory, (TEFluidCistern) entity);}
				break;
			case pressure_vessel_id:
				if (entity != null && entity instanceof TEPressureVessel){return new UIPressureVessel(player.inventory, (TEPressureVessel) entity);}
				break;
			case air_compressor_id:
				if (entity != null && entity instanceof TEAirCompressor){return new UIAirCompressor(player.inventory, (TEAirCompressor) entity);}
				break;
			case gan_exchanger_base_id:
				if (entity != null && entity instanceof TEHeatExchangerController){return new UIHeatExchangerController(player.inventory, (TEHeatExchangerController) entity);}
				break;
			case gan_controller_id:
				if (entity != null && entity instanceof TEGanController){return new UIGanController(player.inventory, (TEGanController) entity);}
				break;
			case multivessel_id:
				if (entity != null && entity instanceof TEMultivessel){return new UIMultivessel(player.inventory, (TEMultivessel) entity);}
				break;
			case gas_condenser_id:
				if (entity != null && entity instanceof TEGasCondenserController){return new UIGasCondenserController(player.inventory, (TEGasCondenserController) entity);}
				break;
			case leaching_vat_controller_id:
				if (entity != null && entity instanceof TELeachingVatController){return new UILeachingVatController(player.inventory, (TELeachingVatController) entity);}
				break;
			case leaching_vat_tank_id:
				if (entity != null && entity instanceof TELeachingVatTank){return new UILeachingVatTank(player.inventory, (TELeachingVatTank) entity);}
				break;
			case leaching_vat_collector_id:
				if (entity != null && entity instanceof TESpecimenCollector){return new UISpecimenCollector(player.inventory, (TESpecimenCollector) entity);}
				break;
			case retention_vat_id:
				if (entity != null && entity instanceof TERetentionVatController){return new UIRetentionVatController(player.inventory, (TERetentionVatController) entity);}
				break;
			case extraction_cabinet_id:
				if (entity != null && entity instanceof TEElementsCabinetBase){return new UIElementsCabinetBase(player.inventory, (TEElementsCabinetBase) entity);}
				break;
			case extraction_controller_id:
				if (entity != null && entity instanceof TEExtractorController){return new UIExtractorController(player.inventory, (TEExtractorController) entity);}
				break;
			case extraction_stabilizer_id:
				if (entity != null && entity instanceof TEExtractorStabilizer){return new UIExtractorStabilizer(player.inventory, (TEExtractorStabilizer) entity);}
				break;
			case reformer_controller_id:
				if (entity != null && entity instanceof TEReformerController){return new UIReformerController(player.inventory, (TEReformerController) entity);}
				break;
			case reformer_reactor_id:
				if (entity != null && entity instanceof TEReformerReactor){return new UIReformerReactor(player.inventory, (TEReformerReactor) entity);}
				break;
			case gas_expander_id:
				if (entity != null && entity instanceof TEGasExpanderController){return new UIGasExpanderController(player.inventory, (TEGasExpanderController) entity);}
				break;
			case extraction_cabinet_injector_id:
				if (entity != null && entity instanceof TEElementsCabinetTop){return new UIElementsCabinetTop(player.inventory, (TEElementsCabinetTop) entity);}
				break;
			case metal_alloyer_id:
				if (entity != null && entity instanceof TEMetalAlloyerController){return new UIMetalAlloyerController(player.inventory, (TEMetalAlloyerController) entity);}
				break;
			case material_cabinet_id:
				if (entity != null && entity instanceof TEMaterialCabinetBase){return new UIMaterialCabinetBase(player.inventory, (TEMaterialCabinetBase) entity);}
				break;
			case material_cabinet_injector_id:
				if (entity != null && entity instanceof TEMaterialCabinetTop){return new UIMaterialCabinetTop(player.inventory, (TEMaterialCabinetTop) entity);}
				break;
			case deposition_chamber_id:
				if (entity != null && entity instanceof TEDepositionBase){return new UIDepositionBase(player.inventory, (TEDepositionBase) entity);}
				break;
			case deposition_chamber_top_id:
				if (entity != null && entity instanceof TEDepositionController){return new UIDepositionController(player.inventory, (TEDepositionController) entity);}
				break;
			case gas_holder_id:
				if (entity != null && entity instanceof TEGasHolderBase){return new UIGasHolderBase(player.inventory, (TEGasHolderBase) entity);}
				break;
			case pulling_crucible_base_id:
				if (entity != null && entity instanceof TEPullingCrucibleController){return new UIPullingCrucibleController(player.inventory, (TEPullingCrucibleController) entity);}
				break;
			case pulling_crucible_top_id:
				if (entity != null && entity instanceof TEPullingCrucibleTop){return new UIPullingCrucibleTop(player.inventory, (TEPullingCrucibleTop) entity);}
				break;
			case orbiter_id:
				if (entity != null && entity instanceof TEOrbiter){return new UIOrbiter(player.inventory, (TEOrbiter) entity);}
				break;
			case transposer_id:
				if (entity != null && entity instanceof TETransposer){return new UITransposer(player.inventory, (TETransposer) entity);}
				break;
			case fluidpedia_id:
				if (entity != null && entity instanceof TEFluidpedia){return new UIFluidpedia(player.inventory, (TEFluidpedia) entity);}
				break;
			case waste_dumper_id:
				if (entity != null && entity instanceof TEWasteDumper){return new UIWasteDumper(player.inventory, (TEWasteDumper) entity);}
				break;
			case flotation_tank_id:
				if (entity != null && entity instanceof TEFlotationTank){return new UIFlotationTank(player.inventory, (TEFlotationTank) entity);}
				break;
			case containment_tank_id:
				if (entity != null && entity instanceof TEContainmentTank){return new UIContainmentTank(player.inventory, (TEContainmentTank) entity);}
				break;
			case catalyst_regen_id:
				if (entity != null && entity instanceof TECatalystRegen){return new UICatalystRegen(player.inventory, (TECatalystRegen) entity);}
				break;
			case powder_mixer_id:
				if (entity != null && entity instanceof TEPowderMixerController){return new UIPowderMixerController(player.inventory, (TEPowderMixerController) entity);}
				break;
			case disposer_id:
				if (entity != null && entity instanceof TEDisposer){return new UIDisposer(player.inventory, (TEDisposer) entity);}
				break;
			case slurry_drum_id:
				if (entity != null && entity instanceof TESlurryDrum){return new UISlurryDrum(player.inventory, (TESlurryDrum) entity);}
				break;
			case buffer_tank_id:
				if (entity != null && entity instanceof TEBufferTank){return new UIBufferTank(player.inventory, (TEBufferTank) entity);}
				break;
			case stirred_tank_id:
				if (entity != null && entity instanceof TEStirredTankController){return new UIStirredTankController(player.inventory, (TEStirredTankController) entity);}
				break;
			case stirred_tank_out_id:
				if (entity != null && entity instanceof TEStirredTankOut){return new UIStirredTankOut(player.inventory, (TEStirredTankOut) entity);}
				break;
			case precipitation_chamber_id:
				if (entity != null && entity instanceof TEPrecipitationChamber){return new UIPrecipitationChamber(player.inventory, (TEPrecipitationChamber) entity);}
				break;
			case precipitation_controller_id:
				if (entity != null && entity instanceof TEPrecipitationController){return new UIPrecipitationController(player.inventory, (TEPrecipitationController) entity);}
				break;
			case tubular_bed_mid_id:
				if (entity != null && entity instanceof TETubularBedLow){return new UITubularBedLow(player.inventory, (TETubularBedLow) entity);}
				break;
			case tubular_bed_monitor_id:
				if (entity != null && entity instanceof TETubularBedController){return new UITubularBedController(player.inventory, (TETubularBedController) entity);}
				break;
			case planning_table_id:
				if (entity != null && entity instanceof TEPlanningTable){return new UIPlanningTable(player.inventory, (TEPlanningTable) entity);}
				break;
		}
        return null;
	}

}