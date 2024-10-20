package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEEvaporationTank;
import com.globbypotato.rockhounding_core.utils.RenderUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class RendererEvaporationTank extends TileEntitySpecialRenderer<TEEvaporationTank>{
	
	@Override
	public void render(TEEvaporationTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
			World world = Minecraft.getMinecraft().world;
			if(te.getStage() < te.finalStage()){
				final FluidTank input = te.inputTank;
	            if (input != null && input.getFluid() != null && input.getFluidAmount() >= 10) {
	                GlStateManager.pushMatrix();
		                GlStateManager.enableBlend();
		                RenderUtils.translateAgainstPlayer(te.getPos(), false);
		                RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.03, 0.065, 0.03, 		0.0d, 0.0d, 0.0d, 		0.94, (te.getTankAmount() * 0.35 / te.getTankCapacity()), 0.94);
		                GlStateManager.disableBlend();
	                GlStateManager.popMatrix();
	            }
			}else{
				ItemStack renderStack = ItemStack.EMPTY;
				if(!te.getOutput().getStackInSlot(TileEntityInv.OUTPUT_SLOT).isEmpty()){
					renderStack = new ItemStack(te.outputSlot().getItem(), 1, te.outputSlot().getItemDamage()) ;
					if(!renderStack.isEmpty()){
						EntityItem saltEntity = new EntityItem(world, 0, 0, 0, renderStack);
						saltEntity.hoverStart = 0;
						GlStateManager.pushMatrix();
						{
							GlStateManager.translate(x, y, z);
							GlStateManager.rotate(0F, 0, 1, 0);
							GlStateManager.translate(0.50, 0.063, 0.50);
							GlStateManager.scale(3.76, 0.10, 3.76);
							Minecraft.getMinecraft().getRenderManager().renderEntity(saltEntity, 0, 0, 0, 0F, 0F, false);
						}
						GlStateManager.popMatrix();				
					}
				}
			}
		}
	}
}