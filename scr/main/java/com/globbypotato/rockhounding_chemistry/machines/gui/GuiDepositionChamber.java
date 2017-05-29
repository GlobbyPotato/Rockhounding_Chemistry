package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDepositionChamber;
import com.globbypotato.rockhounding_chemistry.utils.RenderUtils;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDepositionChamber extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityDepositionChamber depositionChamber;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 214;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guidepositionchamber.png");
	public static final ResourceLocation TEXTURE_JEI =  new ResourceLocation(Reference.MODID + ":textures/gui/guidepositionchamberjei.png");

	public GuiDepositionChamber(InventoryPlayer playerInv, TileEntityDepositionChamber tile){
		super(tile,new ContainerDepositionChamber(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.depositionChamber = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//temperature
		if(mouseX >= 8+x && mouseX <= 17+x && mouseY >= 31+y && mouseY <= 95+y){
			String text = TextFormatting.GOLD + "Temperature: " + TextFormatting.WHITE + (30 + this.depositionChamber.getTemperature()) + " 'C";
			String req = TextFormatting.DARK_GRAY + "Required: " + TextFormatting.WHITE + this.depositionChamber.getRecipe().getTemperature() + " 'C";
			String info = TextFormatting.DARK_GRAY + "Charge: " + TextFormatting.WHITE + this.depositionChamber.tempYeld() +" 'C/tick";
			String drain = TextFormatting.DARK_GRAY + "Drain: " + TextFormatting.WHITE + this.depositionChamber.takenRF + " RF/charge";
			List<String> tooltip = Arrays.asList(text, req, info, drain);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//preassure
		if(mouseX >= 23+x && mouseX <= 33+x && mouseY >= 17+y && mouseY <= 81+y){
			String text = TextFormatting.BLUE + "Pressure: " + TextFormatting.WHITE + this.depositionChamber.getPressure() + " uPa";
			String req = TextFormatting.DARK_GRAY + "Required: " + TextFormatting.WHITE + this.depositionChamber.getRecipe().getPressure() + " uPa";
			String info = TextFormatting.DARK_GRAY + "Charge: " + TextFormatting.WHITE + this.depositionChamber.pressYeld() +" uPa/tick";
			String drain = TextFormatting.DARK_GRAY + "Drain: " + TextFormatting.WHITE + this.depositionChamber.takenRF + " RF/charge";
			List<String> tooltip = Arrays.asList(text, req, info, drain);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//input tank
		if(mouseX>= 148+x && mouseX <= 167+x && mouseY >= 31+y && mouseY <= 95+y){
			int fluidAmount = 0;
			if(depositionChamber.inputTank.getFluid() != null){
				fluidAmount = this.depositionChamber.inputTank.getFluidAmount();
			}
			String text = fluidAmount + "/" + this.depositionChamber.inputTank.getCapacity() + " mb ";
			String liquid = "";
			if(depositionChamber.inputTank.getFluid() != null) liquid = depositionChamber.inputTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text, liquid);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//prev
		if(mouseX >= 137+x && mouseX <= 152+x && mouseY >= 113+y && mouseY <= 125+y){
			List<String> tooltip = Arrays.asList("Previous Recipe");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//next
		if(mouseX >= 153+x && mouseX <= 167+x && mouseY >= 113+y && mouseY <= 125+y){
			List<String> tooltip = Arrays.asList("Next Recipe");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//activation
		if(mouseX >= 7+x && mouseX <= 23+x && mouseY >= 113+y && mouseY <= 125+y){
			List<String> tooltip = Arrays.asList("Activation");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.depositionChamber");
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 4, 4210752);
		String recipeLabel = "";
		if(this.depositionChamber.isValidInterval()){
			recipeLabel = this.depositionChamber.getRecipe().getOutput().getDisplayName();
		}else{
			recipeLabel = "No Recipe";
		}
		this.fontRendererObj.drawString(recipeLabel, 26, 116, 4210752);
	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //temperature bar
        if (this.depositionChamber.temperatureCount > 0){
            int k = this.getBarScaled(65, this.depositionChamber.temperatureCount, this.depositionChamber.temperatureMax);
            this.drawTexturedModalRect(i + 8, j + 31 + (65 - k), 176, 0, 10, k);
        }
		//pressure bar
		if (this.depositionChamber.pressureCount > 0){
			int k = this.getBarScaled(65, this.depositionChamber.pressureCount, this.depositionChamber.pressureMax);
			this.drawTexturedModalRect(i + 23, j + 17 + (65 - k), 176, 65, 10, k);
		}
		//smelt bar
		int k = this.getBarScaled(26, this.depositionChamber.cookTime, this.depositionChamber.getCookTimeMax());
		this.drawTexturedModalRect(i + 58, j + 29, 190, 4, 60, k); //dust
        //activation
        if(this.depositionChamber.activation){
            this.drawTexturedModalRect(i + 7, j + 111, 176, 130, 16, 16);
        }

		//input fluid
		if(depositionChamber.inputTank.getFluid() != null){
			FluidStack temp = depositionChamber.inputTank.getFluid();
			int capacity = depositionChamber.inputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 148, j + 31, zLevel, 20, 65);
			}
		}
	}

}