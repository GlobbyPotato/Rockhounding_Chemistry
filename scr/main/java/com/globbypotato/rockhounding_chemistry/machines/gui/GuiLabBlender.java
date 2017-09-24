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

    private final InventoryPlayer playerInventory;
    private final TileEntityLabBlender labBlender;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 181;

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
		if(mouseX >= 115+x && mouseX <= 130+x && mouseY >= 19+y && mouseY <= 34+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}

		//lock
		if(mouseX >= 69+x && mouseX <= 84+x && mouseY >= 75+y && mouseY <= 90+y){
			if(this.labBlender.isLocked()){
				drawButtonLabel("Locked", mouseX, mouseY);
			}else{
				drawButtonLabel("Unlocked", mouseX, mouseY);
			}
		}

		//lock stacks
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
        		if(mouseX >= 50 + x + (18*j) && mouseX <= 67 + x + (18*j) && mouseY >= 19 + y + (18*i) && mouseY <= 36 + y + (18*i)){
        			if(this.labBlender.lockList.get((j + i * 3)) != null){
        				drawButtonLabel(this.labBlender.lockList.get((j + i * 3)).getDisplayName(), mouseX, mouseY);
        			}
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
            this.drawTexturedModalRect(i + 108, j + 39, 176, 0, k, 31);
        }

        //inductor
        if(this.labBlender.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 7, 176, 81, 18, 18);
        }
        
        //activation
        if(this.labBlender.isActive()){
            this.drawTexturedModalRect(i + 115, j + 19, 176, 99, 16, 16);
        }
        
        //lock
        if(this.labBlender.isLocked()){
            this.drawTexturedModalRect(i + 69, j + 75, 176, 115, 16, 16);

            //locked
            for (int col = 0; col < 3; ++col){
                for (int row = 0; row < 3; ++row){
        			if(this.labBlender.lockList.get((col + row * 3)) != null){
        	            this.drawTexturedModalRect(i + 51 + (18*col), j + 20 + (18*row), 176, 131, 16, 16);
        			}
                }
            }
        }

    }

}