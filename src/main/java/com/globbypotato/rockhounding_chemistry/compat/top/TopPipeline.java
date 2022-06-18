package com.globbypotato.rockhounding_chemistry.compat.top;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEGaslinePump;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelineValve;
import com.google.common.base.Function;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TopPipeline implements IProbeInfoProvider{

	@Override
	public String getID() {
        return Reference.MODID + ":" + "pipeline";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        final TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TEPipelinePump) {
        	TEPipelinePump pump = (TEPipelinePump)te;
			String statusstring = TextFormatting.GREEN + "Enabled";
			if(!pump.isActive()){
				statusstring = TextFormatting.RED + "Disabled";
			}
        	String stage = TextFormatting.GRAY + "Status: " + statusstring;
            probeInfo.text(stage);

			String uphradestring = TextFormatting.AQUA + "Normal (1 B)";
			if(pump.hasUpgrade()){
				uphradestring = TextFormatting.DARK_AQUA + "Advanced (100 B)";
			}
        	String tier = TextFormatting.GRAY + "Tier: " + uphradestring;
            probeInfo.text(tier);

        	String delay = TextFormatting.GRAY + "Delay: " + TextFormatting.GOLD + pump.getDelay() + " ticks";
            probeInfo.text(delay);

        }
        if (te instanceof TEPipelineValve) {
        	TEPipelineValve pump = (TEPipelineValve)te;
			String statusstring = TextFormatting.GREEN + "Enabled";
			if(!pump.isActive()){
				statusstring = TextFormatting.RED + "Disabled";
			}
        	String stage = TextFormatting.GRAY + "Status: " + statusstring;
            probeInfo.text(stage);
            
            String robinstring = TextFormatting.RED + "Disabled";
			if(pump.hasRoundRobin()){
				robinstring = TextFormatting.GREEN + "Enabled";
			}
        	String robin = TextFormatting.GRAY + "Round Robin: " + robinstring;
            probeInfo.text(robin);
        }
        if (te instanceof TEGaslinePump) {
        	TEGaslinePump pump = (TEGaslinePump)te;
			String statusstring = TextFormatting.GREEN + "Enabled";
			if(!pump.isActive()){
				statusstring = TextFormatting.RED + "Disabled";
			}
        	String stage = TextFormatting.GRAY + "Status: " + statusstring;
            probeInfo.text(stage);

			String uphradestring = TextFormatting.AQUA + "Normal (1 cu)";
			if(pump.hasUpgrade()){
				uphradestring = TextFormatting.DARK_AQUA + "Advanced (10 cu)";
			}
        	String tier = TextFormatting.GRAY + "Tier: " + uphradestring;
            probeInfo.text(tier);

        	String delay = TextFormatting.GRAY + "Delay: " + TextFormatting.GOLD + pump.getDelay() + " ticks";
            probeInfo.text(delay);

        }
	}

    public static class getTOP implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe top;

        @Nullable
        @Override
        public Void apply (ITheOneProbe theOneProbe) {

        	top = theOneProbe;
            top.registerProvider(new TopPipeline());
            return null;
        }
    }

}