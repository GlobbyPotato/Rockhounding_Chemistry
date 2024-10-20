package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEContainmentTank;
import com.globbypotato.rockhounding_core.utils.RenderUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class RendererContainmentTank extends TileEntitySpecialRenderer<TEContainmentTank>{
	
	@Override
	public void render(TEContainmentTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
            final FluidTank input = te.inputTank;
            if (input != null && input.getFluid() != null && input.getFluidAmount() >= 10) {
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.135, 0.075, 0.135, 		0.0d, 0.0d, 0.0d, 		0.73, (te.getTankAmount() * 0.86 / te.getTankCapacity()), 0.73);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
		}
	}
}