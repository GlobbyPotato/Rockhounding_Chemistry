package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWaterPump;
import com.globbypotato.rockhounding_core.utils.RenderUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class RendererWaterPump extends TileEntitySpecialRenderer<TEWaterPump>{
	
	@Override
	public void render(TEWaterPump te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
            final FluidTank input = te.inputTank;
			int metadata = te.getFacing().ordinal();
			double i = 0.135;
			double w = 0.73;
            if (input != null && input.getFluid() != null && input.getFluidAmount() >= 10) {
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                if(metadata == 2){
	                	RenderUtils.renderFluid(input.getFluid(), te.getPos(), i, 0.255, 0.70, 		0.0d, 0.0d, 0.0d, 		w, (te.getTankAmount() * 0.74 / te.getTankCapacity()), 0.29);
	                }else if(metadata == 3){
	                	RenderUtils.renderFluid(input.getFluid(), te.getPos(), i, 0.255, 0.01, 		0.0d, 0.0d, 0.0d, 		w, (te.getTankAmount() * 0.74 / te.getTankCapacity()), 0.29);
	                }else if(metadata == 4){
	                	RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.70, 0.255, i, 		0.0d, 0.0d, 0.0d, 		0.29, (te.getTankAmount() * 0.74 / te.getTankCapacity()),w);
	                }else if(metadata == 5){
	                	RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.01, 0.255, i, 		0.0d, 0.0d, 0.0d, 		0.29, (te.getTankAmount() * 0.74 / te.getTankCapacity()), w);
	                }
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
		}
	}
}