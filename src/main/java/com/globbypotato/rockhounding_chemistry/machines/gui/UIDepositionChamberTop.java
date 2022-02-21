package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.CODepositionChamberTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEDepositionChamberTop;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIDepositionChamberTop extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guidepositioncontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guidepositionjei.png");

    private final TEDepositionChamberTop tile;

    public UIDepositionChamberTop(InventoryPlayer playerInv, TEDepositionChamberTop tile){
    	super(new CODepositionChamberTop(playerInv,tile), ModUtils.HEIGHT);
        this.tile = tile;
        GuiBase.TEXTURE = TEXTURE_REF;
		this.containerName = "container." + TEDepositionChamberTop.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
       
	   //preset
	   if(GuiUtils.hoveringArea(7, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Server", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
       
	   //prev
	   if(GuiUtils.hoveringArea(25, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //next
	   if(GuiUtils.hoveringArea(43, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
 
	   //recycle
	   if(GuiUtils.hoveringArea(28, 70, 12, 22, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Ingredients recycling", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

		//temperature
		if(GuiUtils.hoveringArea(7, 45, 12, 67, mouseX, mouseY, x, y)){
			String req = TextFormatting.DARK_GRAY + "Required: " + TextFormatting.WHITE + TextFormatting.WHITE + "N/A";
			if(this.tile.isValidPreset()){
				req = TextFormatting.DARK_GRAY + "Required: " + TextFormatting.WHITE + this.tile.getRecipeList(this.tile.getRecipeIndex()).getTemperature() + "K";
			}
			String text = TextFormatting.GOLD + "Temperature: " + TextFormatting.WHITE + (30 + this.tile.getTemperature()) + "K";
			String info = TextFormatting.DARK_GRAY + "Charge: " + TextFormatting.WHITE + this.tile.tempYeld() +" K/tick";
			multistring = new String[]{text, req, info};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		//preassure
		if(GuiUtils.hoveringArea(157, 45, 12, 67, mouseX, mouseY, x, y)){
			String req = TextFormatting.DARK_GRAY + "Required: " + TextFormatting.WHITE + TextFormatting.WHITE + "N/A";
			String text = TextFormatting.BLUE + "Pressure: " + TextFormatting.WHITE + this.tile.getPressure() + " uPa";
			if(this.tile.isValidPreset()){
				req = TextFormatting.DARK_GRAY + "Required: " + TextFormatting.WHITE + this.tile.getRecipeList(this.tile.getRecipeIndex()).getPressure() + " uPa";
			}
			String info = TextFormatting.DARK_GRAY + "Charge: " + TextFormatting.WHITE + this.tile.pressYeld() +" uPa/tick";
			multistring = new String[]{text, req, info};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		   //monitor
		   if(GuiUtils.hoveringArea(81, 39, 14, 14, mouseX, mouseY, x, y)){
			   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
			   String rf = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.takenRF() + " RF/t"; 
			   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
			   String dr = TextFormatting.GRAY + "Drain: " + TextFormatting.DARK_RED + this.tile.takenRF() + " RF/charge";
			   multistring = new String[]{sf, tk, rf, dr};
			   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = "No recipe selected";
		if(this.tile.isValidPreset()){
			recipeLabel = this.tile.getRecipeList(this.tile.getRecipeIndex()).getOutput().getDisplayName();
		}
		this.fontRenderer.drawString(recipeLabel, 62, 25, 4210752);
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

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(34, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 58, j + 58, 176, 76, 60, 34 - k);
        }else{
            this.drawTexturedModalRect(i + 58, j + 58, 176, 76, 60, 34);
        }
        
        //temperature bar
        if (this.tile.getTemperature() > 0){
            int k = GuiUtils.getScaledValue(65, this.tile.getTemperature(), this.tile.getTemperatureMax());
            this.drawTexturedModalRect(i + 8, j + 46 + (65 - k), 176, 113, 10, k);
        }
		//pressure bar
		if (this.tile.getPressure() > 0){
			int k = GuiUtils.getScaledValue(65, this.tile.getPressure(), this.tile.getPressureMax());
			this.drawTexturedModalRect(i + 158, j + 46 + (65 - k), 186, 113, 10, k);
		}


		//server icon
        if(!this.tile.isServedClosed(this.tile.hasServer(), this.tile.getServer())){
            this.drawTexturedModalRect(i + 9, j + 22, 176, 25, 14, 14);
        }

    }


}