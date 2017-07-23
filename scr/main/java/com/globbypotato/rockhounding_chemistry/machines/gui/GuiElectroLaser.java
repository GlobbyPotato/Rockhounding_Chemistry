package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityElectroLaser;
import com.globbypotato.rockhounding_core.utils.RenderUtils;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiElectroLaser extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityElectroLaser electroLaser;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guielectrolaser.png");

	public GuiElectroLaser(InventoryPlayer playerInv, TileEntityElectroLaser tile){
		super(tile,new ContainerElectroLaser(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.electroLaser = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
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
			List<String> tooltip = Arrays.asList(multistring);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}

		//add details about power 
		
		//input tank
		if(mouseX>= 145+x && mouseX <= 167+x && mouseY >= 17+y && mouseY <= 76+y){
			int fluidAmount = 0;
			if(electroLaser.inputTank.getFluid() != null){
				fluidAmount = this.electroLaser.inputTank.getFluidAmount();
			}
			String text = fluidAmount + "/" + this.electroLaser.inputTank.getCapacity() + " mb ";
			String liquid = "";
			if(electroLaser.inputTank.getFluid() != null) liquid = electroLaser.inputTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text, liquid);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.electroLaser");
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 4, 4210752);
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
		if(electroLaser.inputTank.getFluid() != null){
			FluidStack temp = electroLaser.inputTank.getFluid();
			int capacity = electroLaser.inputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 145, j + 17, zLevel, 23, 60);
			}
		}
	}

}