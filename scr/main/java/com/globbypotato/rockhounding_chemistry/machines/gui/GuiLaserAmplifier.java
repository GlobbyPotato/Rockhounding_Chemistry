package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserAmplifier;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLaserAmplifier extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityLaserAmplifier laserAmplifier;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guilaseramplifier.png");

	public GuiLaserAmplifier(InventoryPlayer playerInv, TileEntityLaserAmplifier tile){
		super(tile,new ContainerLaserAmplifier(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.laserAmplifier = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.laser_amplifier";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//input tank
		if(mouseX>= 76+x && mouseX <= 98+x && mouseY >= 17+y && mouseY <= 76+y){
			int airAmount = this.laserAmplifier.getPower();
			String text = TextFormatting.DARK_GRAY + "Storage: " + TextFormatting.WHITE + this.laserAmplifier.getRedstone() + "/" + this.laserAmplifier.getRedstoneMax() + " RF";
			String info = TextFormatting.DARK_GRAY + "Energy used: " + TextFormatting.RED +  this.laserAmplifier.redstoneCost() + " RF/tick";
			String[] multistring = {text, info};
			drawMultiLabel(multistring, mouseX, mouseY);
		}
	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //laser
        if (this.laserAmplifier.isPowered()){
            this.drawTexturedModalRect(i + 52, j + 87, 176, 60, 71, 2);
        }

        //air bar
        if (this.laserAmplifier.getRedstone() > 0){
            int k = this.getBarScaled(60, this.laserAmplifier.getRedstone(), this.laserAmplifier.getRedstoneMax());
            this.drawTexturedModalRect(i + 76, j + 17 + (60 - k), 176, 0, 23, k);
        }

	}
}