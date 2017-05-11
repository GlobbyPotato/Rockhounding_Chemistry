package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPetrographerTable extends GuiBase {
	
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guipetrographertable.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityPetrographerTable petroTable;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;

    public GuiPetrographerTable(InventoryPlayer playerInv, TileEntityPetrographerTable tile){
        super(tile, new ContainerPetrographerTable(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.petroTable = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String device = Translator.translateToLocal("container.petroTable");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

}