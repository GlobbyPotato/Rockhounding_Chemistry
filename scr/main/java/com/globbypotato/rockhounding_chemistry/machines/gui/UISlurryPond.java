package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COSlurryPond;
import com.globbypotato.rockhounding_chemistry.machines.tile.TESlurryPond;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UISlurryPond extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guislurrypond.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guislurrypondjei.png");

    private final TESlurryPond tile;

    public UISlurryPond(InventoryPlayer playerInv, TESlurryPond tile){
    	super(new COSlurryPond(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TESlurryPond.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

		//void in
	    if(GuiUtils.hoveringArea(8, 27, 16, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//void out
	    if(GuiUtils.hoveringArea(51, 92, 16, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//filter
	    if(GuiUtils.hoveringArea(119, 27, 16, 16, mouseX, mouseY, x, y)){
			String filterstring = TextFormatting.BLUE + "Fluid Filter: " + TextFormatting.WHITE + "use a filled ampoule to set";
			if(!this.tile.hasFilter()){
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}else{
				filterstring = TextFormatting.GRAY + "Filter: " + TextFormatting.WHITE + this.tile.getFilter().getLocalizedName();
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
		}

		//input fluid
	    if(GuiUtils.hoveringArea(28, 27, 67, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

		//output fluid
	    if(GuiUtils.hoveringArea(71, 92, 67, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawFluidTankInfo(this.tile.outputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//conc-
		String concstring = TextFormatting.GRAY + "Current Concentration: " + TextFormatting.GOLD + this.tile.actualConcentration() + "%";
	    if(GuiUtils.hoveringArea(109, 61, 16, 16, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.WHITE + "Increase Concentration";
			multistring = new String[]{voidstring, "", concstring};
			tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		//conc+
	    if(GuiUtils.hoveringArea(145, 61, 16, 16, mouseX, mouseY, x, y)){
			String voidstring = TextFormatting.WHITE + "Decrease Concentration";
			multistring = new String[]{voidstring, "", concstring};
			tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		//conc
	    if(GuiUtils.hoveringArea(105, 79, 60, 6, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(concstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		//conc
	    if(GuiUtils.hoveringArea(126, 60, 18, 19, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(concstring, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	   //monitor
	   if(GuiUtils.hoveringArea(15, 86, 14, 14, mouseX, mouseY, x, y)){
		   String tk = TextFormatting.GRAY + "Process Time: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   String yd = TextFormatting.GRAY + "Slurry Yield: " + TextFormatting.RED + "Not available"; 
		   String us = TextFormatting.GRAY + "Consumed Solvent: " + TextFormatting.RED + " Not available"; 
		   String so = TextFormatting.GRAY + "Consumed Solute: " + TextFormatting.DARK_GREEN + this.tile.calculateInputAmount() + "x"; 

		   if(this.tile.calculateSolventAmount() > 0){
			   us = TextFormatting.GRAY + "Consumed Solvent: " + TextFormatting.GREEN + this.tile.calculateSolventAmount() + " mB";
		   }
		   if(this.tile.calculateSlurryAmount() > 0){
			   yd = TextFormatting.GRAY + "Slurry Yield: " + TextFormatting.GREEN + this.tile.calculateSlurryAmount() + " mB";
		   }

		   multistring = new String[]{concstring, tk, "", so, us, yd};
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

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(24, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 80, j + 58, 176, 43, 22, k);
        }

        //conc bar
        if (this.tile.getConcentration() > 0){
            int k = 6 * this.tile.getConcentration();
            this.drawTexturedModalRect(i + 105, j + 79, 177, 79, k, 6);
        }

		//input fluid
		if(this.tile.hasInputFluid()){
			GuiUtils.renderFluidBar(this.tile.getInputFluid(), this.tile.getInputAmount(), this.tile.getTankCapacity(), i + 28, j + 27, 67, 16);
		}

		//output fluid
		if(this.tile.hasOutputFluid()){
			GuiUtils.renderFluidBar(this.tile.getOutputFluid(), this.tile.getOutputAmount(), this.tile.getTankCapacity(), i + 71, j + 92, 67, 16);
		}

		//filter
		if(this.tile.hasFilter()){
			GuiUtils.renderFluidBar(this.tile.getFilter(), 1000, 1000, i + 119, j + 27, 16, 16);
		}

    }
}
