package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerSaltSeasoner;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltSeasoner;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSaltSeasoner extends GuiBase {
	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guisaltseasoner.png");
    private final InventoryPlayer playerInventory;
    private final TileEntitySaltSeasoner saltSeasoner;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;

    public GuiSaltSeasoner(InventoryPlayer playerInv, TileEntitySaltSeasoner tile){
        super(tile, new ContainerSaltSeasoner(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.saltSeasoner = tile;
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

        String device = Translator.translateToLocal("container.seasoningRack");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //smelt bar
        if (this.saltSeasoner.cookTime > 0){
            int k = this.getBarScaled(32, this.saltSeasoner.cookTime, this.saltSeasoner.getMaxCookTime());
            this.drawTexturedModalRect(i + 72, j + 43, 176, 0, k, 14);
        }
    }
}