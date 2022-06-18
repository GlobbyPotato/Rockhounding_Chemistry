package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIFlotationTank extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiflotationtank.png");

    private final TEFlotationTank tile;

    public UIFlotationTank(InventoryPlayer playerInv, TEFlotationTank tile){
    	super(new COFlotationTank(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEFlotationTank.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//void solvent
	    if(GuiUtils.hoveringArea(79, 95, 18, 18, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
		String filterString = "";
	    if(GuiUtils.hoveringArea(80, 22, 16, 16, mouseX, mouseY, x, y)){
			if(!this.tile.isFiltered() && !this.tile.hasManualSolvent()){
				filterString = TextFormatting.BLUE + this.filter_label + ": " + TextFormatting.WHITE + this.ampoule_label;			
			}
			if(this.tile.isFiltered()){
				if(this.tile.hasFilterSolvent()){
					filterString = TextFormatting.GRAY + this.filter_label + ": " + TextFormatting.AQUA + this.tile.getFilterSolvent().getLocalizedName();
				}
			}else{
				if(this.tile.hasManualSolvent()){
					filterString = TextFormatting.GRAY + this.filter_label + ": " + TextFormatting.AQUA + this.tile.getManualSolvent().getLocalizedName();
				}
			}
			List<String> tooltip = GuiUtils.drawLabel(filterString, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		
		//input solvent
	    if(GuiUtils.hoveringArea(62, 43, 52, 28, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTank, mouseX, mouseY);
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

		//input solvent
		if(this.tile.getSolventFluid() != null){
			GuiUtils.renderFluidBar(this.tile.getSolventFluid(), this.tile.getSolventAmount(), this.tile.getTankCapacity(), i + 62, j + 43, 52, 28);
		}

		if(this.tile.isFiltered()){
			if(this.tile.hasFilterSolvent()){
				GuiUtils.renderFluidBar(this.tile.getFilterSolvent(), 1000, 1000, i + 80, j + 22, 16, 16);
			}
		}else{
			if(this.tile.hasManualSolvent()){
				GuiUtils.renderFluidBar(this.tile.getManualSolvent(), 1000, 1000, i + 80, j + 22, 16, 16);
			}
        }
    }
}