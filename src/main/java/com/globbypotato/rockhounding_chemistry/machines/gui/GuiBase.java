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
	String voltage_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.voltage");
	String generator_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.generator");
	String fuel_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.fuel");
	String redstone_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.redstone");
	String buffer_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.buffer");
	String burnable_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.burnable");
	String reddust_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.reddust");
	String thres_lo_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.thres_lo");
	String thres_hi_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.thres_hi");
	String signal_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.signal");
	String filter_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.filter");
	String ampoule_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.ampoule");
	String method_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.method");
	String no_recipe_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.no_recipe");
	String next_recipe_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.next_recipe");
	String prev_recipe_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.prev_recipe");
	String ingr_ready_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.ingr_ready");
	String ingr_missing_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.ingr_missing");
	String next_page_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.next_page");
	String prev_page_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.prev_page");
	String build_label = Translator.translateToLocal("label." + Reference.MODID + ":descr.build");

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