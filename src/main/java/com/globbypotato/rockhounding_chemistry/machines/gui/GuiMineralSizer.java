package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralSizer extends GuiBase {
	
    
    private final InventoryPlayer playerInventory;
    private final TileEntityMineralSizer mineralSizer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 191;

    public GuiMineralSizer(InventoryPlayer playerInv, TileEntityMineralSizer tile){
        super(tile, new ContainerMineralSizer(playerInv,tile));
        TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralsizer.png");
        this.mineralSizer = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }
   
    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.mineralSizer.getField(0) + "/" + this.mineralSizer.getField(1) + " ticks"};
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }

    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
    	String device = "Mineral Sizer";
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);

        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.mineralSizer.powerCount > 0){
            int k = this.getBarScaled(50, this.mineralSizer.getField(0), this.mineralSizer.getField(1));
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 51, 10, k);
        }
        //smelt bar
        if (this.mineralSizer.getField(2) > 0){
            int k = this.getBarScaled(46, this.mineralSizer.getField(2), this.mineralSizer.getField(3));
            this.drawTexturedModalRect(i + 66, j + 34, 176, 0, k, 46);
        }
    }

}