package com.globbypotato.rockhounding_chemistry.compat.waila;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.GaslinePump;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGaslinePump;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class WailaGaslinePump implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return new ItemStack(ModBlocks.GASLINE_PUMP);
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		BlockPos pos = accessor.getPosition();
		World world = accessor.getWorld();
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof TEGaslinePump){
			TEGaslinePump pump = (TEGaslinePump)te;
			String statusstring = TextFormatting.GREEN + "Enabled";
			if(!pump.isActive()){
				statusstring = TextFormatting.RED + "Disabled";
			}
	    	currenttip.add(TextFormatting.GRAY + "Status: " + statusstring);

			String upgradestring = TextFormatting.AQUA + "Normal (1 cu)";
			if(pump.hasUpgrade()){
				upgradestring = TextFormatting.DARK_AQUA + "Advanced (10 cu)";
			}

	    	currenttip.add(TextFormatting.GRAY + "Tier: " + upgradestring);

	    	currenttip.add(TextFormatting.GRAY + "Delay: " + TextFormatting.GOLD + pump.getDelay() + " ticks");

		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaGaslinePump(), GaslinePump.class);
	}

}