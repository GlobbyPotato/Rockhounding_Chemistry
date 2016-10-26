package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOwcAssembler extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guiowcassembler.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityOwcAssembler owcAssembler;

    public GuiOwcAssembler(InventoryPlayer playerInv, TileEntityOwcAssembler furnaceInv){
        super(new ContainerOwcAssembler(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.owcAssembler = furnaceInv;
		this.xSize = 176;
		this.ySize = 201;

    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        String s = this.owcAssembler.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);

        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //smelt bar
        int k = this.getBarScaled(54, this.owcAssembler.getField(0), this.owcAssembler.getField(1));
        this.drawTexturedModalRect(i + 61, j + 94, 176, 0, k, 5);
    }

	private int getBarScaled(int pixels, int count, int max) {
        return count > 0 && max > 0 ? count * pixels / max : 0;
	}

}