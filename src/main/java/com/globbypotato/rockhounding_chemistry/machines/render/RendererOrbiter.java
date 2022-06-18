package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEOrbiter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererOrbiter extends TileEntitySpecialRenderer<TEOrbiter>{

	@Override
	public void render(TEOrbiter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		if(te != null){
			int metadata = te.getFacing().ordinal();
			World world = Minecraft.getMinecraft().world;
			ItemStack renderStack = ItemStack.EMPTY;

			if(!te.probeSlot().isEmpty()){
				ItemStack slotSack = te.probeSlot().copy();
				renderStack = new ItemStack(slotSack.getItem(), 1, slotSack.getItemDamage()) ;
			}
			if(!renderStack.isEmpty()){
				EntityItem patternEntity = new EntityItem(world, 0, 0, 0, renderStack);
				patternEntity.hoverStart = 0;
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(x, y, z);
					GlStateManager.translate(0.50, 0.84, 0.50);
					if(metadata == 2 || metadata == 3){
						GlStateManager.rotate(0F, 0, 1, 0);
					}else if(metadata == 4 || metadata == 5){
						GlStateManager.rotate(90F, 0, 1, 0);
					}
					GlStateManager.scale(0.7, 0.7, 2.0);
					Minecraft.getMinecraft().getRenderManager().renderEntity(patternEntity, 0, 0, 0, 0F, 0F, false);
				}
				GlStateManager.popMatrix();
			}
		}
	}
}