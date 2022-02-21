package com.globbypotato.rockhounding_chemistry.entities.renderer;

import com.globbypotato.rockhounding_chemistry.entities.EntityToxicSlime;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderToxicSlime extends RenderLiving<EntityToxicSlime>{
    private static final ResourceLocation TOXIC_SLIME_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/toxic_slime/toxic_slime.png");

    public RenderToxicSlime(RenderManager p_i47193_1_){
        super(p_i47193_1_, new ModelSlime(16), 0.25F);
        this.addLayer(new LayerToxicSlimeGel(this));
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityToxicSlime entity, double x, double y, double z, float entityYaw, float partialTicks){
        this.shadowSize = 0.25F * (float)entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    public void preRenderCallback(EntityToxicSlime entitylivingbaseIn, float partialTickTime){
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSlimeSize();
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EntityToxicSlime entity){
        return TOXIC_SLIME_TEXTURES;
    }
}