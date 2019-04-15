package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPressureVessel;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPressureVessel extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guipressurevessel.png");

    private final TEPressureVessel tile;

    public UIPressureVessel(InventoryPlayer playerInv, TEPressureVessel tile){
    	super(new COPressureVessel(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEPressureVessel.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	    //tank
	    if(GuiUtils.hoveringArea(72, 40, 32, 64, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.inputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
	    if(GuiUtils.hoveringArea(23, 57, 18, 18, mouseX, mouseY, x, y)){
			String filterstring = TextFormatting.BLUE + "Gas Filter: " + TextFormatting.WHITE + "use a filled ampoule to set";
			if(!this.tile.hasFilter()){
				List<String> tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}else{
				filterstring = TextFormatting.GRAY + "Filter: " + TextFormatting.WHITE + this.tile.getFilter().getLocalizedName();
				List<String> tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
		}

		//void
	    if(GuiUtils.hoveringArea(133, 91, 18, 18, mouseX, mouseY, x, y)){
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

		//input gas
		if(this.tile.inputTankHasGas()){
			GuiUtils.renderFluidBar(this.tile.inputTank.getFluid(), this.tile.inputTank.getFluidAmount(), this.tile.inputTank.getCapacity(), i + 72, j + 40, 32, 51);
		}

		//filter
		if(this.tile.hasFilter()){
			GuiUtils.renderFluidBar(this.tile.getFilter(), 1000, 1000, i + 23, j + 57, 16, 16);
		}

    }

}