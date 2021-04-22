package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerBase;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase extends GuiContainer{

	String activation_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.activation");
	String void_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.void");
	String speed_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.speed");
	String refractory_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.refractory");
	String insulation_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.insulation");
	String casing_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.casing");

    public static ResourceLocation TEXTURE = new ResourceLocation("");
	public static int WIDTH = 176;
    public String containerName = "";
	public GuiBase(ContainerBase container, int height) {
        super(container);
		this.xSize = WIDTH;
		this.ySize = height;
    }

    @Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String device = Translator.translateToLocal(this.containerName);
		this.fontRenderer.drawString(device, 7, 7, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    this.mc.getTextureManager().bindTexture(TEXTURE);
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}