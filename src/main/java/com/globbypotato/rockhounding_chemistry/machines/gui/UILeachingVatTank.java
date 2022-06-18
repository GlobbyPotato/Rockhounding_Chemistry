package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COLeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UILeachingVatTank extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guileachingvattank.png");

    private final TELeachingVatTank tile;

    public UILeachingVatTank(InventoryPlayer playerInv, TELeachingVatTank tile){
    	super(new COLeachingVatTank(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TELeachingVatTank.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//input fluid
	    if(GuiUtils.hoveringArea(68, 49, 40, 51, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
	    if(GuiUtils.hoveringArea(18, 66, 18, 18, mouseX, mouseY, x, y)){
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
	    if(GuiUtils.hoveringArea(138, 86, 18, 18, mouseX, mouseY, x, y)){
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
		if(this.tile.inputTank.getFluid() != null){
			GuiUtils.renderFluidBar(this.tile.inputTank.getFluid(), this.tile.inputTank.getFluidAmount(), this.tile.inputTank.getCapacity(), i + 68, j + 49, 40, 51);
		}

		//filter
		if(this.tile.hasFilter()){
			GuiUtils.renderFluidBar(this.tile.getFilter(), 1000, 1000, i + 19, j + 67, 16, 16);
		}

    }
}