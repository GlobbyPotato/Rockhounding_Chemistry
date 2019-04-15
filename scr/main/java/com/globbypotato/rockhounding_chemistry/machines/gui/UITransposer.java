package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COTransposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.TETransposer;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UITransposer extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guitransposer.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/jeitransposer.png");

    private final TETransposer tile;

    public UITransposer(InventoryPlayer playerInv, TETransposer tile){
    	super(new COTransposer(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TETransposer.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   // icons
	    if(GuiUtils.hoveringArea(59, 39, 14, 14, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.AQUA + "Transpose gases";
			tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }
	    if(GuiUtils.hoveringArea(59, 81, 14, 14, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.AQUA + "Transpose fluids";
			tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

		//void
	    if(GuiUtils.hoveringArea(146, 22, 18, 18, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.RED + "Void Tank: " + TextFormatting.WHITE + "content will be lost";
			tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }
	    if(GuiUtils.hoveringArea(146, 94, 18, 18, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.RED + "Void Tank: " + TextFormatting.WHITE + "content will be lost";
			tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }
	    if(GuiUtils.hoveringArea(94, 58, 18, 18, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.RED + "Void Tank: " + TextFormatting.WHITE + "content will be lost";
			tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

		//filter
	    if(GuiUtils.hoveringArea(24, 58, 18, 18, mouseX, mouseY, x, y)){
			String filterstring = TextFormatting.BLUE + "Fluid/Gas Filter: " + TextFormatting.WHITE + "use a filled ampoule to set";
			if(!this.tile.hasFilterMain()){
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}else{
				filterstring = TextFormatting.GRAY + "Filter: " + TextFormatting.WHITE + this.tile.getFilterMain().getLocalizedName();
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
		}

	    //activation 
		   if(GuiUtils.hoveringArea(82, 94, 18, 18, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Fluid Module Activation", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
		   if(GuiUtils.hoveringArea(82, 22, 18, 18, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Gas Module Activation", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }

		//inputs
	    if(GuiUtils.hoveringArea(44, 59, 48, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTankMain, mouseX, mouseY);
	    	if(this.tile.hasInputTankMain()){
	    		if(this.tile.getInputTankMain().getFluid().isGaseous()){
	    			tooltip = GuiUtils.drawGasTankInfo(this.tile.inputTankMain, mouseX, mouseY);
	    		}
	    	}
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//outputs
	    if(GuiUtils.hoveringArea(102, 95, 42, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawFluidTankInfo(this.tile.outputTankFluid, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
	    if(GuiUtils.hoveringArea(102, 22, 42, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawGasTankInfo(this.tile.outputTankGas, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	   //speed upgrade
	   if(GuiUtils.hoveringArea(137, 58, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(12, 89, 14, 14, mouseX, mouseY, x, y)){
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x";
		   String tf = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + "N/A";
	        if(this.tile.isFluidActive()){
	        	tf = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.transposeRate() + " mb/t";
	        }
	        if(this.tile.isGasActive()){
	        	tf = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + GuiUtils.translateMC(this.tile.transposeRate()) + " cu/t";
	        }
		   multistring = new String[]{sf, tf};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
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

        //activation
        if(this.tile.isFluidActive()){
       		this.drawTexturedModalRect(i + 84, j + 96, 176, 13, 14, 14);
        }
        if(this.tile.isGasActive()){
       		this.drawTexturedModalRect(i + 84, j + 24, 176, 13, 14, 14);
        }

		//inputs
		if(this.tile.hasInputTankMain()){
			GuiUtils.renderFluidBar(this.tile.getInputTankMain(), this.tile.getInputTankMainAmount(), this.tile.getFluidCapacity(), i + 44, j + 59, 48, 16);
		}

		//outputs
		if(this.tile.hasOutputTankFluid()){
			GuiUtils.renderFluidBar(this.tile.getOutputTankFluid(), this.tile.getOutputTankFluidAmount(), this.tile.getFluidCapacity(), i + 102, j + 95, 42, 16);
		}
		if(this.tile.hasOutputTankGas()){
			GuiUtils.renderFluidBar(this.tile.getOutputTankGas(), this.tile.getOutputTankGasAmount(), this.tile.getGasCapacity(), i + 102, j + 23, 42, 16);
		}

		//filters
		if(this.tile.hasFilterMain()){
			GuiUtils.renderFluidBar(this.tile.getFilterMain(), 1000, 1000, i + 25, j + 59, 16, 16);
		}
    }
}