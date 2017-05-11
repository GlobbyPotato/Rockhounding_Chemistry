package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineCrawlerAssembler extends GuiBase {
	
    public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guicrawlerassembler.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityMineCrawlerAssembler crawlerAssembler;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 201;

    public GuiMineCrawlerAssembler(InventoryPlayer playerInv, TileEntityMineCrawlerAssembler tile){
        super(tile, new ContainerMineCrawlerAssembler(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.crawlerAssembler = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String device = Translator.translateToLocal("container.crawlerAssembler");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //smelt bar
        int k = this.getBarScaled(54, this.crawlerAssembler.cookTime, this.crawlerAssembler.getMaxCookTime());
        this.drawTexturedModalRect(i + 16, j + 100, 176, 0, k, 5);
    }

}