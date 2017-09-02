package com.globbypotato.rockhounding_chemistry.compat.top;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.enums.EnumSaltStages;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltMaker;
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

public class TopSaltTank implements IProbeInfoProvider{

	@Override
	public String getID() {
        return Reference.MODID + ":" + "salt_tank";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        final TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntitySaltMaker) {
        	TileEntitySaltMaker tank = (TileEntitySaltMaker)te;
        	String stage = TextFormatting.GRAY + "Current Stage: " + TextFormatting.AQUA + EnumSaltStages.getStageName(tank.getBlockMetadata());

            probeInfo.text(stage);
        }
	}

    public static class getTOP implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe top;

        @Nullable
        @Override
        public Void apply (ITheOneProbe theOneProbe) {

        	top = theOneProbe;
            top.registerProvider(new TopSaltTank());
            return null;
        }
    }

}