package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMetalAlloyer extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityMetalAlloyer metalAlloyer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 201;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimetalalloyer.png");

	public GuiMetalAlloyer(InventoryPlayer playerInv, TileEntityMetalAlloyer tile){
		super(tile,new ContainerMetalAlloyer(playerInv, tile));
		this.playerInventory = playerInv;
        TEXTURE = TEXTURE_REF;
		this.metalAlloyer = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		   //recipe buttons
		   if(mouseX >= 52+x && mouseX <= 67+x && mouseY >= 21+y && mouseY <= 36+y){
			   List<String> tooltip = Arrays.asList("Previous Recipe");
			   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }
		   if(mouseX >= 68+x && mouseX <= 83+x && mouseY >= 21+y && mouseY <= 36+y){
			   List<String> tooltip = Arrays.asList("Next Recipe");
			   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }
		   //bars progression (fuel)
		   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
			   String text = this.metalAlloyer.getPower() + "/" + this.metalAlloyer.getPowerMax() + " ticks";
			   List<String> tooltip = Arrays.asList(text);
			   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }
	}

	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.metalAlloyer");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);

		String name = "";
		if(this.metalAlloyer.isValidInterval()){
			name = this.metalAlloyer.getRecipe().getAlloyName();
		}else{
			name = "No Recipe";
		}
        this.fontRendererObj.drawString(name, 103, 25, 4210752);
	}

	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.metalAlloyer.powerCount > 0){
            int k = this.getBarScaled(50, this.metalAlloyer.powerCount, this.metalAlloyer.powerMax);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 27, 10, k);
        }
        //smelt bar
        if (this.metalAlloyer.cookTime > 0){
            int k = this.getBarScaled(17, this.metalAlloyer.cookTime, this.metalAlloyer.getMaxCookTime());
            this.drawTexturedModalRect(i + 99, j + 74, 176, 0, 14, k);
        }
        
        //inductor
        if(this.metalAlloyer.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 19, 176, 79, 18, 18);
        }

	}
}