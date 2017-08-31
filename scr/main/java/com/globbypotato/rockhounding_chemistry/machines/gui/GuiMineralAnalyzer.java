package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralAnalyzer extends GuiBase {
    private final InventoryPlayer playerInventory;
    private final TileEntityMineralAnalyzer mineralAnalyzer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 191;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralanalyzer.png");
	private FluidTank sulfTank;
	private FluidTank chloTank;
	private FluidTank fluoTank;

    public GuiMineralAnalyzer(InventoryPlayer playerInv, TileEntityMineralAnalyzer tile){
        super(tile, new ContainerMineralAnalyzer(playerInv, tile));
        this.playerInventory = playerInv;
        TEXTURE = TEXTURE_REF;
        this.mineralAnalyzer = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.sulfTank = this.mineralAnalyzer.sulfTank;
		this.chloTank = this.mineralAnalyzer.chloTank;
		this.fluoTank = this.mineralAnalyzer.fluoTank;
		this.containerName = "container.mineralAnalyzer";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //fuel
	   if(mouseX >= 9+x && mouseX <= 18+x && mouseY >= 54+y && mouseY <= 104+y){
		   drawPowerInfo("ticks", this.mineralAnalyzer.getPower(), this.mineralAnalyzer.getPowerMax(), mouseX, mouseY);
	   }

		//fuel status
		if(this.mineralAnalyzer.getInput().getStackInSlot(this.mineralAnalyzer.FUEL_SLOT) == null){
			   	//fuel
				String fuelString = TextFormatting.DARK_GRAY + "Fuel Type: " + TextFormatting.GOLD + "Common";
				String indString = TextFormatting.DARK_GRAY + "Induction: " + TextFormatting.RED + "OFF";
				String permaString = "";
				if(this.mineralAnalyzer.hasFuelBlend()){
					fuelString = TextFormatting.DARK_GRAY + "Fuel Type: " + TextFormatting.GOLD + "Blend";
				}
				if(this.mineralAnalyzer.canInduct()){
					indString = TextFormatting.DARK_GRAY + "Induction: " + TextFormatting.RED + "ON";
					permaString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.DARK_GREEN + "Mobile";
					if(this.mineralAnalyzer.hasPermanentInduction()){
						permaString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.DARK_RED + "Permanent";
					}
				}
				String multiString[] = new String[]{fuelString, "", indString, permaString};
			if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 33+y && mouseY <= 50+y){
				   drawMultiLabel(multiString, mouseX, mouseY);
			}
		}

		//sulf tank
		if(mouseX>= 105+x && mouseX <= 121+x && mouseY >= 26+y && mouseY <= 86+y){
			drawTankInfo(this.sulfTank, mouseX, mouseY);
		}
		
		//chlo tank
		if(mouseX>= 127+x && mouseX <= 143+x && mouseY >= 26+y && mouseY <= 86+y){
			drawTankInfo(this.chloTank, mouseX, mouseY);
		}
		
		//fluo tank
		if(mouseX>= 149+x && mouseX <= 165+x && mouseY >= 26+y && mouseY <= 86+y){
			drawTankInfo(this.fluoTank, mouseX, mouseY);
		}

		//drain
		if(mouseX >= 83+x && mouseX <= 101+x && mouseY >= 87+y && mouseY <= 105+y){
			drawButtonLabel("Drain Valve", mouseX, mouseY);
		}

    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
       super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.mineralAnalyzer.getPower() > 0){
            int k = this.getBarScaled(50, this.mineralAnalyzer.getPower(), this.mineralAnalyzer.getPowerMax());
            this.drawTexturedModalRect(i + 11, j + 54 + (50 - k), 176, 51, 10, k);
        }
        //smelt bar
        if (this.mineralAnalyzer.cookTime > 0){
            int k = this.getBarScaled(51, this.mineralAnalyzer.cookTime, this.mineralAnalyzer.getCookTimeMax());
            this.drawTexturedModalRect(i + 32, j + 52, 176, 0, 27, k);
        }
        
        //valve
        if(this.mineralAnalyzer.drainValve){
            this.drawTexturedModalRect(i + 83, j + 87, 176, 101, 18, 18);
        }

        //inductor
        if(this.mineralAnalyzer.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 33, 176, 119, 18, 18);
        }

        if(this.sulfTank.getFluid() != null){
			renderFluidBar(this.sulfTank.getFluid(), this.sulfTank.getCapacity(), i + 105, j + 26, 16, 60);
		}
		
		if(this.chloTank.getFluid() != null){
			renderFluidBar(this.chloTank.getFluid(), this.chloTank.getCapacity(), i + 127, j + 26, 16, 60);
		}
		
		if(this.fluoTank.getFluid() != null){
			renderFluidBar(this.fluoTank.getFluid(), this.fluoTank.getCapacity(), i + 149, j + 26, 16, 60);
		}

    }
}