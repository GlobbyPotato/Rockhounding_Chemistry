package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COGanController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGanController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIGanController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guigancontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guigancontrollerjei.png");

    private final TEGanController tile;

    public UIGanController(InventoryPlayer playerInv, TEGanController tile){
    	super(new COGanController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEGanController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   String[] multiString;
	   String caption;
	   String mainprod;

	   //enable N
	   if(GuiUtils.hoveringArea(7, 28, 18, 18, mouseX, mouseY, x, y)){
		   caption = TextFormatting.WHITE + "Enable Channel 1: Nitrogen";
		   mainprod = TextFormatting.AQUA + "Nitrogen: 0.300 cu/sec";
		   multiString = new String[]{caption, mainprod};
		   tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //enable O
	   if(GuiUtils.hoveringArea(26, 28, 18, 18, mouseX, mouseY, x, y)){
		   caption = TextFormatting.WHITE + "Enable Channel 2: Oxygen";
		   mainprod = TextFormatting.AQUA + "Oxygen: 0.100 cu/sec";
		   multiString = new String[]{caption, mainprod};
		   tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //enable X
	   if(GuiUtils.hoveringArea(45, 28, 18, 18, mouseX, mouseY, x, y)){
		   caption = TextFormatting.WHITE + "Enable Channel 3: Rare gases";
		   String prod_Ar = TextFormatting.AQUA + "Argon: 0.006 cu/sec";
		   String prod_CO = TextFormatting.AQUA + "Carbon Dioxide: 0.003 cu/sec";
		   String prod_Ne = TextFormatting.AQUA + "Neon: 0.090 cu/min";
		   String prod_He = TextFormatting.AQUA + "Helium: 0.036 cu/min";
		   String prod_Kr = TextFormatting.AQUA + "Krypton: 0.008 cu/min";
		   String prod_Xe = TextFormatting.AQUA + "Xenon: 0.009 cu/min";
		   multiString = new String[]{caption, prod_Ar, prod_CO, prod_Ne, prod_He, prod_Kr, prod_Xe};
		   tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(137, 28, 14, 14, mouseX, mouseY, x, y)){
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.num_air() + " cu/t"; 
		   String cons = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.calculateConsume() + " RF/t";
		   String speed = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x";
		   multiString = new String[]{tk, speed, cons};
		   tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(7, 67, 18, 18, mouseX, mouseY, x, y)){
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

        //enable N
        if(this.tile.enableN()){
    		this.drawTexturedModalRect(i + 9, j + 30, 204, 10, 14, 14);
	    }

        //enable O
        if(this.tile.enableO()){
    		this.drawTexturedModalRect(i + 28, j + 30, 218, 10, 14, 14);
	    }

        //enable X
        if(this.tile.enableX()){
    		this.drawTexturedModalRect(i + 47, j + 30, 232, 10, 14, 14);
	    }

        //smelt bar
        if (this.tile.getCooktime() > 0){

            int k = GuiUtils.getScaledValue(60, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 71, j + 25 + (60-k), 176, 25, 20, k);

            int p = GuiUtils.getScaledValue(34, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 112, j + 51, 176, 92, 20, p);

        }

    }
}