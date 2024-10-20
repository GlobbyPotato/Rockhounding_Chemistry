package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COHeatExchangerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEHeatExchangerController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIHeatExchangerController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiheatexchanger.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guiheatexchangerjei.png");

	private final TEHeatExchangerController tile;

    public UIHeatExchangerController(InventoryPlayer playerInv, TEHeatExchangerController tile){
    	super(new COHeatExchangerController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEHeatExchangerController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

		//activation
	    if(GuiUtils.hoveringArea(80, 96, 16, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//direction
	    if(GuiUtils.hoveringArea(144, 31, 16, 16, mouseX, mouseY, x, y)){
	    	if(this.tile.getDirection()) {
	    		tooltip = GuiUtils.drawLabel("Right to Left setup", mouseX, mouseY);
	    	}else {
	    		tooltip = GuiUtils.drawLabel("Left to Right setup", mouseX, mouseY);
	    	}
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//monitor
	    if(GuiUtils.hoveringArea(119, 42, 17, 14, mouseX, mouseY, x, y)){
			String process = TextFormatting.GRAY + "Process: " +  TextFormatting.YELLOW + GuiUtils.translateMC(this.tile.processRate()) + " cu/t";
			String consume = TextFormatting.GRAY + "Energy: " +  TextFormatting.RED + this.tile.powerConsume() + " RF/t";

			String coolant = TextFormatting.GRAY + "Coolant: " +  TextFormatting.GOLD + "Not Available";
			String rateCoolant = TextFormatting.GRAY + "Consumption Rate: " + TextFormatting.AQUA + "Not Available";
			if(this.tile.hasFluidCistern()){
				if(this.tile.isValidCoolant()){
					coolant = TextFormatting.GRAY + "Coolant: " +  TextFormatting.GREEN + this.tile.getFluidCistern().inputTank.getFluid().getLocalizedName() + " (" + this.tile.coolantTemperature() + "K)";
					rateCoolant = TextFormatting.GRAY + "Consumption Rate: " +  TextFormatting.AQUA + this.tile.consumeRate() + "%";
				}
			}
			
			multistring = new String[]{process, consume, "", coolant, rateCoolant};
			tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(17, 74, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
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

		//cooling
        if(this.tile.isActive() && this.tile.getCurrentRecipe() != null){
       		this.drawTexturedModalRect(i + 72, j + 43, 176, 26, 32, 45);
        }

		//direction
        if(this.tile.getDirection()){
       		this.drawTexturedModalRect(i + 146, j + 33, 204, 11, 12, 12);
        }

    }

}