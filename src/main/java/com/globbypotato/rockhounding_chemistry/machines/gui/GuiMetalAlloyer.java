package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMetalAlloyer extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guimetalalloyer.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityMetalAlloyer metalAlloyer;

    public GuiMetalAlloyer(InventoryPlayer playerInv, TileEntityMetalAlloyer furnaceInv){
        super(new ContainerMetalAlloyer(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.metalAlloyer = furnaceInv;
		this.xSize = 176;
		this.ySize = 201;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //recipe buttons
	   if(mouseX >= 52+x && mouseX <= 67+x && mouseY >= 16+y && mouseY <= 31+y){
		   String[] text = {"Previous Recipe"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   if(mouseX >= 88+x && mouseX <= 103+x && mouseY >= 16+y && mouseY <= 31+y){
		   String[] text = {"Next Recipe"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.metalAlloyer.getField(0) + "/" + this.metalAlloyer.getField(1) + " ticks"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }

    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.metalAlloyer.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
       
        String name = " - ";
        name = this.metalAlloyer.alloyNames[this.metalAlloyer.countRecipes()];
        this.fontRendererObj.drawString(name, 106, 20, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.metalAlloyer.powerCount > 0){
            int k = this.getPowerLeftScaled(50);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 27, 10, k);
        }
        //smelt bar
        int ll = this.getCookProgressScaled(17);
        this.drawTexturedModalRect(i + 99, j + 69, 176, 0, 14, ll);

    }

    private int getCookProgressScaled(int pixels){
        int i = this.metalAlloyer.getField(2);
        int j = this.metalAlloyer.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getPowerLeftScaled(int pixels){
        int i = this.metalAlloyer.getField(1);
        if (i == 0){i = this.metalAlloyer.machineSpeed();}
        return this.metalAlloyer.getField(0) * pixels / i;
    }

}