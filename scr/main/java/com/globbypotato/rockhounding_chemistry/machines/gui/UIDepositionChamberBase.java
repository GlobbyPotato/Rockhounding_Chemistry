package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.CODepositionChamberBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEDepositionChamberBase;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIDepositionChamberBase extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guidepositionchamber.png");

    private final TEDepositionChamberBase tile;

    public UIDepositionChamberBase(InventoryPlayer playerInv, TEDepositionChamberBase tile){
    	super(new CODepositionChamberBase(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEDepositionChamberBase.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;

	   //speed upgrade
	   if(GuiUtils.hoveringArea(79, 79, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //Insulation upgrade
	   if(GuiUtils.hoveringArea(122, 49, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.insulationSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.insulation_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //Chamber upgrade
	   if(GuiUtils.hoveringArea(36, 49, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.casingSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.casing_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

    }

}