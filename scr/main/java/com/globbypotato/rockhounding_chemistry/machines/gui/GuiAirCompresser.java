package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAirCompresser extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityAirCompresser airCompresser;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guiaircompresser.png");

	public GuiAirCompresser(InventoryPlayer playerInv, TileEntityAirCompresser tile){
		super(tile,new ContainerAirCompresser(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.airCompresser = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//input tank
		if(mouseX>= 76+x && mouseX <= 98+x && mouseY >= 17+y && mouseY <= 76+y){
			int airAmount = this.airCompresser.getAir();
			String name = "Compressed Air";
			String text = airAmount + "/" + this.airCompresser.getAirMax() + " units";
			String[] multistring = {name, text};
			List<String> tooltip = Arrays.asList(multistring);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.airCompresser");
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 4, 4210752);
	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //air bar
        if (this.airCompresser.getAir() > 0){
            int k = this.getBarScaled(60, this.airCompresser.getAir(), this.airCompresser.getAirMax());
            this.drawTexturedModalRect(i + 76, j + 17 + (60 - k), 176, 0, 23, k);
        }

	}

}