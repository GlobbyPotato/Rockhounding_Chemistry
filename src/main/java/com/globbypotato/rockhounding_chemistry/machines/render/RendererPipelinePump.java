package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelinePump;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererPipelinePump extends TileEntitySpecialRenderer<TEPipelinePump>{

	@Override
	public void render(TEPipelinePump te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
			World world = Minecraft.getMinecraft().world;
			ItemStack plug = new ItemStack(Blocks.REDSTONE_BLOCK);
			EntityItem plugEntity = new EntityItem(world, 0, 0, 0, plug);
			plugEntity.hoverStart = 0;
			if(!te.isActive()){
				for(int side = 0; side < 6; side++){
					GlStateManager.pushMatrix();
					{
						GlStateManager.translate(x, y, z);
						GlStateManager.rotate(0F, 0, 0, 0);
						if(side == 0){
							GlStateManager.translate(0.5, 0.185, 0.5);
						}else if(side == 1){
							GlStateManager.translate(0.5, 0.64, 0.5);
						}else if(side == 2){
							GlStateManager.translate(0.50, 0.41, 0.275);
						}else if(side == 3){
							GlStateManager.translate(0.275, 0.41, 0.5);
						}else if(side == 4){
							GlStateManager.translate(0.50, 0.41, 0.725);
						}else if(side == 5){
							GlStateManager.translate(0.725, 0.41, 0.50);
						}
						GlStateManager.scale(0.25, 0.25, 0.25);
						Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
					}
					GlStateManager.popMatrix();					
				}
			}

		}
	}
}