package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerUltraBattery;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityUltraBattery;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUltraBattery extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityUltraBattery battery;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guiultrabattery.png");

	public GuiUltraBattery(InventoryPlayer playerInv, TileEntityUltraBattery tile){
		super(tile,new ContainerUltraBattery(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.battery = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.ultrabattery";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

	   //RF area
		if((mouseX >= 120+x && mouseX <= 145+x && mouseY >= 23+y && mouseY <= 84+y)){
			String power = this.battery.getRedstone() + "/" + this.battery.getRedstoneMax() + " RF";
			drawButtonLabel(power, mouseX, mouseY);
		}

		//DOWn
		if(mouseX >= 65+x && mouseX <= 82+x && mouseY >= 67+y && mouseY <= 84+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[0]){ sideStatus = "OUT"; }
			drawButtonLabel("DOWN: " + sideStatus, mouseX, mouseY);
		}

		//UP
		if(mouseX >= 43+x && mouseX <= 60+x && mouseY >= 45+y && mouseY <= 62+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[1]){ sideStatus = "OUT"; }
			drawButtonLabel("UP: " + sideStatus, mouseX, mouseY);
		}

		//NORTH
		if(mouseX >= 43+x && mouseX <= 60+x && mouseY >= 23+y && mouseY <= 40+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[2]){ sideStatus = "OUT"; }
			drawButtonLabel("NORTH: " + sideStatus, mouseX, mouseY);
		}

		//SOUTH
		if(mouseX >= 43+x && mouseX <= 60+x && mouseY >= 67+y && mouseY <= 84+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[3]){ sideStatus = "OUT"; }
			drawButtonLabel("SOUTH: " + sideStatus, mouseX, mouseY);
		}

		//WEST
		if(mouseX >= 21+x && mouseX <= 38+x && mouseY >= 45+y && mouseY <= 62+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[4]){ sideStatus = "OUT"; }
			drawButtonLabel("WEST: " + sideStatus, mouseX, mouseY);
		}

		//EAST
		if(mouseX >= 65+x && mouseX <= 82+x && mouseY >= 45+y && mouseY <= 62+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[5]){ sideStatus = "OUT"; }
			drawButtonLabel("EAST: " + sideStatus, mouseX, mouseY);
		}

		//CHARGE
		if(mouseX >= 21+x && mouseX <= 38+x && mouseY >= 23+y && mouseY <= 40+y){
			String sideStatus = "OFF";
			if(this.battery.sideStatus[6]){ sideStatus = "ON"; }
			drawButtonLabel("CHARGE: " + sideStatus, mouseX, mouseY);
		}

	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.battery.hasRedstone()){
            int k = this.getBarScaled(60, this.battery.getRedstone(), this.battery.getRedstoneMax());
            this.drawTexturedModalRect(i + 121, j + 24 + (60 - k), 176, 32, 23, k);
        }

		// DOWN
		if(this.battery.sideStatus[0]){
			this.drawTexturedModalRect(i + 66, j + 68, 176, 16, 16, 16); //out
		}
		// UP
		if(this.battery.sideStatus[1]){
			this.drawTexturedModalRect(i + 44, j + 46, 176, 16, 16, 16); //out
		}
		// NORTH
		if(this.battery.sideStatus[2]){
			this.drawTexturedModalRect(i + 44, j + 24, 176, 16, 16, 16); //out
		}
		// SOUTH
		if(this.battery.sideStatus[3]){
			this.drawTexturedModalRect(i + 44, j + 68, 176, 16, 16, 16); //out
		}
		// WEST
		if(this.battery.sideStatus[4]){
			this.drawTexturedModalRect(i + 22, j + 46, 176, 16, 16, 16); //out
		}
		// EAST
		if(this.battery.sideStatus[5]){
			this.drawTexturedModalRect(i + 66, j + 46, 176, 16, 16, 16); //out
		}
		// CHARGE
		if(this.battery.sideStatus[6]){
			this.drawTexturedModalRect(i + 22, j + 24, 176, 0, 16, 16); //charge
		}

	 }
}