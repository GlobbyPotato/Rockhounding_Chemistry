package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCrawlerAssembler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCrawlerAssembler extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guicrawlerassembler.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityCrawlerAssembler crawlerAssembler;

    public GuiCrawlerAssembler(InventoryPlayer playerInv, TileEntityCrawlerAssembler furnaceInv){
        super(new ContainerCrawlerAssembler(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.crawlerAssembler = furnaceInv;
		this.xSize = 176;
		this.ySize = 201;

    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        //String s = this.crawlerAssembler.getDisplayName().getUnformattedText();
        //this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //smelt bar
        int l = this.getCookProgressScaled(54);
        this.drawTexturedModalRect(i + 16, j + 100, 176, 0, l, 5);
    }

    private int getCookProgressScaled(int pixels){
        int i = this.crawlerAssembler.getField(0);
        int j = this.crawlerAssembler.getField(1);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

}