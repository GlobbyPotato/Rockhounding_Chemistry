package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIMaterialCabinetBase extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimaterialcabinet.png");

    private final TEMaterialCabinetBase tile;

    public UIMaterialCabinetBase(InventoryPlayer playerInv, TEMaterialCabinetBase tile){
    	super(new COMaterialCabinetBase(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEMaterialCabinetBase.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //elements
	   int colOffset = 8 + x;
	   int rowOffset = 19 + y;
	   for(int row = 0; row < 7; row++){
		   for(int col = 0; col < 9; col++){
			   int xSpace = col * 18;
			   int ySpace = row * 14;
			   if(mouseX >= colOffset + xSpace && mouseX <= colOffset + xSpace + 15 && mouseY >= rowOffset + ySpace && mouseY <= rowOffset + ySpace + 12){
				   int enumDust = (row * 9) + col;
				   if(enumDust < this.tile.recipeList().size()){
					   String formalElement = this.tile.getRecipeList(enumDust).getName();
					   String elementText = formalElement + " : " + TextFormatting.YELLOW + this.tile.elementList[enumDust] + "/" + this.tile.getExtractingFactor() + " ppc" ;
					   String oredictElement = TextFormatting.DARK_AQUA + "(" + this.tile.getRecipeList(enumDust).getOredict() + ")";
					  
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
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		for(int vSlot = 0; vSlot < 7; vSlot++){
			for(int hSlot = 0; hSlot < 9; hSlot++){
				int slotID = (vSlot * 9) + hSlot;
				   if(slotID < this.tile.recipeList().size()){
						String symbol = this.tile.getRecipeList(slotID).getSymbol();
						this.fontRenderer.drawString(symbol, 10 + (18 * hSlot), 20 + (14 * vSlot), 4210752);
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
		for(int vSlot = 0; vSlot < 7; vSlot++){
			for(int hSlot = 0; hSlot < 9; hSlot++){
				int slotID = (vSlot * 9) + hSlot;
				if(slotID < this.tile.recipeList().size()){
			        int cab = GuiUtils.getScaledValue(19, this.tile.elementList[slotID], this.tile.getExtractingFactor());
					if(cab > 16){cab = 16;}
					this.drawTexturedModalRect(i + 8 + (18 * hSlot), j + 28 + (14 * vSlot), 176, 0, cab, 3);
				}
			}
		}

    }
}