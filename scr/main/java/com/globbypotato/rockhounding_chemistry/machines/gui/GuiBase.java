package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.machines.container.ContainerBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineBase;
import com.globbypotato.rockhounding_core.utils.RenderUtils;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public abstract class GuiBase extends GuiContainer{

    public static ResourceLocation TEXTURE = new ResourceLocation("");
    public String containerName = "";

    public GuiBase(TileEntityMachineBase tile, ContainerBase container) {
        super(container);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    this.mc.getTextureManager().bindTexture(TEXTURE);
	    this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String device = Translator.translateToLocal(containerName);
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 5, 4210752);
	}

	protected void drawPowerInfo(String unit, int power, int powerMax, int mouseX, int mouseY) {
	   String text = power + "/" + powerMax + " " + unit;
	   List<String> tooltip = Arrays.asList(text);
	   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected void drawButtonLabel(String caption, int mouseX, int mouseY) {
		List<String> tooltip = Arrays.asList(caption);
		drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected void drawMultiLabel(String[] multistring, int mouseX, int mouseY) {
		 List<String> tooltip = Arrays.asList(multistring);
		 drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected int getBarScaled(int pixels, int count, int max) {
        return count > 0 && max > 0 ? count * pixels / max : 0;
	}

	protected void drawTankInfo(FluidTank tank, int mouseX, int mouseY) {
			int fluidAmount = 0;
			if(tank.getFluid() != null){
				fluidAmount = tank.getFluidAmount();
			}
			String quantity = fluidAmount + "/" + tank.getCapacity() + " mb ";
			String liquid = "Empty";
			if(tank.getFluid() != null){
				liquid = tank.getFluid().getLocalizedName();
			}
			List<String> tooltip = Arrays.asList(liquid, quantity);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected void renderFluidBar(FluidStack fluid, int capacity, int i, int j, int w, int h) {
		if(fluid.amount > 5){
			RenderUtils.bindBlockTexture();
			RenderUtils.renderGuiTank(fluid,capacity, fluid.amount, i, j, zLevel, w, h);
		}
	}

}