package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEarthBreaker extends GuiBase {
	
	 public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiearthbreaker.png");
    
    private final InventoryPlayer playerInventory;
    private final TileEntityEarthBreaker earthBreaker;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;

    public GuiEarthBreaker(InventoryPlayer playerInv, TileEntityEarthBreaker tile){
        super(tile, new ContainerEarthBreaker(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.earthBreaker = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }
   
    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   
		//redstone
		if(mouseX >= 7+x && mouseX <= 18+x && mouseY >= 21+y && mouseY <= 83+y){
			String text = this.earthBreaker.chargeCount + "/" + this.earthBreaker.chargeMax + " KRF";
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}

		//drain
		if(mouseX >= 158+x && mouseX <= 168+x && mouseY >= 74+y && mouseY <= 91+y){
			List<String> tooltip = Arrays.asList("Activation");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}

    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.earthBreaker");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.earthBreaker.chargeCount > 0){
            int k = this.getBarScaled(60, this.earthBreaker.chargeCount, this.earthBreaker.chargeMax);
            this.drawTexturedModalRect(i + 8, j + 22 + (60 - k), 176, 0, 10, k);
        }
        //smelt bar
        if (this.earthBreaker.cookTime > 0){
            int k = this.getBarScaled(32, this.earthBreaker.cookTime, this.earthBreaker.getMaxCookTime());
            this.drawTexturedModalRect(i + 72, j + 23 + k, 176, 78, 32, 32);
        }
        //valve
        if(this.earthBreaker.isActive()){
            this.drawTexturedModalRect(i + 151, j + 75, 176, 60, 18, 18);
        }
    }
}