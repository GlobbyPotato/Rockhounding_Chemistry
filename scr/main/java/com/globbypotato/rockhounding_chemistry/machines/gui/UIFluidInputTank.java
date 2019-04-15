package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COFluidInputTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidInputTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIFluidInputTank extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guilabovenintank.png");

    private final TEFluidInputTank tile;

    public UIFluidInputTank(InventoryPlayer playerInv, TEFluidInputTank tile){
    	super(new COFluidInputTank(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEFluidInputTank.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//void solvent
	    if(GuiUtils.hoveringArea(26, 81, 18, 18, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//void reagent
	    if(GuiUtils.hoveringArea(132, 81, 18, 18, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
		String filterString = "";
	    if(GuiUtils.hoveringArea(26, 57, 18, 18, mouseX, mouseY, x, y)){
			if(!this.tile.isFiltered() && !this.tile.hasManualSolvent()){
				filterString = TextFormatting.BLUE + "Fluid Filter: " + TextFormatting.WHITE + "use a filled ampoule to set";			
			}
			if(this.tile.isFiltered()){
				if(this.tile.hasFilterSolvent()){
					filterString = TextFormatting.GRAY + "Filter: " + TextFormatting.AQUA + this.tile.getFilterSolvent().getLocalizedName();
				}
			}else{
				if(this.tile.hasManualSolvent()){
					filterString = TextFormatting.GRAY + "Filter: " + TextFormatting.AQUA + this.tile.getManualSolvent().getLocalizedName();
				}
			}
			List<String> tooltip = GuiUtils.drawLabel(filterString, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
	    if(GuiUtils.hoveringArea(132, 57, 18, 18, mouseX, mouseY, x, y)){
			if(!this.tile.isFiltered() && !this.tile.hasManualReagent()){
				filterString = TextFormatting.BLUE + "Fluid Filter: " + TextFormatting.WHITE + "use a filled ampoule to set";			
			}
			if(this.tile.isFiltered()){
				if(this.tile.hasFilterReagent()){
					filterString = TextFormatting.GRAY + "Filter: " + TextFormatting.AQUA + this.tile.getFilterReagent().getLocalizedName();
				}
			}else{
				if(this.tile.hasManualReagent()){
					filterString = TextFormatting.GRAY + "Filter: " + TextFormatting.AQUA + this.tile.getManualReagent().getLocalizedName();
				}
			}
			List<String> tooltip = GuiUtils.drawLabel(filterString, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		
		//input solvent
	    if(GuiUtils.hoveringArea(45, 33, 34, 66, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawFluidTankInfo(this.tile.solventTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//input reagent
	    if(GuiUtils.hoveringArea(97, 33, 34, 66, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawFluidTankInfo(this.tile.reagentTank, mouseX, mouseY);
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
			GuiUtils.renderFluidBar(this.tile.getSolventFluid(), this.tile.getSolventAmount(), this.tile.getTankCapacity(), i + 46, j + 34, 32, 64);
		}

		//input reagent
		if(this.tile.getReagentFluid() != null){
			GuiUtils.renderFluidBar(this.tile.getReagentFluid(), this.tile.getReagentAmount(), this.tile.getTankCapacity(), i + 98, j + 34, 32, 64);
		}

		if(this.tile.isFiltered()){
			if(this.tile.hasFilterSolvent()){
				GuiUtils.renderFluidBar(this.tile.getFilterSolvent(), 1000, 1000, i + 27, j + 58, 16, 16);
			}
			if(this.tile.hasFilterReagent()){
				GuiUtils.renderFluidBar(this.tile.getFilterReagent(), 1000, 1000, i + 133, j + 58, 16, 16);
			}
		}else{
			if(this.tile.hasManualSolvent()){
				GuiUtils.renderFluidBar(this.tile.getManualSolvent(), 1000, 1000, i + 27, j + 58, 16, 16);
			}
			if(this.tile.hasManualReagent()){
				GuiUtils.renderFluidBar(this.tile.getManualReagent(), 1000, 1000, i + 133, j + 58, 16, 16);
			}
        }

    }
}