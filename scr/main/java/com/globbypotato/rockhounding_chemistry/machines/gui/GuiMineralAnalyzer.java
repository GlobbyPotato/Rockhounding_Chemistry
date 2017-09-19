package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

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
	public static final int HEIGHT = 193;
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
	   if(mouseX >= 10+x && mouseX <= 21+x && mouseY >= 27+y && mouseY <= 78+y){
		   drawPowerInfo("ticks", this.mineralAnalyzer.getCookTimeMax(), this.mineralAnalyzer.getPower(), this.mineralAnalyzer.getPowerMax(), mouseX, mouseY);
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
			if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 7+y && mouseY <= 24+y){
				   drawMultiLabel(multiString, mouseX, mouseY);
			}
		}

		//sulf tank
		if(mouseX>= 114+x && mouseX <= 130+x && mouseY >= 25+y && mouseY <= 86+y){
			drawTankInfoWithConsume(this.sulfTank, this.mineralAnalyzer.modSulf(), mouseX, mouseY);
		}

		//chlo tank
		if(mouseX>= 132+x && mouseX <= 150+x && mouseY >= 25+y && mouseY <= 86+y){
			drawTankInfoWithConsume(this.chloTank, this.mineralAnalyzer.modChlo(), mouseX, mouseY);
		}

		//fluo tank
		if(mouseX>= 151+x && mouseX <= 170+x && mouseY >= 25+y && mouseY <= 86+y){
			drawTankInfoWithConsume(this.fluoTank, this.mineralAnalyzer.modFluo(), mouseX, mouseY);
		}

		//drain
		if(mouseX >= 96+x && mouseX <= 109+x && mouseY >= 88+y && mouseY <= 103+y){
		   drawButtonLabel("Drain Fluids. Pipe out fluids if unused", mouseX, mouseY);
		}

		//lo
		if(mouseX >= 25+x && mouseX <= 40+x && mouseY >= 88+y && mouseY <= 103+y){
			drawButtonLabel("Decrease Gravity Reference (-0.5)", mouseX, mouseY);
		}

		//hi
		if(mouseX >= 63+x && mouseX <= 78+x && mouseY >= 88+y && mouseY <= 103+y){
			drawButtonLabel("Increase Gravity Reference (+0.5)", mouseX, mouseY);
		}

		//activation
		if(mouseX >= 7+x && mouseX <= 22+x && mouseY >= 88+y && mouseY <= 103+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}

		//gravity fail
		if(this.mineralAnalyzer.hasGravity() && !this.mineralAnalyzer.isValidRange()){
			if(mouseX >= 80+x && mouseX <= 82+x && mouseY >= 90+y && mouseY <= 101+y){
				String failstring = TextFormatting.RED + "No output expected for this level of gravity";
				drawButtonLabel(failstring, mouseX, mouseY);
			}
		}

		//gravity
		if(mouseX >= 41+x && mouseX <= 62+x && mouseY >= 90+y && mouseY <= 101+y){
			String name = TextFormatting.WHITE + "Specific Gravity";
			String descr = TextFormatting.DARK_GRAY + "Filtered interval: " + TextFormatting.LIGHT_PURPLE + "from " + (this.mineralAnalyzer.getGravity() - 0.5F) + " to " + (this.mineralAnalyzer.getGravity() + 0.5F);
			String[] multistring = new String[]{name, descr};
			drawMultiLabel(multistring, mouseX, mouseY);
		}

		String alertstring = TextFormatting.RED + "Not enough solvent";
		if(mouseX >= 113+x && mouseX <= 121+x && mouseY >= 16+y && mouseY <= 24+y){
			if(this.sulfTank.getFluid() != null && this.mineralAnalyzer.modSulf() > this.sulfTank.getFluidAmount()){
				drawButtonLabel(alertstring, mouseX, mouseY);
			}
		}

		if(mouseX >= 132+x && mouseX <= 140+x && mouseY >= 16+y && mouseY <= 24+y){
			if(this.chloTank.getFluid() != null && this.mineralAnalyzer.modChlo() > this.chloTank.getFluidAmount()){
				drawButtonLabel(alertstring, mouseX, mouseY);
			}
		}

		if(mouseX >= 151+x && mouseX <= 159+x && mouseY >= 16+y && mouseY <= 24+y){
			if(this.fluoTank.getFluid() != null && this.mineralAnalyzer.modFluo() > this.fluoTank.getFluidAmount()){
				drawButtonLabel(alertstring, mouseX, mouseY);
			}
		}

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRendererObj.drawString(String.valueOf(this.mineralAnalyzer.getGravity()), 42, 92, 4210752);
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
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 176, 51, 10, k);
        }

        //smelt bar
        if (this.mineralAnalyzer.cookTime > 0){
            int k = this.getBarScaled(22, this.mineralAnalyzer.cookTime, this.mineralAnalyzer.getCookTimeMax());
            this.drawTexturedModalRect(i + 73, j + 29, 176, 0, 22, k);
        }

        //valve
        if(this.mineralAnalyzer.drainValve){
            this.drawTexturedModalRect(i + 96, j + 88, 176, 101, 16, 16);
        }

        //inductor
        if(this.mineralAnalyzer.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 7, 176, 119, 18, 18);
        }

        //inductor
        if(this.mineralAnalyzer.isActive()){
            this.drawTexturedModalRect(i + 7, j + 88, 176, 153, 16, 16);
        }

        if(this.mineralAnalyzer.hasGravity() && !this.mineralAnalyzer.isValidRange()){
            this.drawTexturedModalRect(i + 80, j + 90, 177, 139, 3, 12);
        }

        //fluid alerts
		if(this.sulfTank.getFluid() != null && this.mineralAnalyzer.modSulf() > this.sulfTank.getFluidAmount()){
            this.drawTexturedModalRect(i + 113, j + 16, 176, 30, 9, 9);
		}

		if(this.chloTank.getFluid() != null && this.mineralAnalyzer.modChlo() > this.chloTank.getFluidAmount()){
            this.drawTexturedModalRect(i + 132, j + 16, 176, 30, 9, 9);
		}

		if(this.fluoTank.getFluid() != null && this.mineralAnalyzer.modFluo() > this.fluoTank.getFluidAmount()){
            this.drawTexturedModalRect(i + 151, j + 16, 176, 30, 9, 9);
		}

        //tanks
        if(this.sulfTank.getFluid() != null){
			renderFluidBar(this.sulfTank.getFluid(), this.sulfTank.getCapacity(), i + 114, j + 26, 16, 60);
		}

		if(this.chloTank.getFluid() != null){
			renderFluidBar(this.chloTank.getFluid(), this.chloTank.getCapacity(), i + 133, j + 26, 16, 60);
		}

		if(this.fluoTank.getFluid() != null){
			renderFluidBar(this.fluoTank.getFluid(), this.fluoTank.getCapacity(), i + 152, j + 26, 16, 60);
		}

    }
}