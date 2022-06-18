package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumEmitting;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEGasHolderBase;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIGasHolderBase extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guigasholder.png");

    private final TEGasHolderBase tile;

    public UIGasHolderBase(InventoryPlayer playerInv, TEGasHolderBase tile){
    	super(new COGasHolderBase(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEGasHolderBase.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;
	   String[] multiString;

		//void
	    if(GuiUtils.hoveringArea(140, 78, 18, 18, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

		//-
	    if(GuiUtils.hoveringArea(63, 95, 16, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.thres_lo_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

		//+
	    if(GuiUtils.hoveringArea(81, 95, 16, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.thres_hi_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

	    //emit type
	    if(GuiUtils.hoveringArea(45, 95, 16, 16, mouseX, mouseY, x, y)){
	    	String emitTitle = TextFormatting.WHITE + this.signal_label;
	    	String emitType = TextFormatting.GRAY + this.method_label + ": " + TextFormatting.RED + EnumEmitting.getFormals()[this.tile.getEmitType()];
			multiString = new String[]{emitTitle, emitType};
			tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

	    //tank
	    if(GuiUtils.hoveringArea(55, 29, 65, 46, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawGasTankInfo(this.tile.inputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
	    if(GuiUtils.hoveringArea(17, 78, 18, 18, mouseX, mouseY, x, y)){
			String filterstring = TextFormatting.BLUE + this.filter_label + ": " + TextFormatting.WHITE + this.ampoule_label;
			if(!this.tile.hasFilter()){
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}else{
				filterstring = TextFormatting.GRAY + this.filter_label + ": " + TextFormatting.WHITE + this.tile.getFilter().getLocalizedName();
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
		}
	    
    }

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String amount = String.valueOf(this.tile.getEmitThreashold()) + "%";
		this.fontRenderer.drawString(amount, 104, 100, 4210752);
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //emit type
        if(this.tile.getEmitType() == EnumEmitting.LEVEL.ordinal()){
    		this.drawTexturedModalRect(i + 47, j + 97, 177, 15, 12, 12);
    	}else if(this.tile.getEmitType() == EnumEmitting.ON.ordinal()){
    		this.drawTexturedModalRect(i + 47, j + 97, 177, 29, 12, 12);
    	}else if(this.tile.getEmitType() == EnumEmitting.OFF.ordinal()){
    		this.drawTexturedModalRect(i + 47, j + 97, 177, 43, 12, 12);
    	}

		//input gas
		if(this.tile.inputTankHasGas()){
			GuiUtils.renderFluidBar(this.tile.inputTank.getFluid(), this.tile.inputTank.getFluidAmount(), this.tile.inputTank.getCapacity(), i + 55, j + 29, 65, 46);
		}

		//filter
		if(this.tile.hasFilter()){
			GuiUtils.renderFluidBar(this.tile.getFilter(), 1000, 1000, i + 18, j + 79, 16, 16);
		}

    }

}