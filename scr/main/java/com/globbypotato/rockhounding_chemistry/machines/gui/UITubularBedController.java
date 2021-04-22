package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COTubularBedController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TETubularBedController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;
import com.google.common.base.Strings;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UITubularBedController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guitubularbedcontrollergui.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guitubularbedcontrollerjei.png");

    private final TETubularBedController tile;

    public UITubularBedController(InventoryPlayer playerInv, TETubularBedController tile){
    	super(new COTubularBedController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TETubularBedController.getName();
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

	   //prev
	   if(GuiUtils.hoveringArea(25, 23, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //prev
	   if(GuiUtils.hoveringArea(43, 23, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
	   
	   //speed upgrade
	   if(GuiUtils.hoveringArea(133, 61, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(26, 83, 14, 14, mouseX, mouseY, x, y)){
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   String rf = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.powerConsume() + " RF/t";
		   String ct = TextFormatting.GRAY + "Catalysts: " + TextFormatting.GOLD + this.tile.catSize() + "x";
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 

		   multistring = new String[]{sf, rf, "", ct, tk};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = "No recipe selected";
		if(this.tile.isValidPreset()){
			if(Strings.isNullOrEmpty(this.tile.getRecipeList(this.tile.getRecipeIndex()).getRecipeName())){
				recipeLabel = this.tile.getRecipeList(this.tile.getRecipeIndex()).getOutput().getLocalizedName();
			}else{
				recipeLabel = this.tile.getRecipeList(this.tile.getRecipeIndex()).getRecipeName();
			}
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
            int p = GuiUtils.getScaledValue(46, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 58, j + 45, 176, 40, 60, p);
        }
    }

}