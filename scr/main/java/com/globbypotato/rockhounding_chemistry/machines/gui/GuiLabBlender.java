package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabBlender;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLabBlender extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guilabblender.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/guilabblenderjei.png");

    private final InventoryPlayer playerInventory;
    private final TileEntityLabBlender labBlender;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 210;

    public GuiLabBlender(InventoryPlayer playerInv, TileEntityLabBlender tile){
        super(tile, new ContainerLabBlender(playerInv,tile));
       this.TEXTURE = TEXTURE_REF;
        this.labBlender = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.lab_blender";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//fuel
	    if(mouseX >= 10+x && mouseX <= 21+x && mouseY >= 27+y && mouseY <= 78+y){
			drawPowerInfo("ticks", this.labBlender.getMaxCookTime(), this.labBlender.getPower(), this.labBlender.getPowerMax(), mouseX, mouseY);
		}

		//fuel status
		String[] fuelstatusString = handleFuelStatus(this.labBlender.isFuelGated(), this.labBlender.hasFuelBlend(), this.labBlender.canInduct(), this.labBlender.allowPermanentInduction());
		if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 7+y && mouseY <= 24+y){
			drawMultiLabel(fuelstatusString, mouseX, mouseY);
		}

		//activation
		if(mouseX >= 105+x && mouseX <= 121+x && mouseY >= 96+y && mouseY <= 111+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}

		//lock
		if(mouseX >= 125+x && mouseX <= 140+x && mouseY >= 96+y && mouseY <= 111+y){
			if(this.labBlender.isLocked()){
				drawButtonLabel("Locked", mouseX, mouseY);
			}else{
				drawButtonLabel("Unlocked", mouseX, mouseY);
			}
		}

		//lock stacks
        for (int i = 0; i < 9; ++i){
    		if(mouseX >= 147 + x - (17*i) && mouseX <= 163 + x - (17*i) && mouseY >= 41 + y + (8*i) && mouseY <= 56 + y + (8*i)){
    			if(this.labBlender.lockList.get(i) != null){
    				drawButtonLabel(this.labBlender.lockList.get(i).getDisplayName(), mouseX, mouseY);
    			}
    		}
        }

    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.labBlender.getPower() > 0){
            int k = this.getBarScaled(50, this.labBlender.getPower(), this.labBlender.getPowerMax());
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 176, 31, 10, k);
        }

        //smelt bar
        if (this.labBlender.cookTime > 0){
            int k = this.getBarScaled(32, this.labBlender.cookTime, this.labBlender.getMaxCookTime());
            this.drawTexturedModalRect(i + 49, j + 23, 176, 0, k, 31);
        }

        //inductor
        if(this.labBlender.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 7, 176, 81, 18, 18);
        }
        
        //activation
        if(this.labBlender.isActive()){
            this.drawTexturedModalRect(i + 105, j + 96, 176, 99, 16, 16);
        }
        
        //lock
        if(this.labBlender.isLocked()){
            this.drawTexturedModalRect(i + 125, j + 96, 176, 115, 16, 16);

            //locked
            for (int col = 0; col < 9; ++col){
    			if(this.labBlender.lockList.get(col) != null){
    	            this.drawTexturedModalRect(i + 151 - (17*col), j + 32 + (8*col), 176, 131, 7, 8);
    			}
            }
        }

    }

}