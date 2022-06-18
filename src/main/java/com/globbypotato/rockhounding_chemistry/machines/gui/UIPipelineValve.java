package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelineValve;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPipelineValve extends GuiBase {

	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guipipelinevalve.png");

	private final TEPipelineValve tile;

	public UIPipelineValve(InventoryPlayer playerInv, TEPipelineValve tile){
		super(new COPipelineValve(playerInv, tile), ModUtils.HEIGHT);
		TEXTURE = TEXTURE_REF;
		this.tile = tile;
		this.containerName = "container.pipeline_valve";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		for(int side = 0; side < this.tile.sideFilter.length; side++){
			int offset = side * 18;
			String filterString = TextFormatting.BLUE + this.filter_label + ": " + TextFormatting.WHITE + this.ampoule_label;
			if(this.tile.sideFilter[side] != null){
				filterString = TextFormatting.GRAY + this.filter_label + ": " + TextFormatting.AQUA + this.tile.sideFilter[side].getLocalizedName();
			}
		    if(GuiUtils.hoveringArea(34 + offset, 63, 18, 18, mouseX, mouseY, x, y)){
				 List<String> tooltip = GuiUtils.drawLabel(filterString, mouseX, mouseY);
				 drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}

			String statusString = TextFormatting.RED + "Disabled";
			if(this.tile.tiltStatus[side]){
				statusString = TextFormatting.GOLD + "Unavailable";
			}else{
				if(this.tile.sideStatus[side]){
					statusString = TextFormatting.GREEN + "Enabled";
				}
			}
			String gaugestring = TextFormatting.GRAY + formalDir(EnumFacing.getFront(side).getName()) + " joint: " + statusString; 
		    if(GuiUtils.hoveringArea(34 + offset, 30, 18, 32, mouseX, mouseY, x, y)){
				List<String> tooltip = GuiUtils.drawLabel(gaugestring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
			
			String robinString = TextFormatting.RED + "Disabled";
			if(this.tile.hasRoundRobin()){
				robinString = TextFormatting.GREEN + "Enabled";
			}
			String robin = TextFormatting.GRAY + "Round Robin: " + robinString; 
		    if(GuiUtils.hoveringArea(11, 53, 18, 18, mouseX, mouseY, x, y)){
				List<String> tooltip = GuiUtils.drawLabel(robin, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}

		}		

	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		for(int side = 0; side < this.tile.sideStatus.length; side++){
			int offset = side * 18;
			if(!this.tile.tiltStatus[side]){
				if(this.tile.sideStatus[side]){
					this.drawTexturedModalRect(i + 36 + offset, j + 46, 176, 14, 14, 14); //on
				}
			}else{
				if(this.tile.sideStatus[side]){
					this.drawTexturedModalRect(i + 36 + offset, j + 46, 176, 0, 14, 14); //tilt
				}
			}
		}

		if(this.tile.hasRoundRobin()){
			this.drawTexturedModalRect(i + 13, j + 55, 176, 28, 14, 14); //rrobin
		}

		for(int side = 0; side < this.tile.sideFilter.length; side++){
			if(this.tile.sideFilter[side] != null){
				int offset = side * 18;
				GuiUtils.renderFluidBar(this.tile.sideFilter[side], 1000, 1000, i + 35 + offset, j + 64, 16, 16);
			}
		}		
	}

	private static String formalDir(String dir) {
		return dir.substring(0, 1).toUpperCase() + dir.substring(1);
	}

}