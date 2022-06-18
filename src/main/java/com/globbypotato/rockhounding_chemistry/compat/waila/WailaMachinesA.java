package com.globbypotato.rockhounding_chemistry.compat.waila;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSaltStages;
import com.globbypotato.rockhounding_chemistry.machines.MachinesA;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEProfilingBench;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidTank;

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

public class WailaMachinesA implements IWailaDataProvider{

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
			if(te instanceof TEMineralSizerController){
				TEMineralSizerController controller = (TEMineralSizerController)te;
				currenttip.add(TextFormatting.GRAY + "Comminution Level: " + TextFormatting.GREEN + controller.getComminution());
			}
			if(te instanceof TEPowerGenerator){
				TEPowerGenerator engine = (TEPowerGenerator)te;
				if(engine.hasPower()){
					currenttip.add(TextFormatting.GRAY + "Fuel Storage: " + TextFormatting.GOLD + engine.getPower() + "/" + engine.getPowerMax() + " ticks");
				}
				if(engine.hasRedstone()){
					currenttip.add(TextFormatting.GRAY + "Energy Storage: " + TextFormatting.RED + engine.getRedstone() + "/" + engine.getRedstoneMax() + " RF");
				}
			}
			if(te instanceof TEFluidTank){
				TEFluidTank tank = (TEFluidTank)te;
				if(tank.hasTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEProfilingBench){
				TEProfilingBench casting = (TEProfilingBench)te;
		    	currenttip.add(TextFormatting.GRAY + "Pattern: " + TextFormatting.AQUA + EnumCasting.getFormalName(casting.getCasting()));
			}
			if(te instanceof TEEvaporationTank){
				TEEvaporationTank tank = (TEEvaporationTank)te;
				currenttip.add(TextFormatting.GRAY + "Stage " + (tank.getStage() + 1) + ": " + TextFormatting.WHITE + EnumSaltStages.getStageName(tank.getStage()));
				if(tank.getStage() == EnumSaltStages.STAGE_A.ordinal()){
					if(tank.hasTankFluid()){
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getTankFluid().getLocalizedName() + " - " + tank.getTankAmount() + "/" + tank.getTankCapacity() + " mB");
					}else{
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
					}
				}
				if(tank.getPurge() < tank.finalStage()){
					currenttip.add(TextFormatting.GRAY + "Purging: " + TextFormatting.AQUA + EnumSaltStages.getStageName(tank.getPurge()));
				}else{
					currenttip.add(TextFormatting.GRAY + "Purging: " + TextFormatting.RED + "Disabled");
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
		registrar.registerBodyProvider(new WailaMachinesA(), MachinesA.class);
	}

}