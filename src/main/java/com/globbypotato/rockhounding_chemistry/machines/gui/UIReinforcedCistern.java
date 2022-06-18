package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COReinforcedCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEReinforcedCistern;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIReinforcedCistern extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guireinforcedcistern.png");

    private final TEReinforcedCistern tile;

    public UIReinforcedCistern(InventoryPlayer playerInv, TEReinforcedCistern tile){
    	super(new COReinforcedCistern(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEReinforcedCistern.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//input fluid
	    if(GuiUtils.hoveringArea(59, 35, 58, 68, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
	    if(GuiUtils.hoveringArea(28, 47, 16, 16, mouseX, mouseY, x, y)){
			String filterstring = TextFormatting.BLUE + this.filter_label + ": " + TextFormatting.WHITE + this.ampoule_label;
			if(!this.tile.hasFilter()){
				List<String> tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}else{
				filterstring = TextFormatting.GRAY + this.filter_label + ": " + TextFormatting.WHITE + this.tile.getFilter().getLocalizedName();
				List<String> tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
		}

		//void
	    if(GuiUtils.hoveringArea(120, 86, 18, 18, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//input fluid
		if(this.tile.hasInputFluid()){
			GuiUtils.renderFluidBar(this.tile.getInputFluid(), this.tile.getInputAmount(), this.tile.getTankCapacity(), i + 96, j + 37, 10, 38);
		}

		//filter
		if(this.tile.hasFilter()){
			GuiUtils.renderFluidBar(this.tile.getFilter(), 1000, 1000, i + 28, j + 47, 16, 16);
		}

    }
}