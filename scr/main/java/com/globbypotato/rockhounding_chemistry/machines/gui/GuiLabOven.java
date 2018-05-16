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
		if(mouseX >= 10+x && mouseX <= 21+x && mouseY >= 28+y && mouseY <= 79+y){
			drawPowerInfo("ticks", this.labOven.getCookTimeMax(), this.labOven.getPower(), this.labOven.getPowerMax(), mouseX, mouseY);
		}

		//fuel status
		String[] fuelstatusString = handleFuelStatus(this.labOven.isFuelGated(), this.labOven.hasFuelBlend(), this.labOven.canInduct(), this.labOven.allowPermanentInduction());
		if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 81+y && mouseY <= 98+y){
			drawMultiLabel(fuelstatusString, mouseX, mouseY);
		}

		//redstone
		if(!this.labOven.hasFuelBlend()){
			if(mouseX >= 31+x && mouseX <= 42+x && mouseY >= 28+y && mouseY <= 78+y){
				drawEnergyInfo("RF", this.labOven.getRedstone(), this.labOven.getRedstoneMax(), mouseX, mouseY);
			}
		}

		//solvent tank
		if(mouseX>= 127+x && mouseX <= 144+x && mouseY >= 27+y && mouseY <= 74+y){
			drawTankInfo(this.solventTank, mouseX, mouseY);
		}

		//reagent tank
		if(mouseX>= 149+x && mouseX <= 166+x && mouseY >= 27+y && mouseY <= 74+y){
			drawTankInfo(this.reagentTank, mouseX, mouseY);
		}

		//output tank
		if(mouseX>= 93+x && mouseX <= 110+x && mouseY >= 27+y && mouseY <= 74+y){
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
		
		//void solvent
		if(mouseX >= 128+x && mouseX <= 143+x && mouseY >= 76+y && mouseY <= 91+y){
			drawButtonLabel("Void Tank", mouseX, mouseY);
		}

		//void reagent
		if(mouseX >= 150+x && mouseX <= 166+x && mouseY >= 76+y && mouseY <= 91+y){
			drawButtonLabel("Void Tank", mouseX, mouseY);
		}

		//void output
		if(mouseX >= 94+x && mouseX <= 110+x && mouseY >= 16+y && mouseY <= 31+y){
			drawButtonLabel("Void Tank", mouseX, mouseY);
		}

		//recycle
		if(mouseX >= 57+x && mouseX <= 68+x && mouseY >= 51+y && mouseY <= 80+y){
			drawButtonLabel("Input Recycling", mouseX, mouseY);
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
            this.drawTexturedModalRect(i + 11, j + 29 + (50 - k), 176, 27, 10, k);
        }

		//redstone
		if(!this.labOven.hasFuelBlend()){
			if (this.labOven.getRedstone() > 0){
				int k = this.getBarScaled(50, this.labOven.getRedstone(), this.labOven.getRedstoneMax());
				this.drawTexturedModalRect(i + 32, j + 29 + (50 - k), 176, 81, 10, k);
			}
		}

		//smelt bar
		int k = this.getBarScaled(15, this.labOven.cookTime, this.labOven.getCookTimeMax());
		this.drawTexturedModalRect(i + 75, j + 46, 176, 0, k, 15); //dust

        //activation
        if(this.labOven.isActive()){
            this.drawTexturedModalRect(i + 7, j + 121, 176, 172, 16, 16);
        }

		//process icons
		if(this.labOven.isCooking()){
			this.drawTexturedModalRect(i + 96, j + 97, 176, 131, 12, 14); //fire
			this.drawTexturedModalRect(i + 112, j + 50, 176, 145, 15, 9); //fluid drop
		}

		//induction icons
		if(this.labOven.hasPermanentInduction()){
			this.drawTexturedModalRect(i + 8, j + 82, 177, 155, 16, 16); //inductor
		}

		//blend fix
		if(this.labOven.hasFuelBlend()){
			this.drawTexturedModalRect(i + 25, j + 7, 224, 0, 21, 92); //blend
		}

		//solvent fluid
		if(this.solventTank.getFluid() != null){
			renderFluidBar(this.solventTank.getFluid(), this.solventTank.getCapacity(), i + 128, j + 34, 16, 40);
		}

		//reagent fluid
		if(this.reagentTank.getFluid() != null){
			renderFluidBar(this.reagentTank.getFluid(), this.reagentTank.getCapacity(), i + 150, j + 34, 16, 40);
		}

		//output fluid
		if(this.outputTank.getFluid() != null){
			renderFluidBar(this.outputTank.getFluid(), this.outputTank.getCapacity(), i + 94, j + 34, 16, 40);
		}
	}

}