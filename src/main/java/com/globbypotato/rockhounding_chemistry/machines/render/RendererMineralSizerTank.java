package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class RendererMineralSizerTank extends TileEntitySpecialRenderer<TEMineralSizerTank>{
	
	@Override
	public void render(TEMineralSizerTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
			World world = Minecraft.getMinecraft().world;
			float offset = 0F;
			for(int gear = 0; gear < TEMineralSizerTank.SLOT_INPUTS.length; gear++){
				ItemStack plug = te.getInput().getStackInSlot(gear).copy();
				ItemStack plugItem = BaseRecipes.crushing_gear.copy();
				EntityItem plugEntity = new EntityItem(world, 0, 0, 0, plugItem);
				plugEntity.hoverStart = 0;
				if(!plug.isEmpty()){
					if(te.getFacing() == EnumFacing.NORTH || te.getFacing() == EnumFacing.SOUTH){
						offset = 0.17F + (0.22F * gear);
						GlStateManager.pushMatrix();
							GlStateManager.translate(x, y, z);
							GlStateManager.translate(0.31, 0.72, offset);
							GlStateManager.rotate(te.getRolling(), 0, 0, 1);	
							GlStateManager.scale(0.55, 0.55, 6.0);
							GlStateManager.translate(0, -0.475, 0);
							Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
						GlStateManager.popMatrix();
						GlStateManager.pushMatrix();
							GlStateManager.translate(x, y, z);
							GlStateManager.translate(0.69, 0.72, offset);
							GlStateManager.rotate(te.getRolling(), 0, 0, 1);	
							GlStateManager.scale(0.55, 0.55, 6.0);
							GlStateManager.translate(0.0, -0.475, 0);
							Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
						GlStateManager.popMatrix();
						if(gear > 0){
							offset = 0.05F + (0.225F * gear);
							GlStateManager.pushMatrix();
								GlStateManager.translate(x, y, z);
								GlStateManager.translate(0.50, 0.45, offset);
								GlStateManager.rotate(360 - (te.getRolling() + 45), 0, 0, 1);	
								GlStateManager.scale(0.72, 0.72, 6.0);
								GlStateManager.translate(0.0, -0.475, 0);
								Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
							GlStateManager.popMatrix();
						}
					}else if(te.getFacing() == EnumFacing.WEST || te.getFacing() == EnumFacing.EAST){
						offset = 0.17F + (0.22F * gear);
						GlStateManager.pushMatrix();
							GlStateManager.translate(x, y, z);
							GlStateManager.rotate(90, 0, 1, 0);	
							GlStateManager.translate(-1.0, 0, 0);
							GlStateManager.translate(0.31, 0.72, offset);
							GlStateManager.rotate(te.getRolling(), 0, 0, 1);	
							GlStateManager.scale(0.55, 0.55, 6.0);
							GlStateManager.translate(0, -0.475, 0);
							Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
						GlStateManager.popMatrix();
						GlStateManager.pushMatrix();
							GlStateManager.translate(x, y, z);
							GlStateManager.rotate(90, 0, 1, 0);	
							GlStateManager.translate(-1.0, 0, 0);
							GlStateManager.translate(0.69, 0.72, offset);
							GlStateManager.rotate(te.getRolling(), 0, 0, 1);	
							GlStateManager.scale(0.55, 0.55, 6.0);
							GlStateManager.translate(0.0, -0.475, 0);
							Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
						GlStateManager.popMatrix();
						if(gear > 0){
							offset = 0.05F + (0.225F * gear);
							GlStateManager.pushMatrix();
								GlStateManager.translate(x, y, z);
								GlStateManager.rotate(90, 0, 1, 0);	
								GlStateManager.translate(-1.0, 0, 0);
								GlStateManager.translate(0.50, 0.45, offset);
								GlStateManager.rotate(360 - (te.getRolling() + 45), 0, 0, 1);	
								GlStateManager.scale(0.72, 0.72, 6.0);
								GlStateManager.translate(0.0, -0.475, 0);
								Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
							GlStateManager.popMatrix();
						}					
					}
				}
			}
		}
	}
}