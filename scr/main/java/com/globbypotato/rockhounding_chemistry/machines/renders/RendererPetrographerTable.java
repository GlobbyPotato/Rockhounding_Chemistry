package com.globbypotato.rockhounding_chemistry.machines.renders;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererPetrographerTable extends TileEntitySpecialRenderer<TileEntityPetrographerTable>{

	@Override
	public void renderTileEntityAt(TileEntityPetrographerTable te, double x, double y, double z, float partialTicks, int destroyStage) {
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
		TileEntityPetrographerTable table = (TileEntityPetrographerTable)te;
		if(table != null){
			World world = Minecraft.getMinecraft().theWorld;
			int metadata = table.getBlockMetadata();
			if(table.getInput().getStackInSlot(table.TOOL_SLOT) != null){
				ItemStack petro = table.getInput().getStackInSlot(table.TOOL_SLOT).copy();
				EntityItem petroEntity = new EntityItem(world, 0, 0, 0, petro);
				petroEntity.hoverStart = 0;
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					if(metadata == 5){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(270F, 0, 0, 1);
						GlStateManager.translate(0.47, -0.07, 0.815);
					}else if(metadata == 4){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(90F, 0, 0, 1);
						GlStateManager.translate(-0.53, -1.07, 0.815);
					}else if(metadata == 3){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(180F, 0, 0, 1);
						GlStateManager.translate(-0.53, -0.07, 0.815);
					}else if(metadata == 2){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(90F, 0, 0, 1);
						GlStateManager.translate(-0.76, -0.80, 0.815);
					}
					GlStateManager.scale(0.7, 0.7, 0.7);
					Minecraft.getMinecraft().getRenderManager().doRenderEntity(petroEntity, 0, 0, 0, 0F, 0F, false);
				}
				GlStateManager.popMatrix();					
			}
			if(table.getInput().getStackInSlot(table.ORE_SLOT) != null){
				ItemStack gangue = table.getInput().getStackInSlot(table.ORE_SLOT).copy();
				EntityItem gangueEntity = new EntityItem(world, 0, 0, 0, gangue);
				gangueEntity.hoverStart = 0;
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					if(metadata == 5){
						GlStateManager.rotate(0F, 1, 0, 0);
						GlStateManager.translate(0.72, 0.62, 0.27);
					}else if(metadata == 4){
						GlStateManager.rotate(0F, 1, 0, 0);
						GlStateManager.translate(0.27, 0.62, 0.72);
					}else if(metadata == 3){
						GlStateManager.rotate(0F, 1, 0, 0);
						GlStateManager.translate(0.72, 0.62, 0.72);
						GlStateManager.scale(0.8, 0.8, 0.8);
					}else if(metadata == 2){
						GlStateManager.rotate(0F, 1, 0, 0);
						GlStateManager.translate(0.27, 0.62, 0.27);
					}
					GlStateManager.scale(0.8, 0.8, 0.8);
					Minecraft.getMinecraft().getRenderManager().doRenderEntity(gangueEntity, 0, 0, 0, 0F, 0F, false);
				}
				GlStateManager.popMatrix();					
			}
			if(table.getInput().getStackInSlot(table.SHARD_SLOT) != null){
				ItemStack specimen = table.getInput().getStackInSlot(table.SHARD_SLOT).copy();
				EntityItem specimenEntity = new EntityItem(world, 0, 0, 0, specimen);
				specimenEntity.hoverStart = 0;
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					if(metadata == 5){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(270F, 0, 0, 1);
						GlStateManager.translate(0.73, 0.48, 0.81);
					}else if(metadata == 4){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(270F, 0, 0, 1);
						GlStateManager.translate(0.27, 0.04, 0.81);
					}else if(metadata == 3){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(270F, 0, 0, 1);
						GlStateManager.translate(0.73, 0.04, 0.81);
					}else if(metadata == 2){
						GlStateManager.rotate(270F, 1, 0, 0);
						GlStateManager.rotate(270F, 0, 0, 1);
						GlStateManager.translate(0.27, 0.48, 0.81);
					}
					GlStateManager.scale(0.5, 0.5, 0.5);
					Minecraft.getMinecraft().getRenderManager().doRenderEntity(specimenEntity, 0, 0, 0, 0F, 0F, false);
				}
				GlStateManager.popMatrix();					
			}

		}
	}
}