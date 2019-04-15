package com.globbypotato.rockhounding_chemistry.compat.waila;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.machines.MachinesB;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierBurner;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierCooler;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.TESlurryPond;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

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

public class WailaMachinesB implements IWailaDataProvider{

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
			if(te instanceof TESlurryPond){
				TESlurryPond tank = (TESlurryPond)te;
				if(tank.hasInputFluid()){
					currenttip.add(TextFormatting.GRAY + "Input: " + TextFormatting.WHITE + tank.getInputFluid().getLocalizedName() + " - " + tank.getInputAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Input: " + TextFormatting.WHITE + "Empty");
				}
				if(tank.hasOutputFluid()){
					currenttip.add(TextFormatting.GRAY + "Output: " + TextFormatting.WHITE + tank.getOutputFluid().getLocalizedName() + " - " + tank.getOutputAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Output: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEGasifierTank){
				TEGasifierTank tank = (TEGasifierTank)te;
				if(tank.hasInputFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getInputFluid().getLocalizedName() + " - " + tank.getInputAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEGasifierCooler){
				TEGasifierCooler tank = (TEGasifierCooler)te;
				currenttip.add(TextFormatting.GRAY + "Temperature: " + TextFormatting.WHITE + tank.getTemperature() + "/" + tank.getTemperatureMax() + "K");
			}
			if(te instanceof TEGasifierBurner){
				TEGasifierBurner tank = (TEGasifierBurner)te;
				if(tank.hasReactant()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getReactant().getLocalizedName() + " - " + tank.getReactantAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEPressureVessel){
				TEPressureVessel tank = (TEPressureVessel)te;
				if(tank.inputTankHasGas()){
					String content = GuiUtils.composeGasContent(tank.inputTank);
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + content);
				}else{
					currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEAirCompressor){
				TEAirCompressor engine = (TEAirCompressor)te;
				currenttip.add(TextFormatting.GRAY + "Fuel Storage: " + TextFormatting.GOLD + engine.getPower() + "/" + engine.getPowerMax() + " ticks");
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
		registrar.registerBodyProvider(new WailaMachinesB(), MachinesB.class);
	}

}