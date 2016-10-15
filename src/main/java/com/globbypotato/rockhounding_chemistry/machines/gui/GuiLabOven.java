package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLabOven extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guilaboven.png");
    /** The player inventory bound to this GUI. */
    private final InventoryPlayer playerInventory;
    private final TileEntityLabOven labOven;

    public GuiLabOven(InventoryPlayer playerInv, TileEntityLabOven furnaceInv){
        super(new ContainerLabOven(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.labOven = furnaceInv;
		this.xSize = 176;
		this.ySize = 191;

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //solvent icon
	   if(mouseX >= 118+x && mouseX <= 130+x && mouseY >= 77+y && mouseY <= 92+y){
		   if(this.labOven.countRecipes() == 1 || this.labOven.countRecipes() == 4){
			   String[] text = {this.labOven.solventNames[1]};
		       List tooltip = Arrays.asList(text);
			   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }else if(this.labOven.countRecipes() == 2 || this.labOven.countRecipes() == 3){
			   String[] text = {this.labOven.solventNames[2]};
		       List tooltip = Arrays.asList(text);
			   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }
	   }
	   //recipe buttons
	   if(mouseX >= 29+x && mouseX <= 42+x && mouseY >= 20+y && mouseY <= 35+y){
		   String[] text = {"Previous Recipe"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   if(mouseX >= 134+x && mouseX <= 146+x && mouseY >= 20+y && mouseY <= 35+y){
		   String[] text = {"Next Recipe"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //bars progression (fuel-redstone)
	   if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.labOven.getField(0) + "/" + this.labOven.getField(1) + " ticks"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   if(mouseX >= 155+x && mouseX <= 164+x && mouseY >= 40+y && mouseY <= 89+y){
		   String[] text = {this.labOven.getField(4) + "/" + this.labOven.getField(5) + " RF"};
	       List tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.labOven.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 92 + 2, 4210752);
        
        String name = " - ";
        name = this.labOven.recipeNames[this.labOven.countRecipes()];
        this.fontRendererObj.drawString(name, 44, 24, 4210752);
        
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
        //power bar
        if (this.labOven.powerCount > 0){
            int k = this.getPowerLeftScaled(50);
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 27, 10, k);
        }
        //redstone
        if (this.labOven.redstoneCount > 0){
            int k = this.getRedstoneLeftScaled(50);
            this.drawTexturedModalRect(i + 155, j + 40 + (50 - k), 176, 81, 10, k);
        }
        //smelt bar
        int l = this.getCookProgressScaled(14);
        this.drawTexturedModalRect(i + 61, j + 58, 176, 0, l, 15);
        //flame icon
        if(this.labOven.getField(2) != 0 && this.labOven.getField(3) != 0){
            this.drawTexturedModalRect(i + 82, j + 79, 176, 131, 12, 14);
        }
        //solvent icon
        if(this.labOven.getField(2) != 0 && this.labOven.getField(3) != 0){
            this.drawTexturedModalRect(i + 100, j + 63, 176, 145, 14, 9);
        }
        //recipe icon
        if(this.labOven.recipeCount == 1 || this.labOven.recipeCount == 4){
            this.drawTexturedModalRect(i + 118, j + 77, 195, 27, 12, 16);
        }else if(this.labOven.recipeCount == 2 || this.labOven.recipeCount == 3){
            this.drawTexturedModalRect(i + 118, j + 77, 195, 43, 12, 16);
        }else{
        	this.drawTexturedModalRect(i + 118, j + 77, 195, 11, 12, 16);
        }
    }

    private int getCookProgressScaled(int pixels){
        int i = this.labOven.getField(2);
        int j = this.labOven.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getPowerLeftScaled(int pixels){
        int i = this.labOven.getField(1);
        if (i == 0){i = this.labOven.machineSpeed();}
        return this.labOven.getField(0) * pixels / i;
    }

    private int getRedstoneLeftScaled(int pixels){
        int i = this.labOven.getField(5);
        if (i == 0){i = this.labOven.redstoneMax;}
        return this.labOven.getField(4) * pixels / i;
    }

}