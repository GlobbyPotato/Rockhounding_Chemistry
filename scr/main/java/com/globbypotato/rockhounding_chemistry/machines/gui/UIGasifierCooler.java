package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasifierCooler;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierCooler;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIGasifierCooler extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guigasifiercooler.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guigasifierjei.png");

    private final TEGasifierCooler tile;

    public UIGasifierCooler(InventoryPlayer playerInv, TEGasifierCooler tile){
    	super( new COGasifierCooler(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEGasifierCooler.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

       //temperature
	    if(GuiUtils.hoveringArea(59, 35, 58, 68, mouseX, mouseY, x, y)){
           String currentTemp = TextFormatting.GRAY + "Temperature: " + TextFormatting.GOLD + this.tile.getTemperature() + "/" + this.tile.getTemperatureMax() + "K";
           multistring = new String[]{currentTemp};
           tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
    	   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
       }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(29, 44, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //casing upgrade
	   if(GuiUtils.hoveringArea(29, 84, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.refractory_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(137, 93, 14, 14, mouseX, mouseY, x, y)){
           String consume = TextFormatting.GRAY + "Heating Factor: " + TextFormatting.YELLOW + this.tile.powerConsume() + " ticks/K";
		   String tier = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x";
		   String upg = TextFormatting.GRAY + "Upgrade: " + TextFormatting.GOLD + this.tile.hasRefractory();
		   multistring = new String[]{consume, "", tier, upg};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
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

        //power bar
        if (this.tile.getTemperature() > 0){
            int k = GuiUtils.getScaledValue(38, this.tile.getTemperature(), this.tile.getTemperatureMax());
            this.drawTexturedModalRect(i + 96, j + 60 + (38 - k), 176, 0, 10, k);
        }

    }
}