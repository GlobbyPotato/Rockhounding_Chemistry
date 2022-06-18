package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIMineralSizerController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralsizercontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guimineralsizerjei.png");

    private final TEMineralSizerController tile;

    public UIMineralSizerController(InventoryPlayer playerInv, TEMineralSizerController tile){
    	super(new COMineralSizerController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEMineralSizerController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   String commDescr = "";
	   String commValue = TextFormatting.GRAY + "Comminution level: " + TextFormatting.GREEN + this.tile.getComminution();

	   //slider
	   if(GuiUtils.hoveringArea(79, 20, 18, 18, mouseX, mouseY, x, y)){
		   commDescr = "Current comminution level";
		   multistring = new String[]{commDescr, commValue};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   // lo
	   if(GuiUtils.hoveringArea(61, 20, 18, 18, mouseX, mouseY, x, y)){
		   commDescr = "Decrease comminution level";
		   multistring = new String[]{commDescr, commValue};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   // hi
	   if(GuiUtils.hoveringArea(97, 20, 18, 18, mouseX, mouseY, x, y)){
		   commDescr = "Increase comminution level";
		   multistring = new String[]{commDescr, commValue};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //exposed
       if(this.tile.expectResult()){
    	  tooltip = new ArrayList<String>();
    	   tooltip.add(TextFormatting.GRAY + "Exposed Gangues:");
    	   for(int k = 0; k < this.tile.recipeOutput().size(); k++){
    		   if(this.tile.recipeComminution().get(k) == this.tile.getComminution()){
    			   tooltip.add(this.tile.recipeOutput().get(k).getDisplayName());
    		   }
    	   }
    	   if(GuiUtils.hoveringArea(39, 21, 20, 17, mouseX, mouseY, x, y)){
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
       }

	   //monitor
	   if(GuiUtils.hoveringArea(130, 96, 14, 14, mouseX, mouseY, x, y)){
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   multistring = new String[]{sf, tk};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(146, 26, 16, 16, mouseX, mouseY, x, y)){
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

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(31, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 138, j + 44, 176, 40, k, 31);
        }

        //slider
    	int offset = this.tile.getComminution() * 2;
        this.drawTexturedModalRect(i + 44, j + 38 + offset, 0, 200, 88, 16);

		//show result
        if(this.tile.expectResult()){
            this.drawTexturedModalRect(i + 39,  j + 21, 176, 88, 20, 17);
        }

    }

}