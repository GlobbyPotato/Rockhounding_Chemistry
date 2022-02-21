package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COExtractorCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIElementsCabinetBase extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiextractorcabinet.png");

    private final TEElementsCabinetBase tile;

    public UIElementsCabinetBase(InventoryPlayer playerInv, TEElementsCabinetBase tile){
    	super(new COExtractorCabinetBase(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEElementsCabinetBase.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //elements
	   int colOffset = 10 + x;
	   int rowOffset = 19 + y;
	   for(int row = 0; row < 7; row++){
		   for(int col = 0; col < 8; col++){
			   int xSpace = col * 20;
			   int ySpace = row * 14;
			   if(mouseX >= colOffset + xSpace && mouseX <= colOffset + xSpace + 15 && mouseY >= rowOffset + ySpace && mouseY <= rowOffset + ySpace + 12){
				   int enumDust = (row * 8) + col;
				   if(enumDust < EnumElements.size()){
					   String inhibit = "";
					   for(int ix = 0; ix < ChemicalExtractorRecipes.inhibited_elements.size(); ix++){
						   if(EnumElements.getDust(enumDust).toLowerCase().matches(ChemicalExtractorRecipes.inhibited_elements.get(ix).toLowerCase())){
							   inhibit = " - (Inhibited)";
						   }
					   }

					   String formalElement = EnumElements.getName(enumDust).substring(0, 1).toUpperCase() + EnumElements.getName(enumDust).substring(1);
					   String elementText = formalElement + " : " + TextFormatting.YELLOW + this.tile.elementList[enumDust] + "/" + this.tile.getExtractingFactor() + " ppc"+ TextFormatting.RED + inhibit ;
					   String oredictElement = TextFormatting.DARK_AQUA + "(" + EnumElements.getDust(enumDust) + ")";

					   String dustCount = TextFormatting.DARK_GREEN + "Not enough element";
					   int dustNum = this.tile.elementList[enumDust] / this.tile.getExtractingFactor();
					   if(dustNum > 0){
						   dustCount = TextFormatting.DARK_GREEN + String.valueOf(dustNum) + " dust/s available";
					   }

					   String[] multilabel = new String[]{elementText, dustCount, oredictElement};
					   List<String> tooltip = GuiUtils.drawMultiLabel(multilabel, mouseX, mouseY);
					   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
				   }
			   }
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

		//cabinet bars
		for(int hSlot = 0; hSlot <= 6; hSlot++){
			for(int vSlot = 0; vSlot <= 7; vSlot++){
				int slotID = (hSlot * 8) + vSlot;
		        int cab = GuiUtils.getScaledValue(19, this.tile.elementList[slotID], this.tile.getExtractingFactor());
				if(cab > 16){cab = 16;}
				this.drawTexturedModalRect(i + 10 + (20 * vSlot), j + 28 + (14 * hSlot), 176, 0, cab, 3);
			}
		}

    }
}