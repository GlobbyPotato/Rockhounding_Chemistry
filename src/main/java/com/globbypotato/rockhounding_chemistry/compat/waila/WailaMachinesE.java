package com.globbypotato.rockhounding_chemistry.compat.waila;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.MachinesE;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TETransposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWaterPump;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExhaustionValve;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGasHolderTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TESlurryDrum;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class WailaMachinesE implements IWailaDataProvider{

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
		if(te != null){
			if(te instanceof TEWaterPump){
				TEWaterPump tank = (TEWaterPump)te;
				if(tank.hasTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
				currenttip.add(TextFormatting.GRAY + "Compression: " + TextFormatting.AQUA + tank.getCompressor());
				currenttip.add(TextFormatting.GRAY + "Tier: " + TextFormatting.DARK_AQUA + (tank.getTier() + 1) + TextFormatting.GRAY + " (" + tank.tierFactor() + " mB)");
			}
			if(te instanceof TEExhaustionValve){
				TileEntity vessel = world.getTileEntity(pos.offset(EnumFacing.DOWN));
				if(vessel instanceof TEPressureVessel){
					TEPressureVessel ves = (TEPressureVessel)vessel;
					currenttip.add(TextFormatting.GRAY + "Tolerance: " + TextFormatting.RED + ves.getCollapse() + "/" + ModConfig.pressureTolerance + " units");
				}
				if(vessel instanceof TEMultivessel){
					TEMultivessel ves = (TEMultivessel)vessel;
					currenttip.add(TextFormatting.GRAY + "Tolerance: " + TextFormatting.RED + ves.getCollapse() + "/" + ModConfig.pressureTolerance + " units");
				}
				if(vessel instanceof TETransposer){
					TETransposer ves = (TETransposer)vessel;
					currenttip.add(TextFormatting.GRAY + "Tolerance: " + TextFormatting.RED + ves.getCollapse() + "/" + ModConfig.pressureTolerance + " units");
				}
				if(vessel instanceof TEGasHolderTop){
					TileEntity holder = world.getTileEntity(pos.offset(EnumFacing.DOWN, 2));
					if(holder instanceof TEGasHolderBase){
						TEGasHolderBase ves = (TEGasHolderBase)holder;
						currenttip.add(TextFormatting.GRAY + "Tolerance: " + TextFormatting.RED + ves.getCollapse() + "/" + ModConfig.pressureTolerance + " units");
					}
				}
			}
			if(te instanceof TESlurryDrum){
				TESlurryDrum tank = (TESlurryDrum)te;
				if(tank.hasTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEBufferTank){
				TEBufferTank tank = (TEBufferTank)te;
				if(tank.hasTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEWashingTank){
				TEWashingTank tank = (TEWashingTank)te;
				if(tank.hasTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEStirredTankOut){
				TEStirredTankOut tank = (TEStirredTankOut)te;
				if(tank.hasTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
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
		registrar.registerBodyProvider(new WailaMachinesE(), MachinesE.class);
	}

}