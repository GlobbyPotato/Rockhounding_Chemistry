package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COLabBlenderTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELabBlenderTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UILabBlenderTank extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guilabblendertank.png");

    private final TELabBlenderTank tile;

    public UILabBlenderTank(InventoryPlayer playerInv, TELabBlenderTank tile){
    	super(new COLabBlenderTank(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TELabBlenderTank.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//lock
	    if(GuiUtils.hoveringArea(25, 95, 18, 18, mouseX, mouseY, x, y)){
			String lockstring = "Unlocked";
			if(this.tile.isLocked()){
				lockstring = "Locked";
			}
		   List<String> tooltip = GuiUtils.drawLabel(lockstring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//lock stacks
		for (int j = 0; j < 7; ++j){
			int offsetX = 18 * j;	int offsetY = 6 * j;
		    if(GuiUtils.hoveringArea(125 - offsetX, 57 - offsetY, 18, 18, mouseX, mouseY, x, y)){
				if(!this.tile.lockList.get(j).isEmpty()){
					List<String> tooltip = GuiUtils.drawLabel(this.tile.lockList.get(j).getDisplayName(), mouseX, mouseY);
					drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
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

        //lock
        if(this.tile.isLocked()){
            this.drawTexturedModalRect(i + 27, j + 97, 176, 11, 14, 14);
        }

        //locked
        if(this.tile.lockList.size() > 0){
			for (int k = 0; k < 7; ++k){
				int offsetX = 18 * k;
				int offsetY = 6 * k;
				if(!this.tile.lockList.get(k).isEmpty() && this.tile.lockList.get(k).getItem() != null){
		            this.drawTexturedModalRect(i + 134 - offsetX, j + 58 - offsetY, 176, 0, 7, 8);
				}
	        }
        }

    }

}