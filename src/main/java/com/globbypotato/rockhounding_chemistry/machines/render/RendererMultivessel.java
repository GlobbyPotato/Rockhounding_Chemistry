package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEMultivessel;

import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTank;

public class RendererMultivessel extends TileEntitySpecialRenderer<TEMultivessel>{
	
	@Override
	public void render(TEMultivessel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        double tankX = 0;
        double tankZ = 0;
		if(te != null){
            final FluidTank tank_Ar = te.tank_Ar;
            if (tank_Ar != null && tank_Ar.getFluid() != null && tank_Ar.getFluidAmount() > 0) {
            	if(te.getFacing() == EnumFacing.EAST){
            		tankX = 0.088; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.WEST){
            		tankX = 0.663; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.NORTH){
            		tankX = 0.088; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.SOUTH){
            		tankX = 0.663; tankZ = 0.088;
            	}
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(tank_Ar.getFluid(), te.getPos(), tankX, 0.125, tankZ, 		0.0d, 0.0d, 0.0d, 		0.24, (tank_Ar.getFluidAmount() * 0.75 / tank_Ar.getCapacity()), 0.24);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            final FluidTank tank_CO = te.tank_CO;
            if (tank_CO != null && tank_CO.getFluid() != null && tank_CO.getFluidAmount() > 0) {
            	if(te.getFacing() == EnumFacing.EAST){
            		tankX = 0.376; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.WEST){
            		tankX = 0.376; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.NORTH){
            		tankX = 0.088; tankZ = 0.376;
            	}else if(te.getFacing() == EnumFacing.SOUTH){
            		tankX = 0.663; tankZ = 0.376;
            	}
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(tank_CO.getFluid(), te.getPos(), tankX, 0.125, tankZ, 		0.0d, 0.0d, 0.0d, 		0.24, (tank_CO.getFluidAmount() * 0.75 / tank_CO.getCapacity()), 0.24);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            final FluidTank tank_Ne = te.tank_Ne;
            if (tank_Ne != null && tank_Ne.getFluid() != null && tank_Ne.getFluidAmount() > 0) {
            	if(te.getFacing() == EnumFacing.EAST){
            		tankX = 0.663; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.WEST){
            		tankX = 0.088; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.NORTH){
            		tankX = 0.088; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.SOUTH){
            		tankX = 0.663; tankZ = 0.663;
            	}
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(tank_Ne.getFluid(), te.getPos(), tankX, 0.125, tankZ, 		0.0d, 0.0d, 0.0d, 		0.24, (tank_Ne.getFluidAmount() * 0.75 / tank_Ne.getCapacity()), 0.24);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            final FluidTank tank_He = te.tank_He;
            if (tank_He != null && tank_He.getFluid() != null && tank_He.getFluidAmount() > 0) {
            	if(te.getFacing() == EnumFacing.EAST){
            		tankX = 0.663; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.WEST){
            		tankX = 0.088; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.NORTH){
            		tankX = 0.663; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.SOUTH){
            		tankX = 0.088; tankZ = 0.663;
            	}
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(tank_He.getFluid(), te.getPos(), tankX, 0.125, tankZ, 		0.0d, 0.0d, 0.0d, 		0.24, (tank_He.getFluidAmount() * 0.75 / tank_He.getCapacity()), 0.24);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            final FluidTank tank_Kr = te.tank_Kr;
            if (tank_Kr != null && tank_Kr.getFluid() != null && tank_Kr.getFluidAmount() > 0) {
            	if(te.getFacing() == EnumFacing.EAST){
            		tankX = 0.376; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.WEST){
            		tankX = 0.376; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.NORTH){
            		tankX = 0.663; tankZ = 0.376;
            	}else if(te.getFacing() == EnumFacing.SOUTH){
            		tankX = 0.088; tankZ = 0.376;
            	}
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(tank_Kr.getFluid(), te.getPos(), tankX, 0.125, tankZ, 		0.0d, 0.0d, 0.0d, 		0.24, (tank_Kr.getFluidAmount() * 0.75 / tank_Kr.getCapacity()), 0.24);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            final FluidTank tank_Xe = te.tank_Xe;
            if (tank_Xe != null && tank_Xe.getFluid() != null && tank_Xe.getFluidAmount() > 0) {
            	if(te.getFacing() == EnumFacing.EAST){
            		tankX = 0.088; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.WEST){
            		tankX = 0.663; tankZ = 0.088;
            	}else if(te.getFacing() == EnumFacing.NORTH){
            		tankX = 0.663; tankZ = 0.663;
            	}else if(te.getFacing() == EnumFacing.SOUTH){
            		tankX = 0.088; tankZ = 0.088;
            	}
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(tank_Xe.getFluid(), te.getPos(), tankX, 0.125, tankZ, 		0.0d, 0.0d, 0.0d, 		0.24, (tank_Xe.getFluidAmount() * 0.75 / tank_Xe.getCapacity()), 0.24);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

		}
	}
}