package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
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
		this.containerName = "container.metalAlloyer";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//fuel
		if(mouseX >= 11+x && mouseX <= 20+x && mouseY >= 40+y && mouseY <= 89+y){
			drawPowerInfo("ticks", this.metalAlloyer.getPower(), this.metalAlloyer.getPowerMax(), mouseX, mouseY);
		}

		//fuel status
		if(this.metalAlloyer.getInput().getStackInSlot(this.metalAlloyer.FUEL_SLOT) == null){
			   	//fuel
				String fuelString = TextFormatting.DARK_GRAY + "Fuel Type: " + TextFormatting.GOLD + "Common";
				String indString = TextFormatting.DARK_GRAY + "Induction: " + TextFormatting.RED + "OFF";
				String permaString = "";
				if(this.metalAlloyer.hasFuelBlend()){
					fuelString = TextFormatting.DARK_GRAY + "Fuel Type: " + TextFormatting.GOLD + "Blend";
				}
				if(this.metalAlloyer.canInduct()){
					indString = TextFormatting.DARK_GRAY + "Induction: " + TextFormatting.RED + "ON";
					permaString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.DARK_GREEN + "Mobile";
					if(this.metalAlloyer.hasPermanentInduction()){
						permaString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.DARK_RED + "Permanent";
					}
				}
				String multiString[] = new String[]{fuelString, "", indString, permaString};
			if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 18+y && mouseY <= 36+y){
				   drawMultiLabel(multiString, mouseX, mouseY);
			}
		}

		//prev
		if(mouseX >= 137+x && mouseX <= 153+x && mouseY >= 98+y && mouseY <= 114+y){
			drawButtonLabel("Previous Recipe", mouseX, mouseY);
		}

		//next
		if(mouseX >= 154+x && mouseX <= 168+x && mouseY >= 98+y && mouseY <= 114+y){
			drawButtonLabel("Next Recipe", mouseX, mouseY);
		}

		//activation
		if(mouseX >= 7+x && mouseX <= 23+x && mouseY >= 98+y && mouseY <= 114+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}

		//activation
        if(!this.metalAlloyer.canEqualize()){
    		if(mouseX >= 41+x && mouseX <= 58+x && mouseY >= 36+y && mouseY <= 52+y){
    			drawButtonLabel("Disabled", mouseX, mouseY);
			}
        }

        //equalizer
		if(mouseX >= 41+x && mouseX <= 58+x && mouseY >= 21+y && mouseY <= 34+y){
			drawButtonLabel("Ingredient Equalizer", mouseX, mouseY);
		}
	}

	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String name = "No Recipe";
		if(this.metalAlloyer.isValidInterval()){
			name = this.metalAlloyer.getRecipe().getAlloyName();
		}
        this.fontRendererObj.drawString(name, 26, 102, 4210752);
	}

	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.metalAlloyer.getPower() > 0){
            int k = this.getBarScaled(50, this.metalAlloyer.getPower(), this.metalAlloyer.getPowerMax());
            this.drawTexturedModalRect(i + 11, j + 40 + (50 - k), 176, 27, 10, k);
        }

        //smelt bar
        if (this.metalAlloyer.cookTime > 0){
            int k = this.getBarScaled(13, this.metalAlloyer.cookTime, this.metalAlloyer.getMaxCookTime());
            this.drawTexturedModalRect(i + 41, j + 66, 176, 0, 16, k);
        }
        
        //inductor
        if(this.metalAlloyer.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 19, 176, 79, 18, 18);
        }

		//activayion
        if(this.metalAlloyer.activation){
            this.drawTexturedModalRect(i + 7, j + 98, 176, 97, 16, 16);
        }

		//equalizer
        if(!this.metalAlloyer.canEqualize()){
            this.drawTexturedModalRect(i + 43, j + 37, 176, 13, 14, 14);
        }

	}
}