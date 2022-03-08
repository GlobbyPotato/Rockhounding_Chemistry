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

	   String[] multistring;
	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(152, 91, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel
	   if(GuiUtils.hoveringArea(28, 51, 12, 61, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawStorage(TextFormatting.GOLD, "ticks", TextFormatting.YELLOW, 0, this.tile.getPower(), this.tile.getPowerMax(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reservoir lava
	   if(GuiUtils.hoveringArea(7, 49, 12, 35, mouseX, mouseY, x, y)){
		   String content = TextFormatting.GRAY + "Internal Buffer: " + TextFormatting.GOLD + TextFormatting.BOLD + "Lava";
		   String amount = TextFormatting.GOLD + "" + this.tile.lavaTank.getFluidAmount() + "/" + this.tile.lavaTank.getCapacity() + " mB";
		   multistring = new String[]{content, amount};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reservoir syngas
	   if(GuiUtils.hoveringArea(72, 24, 12, 35, mouseX, mouseY, x, y)){
		   String content = TextFormatting.GRAY + "Internal Buffer: " + TextFormatting.WHITE + TextFormatting.BOLD + "Syngas";
		   String amount = TextFormatting.WHITE + "" + GuiUtils.translateMC(this.tile.gasTank.getFluidAmount()) + "/" + GuiUtils.translateMC(this.tile.gasTank.getCapacity()) + " cu";
		   multistring = new String[]{content, amount};
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

		//activation
        if(this.tile.isActive()){
        	if(this.tile.isPowered()){
           		this.drawTexturedModalRect(i + 153, j + 92, 190, 0, 14, 14);
        	}else{
           		this.drawTexturedModalRect(i + 153, j + 92, 176, 0, 14, 14);
        	}
        }

        //power bar
        if (this.tile.getPower() > 0){
            int k = GuiUtils.getScaledValue(59, this.tile.getPower(), this.tile.getPowerMax());
            this.drawTexturedModalRect(i + 29, j + 52+(59-k), 176, 14, 10, k);
        }

        //lava reservoir bar
        if (this.tile.lavaTank.getFluidAmount() > 0){
            int k = GuiUtils.getScaledValue(33, this.tile.lavaTank.getFluidAmount(), this.tile.lavaTank.getCapacity());
            this.drawTexturedModalRect(i + 8, j + 51+(32-k), 196, 14, 10, k);
        }

        //syngas reservoir bar
        if (this.tile.gasTank.getFluidAmount() > 0){
            int k = GuiUtils.getScaledValue(33, this.tile.gasTank.getFluidAmount(), this.tile.gasTank.getCapacity());
            this.drawTexturedModalRect(i + 67, j + 25+(33-k), 206, 14, 10, k);
        }

        //air bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(60, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 118, j + 40, 180, 85, k, 38);
        }

    }

}