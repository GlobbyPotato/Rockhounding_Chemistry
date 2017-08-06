package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityElectroLaser;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiElectroLaser extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityElectroLaser electroLaser;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guielectrolaser.png");
	private FluidTank inputTank;

	public GuiElectroLaser(InventoryPlayer playerInv, TileEntityElectroLaser tile){
		super(tile,new ContainerElectroLaser(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.electroLaser = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.inputTank = this.electroLaser.inputTank;
		this.containerName = "container.electroLaser";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//redstone
		if(mouseX >= 8+x && mouseX <= 30+x && mouseY >= 17+y && mouseY <= 76+y){
			String text = TextFormatting.DARK_GRAY + "Storage: " + TextFormatting.WHITE + this.electroLaser.getRedstone() + "/" + this.electroLaser.getRedstoneMax() + " RF";
			String info = TextFormatting.DARK_GRAY + "Energy used: " + TextFormatting.RED +  this.electroLaser.redstoneCost() + " RF/tick";
			String nitro = TextFormatting.DARK_GRAY + "Nitrogen used: " + TextFormatting.GRAY +  this.electroLaser.consumedNitrogen() + " mB/tick";
			String power = TextFormatting.DARK_GRAY + "Ray Power: " + TextFormatting.AQUA +  (this.electroLaser.getStage() + 1) + "x";
			String damage = TextFormatting.DARK_GRAY + "Damage: " + TextFormatting.GOLD +  this.electroLaser.getDamage() + "HP/tick";
			String[] multistring = {text, info, nitro, power, damage};
			drawMultiLabel(multistring, mouseX, mouseY);
		}

		//input tank
		if(mouseX>= 145+x && mouseX <= 167+x && mouseY >= 17+y && mouseY <= 76+y){
			drawTankInfo(this.inputTank, mouseX, mouseY);
		}
	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //redstone
        if (this.electroLaser.getRedstone() > 0){
            int k = this.getBarScaled(60, this.electroLaser.getRedstone(), this.electroLaser.getRedstoneMax());
            this.drawTexturedModalRect(i + 8, j + 17 + (60 - k), 176, 0, 23, k);
        }

        //laser
        if (this.electroLaser.isPowered()){
            this.drawTexturedModalRect(i + 13, j + 58, 176, 73, 37, 32);
        }

        //ray
        if (this.electroLaser.isEmitting()){
            this.drawTexturedModalRect(i + 58, j + 42, 176, 60, 80, 12);
        }

		//input fluid
		if(this.inputTank.getFluid() != null){
			renderFluidBar(this.inputTank.getFluid(), this.inputTank.getCapacity(), i + 145, j + 17, 23, 60);
		}
	}

}