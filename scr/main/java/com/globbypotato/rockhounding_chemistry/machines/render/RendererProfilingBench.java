package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEProfilingBench;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererProfilingBench extends TileEntitySpecialRenderer<TEProfilingBench>{

	@Override
	public void render(TEProfilingBench te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		if(te != null){
			int metadata = te.getFacing().ordinal();
			World world = Minecraft.getMinecraft().world;
			ItemStack renderStack = ItemStack.EMPTY;
			float progress = (float) ((te.getCooktime() * 0.22) / te.getCooktimeMax());

			//slots
			if(progress < 0.16F){
				if(!te.getOutput().getStackInSlot(TileEntityInv.OUTPUT_SLOT).isEmpty()){
					renderStack = new ItemStack(te.outputSlot().getItem(), 1, te.outputSlot().getItemDamage()) ;
				}else{
					if(!te.getInput().getStackInSlot(TileEntityInv.INPUT_SLOT).isEmpty()){
						renderStack = new ItemStack(te.inputSlot().getItem(), 1, te.inputSlot().getItemDamage());				
					}
				}
				if(!renderStack.isEmpty()){
					float hDev = 0F;
					float lDev = 0F;
					if(renderStack.getItem() instanceof ItemBlock){
						hDev = 0.52F;
						lDev = 0.07F;
					}else{
						hDev = 0.45F;
						lDev = 0F;
					}
					EntityItem inputEntity = new EntityItem(world, 0, 0, 0, renderStack);
					inputEntity.hoverStart = 0;
					GlStateManager.pushMatrix();
					{
						GlStateManager.translate(x, y, z);
						GlStateManager.rotate(270F, 1, 0, 0);
						if(metadata == 2){
							GlStateManager.translate(0.50, -0.66 + lDev, hDev);
						}else if(metadata == 3){
							GlStateManager.rotate(180F, 0, 0, 1);
							GlStateManager.translate(-0.50, 0.34 + lDev, hDev);
						}else if(metadata == 4){
							GlStateManager.rotate(90F, 0, 0, 1);
							GlStateManager.translate(-0.50, -0.66 + lDev, hDev);
						}else if(metadata == 5){
							GlStateManager.rotate(270F, 0, 0, 1);
							GlStateManager.translate(0.50, 0.34 + lDev, hDev);
						}
						GlStateManager.scale(0.6, 0.6, 0.6);
						Minecraft.getMinecraft().getRenderManager().renderEntity(inputEntity, 0, 0, 0, 0F, 0F, false);
					}
					GlStateManager.popMatrix();
				}
			}

			//piston
			EntityItem pistonEntity = new EntityItem(world, 0, 0, 0, BaseRecipes.profiler_piston.copy());
			pistonEntity.hoverStart = 0;
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(x, y, z);
				if(metadata == 2){
					GlStateManager.rotate(0F, 1, 0, 0);
					GlStateManager.translate(0.50, 0.38 - progress, 0.375);
				}else if(metadata == 3){
					GlStateManager.rotate(0F, 1, 0, 0);
					GlStateManager.translate(0.50, 0.38 - progress, 0.625);
				}else if(metadata == 4){
					GlStateManager.rotate(0F, 1, 0, 0);
					GlStateManager.translate(0.375, 0.38 - progress, 0.50);
				}else if(metadata == 5){
					GlStateManager.rotate(0F, 1, 0, 0);
					GlStateManager.translate(0.625, 0.38 - progress, 0.50);
				}
				GlStateManager.scale(1.50, 1.30, 1.50);
				Minecraft.getMinecraft().getRenderManager().renderEntity(pistonEntity, 0, 0, 0, 0F, 0F, false);
			}
			GlStateManager.popMatrix();

			// label
			ItemStack pattern = BaseRecipes.patterns(1, EnumCasting.values()[te.getCurrentCast()]);				
			EntityItem patternEntity = new EntityItem(world, 0, 0, 0, pattern);
			patternEntity.hoverStart = 0;
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(x, y, z);
				GlStateManager.rotate(270F, 1, 0, 0);
				if(metadata == 2){
					GlStateManager.translate(0.50, -1.07, 0.395);
				}else if(metadata == 3){
					GlStateManager.rotate(180F, 0, 0, 1);
					GlStateManager.translate(-0.50, -0.07, 0.395);
				}else if(metadata == 4){
					GlStateManager.rotate(90F, 0, 0, 1);
					GlStateManager.translate(-0.50, -1.07, 0.395);
				}else if(metadata == 5){
					GlStateManager.rotate(270F, 0, 0, 1);
					GlStateManager.translate(0.50, -0.07, 0.395);
				}
				GlStateManager.scale(0.6, 0.6, 1.0);
				Minecraft.getMinecraft().getRenderManager().renderEntity(patternEntity, 0, 0, 0, 0F, 0F, false);
			}
			GlStateManager.popMatrix();
		}
	}
}