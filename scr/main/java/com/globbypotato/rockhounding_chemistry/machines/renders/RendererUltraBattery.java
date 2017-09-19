package com.globbypotato.rockhounding_chemistry.machines.renders;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityUltraBattery;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererUltraBattery extends TileEntitySpecialRenderer<TileEntityUltraBattery>{

	@Override
	public void renderTileEntityAt(TileEntityUltraBattery te, double x, double y, double z, float partialTicks, int destroyStage) {
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

		TileEntityUltraBattery battery = (TileEntityUltraBattery)te;
		if(battery != null){
			World world = Minecraft.getMinecraft().theWorld;
			for(int side = 0; side < 6; side++){
				ItemStack plug = null;
				if(battery.sideStatus[6]){
					plug = BaseRecipes.plugIn;
				}else{ 
					if(!battery.sideStatus[6] && battery.sideStatus[side]){
						plug = BaseRecipes.plugOut;
					}
				}
				if(plug != null){
					EntityItem plugEntity = new EntityItem(world, 0, 0, 0, plug);
					plugEntity.hoverStart = 0;
					GlStateManager.pushMatrix();
					{
						GlStateManager.translate(x, y, z);
						if(side == 0){
							GlStateManager.rotate(90F, 1, 0, 0);
							GlStateManager.translate(0.5, 0.02, -0.01);
						}else if(side == 1){
							GlStateManager.rotate(90F, 1, 0, 0);
							GlStateManager.translate(0.5, 0.02, -0.99);
						}else if(side == 2){
							GlStateManager.rotate(0F, 0, 1, 0);
							GlStateManager.translate(0.50, 0.02, 0.01);
						}else if(side == 3){
							GlStateManager.rotate(0F, 0, 1, 0);
							GlStateManager.translate(0.50, 0.02, 0.99);
						}else if(side == 4){
							GlStateManager.rotate(270F, 0, 1, 0);
							GlStateManager.translate(0.50, 0.02, -0.01);
						}else if(side == 5){
							GlStateManager.rotate(270F, 0, 1, 0);
							GlStateManager.translate(0.50, 0.02, -0.99);
						}
						GlStateManager.scale(1.0, 1.0, 1.0);
						Minecraft.getMinecraft().getRenderManager().doRenderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
					}
					GlStateManager.popMatrix();
				}
			}
		}
	}
}