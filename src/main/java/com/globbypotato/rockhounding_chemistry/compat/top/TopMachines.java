package com.globbypotato.rockhounding_chemistry.compat.top;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSaltStages;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEProfilingBench;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TETransposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWaterPump;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExhaustionValve;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGasHolderTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGasifierBurner;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEStirredTankOut;
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
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;
import com.google.common.base.Function;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TopMachines implements IProbeInfoProvider{

	@Override
	public String getID() {
        return Reference.MODID + ":" + "machines";
	}

	public static class EnergyInfo implements IProbeConfigProvider{
		@Override
		public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {}

		@Override
		public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
	        final TileEntity te = world.getTileEntity(data.getPos());
	        if(te != null){
				if(te instanceof TEPowerGenerator
				|| te instanceof TEAirCompressor
				){
					config.setRFMode(0);
				}
	        }
		}
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		final TileEntity te = world.getTileEntity(data.getPos());
		if(te != null){
			if(te instanceof TEPowerGenerator){
				TEPowerGenerator tile = (TEPowerGenerator)te;
				if(tile.hasPower()){
					probeInfo.progress(tile.getPower(), tile.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
				if(tile.hasRedstone()){
					probeInfo.progress(tile.getRedstone(), tile.getRedstoneMax(), probeInfo.defaultProgressStyle().suffix(" RF").filledColor(0xFFFF0000).alternateFilledColor(0xFF5A0303).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEAirCompressor){
				TEAirCompressor tile = (TEAirCompressor)te;
				probeInfo.progress(tile.getPower(), tile.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}
			if(te instanceof TEMineralSizerController){
				TEMineralSizerController tile = (TEMineralSizerController)te;
				String comm = TextFormatting.GRAY + "Comminution Level: " + TextFormatting.GREEN + tile.getComminution();
				
				String[] multistring = {comm};
				for(int x = 0; x < multistring.length; x++){
					probeInfo.text(multistring[x]);
				}
			}
			if(te instanceof TEFluidTank){
				TEFluidTank tank = (TEFluidTank)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}

	        if (te instanceof TEProfilingBench) {
	        	TEProfilingBench bench = (TEProfilingBench)te;
	        	String pattern = TextFormatting.GRAY + "Pattern: " + TextFormatting.AQUA + EnumCasting.getFormalName(bench.getCasting());
	            probeInfo.text(pattern);
	        }
			if(te instanceof TEEvaporationTank){
				TEEvaporationTank tank = (TEEvaporationTank)te;
				probeInfo.text("Stage " + (tank.getStage() + 1) + ": " + EnumSaltStages.getStageName(tank.getStage()));

				if(tank.getStage() == EnumSaltStages.STAGE_A.ordinal() ){
					if(tank.hasTankFluid() ){
						probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFF84CCFF).alternateFilledColor(0xFF0096FF).borderColor(0x000000).numberFormat(NumberFormat.FULL));
					}
				}
				String purge = TextFormatting.GRAY + "Purging: " + TextFormatting.RED + "Disabled";
				if(tank.getPurge() < tank.finalStage()){
					purge = TextFormatting.GRAY + "Purging: " + TextFormatting.AQUA + EnumSaltStages.getStageName(tank.getPurge());
					probeInfo.text(purge);
				}else{
		            probeInfo.text(purge);
				}

			}
			if(te instanceof TEReinforcedCistern){
				TEReinforcedCistern tank = (TEReinforcedCistern)te;
				if(tank.hasInputFluid()){
					probeInfo.progress(tank.getInputAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFF84CCFF).alternateFilledColor(0xFF0096FF).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEFluidCistern){
				TEFluidCistern tank = (TEFluidCistern)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFF84CCFF).alternateFilledColor(0xFF0096FF).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEGasifierController){
				TEGasifierController tank = (TEGasifierController)te;
				probeInfo.progress(tank.getTemperature(), tank.getTemperatureMax(), probeInfo.defaultProgressStyle().suffix("k").filledColor(0xFFFF7200).alternateFilledColor(0xFFFF0000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}
			if(te instanceof TEGasifierBurner){
				TileEntity base = world.getTileEntity(data.getPos().offset(EnumFacing.DOWN));
				if(base instanceof TEGasifierController){
					TEGasifierController tank = (TEGasifierController)base;
					probeInfo.progress(tank.getTemperature(), tank.getTemperatureMax(), probeInfo.defaultProgressStyle().suffix("k").filledColor(0xFFFF7200).alternateFilledColor(0xFFFF0000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEPressureVessel){
				TEPressureVessel tank = (TEPressureVessel)te;
				if(tank.inputTankHasGas()){
					int gasColor = tank.inputTank.getFluid().getFluid().getColor();
					String suffix = GuiUtils.composeGasContent(tank.inputTank);
					probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(suffix).filledColor(gasColor).alternateFilledColor(gasColor).borderColor(0x000000).numberFormat(NumberFormat.NONE));
				}
			}
			if(te instanceof TELeachingVatTank){
				TELeachingVatTank tank = (TELeachingVatTank)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEGasHolderBase){
				TEGasHolderBase tank = (TEGasHolderBase)te;
				if(tank.inputTankHasGas()){
					int gasColor = tank.inputTank.getFluid().getFluid().getColor();
					String suffix = GuiUtils.composeGasContent(tank.inputTank);
					probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(suffix).filledColor(gasColor).alternateFilledColor(gasColor).borderColor(0x000000).numberFormat(NumberFormat.NONE));
				}
			}
			if(te instanceof TEGasHolderTop){
				TileEntity base = world.getTileEntity(data.getPos().offset(EnumFacing.DOWN));
				if(base instanceof TEGasHolderBase){
					TEGasHolderBase tank = (TEGasHolderBase)base;
					if(tank.inputTankHasGas()){
						int gasColor = tank.inputTank.getFluid().getFluid().getColor();
						String suffix = GuiUtils.composeGasContent(tank.inputTank);
						probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(suffix).filledColor(gasColor).alternateFilledColor(gasColor).borderColor(0x000000).numberFormat(NumberFormat.NONE));
					}
				}
			}
			if(te instanceof TEOrbiter){
				TEOrbiter tank = (TEOrbiter)te;
				if(tank.getXPCount() > 0){
					probeInfo.progress(tank.getXPCount(), tank.getXPCountMax(), probeInfo.defaultProgressStyle().suffix(" xp").filledColor(0xFF439E00).alternateFilledColor(0xFF5DD703).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TETransposer){
				TETransposer tank = (TETransposer)te;
				if(tank.hasInputTankMain()){
					if(tank.getInputTankMain().getFluid().isGaseous()){
						int gasColor = tank.inputTankMain.getFluid().getFluid().getColor();
						String suffix = GuiUtils.composeGasContent(tank.inputTankMain);
						probeInfo.progress(tank.inputTankMain.getFluidAmount(), tank.inputTankMain.getCapacity(), probeInfo.defaultProgressStyle().suffix(suffix).filledColor(gasColor).alternateFilledColor(gasColor).borderColor(0x000000).numberFormat(NumberFormat.NONE));
					}else{
						probeInfo.progress(tank.getInputTankMainAmount(), tank.getFluidCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
					}
				}
				if(tank.hasOutputTankFluid()){
					probeInfo.progress(tank.getOutputTankFluidAmount(), tank.getFluidCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
				if(tank.hasOutputTankGas()){
					int gasColor = tank.outputTankGas.getFluid().getFluid().getColor();
					String suffix = GuiUtils.composeGasContent(tank.outputTankGas);
					probeInfo.progress(tank.outputTankGas.getFluidAmount(), tank.outputTankGas.getCapacity(), probeInfo.defaultProgressStyle().suffix(suffix).filledColor(gasColor).alternateFilledColor(gasColor).borderColor(0x000000).numberFormat(NumberFormat.NONE));
				}
			}
			if(te instanceof TEFlotationTank){
				TEFlotationTank tank = (TEFlotationTank)te;
				if(tank.hasSolventFluid()){
					probeInfo.progress(tank.getSolventAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB - Solvent").filledColor(0xFFFFFFFF).alternateFilledColor(0xFF90FBFF).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEContainmentTank){
				TEContainmentTank tank = (TEContainmentTank)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEWaterPump){
				TEWaterPump tank = (TEWaterPump)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
	        	String status = TextFormatting.GRAY + "Compression: " + TextFormatting.AQUA + tank.getCompressor();
	            probeInfo.text(status);
	        	String tier = TextFormatting.GRAY + "Tier: " + TextFormatting.DARK_AQUA + (tank.getTier() + 1) + TextFormatting.GRAY + " (" + tank.tierFactor() + " mB)";
	            probeInfo.text(tier);
			}
			if(te instanceof TEExhaustionValve){
				TileEntity vessel = world.getTileEntity(data.getPos().offset(EnumFacing.DOWN));
				if(vessel instanceof TEPressureVessel){
					TEPressureVessel ves = (TEPressureVessel)vessel;
					probeInfo.progress(ves.getCollapse(), ModConfig.pressureTolerance, probeInfo.defaultProgressStyle().prefix("Tolerance: ").suffix("/" + ModConfig.pressureTolerance + " units").filledColor(0xFFED1919).alternateFilledColor(0x000000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
				if(vessel instanceof TEMultivessel){
					TEMultivessel ves = (TEMultivessel)vessel;
					probeInfo.progress(ves.getCollapse(), ModConfig.pressureTolerance, probeInfo.defaultProgressStyle().prefix("Tolerance: ").suffix("/" + ModConfig.pressureTolerance + " units").filledColor(0xFFED1919).alternateFilledColor(0x000000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
				if(vessel instanceof TETransposer){
					TETransposer ves = (TETransposer)vessel;
					probeInfo.progress(ves.getCollapse(), ModConfig.pressureTolerance, probeInfo.defaultProgressStyle().prefix("Tolerance: ").suffix("/" + ModConfig.pressureTolerance + " units").filledColor(0xFFED1919).alternateFilledColor(0x000000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
				if(vessel instanceof TEGasHolderTop){
					TileEntity holder = world.getTileEntity(data.getPos().offset(EnumFacing.DOWN, 2));
					if(holder instanceof TEGasHolderBase){
						TEGasHolderBase ves = (TEGasHolderBase)holder;
						probeInfo.progress(ves.getCollapse(), ModConfig.pressureTolerance, probeInfo.defaultProgressStyle().prefix("Tolerance: ").suffix("/" + ModConfig.pressureTolerance + " units").filledColor(0xFFED1919).alternateFilledColor(0x000000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
					}
				}
			}
			if(te instanceof TESlurryDrum){
				TESlurryDrum tank = (TESlurryDrum)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEBufferTank){
				TEBufferTank tank = (TEBufferTank)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEStirredTankOut){
				TEStirredTankOut tank = (TEStirredTankOut)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}
			if(te instanceof TEWashingTank){
				TEWashingTank tank = (TEWashingTank)te;
				if(tank.hasTankFluid()){
					probeInfo.progress(tank.getTankAmount(), tank.getTankCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFD3D3D3).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				}
			}

		}
	}

    public static class getTOP implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe top;
        @Nullable
        @Override
        public Void apply (ITheOneProbe theOneProbe) {
        	top = theOneProbe;
            top.registerProvider(new TopMachines());
            top.registerProbeConfigProvider(new EnergyInfo());
            return null;
        }
    }

}