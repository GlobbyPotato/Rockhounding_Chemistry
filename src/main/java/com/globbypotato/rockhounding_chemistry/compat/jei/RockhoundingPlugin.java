package com.globbypotato.rockhounding_chemistry.compat.jei;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.air_compressor.AirCompressorCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.air_compressor.AirCompressorWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.bed_reactor.BedReactorCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.bed_reactor.BedReactorWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemical_extractor.ChemicalExtractorCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemical_extractor.ChemicalExtractorWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.deposition_chamber.DepositionChamberCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.deposition_chamber.DepositionChamberWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.evaporation_tank.EvaporationTankCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.evaporation_tank.EvaporationTankWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gan_plant.GanPlantCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gan_plant.GanPlantWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_condenser.GasCondenserCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_condenser.GasCondenserWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_expander.GasExpanderCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_expander.GasExpanderWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_purifier.GasPurifierCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_purifier.GasPurifierWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_reformer.GasReformerCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gas_reformer.GasReformerWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gasifier_plant.GasifierPlantCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gasifier_plant.GasifierPlantWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.heat_exchanger.HeatExchangerCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.heat_exchanger.HeatExchangerWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.lab_blender.LabBlenderCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.lab_blender.LabBlenderWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.lab_oven.LabOvenCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.lab_oven.LabOvenWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.leaching_vat.LeachingVatCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.leaching_vat.LeachingVatWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.metal_alloyer.MetalAlloyerCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.metal_alloyer.MetalAlloyerWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineral_sizer.MineralSizerCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineral_sizer.MineralSizerWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.orbiter_exp.OrbiterCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.orbiter_exp.OrbiterWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_fluids.PollutantFluidCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_fluids.PollutantFluidWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_gases.PollutantGasCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.pollutant_gases.PollutantGasWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.powder_mixer.PowderMixerCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.powder_mixer.PowderMixerWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.precipitation_chamber.PrecipitationCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.precipitation_chamber.PrecipitationWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.profiling_bench.ProfilingBenchCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.profiling_bench.ProfilingBenchWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.pulling_crucible.PullingCrucibleCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.pulling_crucible.PullingCrucibleWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.retention_vat.RetentionVatCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.retention_vat.RetentionVatWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.seasoning_rack.SeasoningRackCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.seasoning_rack.SeasoningRackWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.shaking_table.ShakingTableCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.shaking_table.ShakingTableWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.slurry_drum.SlurryDrumCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.slurry_drum.SlurryDrumWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.slurry_pond.SlurryPondCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.slurry_pond.SlurryPondWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.stirred_tank.StirredTankCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.stirred_tank.StirredTankWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.toxic_mutation.ToxicMutationCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.toxic_mutation.ToxicMutationWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.transposer.TransposerCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.transposer.TransposerWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesC;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesE;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesF;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSpeeds;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.container.COSeasoningRack;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIDepositionController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIExtractorController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGanController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasCondenserController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasExpanderController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasPurifierController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIGasifierController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIHeatExchangerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabBlenderController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabBlenderTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILabOvenController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UILeachingVatController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPowderMixerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPrecipitationChamber;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPrecipitationController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIReformerController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIRetentionVatController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISeasoningRack;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIShredderController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISlurryDrum;
import com.globbypotato.rockhounding_chemistry.machines.gui.UISlurryPondController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UIStirredTankController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITransposer;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITubularBedController;
import com.globbypotato.rockhounding_chemistry.machines.gui.UITubularBedLow;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@JEIPlugin
@SideOnly(Side.CLIENT)
public class RockhoundingPlugin implements IModPlugin {

	public static IJeiHelpers jeiHelpers;

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(	
				new MineralSizerCategory(guiHelper, RHRecipeUID.SIZER),
				new SlurryPondCategory(guiHelper, RHRecipeUID.POND),
				new LabOvenCategory(guiHelper, RHRecipeUID.OVEN),
				new LabBlenderCategory(guiHelper, RHRecipeUID.BLENDER),
				new GasifierPlantCategory(guiHelper, RHRecipeUID.GASIFIER),
				new AirCompressorCategory(guiHelper, RHRecipeUID.COMPRESSOR),
				new GasPurifierCategory(guiHelper, RHRecipeUID.PURIFIER),
				new SeasoningRackCategory(guiHelper, RHRecipeUID.SEASONING),
				new HeatExchangerCategory(guiHelper, RHRecipeUID.EXCHANGER),
				new GasCondenserCategory(guiHelper, RHRecipeUID.CONDENSER),
				new GasExpanderCategory(guiHelper, RHRecipeUID.EXPANDER),
				new ProfilingBenchCategory(guiHelper, RHRecipeUID.PROFILER),
				new LeachingVatCategory(guiHelper, RHRecipeUID.LEACHING),
				new RetentionVatCategory(guiHelper, RHRecipeUID.RETENTION),
				new GanPlantCategory(guiHelper, RHRecipeUID.SEPARATION),
				new ChemicalExtractorCategory(guiHelper, RHRecipeUID.EXTRACTION),
				new GasReformerCategory(guiHelper, RHRecipeUID.REFORMING),
				new MetalAlloyerCategory(guiHelper, RHRecipeUID.ALLOYER),
				new DepositionChamberCategory(guiHelper, RHRecipeUID.DEPOSITION),
				new PullingCrucibleCategory(guiHelper, RHRecipeUID.PULLING),
				new TransposerCategory(guiHelper, RHRecipeUID.TRANSPOSER),
				new ToxicMutationCategory(guiHelper, RHRecipeUID.MUTATION),
				new PollutantFluidCategory(guiHelper, RHRecipeUID.POLLUTANT_FLUIDS),
				new PollutantGasCategory(guiHelper, RHRecipeUID.POLLUTANT_GASES),
				new SlurryDrumCategory(guiHelper, RHRecipeUID.SLURRY_DRUM),
				new StirredTankCategory(guiHelper, RHRecipeUID.STIRRED_TANK),
				new EvaporationTankCategory(guiHelper, RHRecipeUID.EVAPORATION_TANK),
				new PrecipitationCategory(guiHelper, RHRecipeUID.PRECIPITATION),
				new OrbiterCategory(guiHelper, RHRecipeUID.ORBITER),
				new BedReactorCategory(guiHelper, RHRecipeUID.BED_REACTOR),
				new ShakingTableCategory(guiHelper, RHRecipeUID.SHAKING_TABLE),
				new PowderMixerCategory(guiHelper, RHRecipeUID.POWDER_MIXER)

		);
	}

	@Override
	public void register(IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();
		int rX = 156; int rY = 4; int rW = 16; int rH = 14; 

		registry.addRecipes(MineralSizerWrapper.getRecipes(), RHRecipeUID.SIZER);
		registry.addRecipes(SlurryPondWrapper.getRecipes(), RHRecipeUID.POND);
		registry.addRecipes(LabOvenWrapper.getRecipes(), RHRecipeUID.OVEN);
		registry.addRecipes(LabBlenderWrapper.getRecipes(), RHRecipeUID.BLENDER);
		registry.addRecipes(GasifierPlantWrapper.getRecipes(), RHRecipeUID.GASIFIER);
		registry.addRecipes(GasPurifierWrapper.getRecipes(), RHRecipeUID.PURIFIER);
		registry.addRecipes(AirCompressorWrapper.getRecipes(), RHRecipeUID.COMPRESSOR);
		registry.addRecipes(SeasoningRackWrapper.getRecipes(), RHRecipeUID.SEASONING);
		registry.addRecipes(HeatExchangerWrapper.getRecipes(), RHRecipeUID.EXCHANGER);
		registry.addRecipes(GasCondenserWrapper.getRecipes(), RHRecipeUID.CONDENSER);
		registry.addRecipes(ProfilingBenchWrapper.getRecipes(), RHRecipeUID.PROFILER);
		registry.addRecipes(LeachingVatWrapper.getRecipes(), RHRecipeUID.LEACHING);
		registry.addRecipes(RetentionVatWrapper.getRecipes(), RHRecipeUID.RETENTION);
		registry.addRecipes(GanPlantWrapper.getRecipes(), RHRecipeUID.SEPARATION);
		registry.addRecipes(ChemicalExtractorWrapper.getRecipes(), RHRecipeUID.EXTRACTION);
		registry.addRecipes(GasReformerWrapper.getRecipes(), RHRecipeUID.REFORMING);
		registry.addRecipes(GasExpanderWrapper.getRecipes(), RHRecipeUID.EXPANDER);
		registry.addRecipes(MetalAlloyerWrapper.getRecipes(), RHRecipeUID.ALLOYER);
		registry.addRecipes(DepositionChamberWrapper.getRecipes(), RHRecipeUID.DEPOSITION);
		registry.addRecipes(PullingCrucibleWrapper.getRecipes(), RHRecipeUID.PULLING);
		registry.addRecipes(TransposerWrapper.getRecipes(), RHRecipeUID.TRANSPOSER);
		registry.addRecipes(ToxicMutationWrapper.getRecipes(), RHRecipeUID.MUTATION);
		registry.addRecipes(PollutantFluidWrapper.getRecipes(), RHRecipeUID.POLLUTANT_FLUIDS);
		registry.addRecipes(PollutantGasWrapper.getRecipes(), RHRecipeUID.POLLUTANT_GASES);
		registry.addRecipes(SlurryDrumWrapper.getRecipes(), RHRecipeUID.SLURRY_DRUM);
		registry.addRecipes(StirredTankWrapper.getRecipes(), RHRecipeUID.STIRRED_TANK);
		registry.addRecipes(EvaporationTankWrapper.getRecipes(), RHRecipeUID.EVAPORATION_TANK);
		registry.addRecipes(PrecipitationWrapper.getRecipes(), RHRecipeUID.PRECIPITATION);
		registry.addRecipes(BedReactorWrapper.getRecipes(), RHRecipeUID.BED_REACTOR);
		registry.addRecipes(OrbiterWrapper.getRecipes(), RHRecipeUID.ORBITER);
		registry.addRecipes(ShakingTableWrapper.getRecipes(), RHRecipeUID.SHAKING_TABLE);
		registry.addRecipes(PowderMixerWrapper.getRecipes(), RHRecipeUID.POWDER_MIXER);

		registry.addRecipeClickArea(UIMineralSizerController.class, rX, rY, rW, rH, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(UIMineralSizerTank.class, rX, rY, rW, rH, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(UIMineralSizerCollector.class, rX, rY, rW, rH, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(UISlurryPondController.class, rX, rY, rW, rH, RHRecipeUID.POND);
		registry.addRecipeClickArea(UILabOvenController.class, rX, rY, rW, rH, RHRecipeUID.OVEN);
		registry.addRecipeClickArea(UILabBlenderController.class, rX, rY, rW, rH, RHRecipeUID.BLENDER);
		registry.addRecipeClickArea(UILabBlenderTank.class, rX, rY, rW, rH, RHRecipeUID.BLENDER);
		registry.addRecipeClickArea(UIFluidCistern.class, rX, rY, rW, rH, RHRecipeUID.GASIFIER);
		registry.addRecipeClickArea(UIGasifierController.class, rX, rY, rW, rH, RHRecipeUID.GASIFIER);
		registry.addRecipeClickArea(UIGasPurifierController.class, rX, rY, rW, rH, RHRecipeUID.PURIFIER);
		registry.addRecipeClickArea(UIAirCompressor.class, rX, rY, rW, rH, RHRecipeUID.COMPRESSOR);
		registry.addRecipeClickArea(UISeasoningRack.class, rX, rY, rW, rH, RHRecipeUID.SEASONING);
		registry.addRecipeClickArea(UIHeatExchangerController.class, rX, rY, rW, rH, RHRecipeUID.EXCHANGER);
		registry.addRecipeClickArea(UIGasCondenserController.class, rX, rY, rW, rH, RHRecipeUID.CONDENSER);
		registry.addRecipeClickArea(UILeachingVatController.class, rX, rY, rW, rH, RHRecipeUID.LEACHING);
		registry.addRecipeClickArea(UIRetentionVatController.class, rX, rY, rW, rH, RHRecipeUID.RETENTION);
		registry.addRecipeClickArea(UIGanController.class, rX, rY, rW, rH, RHRecipeUID.SEPARATION);
		registry.addRecipeClickArea(UIExtractorController.class, rX, rY, rW, rH, RHRecipeUID.EXTRACTION);
		registry.addRecipeClickArea(UIReformerController.class, rX, rY, rW, rH, RHRecipeUID.REFORMING);
		registry.addRecipeClickArea(UIReformerReactor.class, rX, rY, rW, rH, RHRecipeUID.REFORMING);
		registry.addRecipeClickArea(UIGasExpanderController.class, rX, rY, rW, rH, RHRecipeUID.EXPANDER);
		registry.addRecipeClickArea(UIMetalAlloyerController.class, rX, rY, rW, rH, RHRecipeUID.ALLOYER);
		registry.addRecipeClickArea(UIDepositionController.class, rX, rY, rW, rH, RHRecipeUID.DEPOSITION);
		registry.addRecipeClickArea(UIPullingCrucibleController.class, rX, rY, rW, rH, RHRecipeUID.PULLING);
		registry.addRecipeClickArea(UIPullingCrucibleTop.class, rX, rY, rW, rH, RHRecipeUID.PULLING);
		registry.addRecipeClickArea(UITransposer.class, rX, rY, rW, rH, RHRecipeUID.TRANSPOSER);
		registry.addRecipeClickArea(UISlurryDrum.class, rX, rY, rW, rH, RHRecipeUID.SLURRY_DRUM);
		registry.addRecipeClickArea(UIStirredTankController.class, rX, rY, rW, rH, RHRecipeUID.STIRRED_TANK);
		registry.addRecipeClickArea(UIPrecipitationController.class, rX, rY, rW, rH, RHRecipeUID.PRECIPITATION);
		registry.addRecipeClickArea(UIPrecipitationChamber.class, rX, rY, rW, rH, RHRecipeUID.PRECIPITATION);
		registry.addRecipeClickArea(UITubularBedController.class, rX, rY, rW, rH, RHRecipeUID.BED_REACTOR);
		registry.addRecipeClickArea(UITubularBedLow.class, rX, rY, rW, rH, RHRecipeUID.BED_REACTOR);
		registry.addRecipeClickArea(UIShredderController.class, rX, rY, rW, rH, RHRecipeUID.SHAKING_TABLE);
		registry.addRecipeClickArea(UIPowderMixerController.class, rX, rY, rW, rH, RHRecipeUID.POWDER_MIXER);

		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.SIZER_CONTROLLER.ordinal()), RHRecipeUID.SIZER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.SLURRY_POND.ordinal()), RHRecipeUID.POND);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.LAB_OVEN_CHAMBER.ordinal()), RHRecipeUID.OVEN);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.LAB_BLENDER_CONTROLLER.ordinal()), RHRecipeUID.BLENDER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.GASIFIER_CONTROLLER.ordinal()), RHRecipeUID.GASIFIER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.GAS_PURIFIER.ordinal()), RHRecipeUID.PURIFIER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.AIR_COMPRESSOR.ordinal()), RHRecipeUID.COMPRESSOR);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.SEASONING_RACK.ordinal()), RHRecipeUID.SEASONING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.HEAT_EXCHANGER_BASE.ordinal()), RHRecipeUID.EXCHANGER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.GAS_CONDENSER.ordinal()), RHRecipeUID.CONDENSER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.PROFILING_BENCH.ordinal()), RHRecipeUID.PROFILER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.LEACHING_VAT_CONTROLLER.ordinal()), RHRecipeUID.LEACHING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.RETENTION_VAT_CONTROLLER.ordinal()), RHRecipeUID.RETENTION);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.GAN_CONTROLLER.ordinal()), RHRecipeUID.SEPARATION);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.EXTRACTOR_CONTROLLER.ordinal()), RHRecipeUID.EXTRACTION);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.REFORMER_CONTROLLER.ordinal()), RHRecipeUID.REFORMING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.GAS_EXPANDER.ordinal()), RHRecipeUID.EXPANDER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.METAL_ALLOYER.ordinal()), RHRecipeUID.ALLOYER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal()), RHRecipeUID.DEPOSITION);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal()), RHRecipeUID.PULLING);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.TRANSPOSER.ordinal()), RHRecipeUID.TRANSPOSER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.CONTAINMENT_TANK.ordinal()), RHRecipeUID.POLLUTANT_FLUIDS);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.PRESSURE_VESSEL.ordinal()), RHRecipeUID.POLLUTANT_GASES);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.SLURRY_DRUM.ordinal()), RHRecipeUID.SLURRY_DRUM);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.STIRRED_TANK_BASE.ordinal()), RHRecipeUID.STIRRED_TANK);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.EVAPORATION_TANK.ordinal()), RHRecipeUID.EVAPORATION_TANK);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.PRECIPITATION_CHAMBER.ordinal()), RHRecipeUID.PRECIPITATION);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.TUBULAR_BED_TANK.ordinal()), RHRecipeUID.BED_REACTOR);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.ORBITER.ordinal()), RHRecipeUID.ORBITER);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.SHREDDER_BASE.ordinal()), RHRecipeUID.SHAKING_TABLE);
		registry.addRecipeCatalyst(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.POWDER_MIXER_CONTROLLER.ordinal()), RHRecipeUID.POWDER_MIXER);

		registry.addRecipeCatalyst(CoreUtils.getFluidBucket(EnumFluid.pickFluid(EnumFluid.TOXIC_WASTE)), RHRecipeUID.MUTATION);

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		recipeTransferRegistry.addRecipeTransferHandler(COSeasoningRack.class,RHRecipeUID.SEASONING, 0, 1, 1, 36);

		String salt_text = "Raw salt is an intermediate stage of the salt making. It is produced by the Evaporation Tank as result of water evaporation or desublimation and is refined in the Seasoning Rack into the generic refined salt used for recipes. In case of desublimation, since less water is required, a poorer variant will be produced, returning less refined salt.";
		registry.addIngredientInfo(new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.RAW_SALT.ordinal()), VanillaTypes.ITEM, salt_text);
		registry.addIngredientInfo(new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.POOR_RAW_SALT.ordinal()), VanillaTypes.ITEM, salt_text);

		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.PRESSER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.CYCLONE_SEPARATOR_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.LAB_BLENDER_TANK.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_A, 1, EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_C, 1, EnumMachinesC.REFORMER_REACTOR.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.METAL_ALLOYER_TANK.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.GAS_HOLDER_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.GASIFIER_BURNER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.STIRRED_TANK_CONTROLLER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.TUBULAR_BED_LOW.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.TUBULAR_BED_TOP.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.TUBULAR_BED_CONTROLLER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_F, 1, EnumMachinesF.SHREDDER_CONTROLLER.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.POWDER_MIXER_TANK.ordinal()));

		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.PIPELINE_HALT));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.GASLINE_HALT));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.DUSTLINE_HALT));

		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModItems.SPEED_ITEMS, 1, EnumSpeeds.BASE.ordinal()));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.WATERLOCK,1,1));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.TOXIC_CLOUD));

		if(!ModConfig.enableHazard){
			itemBlacklist.addIngredientToBlacklist(BaseRecipes.toxic_slimeball);
			itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_E, 1, EnumMachinesE.EXHAUSTION_VALVE.ordinal()));
			itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.CONTAINMENT_TANK.ordinal()));
		}

		for(Fluid gas : ModFluids.GASES){
			ItemStack gas_bucket = FluidUtil.getFilledBucket(new FluidStack(gas, 1000));
			itemBlacklist.addIngredientToBlacklist(gas_bucket);
		}

		for(int i=0;i<EnumCasting.size();i++){
			itemBlacklist.addIngredientToBlacklist(BaseRecipes.patterns(1, EnumCasting.values()[i]));
		}		
		
	}
}