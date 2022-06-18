package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.CODisposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEDisposer;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIDisposer extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guidisposer.png");

    private final TEDisposer tile;

    public UIDisposer(InventoryPlayer playerInv, TEDisposer tile){
    	super(new CODisposer(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEDisposer.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

		//lock
	    if(GuiUtils.hoveringArea(118, 41, 14, 14, mouseX, mouseY, x, y)){
			String lockstring = "Unlocked";
			if(this.tile.isLocked()){
				lockstring = "Locked";
			}
		   tooltip = GuiUtils.drawLabel(lockstring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		   //+
		   if(GuiUtils.hoveringArea(102, 77, 16, 16, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Slower: +2 ticks", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
		   //-
		   if(GuiUtils.hoveringArea(58, 77, 16, 16, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Faster: -2 ticks", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
		   //+
		   if(GuiUtils.hoveringArea(118, 77, 16, 16, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Slower: +10 ticks", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
		   //-
		   if(GuiUtils.hoveringArea(42, 77, 16, 16, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Faster: -10 ticks", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }

		//lock stacks
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
    			int offsetX = 18 * j;	int offsetY = 18 * i;
    		    if(GuiUtils.hoveringArea(62 + offsetX, 22 + offsetY, 18, 18, mouseX, mouseY, x, y)){
    				if(!this.tile.lockList.get((j + i * 3)).isEmpty()){
    					tooltip = GuiUtils.drawLabel(this.tile.lockList.get((j + i * 3)).getDisplayName(), mouseX, mouseY);
    					drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
    				}
    			}
            }
        }

    }

    @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String name = String.valueOf(this.tile.getInterval());
		this.fontRenderer.drawString(String.valueOf(this.tile.getInterval()), 77, 82, 4210752);
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//activation
        if(this.tile.isActive()){
        	if(this.tile.isPowered()){
        		this.drawTexturedModalRect(i + 81, j + 97, 190, 10, 14, 14);
        	}else{
        		this.drawTexturedModalRect(i + 81, j + 97, 176, 10, 14, 14);
        	}
        }

        //lock
        if(this.tile.isLocked()){
            this.drawTexturedModalRect(i + 118, j + 41, 177, 25, 14, 14);
        }

        //locked
        if(this.tile.lockList.size() > 0){
	        for (int col = 0; col < 3; ++col){
	            for (int row = 0; row < 3; ++row){
	    			if(!this.tile.lockList.get((col + row * 3)).isEmpty()){
	    	            this.drawTexturedModalRect(i + 62 + (18*col), j + 22 + (18*row), 176, 40, 16, 16);
	    			}
	            }
	        }
        }
    
    }

}