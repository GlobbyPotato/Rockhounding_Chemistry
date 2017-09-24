package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelineValve;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPipelineValve extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityPipelineValve pipelineValve;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 158;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guipipelinevalve.png");
	private FluidTank inputTank;

	public GuiPipelineValve(InventoryPlayer playerInv, TileEntityPipelineValve tile){
		super(tile,new ContainerPipelineValve(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.pipelineValve = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.pipeline_valve";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		for(int side = 0; side < this.pipelineValve.sideFilter.length; side++){
			int offset = side * 18;
			String filterString = TextFormatting.GRAY + "Filter: " + TextFormatting.WHITE + "None";
			if(this.pipelineValve.sideFilter[side] != null){
				filterString = TextFormatting.GRAY + "Filter: " + TextFormatting.AQUA + this.pipelineValve.sideFilter[side].getLocalizedName();
			}
			if(mouseX>= 33 + x + offset && mouseX <= 50 + x + offset && mouseY >= 53+y && mouseY <= 70+y){
				drawButtonLabel(filterString, mouseX, mouseY);
			}

			String statusString = TextFormatting.RED + "Disabled";
			if(this.pipelineValve.tiltStatus[side]){
				statusString = TextFormatting.GOLD + "Unavailable";
			}else{
				if(this.pipelineValve.sideStatus[side]){
					statusString = TextFormatting.GREEN + "Enabled";
				}
			}
			String gaugestring = TextFormatting.GRAY + formalDir(EnumFacing.getFront(side).getName()) + " joint: " + statusString; 
			if(mouseX>= 33 + x + offset && mouseX <= 50 + x + offset && mouseY >= 34+y && mouseY <= 51+y){
				drawButtonLabel(gaugestring, mouseX, mouseY);
			}
		}		

	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		for(int side = 0; side < this.pipelineValve.sideStatus.length; side++){
			int offset = side * 18;
			if(this.pipelineValve.tiltStatus[side]){
				this.drawTexturedModalRect(i + 34 + offset, j + 35, 176, 16, 16, 16); //tilt
			}else{
				if(this.pipelineValve.sideStatus[side]){
					this.drawTexturedModalRect(i + 34 + offset, j + 35, 176, 0, 16, 16); //on
				}
			}
		}

		for(int side = 0; side < this.pipelineValve.sideFilter.length; side++){
			if(this.pipelineValve.sideFilter[side] != null){
				int offset = side * 18;
				renderFluidBar(this.pipelineValve.sideFilter[side], 1000, i + 34 + offset, j + 54, 16, 16);
			}
		}		
	}

	private static String formalDir(String dir) {
		return dir.substring(0, 1).toUpperCase() + dir.substring(1);
	}

}