package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.machines.container.ContainerBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineBase;
import com.globbypotato.rockhounding_core.utils.RenderUtils;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
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

	protected void drawPowerInfo(String unit, int cooktime, int power, int powerMax, int mouseX, int mouseY) {
	   String counter = TextFormatting.DARK_GRAY + "Storage: " + TextFormatting.GOLD + power + "/" + powerMax + " " + unit;
	   String cooking = TextFormatting.DARK_GRAY + "Process: " + TextFormatting.YELLOW + cooktime + " ticks";
	   List<String> tooltip = Arrays.asList(counter, cooking);
	   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected void drawEnergyInfo(String unit, int power, int powerMax, int mouseX, int mouseY) {
	   String text = TextFormatting.DARK_GRAY + "Storage: " + TextFormatting.RED +  power + "/" + powerMax + " " + unit;
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
			String liquid = TextFormatting.GRAY + "Empty";
			if(tank.getFluid() != null){
				liquid = TextFormatting.BOLD + tank.getFluid().getLocalizedName();
			}
			List<String> tooltip = Arrays.asList(liquid, quantity);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected void drawTankInfoWithConsume(FluidTank tank, int consumes, int mouseX, int mouseY) {
		int fluidAmount = 0;
		if(tank.getFluid() != null){fluidAmount = tank.getFluidAmount();}
		String quantity = fluidAmount + "/" + tank.getCapacity() + " mb ";
		String liquid = TextFormatting.GRAY + "Empty";
		String cons = "";
		if(tank.getFluid() != null){liquid = TextFormatting.BOLD + tank.getFluid().getLocalizedName();}
		if(consumes > 0){
			cons = TextFormatting.YELLOW + "Consumes " + consumes + "mB";
		}
		List<String> tooltip = Arrays.asList(liquid, quantity, cons);
		drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	}

	protected void renderFluidBar(FluidStack fluid, int capacity, int i, int j, int w, int h) {
		if(fluid.amount > 5){
			RenderUtils.bindBlockTexture();
			RenderUtils.renderGuiTank(fluid,capacity, fluid.amount, i, j, zLevel, w, h);
		}
	}

    protected String[] handleFuelStatus(boolean fuelGated, boolean hasFuelBlend, boolean canInduct, boolean isPermanentInduction) {		
		String fuelCaption = TextFormatting.GRAY + "Fuel:";
		String inductionCaption = TextFormatting.GRAY + "Induction:";

		String fuelStatus = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.GOLD + "Free";
		if(fuelGated){
			fuelStatus = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.GOLD + "Gated";
		}

		String fuelType = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.YELLOW + "Common";
		if(hasFuelBlend){
			fuelType = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.YELLOW + "Blend";
		}

		String indString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.RED + "OFF";
		if(canInduct){
			indString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.RED + "ON";
		}

		String permaString = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.DARK_RED + "Moveable";
		if(isPermanentInduction){
			permaString = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.DARK_RED + "Permanent";
		}

		return new String[]{fuelCaption, fuelType, fuelStatus, "", inductionCaption, permaString, indString};
	}

}