package com.globbypotato.rockhounding_chemistry.compat.waila;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.blocks.GanBlocks;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class WailaGAN implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return new ItemStack(accessor.getBlock(), 1, accessor.getMetadata());
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
		Block block = world.getBlockState(pos).getBlock();
		int tier = 1;
		if(block instanceof GanBlocks){
			if(itemStack.getMetadata() >= 6 && itemStack.getMetadata() <= 11){
				tier = 2;
			}
			if(itemStack.getMetadata() <= 11){
				currenttip.add(TextFormatting.GRAY + "Tier: " + TextFormatting.WHITE + tier);
			}
		}
		
		if(te != null){
			if(te instanceof TileEntityAirCompresser){
				TileEntityAirCompresser tank = (TileEntityAirCompresser)te;
				currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Compressed Air");
		    	currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.AQUA + tank.getAir() + " / " + tank.getAirMax() + " Units");
			}
			if(te instanceof TileEntityAirChiller){
				TileEntityAirChiller tank = (TileEntityAirChiller)te;
				if(tank.inputTank.getFluid() != null){
					if(tank.inputTank.getFluid().getFluid().getTemperature() <= 300){
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.inputTank.getFluid().getLocalizedName() + " (" + tank.inputTank.getFluid().getFluid().getTemperature() + "K)");
						currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.GREEN + tank.inputTank.getFluidAmount() + " / " + tank.inputTank.getCapacity() + " mB");
					}else{
						currenttip.add(TextFormatting.RED + "Invalid Refrigerant. Too high temperature (>300K)");
					}
				}else{
					currenttip.add(TextFormatting.GOLD + "No Refrigerant Available");
				}
			}
			if(te instanceof TileEntityNitrogenTank){
				TileEntityNitrogenTank tank = (TileEntityNitrogenTank)te;
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Liquid Nitrogen");
					currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.WHITE + tank.inputTank.getFluidAmount() + " / " + tank.inputTank.getCapacity() + " mB");
			}
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
		registrar.registerBodyProvider(new WailaGAN(), GanBlocks.class);
	}

}