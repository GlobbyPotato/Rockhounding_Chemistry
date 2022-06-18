package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEStirredTankOut;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIStirredTankOut extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guistirredtankout.png");

    private final TEStirredTankOut tile;

    public UIStirredTankOut(InventoryPlayer playerInv, TEStirredTankOut tile){
    	super(new COStirredTankOut(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEStirredTankOut.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;

		//void solvent
	    if(GuiUtils.hoveringArea(80, 86, 16, 16, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawLabel(this.void_label, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//input solvent
    	if(GuiUtils.hoveringArea(62, 63, 52, 28, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTank, mouseX, mouseY);
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

		//output fluid
		if(this.tile.getTankFluid() != null){
			GuiUtils.renderFluidBar(this.tile.getTankFluid(), this.tile.getTankAmount(), this.tile.getTankCapacity(), i + 62, j + 53, 52, 28);
		}

    }
}