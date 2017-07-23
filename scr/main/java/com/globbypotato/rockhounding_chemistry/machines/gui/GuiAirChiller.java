package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_core.utils.RenderUtils;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAirChiller extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityAirChiller airChiller;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guiairchiller.png");

	public GuiAirChiller(InventoryPlayer playerInv, TileEntityAirChiller tile){
		super(tile,new ContainerAirChiller(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.airChiller = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//input tank
		if(mouseX>= 76+x && mouseX <= 98+x && mouseY >= 17+y && mouseY <= 76+y){
			int fluidAmount = 0;
			if(airChiller.inputTank.getFluid() != null){
				fluidAmount = this.airChiller.inputTank.getFluidAmount();
			}
			String text = fluidAmount + "/" + this.airChiller.inputTank.getCapacity() + " mb ";
			String liquid = "";
			if(airChiller.inputTank.getFluid() != null) liquid = airChiller.inputTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text, liquid);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.airChiller");
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 4, 4210752);
	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//input fluid
		if(airChiller.inputTank.getFluid() != null){
			FluidStack temp = airChiller.inputTank.getFluid();
			int capacity = airChiller.inputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 76, j + 17, zLevel, 23, 60);
			}
		}
	}

}