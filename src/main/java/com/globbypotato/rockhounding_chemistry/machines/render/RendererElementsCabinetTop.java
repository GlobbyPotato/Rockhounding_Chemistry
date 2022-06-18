package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetTop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererElementsCabinetTop extends TileEntitySpecialRenderer<TEElementsCabinetTop>{

	@Override
	public void render(TEElementsCabinetTop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		if(te != null){
			int metadata = te.getFacing().ordinal();
			World world = Minecraft.getMinecraft().world;
			ItemStack renderStack = ItemStack.EMPTY;

			if(!te.cylinderSlot().isEmpty()){
				ItemStack slotSack = te.cylinderSlot().copy();
				renderStack = new ItemStack(slotSack.getItem()) ;
			}
			if(!renderStack.isEmpty()){
				EntityItem patternEntity = new EntityItem(world, 0, 0, 0, renderStack);
				patternEntity.hoverStart = 0;
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					if(metadata == 2){
						GlStateManager.translate(0.5, 0.43, 0.8);
						GlStateManager.rotate(0F, 0, 1, 0);
					}else if(metadata == 3){
						GlStateManager.translate(0.5, 0.43, 0.2);
						GlStateManager.rotate(0F, 0, 1, 0);
					}else if(metadata == 4){
						GlStateManager.translate(0.8, 0.43, 0.5);
						GlStateManager.rotate(90F, 0, 1, 0);
					}else if(metadata == 5){
						GlStateManager.translate(0.2, 0.43, 0.5);
						GlStateManager.rotate(90F, 0, 1, 0);
					}
					GlStateManager.scale(0.55, 0.55, 2.5);
					Minecraft.getMinecraft().getRenderManager().renderEntity(patternEntity, 0, 0, 0, 0F, 0F, false);
				}
				GlStateManager.popMatrix();
			}
		}
	}
}