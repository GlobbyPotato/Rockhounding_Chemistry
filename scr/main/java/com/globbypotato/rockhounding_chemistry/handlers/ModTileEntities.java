package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.tile.*;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TEMineralSizerController.class, Reference.MODID + "_" + TEMineralSizerController.getName());

		GameRegistry.registerTileEntity(TEMineralSizerController.class, Reference.MODID + "_" + TEMineralSizerController.getName());
		GameRegistry.registerTileEntity(TEMineralSizerTank.class, Reference.MODID + "_" + TEMineralSizerTank.getName());
		GameRegistry.registerTileEntity(TEPowerGenerator.class, Reference.MODID + "_" + TEPowerGenerator.getName());
		GameRegistry.registerTileEntity(TEMineralSizerCollector.class, Reference.MODID + "_" + TEMineralSizerCollector.getName());
		GameRegistry.registerTileEntity(TEFluidTank.class, Reference.MODID + "_" + TEFluidTank.getName());
		GameRegistry.registerTileEntity(TELabOvenController.class, Reference.MODID + "_" + TELabOvenController.getName());
		GameRegistry.registerTileEntity(TELabOvenChamber.class, Reference.MODID + "_" + TELabOvenChamber.getName());
		GameRegistry.registerTileEntity(TEFluidInputTank.class, Reference.MODID + "_" + TEFluidInputTank.getName());
		GameRegistry.registerTileEntity(TEFluidOutputTank.class, Reference.MODID + "_" + TEFluidOutputTank.getName());
		GameRegistry.registerTileEntity(TELabBlenderController.class, Reference.MODID + "_" + TELabBlenderController.getName());
		GameRegistry.registerTileEntity(TELabBlenderTank.class, Reference.MODID + "_" + TELabBlenderTank.getName());
		GameRegistry.registerTileEntity(TEProfilingBench.class, Reference.MODID + "_" + TEProfilingBench.getName());
		GameRegistry.registerTileEntity(TEEvaporationTank.class, Reference.MODID + "_" + TEEvaporationTank.getName());
		GameRegistry.registerTileEntity(TESeasoningRack.class, Reference.MODID + "_" + TESeasoningRack.getName());
		GameRegistry.registerTileEntity(TEServer.class, Reference.MODID + "_" + TEServer.getName());
		GameRegistry.registerTileEntity(TEGasExpander.class, Reference.MODID + "_" + TEGasExpander.getName());

		GameRegistry.registerTileEntity(TESlurryPond.class, Reference.MODID + "_" + TESlurryPond.getName());
		GameRegistry.registerTileEntity(TEGasifierTank.class, Reference.MODID + "_" + TEGasifierTank.getName());
		GameRegistry.registerTileEntity(TEGasifierCooler.class, Reference.MODID + "_" + TEGasifierCooler.getName());
		GameRegistry.registerTileEntity(TEGasifierBurner.class, Reference.MODID + "_" + TEGasifierBurner.getName());
		GameRegistry.registerTileEntity(TEGasPurifier.class, Reference.MODID + "_" + TEGasPurifier.getName());
		GameRegistry.registerTileEntity(TEGasPressurizer.class, Reference.MODID + "_" + TEGasPressurizer.getName());
		GameRegistry.registerTileEntity(TEPurifierCycloneBase.class, Reference.MODID + "_" + TEPurifierCycloneBase.getName());
		GameRegistry.registerTileEntity(TEPurifierCycloneTop.class, Reference.MODID + "_" + TEPurifierCycloneTop.getName());
		GameRegistry.registerTileEntity(TEPurifierCycloneCap.class, Reference.MODID + "_" + TEPurifierCycloneCap.getName());
		GameRegistry.registerTileEntity(TEParticulateCollector.class, Reference.MODID + "_" + TEParticulateCollector.getName());
		GameRegistry.registerTileEntity(TEPressureVessel.class, Reference.MODID + "_" + TEPressureVessel.getName());
		GameRegistry.registerTileEntity(TEAirCompressor.class, Reference.MODID + "_" + TEAirCompressor.getName());
		GameRegistry.registerTileEntity(TEHeatExchangerBase.class, Reference.MODID + "_" + TEHeatExchangerBase.getName());
		GameRegistry.registerTileEntity(TEHeatExchangerTop.class, Reference.MODID + "_" + TEHeatExchangerTop.getName());
		GameRegistry.registerTileEntity(TEGanExpanderBase.class, Reference.MODID + "_" + TEGanExpanderBase.getName());
		GameRegistry.registerTileEntity(TEGanExpanderTop.class, Reference.MODID + "_" + TEGanExpanderTop.getName());

		GameRegistry.registerTileEntity(TEGanController.class, Reference.MODID + "_" + TEGanController.getName());
		GameRegistry.registerTileEntity(TEGasCondenser.class, Reference.MODID + "_" + TEGasCondenser.getName());
		GameRegistry.registerTileEntity(TEMultivessel.class, Reference.MODID + "_" + TEMultivessel.getName());
		GameRegistry.registerTileEntity(TELeachingVatController.class, Reference.MODID + "_" + TELeachingVatController.getName());
		GameRegistry.registerTileEntity(TELeachingVatTank.class, Reference.MODID + "_" + TELeachingVatTank.getName());
		GameRegistry.registerTileEntity(TELeachingVatCollector.class, Reference.MODID + "_" + TELeachingVatCollector.getName());
		GameRegistry.registerTileEntity(TERetentionVat.class, Reference.MODID + "_" + TERetentionVat.getName());
		GameRegistry.registerTileEntity(TEExtractorController.class, Reference.MODID + "_" + TEExtractorController.getName());
		GameRegistry.registerTileEntity(TEExtractorReactor.class, Reference.MODID + "_" + TEExtractorReactor.getName());
		GameRegistry.registerTileEntity(TEElementsCabinetBase.class, Reference.MODID + "_" + TEElementsCabinetBase.getName());
		GameRegistry.registerTileEntity(TEElementsCabinetTop.class, Reference.MODID + "_" + TEElementsCabinetTop.getName());
		GameRegistry.registerTileEntity(TEExtractorInjector.class, Reference.MODID + "_" + TEExtractorInjector.getName());
		GameRegistry.registerTileEntity(TEExtractorBalance.class, Reference.MODID + "_" + TEExtractorBalance.getName());
		GameRegistry.registerTileEntity(TEReformerController.class, Reference.MODID + "_" + TEReformerController.getName());
		GameRegistry.registerTileEntity(TEReformerReactor.class, Reference.MODID + "_" + TEReformerReactor.getName());
		GameRegistry.registerTileEntity(TEExtractorStabilizer.class, Reference.MODID + "_" + TEExtractorStabilizer.getName());

		GameRegistry.registerTileEntity(TEMetalAlloyer.class, Reference.MODID + "_" + TEMetalAlloyer.getName());
		GameRegistry.registerTileEntity(TEMetalAlloyerTank.class, Reference.MODID + "_" + TEMetalAlloyerTank.getName());
		GameRegistry.registerTileEntity(TEMaterialCabinetBase.class, Reference.MODID + "_" + TEMaterialCabinetBase.getName());
		GameRegistry.registerTileEntity(TEMaterialCabinetTop.class, Reference.MODID + "_" + TEMaterialCabinetTop.getName());
		GameRegistry.registerTileEntity(TEDepositionChamberBase.class, Reference.MODID + "_" + TEDepositionChamberBase.getName());
		GameRegistry.registerTileEntity(TEDepositionChamberTop.class, Reference.MODID + "_" + TEDepositionChamberTop.getName());
		GameRegistry.registerTileEntity(TEGasHolderBase.class, Reference.MODID + "_" + TEGasHolderBase.getName());
		GameRegistry.registerTileEntity(TEGasHolderTop.class, Reference.MODID + "_" + TEGasHolderTop.getName());
		GameRegistry.registerTileEntity(TEPullingCrucibleBase.class, Reference.MODID + "_" + TEPullingCrucibleBase.getName());
		GameRegistry.registerTileEntity(TEPullingCrucibleTop.class, Reference.MODID + "_" + TEPullingCrucibleTop.getName());
		GameRegistry.registerTileEntity(TEOrbiter.class, Reference.MODID + "_" + TEOrbiter.getName());
		GameRegistry.registerTileEntity(TETransposer.class, Reference.MODID + "_" + TETransposer.getName());
		GameRegistry.registerTileEntity(TEFluidpedia.class, Reference.MODID + "_" + TEFluidpedia.getName());
		GameRegistry.registerTileEntity(TEWasteDumper.class, Reference.MODID + "_" + TEWasteDumper.getName());
		GameRegistry.registerTileEntity(TEFlotationTank.class, Reference.MODID + "_" + TEFlotationTank.getName());
		GameRegistry.registerTileEntity(TEContainmentTank.class, Reference.MODID + "_" + TEContainmentTank.getName());

		GameRegistry.registerTileEntity(TELaserEmitter.class, Reference.MODID + "_" + TELaserEmitter.getName());
		GameRegistry.registerTileEntity(TEExhaustionValve.class, Reference.MODID + "_" + TEExhaustionValve.getName());
		GameRegistry.registerTileEntity(TEWaterPump.class, Reference.MODID + "_" + TEWaterPump.getName());
		GameRegistry.registerTileEntity(TECatalystRegen.class, Reference.MODID + "_" + TECatalystRegen.getName());
		GameRegistry.registerTileEntity(TEDisposer.class, Reference.MODID + "_" + TEDisposer.getName());
		GameRegistry.registerTileEntity(TESlurryDrum.class, Reference.MODID + "_" + TESlurryDrum.getName());
		GameRegistry.registerTileEntity(TEBufferTank.class, Reference.MODID + "_" + TEBufferTank.getName());
		GameRegistry.registerTileEntity(TEStirredTankBase.class, Reference.MODID + "_" + TEStirredTankBase.getName());
		GameRegistry.registerTileEntity(TEStirredTankTop.class, Reference.MODID + "_" + TEStirredTankTop.getName());
		GameRegistry.registerTileEntity(TEStirredTankOut.class, Reference.MODID + "_" + TEStirredTankOut.getName());
		GameRegistry.registerTileEntity(TEPrecipitationController.class, Reference.MODID + "_" + TEPrecipitationController.getName());
		GameRegistry.registerTileEntity(TEPrecipitationChamber.class, Reference.MODID + "_" + TEPrecipitationChamber.getName());
		GameRegistry.registerTileEntity(TEPrecipitationReactor.class, Reference.MODID + "_" + TEPrecipitationReactor.getName());

		GameRegistry.registerTileEntity(TEPipelinePump.class, Reference.MODID + "_" + TEPipelinePump.getName());
		GameRegistry.registerTileEntity(TEPipelineValve.class, Reference.MODID + "_" + TEPipelineValve.getName());
		GameRegistry.registerTileEntity(TEGaslinePump.class, Reference.MODID + "_" + TEGaslinePump.getName());
	}
}