package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLabOven extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityLabOven labOven;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 224;
	public static final ResourceLocation TEXTURE_REF =  new ResourceLocation(Reference.MODID + ":textures/gui/guilaboven.png");
	private FluidTank solventTank;
	private FluidTank reagentTank;
	private FluidTank outputTank;

	public GuiLabOven(InventoryPlayer playerInv, TileEntityLabOven tile){
		super(tile,new ContainerLabOven(playerInv, tile));
		this.playerInventory = playerInv;
		TEXTURE = TEXTURE_REF;
		this.labOven = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.solventTank = this.labOven.solventTank;
		this.reagentTank = this.labOven.reagentTank;
		this.outputTank = this.labOven.outputTank;
		this.containerName = "container.lab_oven";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//fuel
		if(mouseX >= 10+x && mouseX <= 21+x && mouseY >= 27+y && mouseY <= 78+y){
			drawPowerInfo("ticks", this.labOven.getCookTimeMax(), this.labOven.getPower(), this.labOven.getPowerMax(), mouseX, mouseY);
		}

		//fuel status
		String[] fuelstatusString = handleFuelStatus(this.labOven.isFuelGated(), this.labOven.hasFuelBlend(), this.labOven.canInduct(), this.labOven.allowPermanentInduction());
		if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 7+y && mouseY <= 24+y){
			drawMultiLabel(fuelstatusString, mouseX, mouseY);
		}

		//redstone
		if(!this.labOven.hasFuelBlend()){
			if(mouseX >= 31+x && mouseX <= 42+x && mouseY >= 27+y && mouseY <= 78+y){
				drawEnergyInfo("RF", this.labOven.getRedstone(), this.labOven.getRedstoneMax(), mouseX, mouseY);
			}
		}

		//solvent tank
		if(mouseX>= 125+x && mouseX <= 146+x && mouseY >= 33+y && mouseY <= 99+y){
			drawTankInfo(this.solventTank, mouseX, mouseY);
		}

		//reagent tank
		if(mouseX>= 147+x && mouseX <= 169+x && mouseY >= 33+y && mouseY <= 99+y){
			drawTankInfo(this.reagentTank, mouseX, mouseY);
		}

		//output tank
		if(mouseX>= 84+x && mouseX <= 104+x && mouseY >= 33+y && mouseY <= 99+y){
			drawTankInfo(this.outputTank, mouseX, mouseY);
		}

		//prev
		if(mouseX >= 137+x && mouseX <= 153+x && mouseY >= 121+y && mouseY <= 137+y){
			drawButtonLabel("Previous Recipe", mouseX, mouseY);
		}

		//next
		if(mouseX >= 154+x && mouseX <= 168+x && mouseY >= 121+y && mouseY <= 137+y){
			drawButtonLabel("Next Recipe", mouseX, mouseY);
		}

		//activation
		if(mouseX >= 7+x && mouseX <= 23+x && mouseY >= 121+y && mouseY <= 137+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String recipeLabel = "No Recipe";
		if(this.labOven.isValidInterval()){
			recipeLabel = this.labOven.getRecipe().getOutput().getLocalizedName();
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
        if (this.labOven.getPower() > 0){
            int k = this.getBarScaled(50, this.labOven.getPower(), this.labOven.getPowerMax());
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 176, 27, 10, k);
        }

		//redstone
		if(!this.labOven.hasFuelBlend()){
			if (this.labOven.getRedstone() > 0){
				int k = this.getBarScaled(50, this.labOven.getRedstone(), this.labOven.getRedstoneMax());
				this.drawTexturedModalRect(i + 32, j + 28 + (50 - k), 176, 81, 10, k);
			}
		}

		//smelt bar
		int k = this.getBarScaled(15, this.labOven.cookTime, this.labOven.getCookTimeMax());
		this.drawTexturedModalRect(i + 62, j + 56, 176, 0, k, 15); //dust
		
        //activation
        if(this.labOven.isActive()){
            this.drawTexturedModalRect(i + 7, j + 121, 176, 172, 16, 16);
        }

		//process icons
		if(this.labOven.isCooking()){
			this.drawTexturedModalRect(i + 88, j + 102, 176, 131, 12, 14); //fire
			this.drawTexturedModalRect(i + 108, j + 61, 176, 145, 15, 9); //fluid drop
		}

		//induction icons
		if(this.labOven.hasPermanentInduction()){
			this.drawTexturedModalRect(i + 7, j + 7, 176, 154, 18, 18); //inductor
		}

		//blend fix
		if(this.labOven.hasFuelBlend()){
			this.drawTexturedModalRect(i + 25, j + 7, 224, 0, 21, 92); //blend
		}

		//solvent fluid
		if(this.solventTank.getFluid() != null){
			renderFluidBar(this.solventTank.getFluid(), this.solventTank.getCapacity(), i + 126, j + 34, 20, 65);
		}

		//reagent fluid
		if(this.reagentTank.getFluid() != null){
			renderFluidBar(this.reagentTank.getFluid(), this.reagentTank.getCapacity(), i + 148, j + 34, 20, 65);
		}

		//output fluid
		if(this.outputTank.getFluid() != null){
			renderFluidBar(this.outputTank.getFluid(), this.outputTank.getCapacity(), i + 84, j + 34, 20, 65);
		}
	}

}