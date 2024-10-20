package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEShredderTable;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.RenderUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;

public class RendererShredderTable extends TileEntitySpecialRenderer<TEShredderTable>{
	
	@Override
	public void render(TEShredderTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
			final FluidStack water = CoreBasics.waterStack(1000);
            if (water != null && te.isActive()) {
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(water, te.getPos(), 0.0, 0.97, 0.0, 		0.0d, 0.0d, 0.0d, 		1, 0.00, 1);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
		}
	}
}