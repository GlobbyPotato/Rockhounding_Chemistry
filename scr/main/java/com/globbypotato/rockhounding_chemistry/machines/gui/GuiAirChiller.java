package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAirChiller extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityAirChiller airChiller;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guiairchiller.png");
	private FluidTank inputTank;

	public GuiAirChiller(InventoryPlayer playerInv, TileEntityAirChiller tile){
		super(tile,new ContainerAirChiller(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.airChiller = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.inputTank = this.airChiller.inputTank;
		this.containerName = "container.air_chiller";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//input tank
		if(mouseX>= 76+x && mouseX <= 98+x && mouseY >= 17+y && mouseY <= 76+y){
			drawTankInfo(this.inputTank, mouseX, mouseY);
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//input fluid
		if(this.inputTank.getFluid() != null){
			renderFluidBar(this.inputTank.getFluid(), this.inputTank.getCapacity(), i + 76, j + 17, 23, 60);
		}
	}

}