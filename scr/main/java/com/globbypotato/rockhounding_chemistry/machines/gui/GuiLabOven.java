package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;
import com.globbypotato.rockhounding_chemistry.utils.RenderUtils;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLabOven extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityLabOven labOven;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 224;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guilaboven.png");

	public GuiLabOven(InventoryPlayer playerInv, TileEntityLabOven tile){
		super(tile,new ContainerLabOven(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.labOven = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//fuel
		if(mouseX >= 10+x && mouseX <= 21+x && mouseY >= 48+y && mouseY <= 99+y){
			String text = this.labOven.getPower() + "/" + this.labOven.getPowerMax() + " ticks";
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//redstone
		if(mouseX >= 31+x && mouseX <= 42+x && mouseY >= 33+y && mouseY <= 84+y){
			String text = this.labOven.getRedstone() + "/" + this.labOven.getRedstoneMax() + " RF";
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//input tank
		if(mouseX>= 125+x && mouseX <= 146+x && mouseY >= 33+y && mouseY <= 99+y){
			int fluidAmount = 0;
			if(labOven.inputTank.getFluid() != null){
				fluidAmount = this.labOven.inputTank.getFluidAmount();
			}
			String text = fluidAmount + "/" + this.labOven.inputTank.getCapacity() + " mb ";
			String liquid = "";
			if(labOven.inputTank.getFluid() != null) liquid = labOven.inputTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text, liquid);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//reagent tank
		if(mouseX>= 147+x && mouseX <= 169+x && mouseY >= 33+y && mouseY <= 99+y){
			int fluidAmount = 0;
			if(labOven.reagentTank.getFluid() != null){
				fluidAmount = this.labOven.reagentTank.getFluidAmount();
			}
			String text = fluidAmount + "/" + this.labOven.reagentTank.getCapacity() + " mb ";
			String liquid = "";
			if(labOven.reagentTank.getFluid() != null) liquid = labOven.reagentTank.getFluid().getLocalizedName();
			List<String> tooltip = Arrays.asList(text, liquid);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//output tank
		if(mouseX>= 84+x && mouseX <= 104+x && mouseY >= 33+y && mouseY <= 99+y){
			int fluidAmount = 0;
			if(labOven.outputTank.getFluid() != null){
				fluidAmount = this.labOven.outputTank.getFluidAmount();
			}
			String text = fluidAmount + "/" + this.labOven.outputTank.getCapacity() + " mb ";
			String liquid = "";
			if(labOven.outputTank.getFluid() != null){liquid = labOven.outputTank.getFluid().getLocalizedName();}
			List<String> tooltip = Arrays.asList(text, liquid);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//prev
		if(mouseX >= 137+x && mouseX <= 153+x && mouseY >= 121+y && mouseY <= 137+y){
			List<String> tooltip = Arrays.asList("Previous Recipe");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//next
		if(mouseX >= 154+x && mouseX <= 168+x && mouseY >= 121+y && mouseY <= 137+y){
			List<String> tooltip = Arrays.asList("Next Recipe");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//activation
		if(mouseX >= 7+x && mouseX <= 23+x && mouseY >= 121+y && mouseY <= 137+y){
			List<String> tooltip = Arrays.asList("Activation");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
	}

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        String device = Translator.translateToLocal("container.labOven");
		this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 4, 4210752);
		String recipeLabel = "";
		if(this.labOven.isValidInterval()){
			recipeLabel = this.labOven.getRecipe().getOutput().getLocalizedName();
		}else{
			recipeLabel = "No Recipe";
		}
		this.fontRendererObj.drawString(recipeLabel, 26, 126, 4210752);
	}

	 @Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.labOven.powerCount > 0){
            int k = this.getBarScaled(50, this.labOven.powerCount, this.labOven.powerMax);
            this.drawTexturedModalRect(i + 11, j + 49 + (50 - k), 176, 27, 10, k);
        }
		//redstone
		if (this.labOven.redstoneCount > 0){
			int k = this.getBarScaled(50, this.labOven.redstoneCount, this.labOven.redstoneMax);
			this.drawTexturedModalRect(i + 32, j + 34 + (50 - k), 176, 81, 10, k);
		}
		//smelt bar
		int k = this.getBarScaled(15, this.labOven.cookTime, this.labOven.getCookTimeMax());
		this.drawTexturedModalRect(i + 62, j + 56, 176, 0, k, 15); //dust
		
        //activation
        if(this.labOven.activation){
            this.drawTexturedModalRect(i + 7, j + 121, 176, 172, 16, 16);
        }

		//process icons
		if(this.labOven.isCooking()){
			this.drawTexturedModalRect(i + 88, j + 102, 176, 131, 12, 14); //fire
			this.drawTexturedModalRect(i + 108, j + 61, 176, 145, 15, 9); //fluid drop
		}
		//induction icons
		if(this.labOven.hasPermanentInduction()){
			this.drawTexturedModalRect(i + 7, j + 30, 176, 154, 18, 18); //inductor
		}
		//input fluid
		if(labOven.inputTank.getFluid() != null){
			FluidStack temp = labOven.inputTank.getFluid();
			int capacity = labOven.inputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 126, j + 34, zLevel, 20, 65);
			}
		}
		//reagent fluid
		if(labOven.reagentTank.getFluid() != null){
			FluidStack temp = labOven.reagentTank.getFluid();
			int capacity = labOven.reagentTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 148, j + 34, zLevel, 20, 65);
			}
		}
		//output fluid
		if(labOven.outputTank.getFluid() != null){
			FluidStack temp = labOven.outputTank.getFluid();
			int capacity = labOven.outputTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 84, j + 34, zLevel, 20, 65);
			}
		}
	}

}