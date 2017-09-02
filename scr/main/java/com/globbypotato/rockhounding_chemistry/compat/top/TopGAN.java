package com.globbypotato.rockhounding_chemistry.compat.top;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.GanBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TopGAN implements IProbeInfoProvider{

	@Override
	public String getID() {
        return Reference.MODID + ":" + "gan_blocks";
	}

	public static class EnergyInfo implements IProbeConfigProvider{
		@Override
		public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {}

		@Override
		public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
	        final TileEntity te = world.getTileEntity(data.getPos());
			if(te instanceof TileEntityNitrogenTank
			|| te instanceof TileEntityAirChiller
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

		int tier = 1;
		String tierString = "";
		String content = "";
		String storage = "";
		if(block instanceof GanBlocks){
			if(meta >= 6 && meta <= 11){
				tier = 2;
			}
			if(meta <= 11){
				tierString = TextFormatting.GRAY + "Tier: " + TextFormatting.WHITE + tier;
			}
		}

		if(te != null){
			if(te instanceof TileEntityAirCompresser){
				TileEntityAirCompresser tank = (TileEntityAirCompresser)te;
				probeInfo.progress(tank.getAir(), tank.getAirMax(), probeInfo.defaultProgressStyle()
																	.suffix(" unit/s")
																	.filledColor(0xFFAEE1F2)
																	.alternateFilledColor(0xFF75D2F1)
																	.borderColor(0x000000)
																	.numberFormat(NumberFormat.FULL));

			}
			if(te instanceof TileEntityAirChiller){
				TileEntityAirChiller tank = (TileEntityAirChiller)te;
				if(tank.inputTank.getFluid() != null){
					if(tank.inputTank.getFluid().getFluid().getTemperature() <= 300){
						content = TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.inputTank.getFluid().getLocalizedName() + " (" + tank.inputTank.getFluid().getFluid().getTemperature() + "K)";
						probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle()
																											.suffix(" mB")
																											.filledColor(0xFF75DB88)
																											.alternateFilledColor(0xFFB1D5B8)
																											.borderColor(0x000000)
																											.numberFormat(NumberFormat.FULL));
					}else{
						content = TextFormatting.RED + "Invalid Refrigerant. Too high temperature (>300K)";
					}
				}else{
					content = TextFormatting.GOLD + "No Refrigerant Available";
				}
			}
			if(te instanceof TileEntityNitrogenTank){
				TileEntityNitrogenTank tank = (TileEntityNitrogenTank)te;
				content = TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Liquid Nitrogen";
				probeInfo.progress(tank.inputTank.getFluidAmount(), tank.inputTank.getCapacity(), probeInfo.defaultProgressStyle()
																									.suffix(" mB")
																									.filledColor(0xFFFFFFFF)
																									.alternateFilledColor(0xFFD3D3D3)
																									.borderColor(0x000000)
																									.numberFormat(NumberFormat.FULL));
			}
		}
        
		String[] multistring = {tierString, content};
		for(int x = 0; x < multistring.length; x++){
			probeInfo.text(multistring[x]);
		}
	}

    public static class getTOP implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe top;

        @Nullable
        @Override
        public Void apply (ITheOneProbe theOneProbe) {

        	top = theOneProbe;
            top.registerProvider(new TopGAN());
            top.registerProbeConfigProvider(new EnergyInfo());
            return null;
        }
    }

}