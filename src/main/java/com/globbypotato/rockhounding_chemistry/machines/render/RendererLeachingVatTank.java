package com.globbypotato.rockhounding_chemistry.machines.render;

import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.darkhax.bookshelf.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class RendererLeachingVatTank extends TileEntitySpecialRenderer<TELeachingVatTank>{
	
	@Override
	public void render(TELeachingVatTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		if(te != null){
			World world = Minecraft.getMinecraft().world;

			ItemStack plug = te.getInput().getStackInSlot(TileEntityInv.INPUT_SLOT).copy();
			ItemStack plugItem = BaseRecipes.slurry_agitator.copy();
			EntityItem plugEntity = new EntityItem(world, 0, 0, 0, plugItem);
			plugEntity.hoverStart = 0;
			if(!plug.isEmpty()){
				GlStateManager.pushMatrix();
					GlStateManager.translate(x, y, z);
					GlStateManager.translate(0.5, -0.2, 0.5);
					GlStateManager.rotate(te.getRotation(), 0, 1, 0);	
					GlStateManager.scale(1.0, 1.525, 1.0);
					Minecraft.getMinecraft().getRenderManager().renderEntity(plugEntity, 0, 0, 0, 0F, 0F, false);
				GlStateManager.popMatrix();
			}

			final FluidTank input = te.inputTank;
            if (input != null && input.getFluid() != null && input.getFluidAmount() >= 10) {
                GlStateManager.pushMatrix();
	                GlStateManager.enableBlend();
	                RenderUtils.translateAgainstPlayer(te.getPos(), false);
	                RenderUtils.renderFluid(input.getFluid(), te.getPos(), 0.04, 0.20, 0.03, 		0.0d, 0.0d, 0.0d, 		0.92, (input.getFluidAmount() * 0.74 / input.getCapacity()), 0.94);
	                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

		}
	}
}