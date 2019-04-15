package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMultivessel;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIMultivessel extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimultivessel.png");

    private final TEMultivessel tile;

    public UIMultivessel(InventoryPlayer playerInv, TEMultivessel tile){
    	super(new COMultivessel(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEMultivessel.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //labels
	   if(GuiUtils.hoveringArea(22, 21, 18, 14, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Argon", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   if(GuiUtils.hoveringArea(44, 21, 18, 14, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Carbon Dioxide", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   if(GuiUtils.hoveringArea(66, 21, 18, 14, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Neon", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   if(GuiUtils.hoveringArea(88, 21, 18, 14, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Helium", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   if(GuiUtils.hoveringArea(110, 21, 18, 14, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Krypton", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   if(GuiUtils.hoveringArea(132, 21, 18, 14, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Xenon", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	    if(GuiUtils.hoveringArea(24, 39, 18, 56, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.tank_Ar, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	    if(GuiUtils.hoveringArea(46, 39, 18, 56, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.tank_CO, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	    if(GuiUtils.hoveringArea(68, 39, 18, 56, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.tank_Ne, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	    if(GuiUtils.hoveringArea(90, 39, 18, 56, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.tank_He, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	    if(GuiUtils.hoveringArea(112, 39, 18, 56, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.tank_Kr, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	    if(GuiUtils.hoveringArea(134, 39, 18, 56, mouseX, mouseY, x, y)){
			List<String> tooltip = GuiUtils.drawGasTankInfo(this.tile.tank_Xe, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	   //drain
	   if(GuiUtils.hoveringArea(24, 95, 128, 16, mouseX, mouseY, x, y)){
		   List<String> tooltip = GuiUtils.drawLabel("Drain the content from the tank", mouseX, mouseY);
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

		//drain
        if(this.tile.drainValve[0]){
            this.drawTexturedModalRect(i + 26, j + 96, 176, 0, 14, 14);
        }

        if(this.tile.drainValve[1]){
            this.drawTexturedModalRect(i + 48, j + 96, 176, 0, 14, 14);
        }

        if(this.tile.drainValve[2]){
            this.drawTexturedModalRect(i + 70, j + 96, 176, 0, 14, 14);
        }

        if(this.tile.drainValve[3]){
            this.drawTexturedModalRect(i + 92, j + 96, 176, 0, 14, 14);
        }

        if(this.tile.drainValve[4]){
            this.drawTexturedModalRect(i + 114, j + 96, 176, 0, 14, 14);
        }

        if(this.tile.drainValve[5]){
            this.drawTexturedModalRect(i + 136, j + 96, 176, 0, 14, 14);
        }

		//enabler
        if(this.tile.rareEnabler[0]){
            this.drawTexturedModalRect(i + 24, j + 23, 176, 17, 18, 14);
        }

        if(this.tile.rareEnabler[1]){
            this.drawTexturedModalRect(i + 46, j + 23, 176, 32, 18, 14);
        }

        if(this.tile.rareEnabler[2]){
            this.drawTexturedModalRect(i + 68, j + 23, 176, 47, 18, 14);
        }

        if(this.tile.rareEnabler[3]){
            this.drawTexturedModalRect(i + 90, j + 23, 176, 62, 18, 14);
        }

        if(this.tile.rareEnabler[4]){
            this.drawTexturedModalRect(i + 112, j + 23, 176, 77, 18, 14);
        }

        if(this.tile.rareEnabler[5]){
            this.drawTexturedModalRect(i + 134, j + 23, 176, 92, 18, 14);
        }

		//input gas
		if(this.tile.has_Ar()){
			GuiUtils.renderFluidBar(this.tile.tank_Ar.getFluid(), this.tile.tank_Ar.getFluidAmount(), this.tile.tank_Ar.getCapacity(), i + 25, j + 40, 16, 54);
		}
		if(this.tile.has_CO()){
			GuiUtils.renderFluidBar(this.tile.tank_CO.getFluid(), this.tile.tank_CO.getFluidAmount(), this.tile.tank_CO.getCapacity(), i + 47, j + 40, 16, 54);
		}
		if(this.tile.has_Ne()){
			GuiUtils.renderFluidBar(this.tile.tank_Ne.getFluid(), this.tile.tank_Ne.getFluidAmount(), this.tile.tank_Ne.getCapacity(), i + 69, j + 40, 16, 54);
		}
		if(this.tile.has_He()){
			GuiUtils.renderFluidBar(this.tile.tank_He.getFluid(), this.tile.tank_He.getFluidAmount(), this.tile.tank_He.getCapacity(), i + 91, j + 40, 16, 54);
		}
		if(this.tile.has_Kr()){
			GuiUtils.renderFluidBar(this.tile.tank_Kr.getFluid(), this.tile.tank_Kr.getFluidAmount(), this.tile.tank_Kr.getCapacity(), i + 113, j + 40, 16, 54);
		}
		if(this.tile.has_Xe()){
			GuiUtils.renderFluidBar(this.tile.tank_Xe.getFluid(), this.tile.tank_Xe.getFluidAmount(), this.tile.tank_Xe.getCapacity(), i + 135, j + 40, 16, 54);
		}

    }

}