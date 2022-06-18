package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COExtractorController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIExtractorController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiextractorcontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guichemicalextractorjei.png");

    private final TEExtractorController tile;

    public UIExtractorController(InventoryPlayer playerInv, TEExtractorController tile){
    	super(new COExtractorController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEExtractorController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   String commDescr = "";
	   String commValue = TextFormatting.GRAY + "Reaction Intensity: " + TextFormatting.GREEN + this.tile.getIntensity() + "/16";

	   //display
	   if(GuiUtils.hoveringArea(26, 97, 15, 16, mouseX, mouseY, x, y)){
		   commDescr = "Current reaction intensity";
		   multistring = new String[]{commDescr, commValue};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(134, 61, 16, 16, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   // lo
	   if(GuiUtils.hoveringArea(10, 97, 16, 16, mouseX, mouseY, x, y)){
		   commDescr = "Decrease reaction intensity";
		   multistring = new String[]{commDescr, commValue};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   // hi
	   if(GuiUtils.hoveringArea(41, 97, 16, 16, mouseX, mouseY, x, y)){
		   commDescr = "Increase reaction intensity";
		   multistring = new String[]{commDescr, commValue};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(115, 91, 14, 14, mouseX, mouseY, x, y)){
		   String rf = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.powerConsume() + " RF/t"; 
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   multistring = new String[]{sf, tk, rf};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //left acid consume
	   if(GuiUtils.hoveringArea(14, 34, 16, 31, mouseX, mouseY, x, y)){
		   String nitrCons = TextFormatting.RED + "N/A: " + this.tile.calculatedNitr() + " mB";
		   commDescr = TextFormatting.GRAY + "Left Channel:";
		   if(this.tile.hasFlotationTank1()){
			   if(this.tile.getFlotationTank1().hasSolventFluid()){
				   nitrCons = TextFormatting.WHITE + this.tile.getFlotationTank1().getSolventFluid().getLocalizedName() + ": "+ TextFormatting.AQUA + this.tile.calculatedNitr() + " mB";
			   }
		   }
		   multistring = new String[]{commDescr, nitrCons};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //right acid consume
	   if(GuiUtils.hoveringArea(37, 34, 16, 31, mouseX, mouseY, x, y)){
		   String cyanCons = TextFormatting.RED + "N/A: " + this.tile.calculatedCyan() + " mB";
		   commDescr = TextFormatting.GRAY + "Right Channel:";
		   if(this.tile.hasFlotationTank2()){
			   if(this.tile.getFlotationTank2().hasSolventFluid()){
				   cyanCons = TextFormatting.WHITE + this.tile.getFlotationTank2().getSolventFluid().getLocalizedName() + ": "+ TextFormatting.AQUA + this.tile.calculatedCyan() + " mB";
			   }
		   }
		   multistring = new String[]{commDescr, cyanCons};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //recovery factor
	   if(GuiUtils.hoveringArea(114, 25, 19, 19, mouseX, mouseY, x, y)){
		   String recoveryB = TextFormatting.GRAY + "Safe Recovery: "  + TextFormatting.DARK_GREEN + this.tile.baseRecovery() + "%";
		   String recoveryR = TextFormatting.GRAY + "Recovery Efficiency: " + TextFormatting.GREEN + this.tile.normalRecovery() + "%";
		   String recoveryD = TextFormatting.GRAY + "Dissolution Chance: "  + TextFormatting.GOLD + this.tile.dissolutionChance() + "%";
		   String recoveryF = TextFormatting.GRAY + "Stabilization: " + TextFormatting.YELLOW + this.tile.recoveryFactor() + "%";
		   multistring = new String[]{recoveryB, " ", recoveryR, recoveryD, recoveryF};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRenderer.drawString(String.valueOf(this.tile.getIntensity()), 28, 102, 4210752);
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
            int k = GuiUtils.getScaledValue(29, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 70, j + 49, 176, 27, 36, (29 - k));
        }else{
            this.drawTexturedModalRect(i + 70, j + 49, 176, 27, 36, 29);
        }

    }

}