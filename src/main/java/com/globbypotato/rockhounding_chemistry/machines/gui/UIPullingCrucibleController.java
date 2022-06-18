package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPullingCrucibleController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guipullingcruciblebase.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guipullingcruciblejei.png");

    private final TEPullingCrucibleController tile;

    public UIPullingCrucibleController(InventoryPlayer playerInv, TEPullingCrucibleController tile){
    	super(new COPullingCrucibleController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEPullingCrucibleController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   //monitor
	   if(GuiUtils.hoveringArea(123, 75, 14, 14, mouseX, mouseY, x, y)){
		   String rf = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.powerConsume() + " RF/t"; 
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   multistring = new String[]{sf, tk, rf};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //charging
	   if(GuiUtils.hoveringArea(23, 97, 32, 7, mouseX, mouseY, x, y)){
		   String rf = TextFormatting.GRAY + "Melting Point: " + TextFormatting.RED + this.tile.getMelting() + "/" + this.tile.getMeltingMax() + " RF"; 
		   tooltip = GuiUtils.drawLabel(rf, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(152, 74, 16, 16, mouseX, mouseY, x, y)){
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
            int p = GuiUtils.getScaledValue(30, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 83, j + 33 + (30 - p), 176, 41, 10, p);
        }

        //melt bars
        if (this.tile.getMelting() > 0){
            int p = GuiUtils.getScaledValue(30, this.tile.getMelting(), this.tile.getMeltingMax());
            this.drawTexturedModalRect(i + 24, j + 98, 176, 92, p, 5);
        }

        if(this.tile.getMelting() >= this.tile.getMeltingLev()){
            this.drawTexturedModalRect(i + 80, j + 64, 176, 75, 16, 16);
        }
    }
}