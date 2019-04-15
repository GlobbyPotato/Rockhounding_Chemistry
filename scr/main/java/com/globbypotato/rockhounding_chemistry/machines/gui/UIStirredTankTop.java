package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COStirredTankTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEStirredTankTop;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIStirredTankTop extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guistirredtanktop.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guistirredtanktopjei.png");

    private final TEStirredTankTop tile;

    public UIStirredTankTop(InventoryPlayer playerInv, TEStirredTankTop tile){
    	super(new COStirredTankTop(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEStirredTankTop.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;
	   
	   //monitor
	   if(GuiUtils.hoveringArea(111, 87, 14, 14, mouseX, mouseY, x, y)){
		   String rf = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.powerConsume() + " RF/t";
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   String yd = TextFormatting.GRAY + "Yeld: " + TextFormatting.YELLOW + this.tile.getCalculatedYeld() * 2 + " mb/t"; 
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   multistring = new String[]{sf, tk, yd, rf};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //acid consume
	   if(GuiUtils.hoveringArea(30, 60, 16, 31, mouseX, mouseY, x, y)){
		   String solv = TextFormatting.RED + "N/A";
		   String commDescr = TextFormatting.GRAY + "Primary Solvent:";
		   if(this.tile.hasIntank()){
			   if(this.tile.getIntank().hasSolventFluid()){
				   solv = TextFormatting.WHITE + this.tile.getIntank().getSolventFluid().getLocalizedName() + ": "+ TextFormatting.AQUA + this.tile.getCalculatedYeld() + " mB";
			   }
		   }
		   multistring = new String[]{commDescr, solv};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   if(GuiUtils.hoveringArea(130, 60, 16, 31, mouseX, mouseY, x, y)){
		   String solv = TextFormatting.RED + "N/A";
		   String commDescr = TextFormatting.GRAY + "Secondary Solvent:";
		   if(this.tile.hasIntank()){
			   if(this.tile.getIntank().hasReagentFluid()){
				   solv = TextFormatting.WHITE + this.tile.getIntank().getReagentFluid().getLocalizedName() + ": "+ TextFormatting.AQUA + this.tile.getCalculatedYeld() + " mB";
			   }
		   }
		   multistring = new String[]{commDescr, solv};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //tank full
	   if(GuiUtils.hoveringArea(67, 51, 42, 36, mouseX, mouseY, x, y)){
		   String volt = "Empty Tank";
		   if(this.tile.isValidRecipe()){
			   volt = "Valid Recipe";
		   }
		   tooltip = GuiUtils.drawLabel(volt, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(152, 94, 16, 16, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //voltage
	   if(GuiUtils.hoveringArea(12, 25, 14, 14, mouseX, mouseY, x, y)){
		   String volt = "Voltage not required";
		   if(this.tile.hasVoltage()){
			   volt = "Voltage required";
		   }
		   tooltip = GuiUtils.drawLabel(volt, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//fill tank
        if(this.tile.isValidRecipe()){
       		this.drawTexturedModalRect(i + 67, j + 51, 176, 47, 42, 36);
        }

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(30, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 25, j + 102, 176, 95, k, 5);
        }

		//activation
        if(this.tile.isActive()){
        	if(this.tile.isPowered()){
        		this.drawTexturedModalRect(i + 81, j + 97, 190, 10, 14, 14);
        	}else{
        		this.drawTexturedModalRect(i + 81, j + 97, 176, 10, 14, 14);
        	}
        }

		//voltage
        if(this.tile.hasVoltage()){
       		this.drawTexturedModalRect(i + 12, j + 25, 176, 29, 14, 14);
        }

    }

}