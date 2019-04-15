package com.globbypotato.rockhounding_chemistry.compat.waila;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.machines.MachinesD;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasHolderTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.tile.TETransposer;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

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

public class WailaMachinesD implements IWailaDataProvider{

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
			if(te instanceof TEGasHolderBase){
				TEGasHolderBase tank = (TEGasHolderBase)te;
				if(tank.inputTankHasGas()){
					String content = GuiUtils.composeGasContent(tank.inputTank);
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + content);
				}else{
					currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEGasHolderTop){
				TileEntity base = world.getTileEntity(pos.offset(EnumFacing.DOWN));
				if(base instanceof TEGasHolderBase){
					TEGasHolderBase tank = (TEGasHolderBase)base;
					if(tank.inputTankHasGas()){
						String content = GuiUtils.composeGasContent(tank.inputTank);
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + content);
					}else{
						currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.WHITE + "Empty");
					}
				}
			}
			if(te instanceof TEOrbiter){
				TEOrbiter tank = (TEOrbiter)te;
				if(tank.wasteHasFluid()){
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getWasteFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getWasteAmount() + "/" + tank.getWasteCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
				
				if(tank.xpJuiceExists()){
					if(tank.juiceHasFluid()){
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + tank.getJuiceFluid().getLocalizedName() + " - " + TextFormatting.WHITE + tank.getJuiceAmount() + "/" + tank.getJuiceCapacity() + " mB");
					}else{
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
					}
				}else{
					if(tank.getXPCount() > 0){
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Stored Experience" + " - " + TextFormatting.WHITE + tank.getXPCount() + "/" + tank.getXPCountMax() + " xp");
					}else{
						currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
					}
				}
			}
			if(te instanceof TETransposer){
				TETransposer tank = (TETransposer)te;
				if(tank.hasInputTankMain()){
					if(tank.getInputTankMain().getFluid().isGaseous()){
						String content = GuiUtils.composeGasContent(tank.inputTankMain);
						currenttip.add(TextFormatting.GRAY + "Input: " + TextFormatting.WHITE + content);
					}else{
						currenttip.add(TextFormatting.GRAY + "Input: " + TextFormatting.WHITE + tank.getInputTankMain().getLocalizedName() + " - " + tank.getInputTankMainAmount() + "/" + tank.getFluidCapacity() + " mB");
					}
				}else{
					currenttip.add(TextFormatting.GRAY + "Storage: " + TextFormatting.WHITE + "Empty");
				}
				if(tank.hasOutputTankFluid()){
					currenttip.add(TextFormatting.GRAY + "Fluid: " + TextFormatting.WHITE + tank.getOutputTankFluid().getLocalizedName() + " - " + tank.getOutputTankFluidAmount() + "/" + tank.getFluidCapacity() + " mB");
				}
				if(tank.hasOutputTankGas()){
					String content = GuiUtils.composeGasContent(tank.outputTankGas);
					currenttip.add(TextFormatting.GRAY + "Gas: " + TextFormatting.WHITE + content);
				}
			}
			if(te instanceof TEFlotationTank){
				TEFlotationTank tank = (TEFlotationTank)te;
				if(tank.hasSolventFluid()){
					currenttip.add(TextFormatting.GRAY + "Solvent: " + TextFormatting.WHITE + tank.getSolventFluid().getLocalizedName() + " - " + tank.getSolventAmount() + "/" + tank.getTankCapacity() + " mB");
				}else{
					currenttip.add(TextFormatting.GRAY + "Content: " + TextFormatting.WHITE + "Empty");
				}
			}
			if(te instanceof TEContainmentTank){
				TEContainmentTank tank = (TEContainmentTank)te;
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
		registrar.registerBodyProvider(new WailaMachinesD(), MachinesD.class);
	}

}