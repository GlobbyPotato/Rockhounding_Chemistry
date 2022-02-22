package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEAirCompressor;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIAirCompressor extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiaircompressor.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guiaircompressorjei.png");

    private final TEAirCompressor tile;

    public UIAirCompressor(InventoryPlayer playerInv, TEAirCompressor tile){
    	super(new COAirCompressor(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEAirCompressor.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel
	   if(GuiUtils.hoveringArea(28, 107, 51, 6, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawStorage(TextFormatting.GOLD, "ticks", TextFormatting.YELLOW, 0, this.tile.getPower(), this.tile.getPowerMax(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//activation
        if(this.tile.isActive()){
        	if(this.tile.isPowered()){
        		this.drawTexturedModalRect(i + 81, j + 97, 190, 10, 14, 14);
        	}else{
        		this.drawTexturedModalRect(i + 81, j + 97, 176, 10, 14, 14);
        	}
        }

        //power bar
        if (this.tile.getPower() > 0){
            int k = GuiUtils.getScaledValue(50, this.tile.getPower(), this.tile.getPowerMax());
            this.drawTexturedModalRect(i + 25, j + 108, 176, 0, k, 4);
        }

        //inductor
        if(this.tile.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 8, j + 96, 211, 10, 16, 16);
        }

        //air bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(62, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 71, j + 57, 176, 27, k, 12);
        }

    }

}