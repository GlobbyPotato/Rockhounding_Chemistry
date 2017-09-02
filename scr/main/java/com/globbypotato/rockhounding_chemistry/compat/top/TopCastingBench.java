package com.globbypotato.rockhounding_chemistry.compat.top;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;
import com.google.common.base.Function;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TopCastingBench implements IProbeInfoProvider{

	@Override
	public String getID() {
        return Reference.MODID + ":" + "casting_bench";
	}

	public static class EnergyInfo implements IProbeConfigProvider{
		@Override
		public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {}

		@Override
		public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
	        final TileEntity te = world.getTileEntity(data.getPos());
			if(te instanceof TileEntityCastingBench){
				config.setRFMode(0);
			}			
		}
	}


	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        final TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityCastingBench) {
        	TileEntityCastingBench bench = (TileEntityCastingBench)te;
        	String pattern = TextFormatting.GRAY + "Casting Pattern: " + TextFormatting.AQUA + EnumCasting.getFormalName(bench.currentCast);

            probeInfo.text(pattern);
        }
	}

    public static class getTOP implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe top;

        @Nullable
        @Override
        public Void apply (ITheOneProbe theOneProbe) {

        	top = theOneProbe;
            top.registerProvider(new TopCastingBench());
            top.registerProbeConfigProvider(new EnergyInfo());
            return null;
        }
    }

}