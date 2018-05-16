package com.globbypotato.rockhounding_chemistry.machines.renders;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RendererCastingBench extends TileEntitySpecialRenderer<TileEntityCastingBench>{

	@Override
	public void renderTileEntityAt(TileEntityCastingBench bench, double x, double y, double z, float partialTicks, int destroyStage) {
		super.renderTileEntityAt(bench, x, y, z, partialTicks, destroyStage);

		if(bench != null){
			int metadata = bench.getBlockMetadata();
			World world = Minecraft.getMinecraft().theWorld;
			ItemStack renderStack = null;
			//slots
			if(bench.getOutput().getStackInSlot(bench.OUTPUT_SLOT) != null){
				renderStack = new ItemStack(bench.getOutput().getStackInSlot(bench.OUTPUT_SLOT).getItem(),1,bench.getOutput().getStackInSlot(bench.OUTPUT_SLOT).getItemDamage()) ;
			}else{
				if(bench.getInput().getStackInSlot(bench.INPUT_SLOT) != null){
					renderStack = new ItemStack(bench.getInput().getStackInSlot(bench.INPUT_SLOT).getItem(),1,bench.getInput().getStackInSlot(bench.INPUT_SLOT).getItemDamage());				
				}
			}
			if(renderStack != null){
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
					Minecraft.getMinecraft().getRenderManager().doRenderEntity(inputEntity, 0, 0, 0, 0F, 0F, false);
				}
				GlStateManager.popMatrix();
			}
			//piston
			float progress = (float) ((bench.getprogress() * 0.158) / bench.getMaxCookTime());
			ItemStack piston = new ItemStack(Blocks.ANVIL);				
			EntityItem pistonEntity = new EntityItem(world, 0, 0, 0, piston);
			pistonEntity.hoverStart = 0;
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(x, y, z);
				if(metadata == 2){
					GlStateManager.rotate(180F, 1, 0, 0);
					GlStateManager.translate(0.50, -1.34 + progress, -0.375);
				}else if(metadata == 3){
					GlStateManager.rotate(180F, 1, 0, 0);
					GlStateManager.translate(0.50, -1.34 + progress, -0.625);
				}else if(metadata == 4){
					GlStateManager.rotate(180F, 1, 0, 0);
					GlStateManager.rotate(90F, 0, 1, 0);
					GlStateManager.translate(0.50, -1.34 + progress, 0.375);
				}else if(metadata == 5){
					GlStateManager.rotate(180F, 1, 0, 0);
					GlStateManager.rotate(90F, 0, 1, 0);
					GlStateManager.translate(0.50, -1.34 + progress, 0.620);
				}
				GlStateManager.scale(1.8, 1.55, 1.8);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(pistonEntity, 0, 0, 0, 0F, 0F, false);
			}
			GlStateManager.popMatrix();

			// label
			ItemStack pattern = new ItemStack(ModItems.patternItems, 1, bench.getCurrentCast());				
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
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(patternEntity, 0, 0, 0, 0F, 0F, false);
			}
			GlStateManager.popMatrix();
		}
	}
}