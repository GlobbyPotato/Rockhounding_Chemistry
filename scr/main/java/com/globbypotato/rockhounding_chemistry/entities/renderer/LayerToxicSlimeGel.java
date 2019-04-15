package com.globbypotato.rockhounding_chemistry.entities.renderer;

import com.globbypotato.rockhounding_chemistry.entities.EntityToxicSlime;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerToxicSlimeGel implements LayerRenderer<EntityToxicSlime>{
    private final RenderToxicSlime slimeRenderer;
    private final ModelBase slimeModel = new ModelSlime(0);

    public LayerToxicSlimeGel(RenderToxicSlime slimeRendererIn){
        this.slimeRenderer = slimeRendererIn;
    }

    public void doRenderLayer(EntityToxicSlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale){
        if (!entitylivingbaseIn.isInvisible()){
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    public boolean shouldCombineTextures(){
        return true;
    }
}