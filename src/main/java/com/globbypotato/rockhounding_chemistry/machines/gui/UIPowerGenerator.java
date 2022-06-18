package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPowerGenerator extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guipowergenerator.png");

    private final TEPowerGenerator tile;

    public UIPowerGenerator(InventoryPlayer playerInv, TEPowerGenerator tile){
    	super(new COPowerGenerator(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEPowerGenerator.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(153, 98, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.generator_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel module
	   if(GuiUtils.hoveringArea(153, 26, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.fuel_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //redstone module
	   if(GuiUtils.hoveringArea(153, 44, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.redstone_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel
	   if(GuiUtils.hoveringArea(28, 51, 12, 61, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawStorage(TextFormatting.GOLD, "ticks", TextFormatting.YELLOW, this.tile.getCooktime(), this.tile.getPower(), this.tile.getPowerMax(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //redstone
	   if(GuiUtils.hoveringArea(126, 48, 12, 61, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawStorage(TextFormatting.RED, "RF", TextFormatting.DARK_RED, 0, this.tile.getRedstone(), this.tile.getRedstoneMax(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //induction
	   if(GuiUtils.hoveringArea(66, 86, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(BaseRecipes.heat_inductor.getDisplayName(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //turbine
	   if(GuiUtils.hoveringArea(97, 42, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(BaseRecipes.gas_turbine.getDisplayName(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //voltage
	   if(GuiUtils.hoveringArea(87, 102, 20, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.voltage_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //burnable
	   if(GuiUtils.hoveringArea(26, 24, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.burnable_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reddust
	   if(GuiUtils.hoveringArea(124, 24, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.reddust_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reservoir lava
	   if(GuiUtils.hoveringArea(8, 50, 10, 33, mouseX, mouseY, x, y)){
		   String content = TextFormatting.GRAY + this.buffer_label + ": " + TextFormatting.GOLD + TextFormatting.BOLD + "Lava";
		   String amount = TextFormatting.GOLD + "" + this.tile.lavaTank.getFluidAmount() + "/" + this.tile.lavaTank.getCapacity() + " mB";
		   multistring = new String[]{content, amount};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reservoir syngas
	   if(GuiUtils.hoveringArea(73, 25, 10, 33, mouseX, mouseY, x, y)){
		   String content = TextFormatting.GRAY + this.buffer_label + ": " + TextFormatting.WHITE + TextFormatting.BOLD + "Syngas";
		   String amount = TextFormatting.WHITE + "" + GuiUtils.translateMC(this.tile.gasTank.getFluidAmount()) + "/" + GuiUtils.translateMC(this.tile.gasTank.getCapacity()) + " cu";
		   multistring = new String[]{content, amount};
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
        if(this.tile.isActive()){
       		this.drawTexturedModalRect(i + 153, j + 98, 232, 0, 14, 14);
        }

        //power module
        if (this.tile.enablePower()){
            this.drawTexturedModalRect(i + 153, j + 26, 204, 0, 14, 14);
        }

        //redstone module
        if (this.tile.enableRedstone()){
            this.drawTexturedModalRect(i + 153, j + 44, 218, 0, 14, 14);
        }

        //power bar
        if (this.tile.getPower() > 0){
            int k = GuiUtils.getScaledValue(59, this.tile.getPower(), this.tile.getPowerMax());
            this.drawTexturedModalRect(i + 29, j + 52+(59-k), 176, 14, 10, k);
        }

        //redstone bar
        if (this.tile.getRedstone() > 0){
            int k = GuiUtils.getScaledValue(59, this.tile.getRedstone(), this.tile.getRedstoneMax());
            this.drawTexturedModalRect(i + 127, j + 52+(59-k), 186, 14, 10, k);
        }

        //lava reservoir bar
        if (this.tile.lavaTank.getFluidAmount() > 0){
            int k = GuiUtils.getScaledValue(33, this.tile.lavaTank.getFluidAmount(), this.tile.lavaTank.getCapacity());
            this.drawTexturedModalRect(i + 8, j + 51+(32-k), 196, 14, 10, k);
        }

        //syngas reservoir bar
        if (this.tile.gasTank.getFluidAmount() > 0){
            int k = GuiUtils.getScaledValue(33, this.tile.gasTank.getFluidAmount(), this.tile.gasTank.getCapacity());
            this.drawTexturedModalRect(i + 73, j + 25+(33-k), 206, 14, 10, k);
        }

    }

}