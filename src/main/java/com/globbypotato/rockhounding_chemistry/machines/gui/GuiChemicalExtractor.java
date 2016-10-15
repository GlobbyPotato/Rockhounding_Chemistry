package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChemicalExtractor extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guichemicalextractor.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityChemicalExtractor chemicalExtractor;

    public GuiChemicalExtractor(InventoryPlayer playerInv, TileEntityChemicalExtractor furnaceInv){
        super(new ContainerChemicalExtractor(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.chemicalExtractor = furnaceInv;
		this.xSize = 245;
		this.ySize = 256;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.chemicalExtractor.getField(0) + "/" + this.chemicalExtractor.getField(1) + " ticks"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   if(mouseX >= 31+x && mouseX <= 40+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.chemicalExtractor.getField(4) + "/" + this.chemicalExtractor.getField(5) + " RF"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.chemicalExtractor.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.chemicalExtractor.powerCount > 0){
            int k = this.getPowerLeftScaled(50);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 246, 0, 10, k);
        }
        //redstone bar
        if (this.chemicalExtractor.redstoneCount > 0){
            int k = this.getRedstoneLeftScaled(50);
            this.drawTexturedModalRect(i + 31, j + 40 + (50 - k), 246, 109, 10, k);
        }
        //smelt bar
        int l = this.getCookProgressScaled(36);
        this.drawTexturedModalRect(i + 62, j + 97, 250, 50, 6, 36 - l);
        
		//cabinet bars
		for(int hSlot = 0; hSlot <= 7; hSlot++){
			for(int vSlot = 0; vSlot <= 6; vSlot++){
				int slotID = (vSlot * 8) + hSlot;
	            int k = this.getCabinetScaled(19);
				int cab = (this.chemicalExtractor.elementList[slotID] * 19) / this.chemicalExtractor.extractingFactor;
				if(cab > 19){cab = 19;}
				this.drawTexturedModalRect(i + 100 + (19 * hSlot), j + 19 + (21 * vSlot), 245, 89, 4, 19 - cab);
			}
		}
    }

    private int getCookProgressScaled(int pixels){
        int i = this.chemicalExtractor.getField(2);
        int j = this.chemicalExtractor.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getPowerLeftScaled(int pixels){
        int i = this.chemicalExtractor.getField(1);
        if (i == 0){i = this.chemicalExtractor.machineSpeed();}
        return this.chemicalExtractor.getField(0) * pixels / i;
    }
    private int getRedstoneLeftScaled(int pixels){
        int i = this.chemicalExtractor.getField(5);
        if (i == 0){i = this.chemicalExtractor.redstoneMax;}
        return this.chemicalExtractor.getField(4) * pixels / i;
    }
    private int getCabinetScaled(int pixels){
        int i = this.chemicalExtractor.extractingFactor;
        if (i == 0){i = this.chemicalExtractor.extractingFactor;}
        return this.chemicalExtractor.getField(4) * pixels / i;
    }

}