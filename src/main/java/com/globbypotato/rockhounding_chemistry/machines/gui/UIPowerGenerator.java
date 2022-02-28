package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPowerGenerator;
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
	   if(GuiUtils.hoveringArea(81, 97, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("RF Generator Mode", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel module
	   if(GuiUtils.hoveringArea(52, 28, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Fuel Module", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //redstone module
	   if(GuiUtils.hoveringArea(110, 28, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Redstone Module", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel
	   if(GuiUtils.hoveringArea(25, 51, 25, 62, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawStorage(TextFormatting.GOLD, "ticks", TextFormatting.YELLOW, this.tile.getCooktime(), this.tile.getPower(), this.tile.getPowerMax(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fuel status
	   if(GuiUtils.hoveringArea(7, 95, 18, 18, mouseX, mouseY, x, y)){
		   String[] fuelstatusString = GuiUtils.handleFuelStatus(this.tile.isFuelGated(), this.tile.canInduct(), this.tile.allowPermanentInduction());
		   tooltip = GuiUtils.drawMultiLabel(fuelstatusString, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //redstone
	   if(GuiUtils.hoveringArea(126, 51, 25, 62, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawStorage(TextFormatting.RED, "RF", TextFormatting.DARK_RED, 0, this.tile.getRedstone(), this.tile.getRedstoneMax(), mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reservoir lava
	   if(GuiUtils.hoveringArea(81, 60, 6, 34, mouseX, mouseY, x, y)){
		   String content = TextFormatting.GRAY + "Internal Buffer: " + TextFormatting.GOLD + TextFormatting.BOLD + "Lava";
		   String amount = TextFormatting.GOLD + "" + this.tile.lavaTank.getFluidAmount() + "/" + this.tile.lavaTank.getCapacity() + " mB";
		   multistring = new String[]{content, amount};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //reservoir syngas
	   if(GuiUtils.hoveringArea(89, 60, 6, 34, mouseX, mouseY, x, y)){
		   String content = TextFormatting.GRAY + "Internal Buffer: " + TextFormatting.WHITE + TextFormatting.BOLD + "Syngas";
		   String amount = TextFormatting.WHITE + "" + GuiUtils.translateMC(this.tile.gasTank.getFluidAmount()) + "/" + GuiUtils.translateMC(this.tile.gasTank.getCapacity()) + " cu";
		   multistring = new String[]{content, amount};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //info
	   if(ModConfig.hasInfo){
		   if(GuiUtils.hoveringArea(154, 81, 12, 12, mouseX, mouseY, x, y)){
			   String text = "Takes Redstone dust or blocks";
			   String text2 = "The Gas Turbine turns Syngas into RF";
			   multistring = new String[]{text, text2};
			   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	
		   if(GuiUtils.hoveringArea(10, 81, 12, 12, mouseX, mouseY, x, y)){
			   String text = "Takes coal, lava or any burnable item";
			   String text2 = "Syngas turns into fuel power";
			   String text3 = "The Induction Heating Interface turns external RF into fuel power";
			   multistring = new String[]{text, text2, text3};
			   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

 	   //info
 	   if(ModConfig.hasInfo){
   		this.drawTexturedModalRect(i + 10, j + 81,  242, 12, 12, 12);
		this.drawTexturedModalRect(i + 154, j + 81, 242, 12, 12, 12);
 	   }

		//activation
        if(this.tile.isActive()){
       		this.drawTexturedModalRect(i + 81, j + 97, 204, 152, 14, 14);
        }

        //power module
        if (this.tile.enablePower()){
            this.drawTexturedModalRect(i + 52, j + 28, 176, 152, 14, 14);
        }

        //redstone module
        if (this.tile.enableRedstone()){
            this.drawTexturedModalRect(i + 110, j + 28, 190, 152, 14, 14);
        }

        //power bar
        if (this.tile.getPower() > 0){
            int k = GuiUtils.getScaledValue(60, this.tile.getPower(), this.tile.getPowerMax());
            this.drawTexturedModalRect(i + 26, j + 52+(60-k), 176, 29, 23, k);
        }

        //inductor
        if(this.tile.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 8, j + 96, 211, 10, 16, 16);
        }

        //redstone bar
        if (this.tile.getRedstone() > 0){
            int k = GuiUtils.getScaledValue(60, this.tile.getRedstone(), this.tile.getRedstoneMax());
            this.drawTexturedModalRect(i + 127, j + 52+(60-k), 199, 29, 23, k);
        }

        //reservoir bar
        if (this.tile.lavaTank.getFluidAmount() > 0){
            int k = GuiUtils.getScaledValue(32, this.tile.lavaTank.getFluidAmount(), this.tile.lavaTank.getCapacity());
            this.drawTexturedModalRect(i + 82, j + 61+(32-k), 176, 93, 4, k);
        }
        if (this.tile.gasTank.getFluidAmount() > 0){
            int k = GuiUtils.getScaledValue(32, this.tile.gasTank.getFluidAmount(), this.tile.gasTank.getCapacity());
            this.drawTexturedModalRect(i + 90, j + 61+(32-k), 180, 93, 4, k);
        }

    }

}