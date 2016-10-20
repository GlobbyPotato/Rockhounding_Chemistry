package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChemicalExtractor extends GuiBase {
    private final InventoryPlayer playerInventory;
    private final TileEntityChemicalExtractor chemicalExtractor;
	public static final int WIDTH = 245;
	public static final int HEIGHT = 256;

	
    public GuiChemicalExtractor(InventoryPlayer playerInv, TileEntityChemicalExtractor tile){
        super(tile,new ContainerChemicalExtractor(playerInv, tile));
        this.playerInventory = playerInv;
        this.chemicalExtractor = tile;
        TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/guichemicalextractor.png");
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.chemicalExtractor.getField(0) + "/" + this.chemicalExtractor.getField(1) + " ticks"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   if(mouseX >= 31+x && mouseX <= 40+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.chemicalExtractor.getField(4) + "/" + this.chemicalExtractor.getField(5) + " RF"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.chemicalExtractor.powerCount > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.getField(0), this.chemicalExtractor.getField(1));
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 246, 0, 10, k);
        }
        //redstone bar
        if (this.chemicalExtractor.redstoneCount > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.getField(4), this.chemicalExtractor.getField(5));
            this.drawTexturedModalRect(i + 31, j + 40 + (50 - k), 246, 109, 10, k);
        }
        //smelt bar
        int k = this.getBarScaled(36, this.chemicalExtractor.getField(2), this.chemicalExtractor.getField(3));
        this.drawTexturedModalRect(i + 62, j + 97, 250, 50, 6, 36 - k);
		//cabinet bars
		for(int hSlot = 0; hSlot <= 6; hSlot++){
			for(int vSlot = 0; vSlot <= 7; vSlot++){
				int slotID = (hSlot * 8) + vSlot;
		        int cab = this.getBarScaled(19, this.chemicalExtractor.elementList[slotID], this.chemicalExtractor.extractingFactor);
				if(cab > 19){cab = 19;}
				this.drawTexturedModalRect(i + 100 + (19 * vSlot), j + 19 + (21 * hSlot), 245, 89, 4, 19 - cab);
			}
		}
    }


	private int getBarScaled(int pixels, int count, int max) {
        int i = max;
        if (i == 0){i = max;}
        return count > 0 && max > 0 ? count * pixels / max : 0;
	}

}