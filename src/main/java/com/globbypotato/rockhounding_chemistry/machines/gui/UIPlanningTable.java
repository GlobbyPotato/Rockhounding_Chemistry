package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumStructure;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPlanningTable;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPlanningTable;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPlanningTable extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiplanningtable.png");

    private final TEPlanningTable tile;

    public UIPlanningTable(InventoryPlayer playerInv, TEPlanningTable tile){
    	super(new COPlanningTable(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.xSize = 252;
		this.containerName = "container." + TEPlanningTable.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(179, 98, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.build_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //prev
	   if(GuiUtils.hoveringArea(7, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.prev_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //next
	   if(GuiUtils.hoveringArea(25, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.next_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = this.no_recipe_label;
		int xLeft = 0, xRight = 0, yUp = 0, zFront = 0;

		if(this.tile.isValidPreset()){
			recipeLabel = EnumStructure.getStructure(this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOrder());
			xLeft = EnumStructure.getLeftBlocks(this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOrder());
			xRight = EnumStructure.getRightBlocks(this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOrder());
			yUp = EnumStructure.getUpBlocks(this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOrder());
			zFront = EnumStructure.getFrontBlocks(this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOrder());
		}

		this.fontRenderer.drawString(recipeLabel, 46, 25, 4210752);
		this.fontRenderer.drawString("+" + String.valueOf(xLeft), 177, 183, 4210752);
		this.fontRenderer.drawString("+" + String.valueOf(xRight), 239, 183, 4210752);
		this.fontRenderer.drawString("+" + String.valueOf(yUp), 203, 148, 4210752);
		this.fontRenderer.drawString("+" + String.valueOf(zFront), 235, 154, 4210752);
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if(this.tile.isValidPreset()){
        	this.drawTexturedModalRect(i + 181, j + 100, 36, 200, 12, 12);
		}
        
        for (int u = 0; u < 4; ++u){
            for (int v = 0; v < 4; ++v){
            	int slot = v + u * 4;
            	int x = 13 + v * 38;
            	int y = 40 + u * 19;
            	if(this.tile.isValidPreset() && slot < this.tile.totalInputs().size()) {
	                if(this.tile.inputSlot(slot).getCount() == this.tile.previewSlot(slot).getCount()){
	                	this.drawTexturedModalRect(i + x, j + y, 0, 200, 18, 18);
	         	   	}
            	}else {
                	this.drawTexturedModalRect(i + x, j + y, 18, 200, 18, 18);
            	}

            }
        }
    }

}