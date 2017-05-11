package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOwcAssembler extends GuiBase {

    public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiowcassembler.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityOwcAssembler owcAssembler;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 201;

    public GuiOwcAssembler(InventoryPlayer playerInv, TileEntityOwcAssembler tile){
        super(tile, new ContainerOwcAssembler(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.owcAssembler = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String device = Translator.translateToLocal("container.owcAssembler");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //smelt bar
        int k = this.getBarScaled(54, this.owcAssembler.cookTime, this.owcAssembler.getMaxCookTime());
        this.drawTexturedModalRect(i + 61, j + 94, 176, 0, k, 5);
    }

}