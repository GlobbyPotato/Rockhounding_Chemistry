package com.globbypotato.rockhounding_chemistry.compat.top;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDisposer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMachineServer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltSeasoner;
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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
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
			if(te instanceof TileEntityMineralSizer
			|| te instanceof TileEntitySaltSeasoner
			|| te instanceof TileEntityLabBlender
			|| te instanceof TileEntityMineralAnalyzer
			|| te instanceof TileEntityLabOven
			|| te instanceof TileEntityChemicalExtractor
			|| te instanceof TileEntityMetalAlloyer
			|| te instanceof TileEntityElectroLaser
			|| te instanceof TileEntityDepositionChamber
			|| te instanceof TileEntityDisposer
			|| te instanceof TileEntityEarthBreaker
			|| te instanceof TileEntityMachineServer
			){
				config.setRFMode(0);
			}			
		}
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        final TileEntity te = world.getTileEntity(data.getPos());
		Block block = blockState.getBlock();
		int meta = block.getMetaFromState(blockState);
		if(te != null){
			if(te instanceof TileEntityEarthBreaker){
				TileEntityEarthBreaker tank = (TileEntityEarthBreaker)te;
				probeInfo.progress(tank.chargeCount, tank.chargeMax, probeInfo.defaultProgressStyle().suffix("KRF").filledColor(0xFFFF0000).alternateFilledColor(0xFF5A0303).borderColor(0x000000).numberFormat(NumberFormat.COMPACT));
			}

			if(te instanceof TileEntityMineralSizer){
				TileEntityMineralSizer tank = (TileEntityMineralSizer)te;
				probeInfo.progress(tank.getPower(), tank.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityLabBlender){
				TileEntityLabBlender tank = (TileEntityLabBlender)te;
				probeInfo.progress(tank.getPower(), tank.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityElectroLaser){
				TileEntityElectroLaser tank = (TileEntityElectroLaser)te;
				probeInfo.progress(tank.getRedstone(), tank.getRedstoneMax(), probeInfo.defaultProgressStyle().suffix("RF").filledColor(0xFFFF0000).alternateFilledColor(0xFF5A0303).borderColor(0x000000).numberFormat(NumberFormat.COMPACT));
				probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFC5C5C5).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityMetalAlloyer){
				TileEntityMetalAlloyer tank = (TileEntityMetalAlloyer)te;
				probeInfo.progress(tank.getPower(), tank.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityDepositionChamber){
				TileEntityDepositionChamber tank = (TileEntityDepositionChamber)te;
				probeInfo.progress(tank.getTemperature(), tank.getTemperatureMax(), probeInfo.defaultProgressStyle().suffix(" K").filledColor(0xFFFF9000).alternateFilledColor(0xFFFF6000).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.getPressure(), tank.getPressureMax(), probeInfo.defaultProgressStyle().suffix(" uPa").filledColor(0xFF0042FF).alternateFilledColor(0xFF0090FF).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFC5C5C5).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityLabOven){
				TileEntityLabOven tank = (TileEntityLabOven)te;
				probeInfo.progress(tank.getPower(), tank.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				if(!tank.hasFuelBlend()){
					probeInfo.progress(tank.getRedstone(), tank.getRedstoneMax(), probeInfo.defaultProgressStyle().suffix("RF").filledColor(0xFFFF0000).alternateFilledColor(0xFF5A0303).borderColor(0x000000).numberFormat(NumberFormat.COMPACT));
				}
				probeInfo.progress(tank.outputTank.getFluidAmount(), tank.outputTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFC5C5C5).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.solventTank.getFluidAmount(), tank.solventTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFB2D8E9).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.reagentTank.getFluidAmount(), tank.reagentTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFFFFFFF).alternateFilledColor(0xFFB2E9B9).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityMineralAnalyzer){
				TileEntityMineralAnalyzer tank = (TileEntityMineralAnalyzer)te;
				probeInfo.progress(tank.getPower(), tank.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.sulfTank.getFluidAmount(), tank.sulfTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFBFBFBF).alternateFilledColor(0xFFC5C5C5).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.chloTank.getFluidAmount(), tank.chloTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFA0C0E7).alternateFilledColor(0xFFA0E7E5).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.fluoTank.getFluidAmount(), tank.fluoTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFE7DBA0).alternateFilledColor(0xFFE5E7A0).borderColor(0x000000).numberFormat(NumberFormat.FULL));
			}

			if(te instanceof TileEntityChemicalExtractor){
				TileEntityChemicalExtractor tank = (TileEntityChemicalExtractor)te;
				probeInfo.progress(tank.getPower(), tank.getPowerMax(), probeInfo.defaultProgressStyle().suffix(" ticks").filledColor(0xFFFFB400).alternateFilledColor(0xFFFF7200).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				if(!tank.hasFuelBlend()){
					probeInfo.progress(tank.getRedstone(), tank.getRedstoneMax(), probeInfo.defaultProgressStyle().suffix("RF").filledColor(0xFFFF0000).alternateFilledColor(0xFF5A0303).borderColor(0x000000).numberFormat(NumberFormat.COMPACT));
				}
				probeInfo.progress(tank.nitrTank.getFluidAmount(), tank.nitrTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFCAB055).alternateFilledColor(0xFFCEC091).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.phosTank.getFluidAmount(), tank.phosTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFDEDCBA).alternateFilledColor(0xFFCAC9C1).borderColor(0x000000).numberFormat(NumberFormat.FULL));
				probeInfo.progress(tank.cyanTank.getFluidAmount(), tank.cyanTank.getCapacity(), probeInfo.defaultProgressStyle().suffix(" mB").filledColor(0xFFDDF6F3).alternateFilledColor(0xFFCBE4E1).borderColor(0x000000).numberFormat(NumberFormat.FULL));
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