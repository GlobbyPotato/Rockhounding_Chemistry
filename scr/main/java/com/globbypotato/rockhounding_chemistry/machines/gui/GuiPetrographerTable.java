package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;

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
		this.containerName = "container.petroTable";
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

}