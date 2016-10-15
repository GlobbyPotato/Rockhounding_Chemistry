package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralAnalyzer extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralanalyzer.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityMineralAnalyzer mineralAnalyzer;

    public GuiMineralAnalyzer(InventoryPlayer playerInv, TileEntityMineralAnalyzer furnaceInv){
        super(new ContainerMineralAnalyzer(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.mineralAnalyzer = furnaceInv;
		this.xSize = 176;
		this.ySize = 191;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.mineralAnalyzer.getField(0) + "/" + this.mineralAnalyzer.getField(1) + " ticks"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }

    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.mineralAnalyzer.getDisplayName().getUnformattedText();
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
        if (this.mineralAnalyzer.powerCount > 0){
            int k = this.getPowerLeftScaled(50);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 51, 10, k);
        }
        //smelt bar
        int l = this.getCookProgressScaled(51);
        this.drawTexturedModalRect(i + 48, j + 42, 176, 0, 27, l);
    }

    private int getCookProgressScaled(int pixels){
        int i = this.mineralAnalyzer.getField(2);
        int j = this.mineralAnalyzer.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getPowerLeftScaled(int pixels){
        int i = this.mineralAnalyzer.getField(1);
        if (i == 0){i = this.mineralAnalyzer.machineSpeed();}
        return this.mineralAnalyzer.getField(0) * pixels / i;
    }

}