package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;
import com.globbypotato.rockhounding_core.utils.RenderUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class RendererWashingTank extends TileEntitySpecialRenderer<TEWashingTank>{
	
	@Override
	public void render(TEWashingTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
            final FluidTank input = te.inputTank;
            if (input != null && input.getFluid() != null && input.getFluidAmount() >= 10) {
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.125, 0.065, 0.125, 		0.0d, 0.0d, 0.0d, 		0.75, (te.getTankAmount() * 0.90 / te.getTankCapacity()), 0.75);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
		}
	}
}