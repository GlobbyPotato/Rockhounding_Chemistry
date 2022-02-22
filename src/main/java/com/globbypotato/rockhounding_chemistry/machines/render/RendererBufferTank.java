package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEBufferTank;

import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class RendererBufferTank extends TileEntitySpecialRenderer<TEBufferTank>{

	@Override
	public void render(TEBufferTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
            final FluidTank input = te.inputTank;
            if (input != null && input.getFluid() != null && input.getFluidAmount() >= 10) {
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.26, 0.47, 0.26, 		0.0d, 0.0d, 0.0d, 		0.48, (te.getTankAmount() * 0.51 / te.getTankCapacity()), 0.48);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
		}
	}
}