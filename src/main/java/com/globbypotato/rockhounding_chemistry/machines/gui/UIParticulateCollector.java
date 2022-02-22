package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COParticulateCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEParticulateCollector;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIParticulateCollector extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiparticulatecollector.png");

    private final TEParticulateCollector tile;

    public UIParticulateCollector(InventoryPlayer playerInv, TEParticulateCollector tile){
    	super(new COParticulateCollector(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEParticulateCollector.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //preview
	   if(this.tile.getTemplate().getStackInSlot(TEParticulateCollector.PRIMARY_PREVIEW).isEmpty()){
		   if(GuiUtils.hoveringArea(105, 44, 18, 18, mouseX, mouseY, x, y)){
			   List<String> tooltip = GuiUtils.drawLabel("Preview", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }
	   if(this.tile.getTemplate().getStackInSlot(TEParticulateCollector.SECONDARY_PREVIEW).isEmpty()){
		   if(GuiUtils.hoveringArea(105, 71, 18, 18, mouseX, mouseY, x, y)){
			   List<String> tooltip = GuiUtils.drawLabel("Preview", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //progress
	   if(GuiUtils.hoveringArea(51, 54, 53, 8, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.GRAY + "Primary Waste: " + TextFormatting.WHITE + this.tile.getPrimaryCount() + "/100 units";
			List<String> tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
	   if(GuiUtils.hoveringArea(51, 81, 53, 8, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.GRAY + "Secondary Waste: " + TextFormatting.WHITE + this.tile.getSecondaryCount() + "/100 units";
			List<String> tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
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

        if(this.tile.getPrimaryCount() > 0){
        	int prim = this.tile.getPrimaryCount();
        	if(this.tile.getPrimaryCount() > 100){prim = 100;}
            int k = GuiUtils.getScaledValue(52, prim, 100);
            this.drawTexturedModalRect(i + 51, j + 55, 176, 8, k, 6);
        }
        
        if(this.tile.getSecondaryCount() > 0){
        	int prim = this.tile.getSecondaryCount();
        	if(this.tile.getSecondaryCount() > 100){prim = 100;}
            int k = GuiUtils.getScaledValue(52, prim, 100);
            this.drawTexturedModalRect(i + 51, j + 82, 176, 0, k, 6);
        }

    }
}