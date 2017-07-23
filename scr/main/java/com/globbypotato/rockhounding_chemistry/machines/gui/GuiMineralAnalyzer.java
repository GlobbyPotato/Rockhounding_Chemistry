package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;
import com.globbypotato.rockhounding_core.utils.RenderUtils;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralAnalyzer extends GuiBase {
    private final InventoryPlayer playerInventory;
    private final TileEntityMineralAnalyzer mineralAnalyzer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 191;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralanalyzer.png");

    public GuiMineralAnalyzer(InventoryPlayer playerInv, TileEntityMineralAnalyzer tile){
        super(tile, new ContainerMineralAnalyzer(playerInv, tile));
        this.playerInventory = playerInv;
        TEXTURE = TEXTURE_REF;

        this.mineralAnalyzer = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //bars progression (fuel-redstone)
	   if(mouseX >= 9+x && mouseX <= 18+x && mouseY >= 54+y && mouseY <= 104+y){
		   String text = this.mineralAnalyzer.getPower() + "/" + this.mineralAnalyzer.getPowerMax() + " ticks";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   
		//sulf tank
		if(mouseX>= 105+x && mouseX <= 121+x && mouseY >= 26+y && mouseY <= 86+y){
			int fluidAmount = 0;
			if(mineralAnalyzer.sulfTank.getFluid() != null){
				fluidAmount = this.mineralAnalyzer.sulfTank.getFluidAmount();
			}
			String liquid = "";
			if(mineralAnalyzer.sulfTank.getFluid() != null) liquid = mineralAnalyzer.sulfTank.getFluid().getLocalizedName();
			String[] text = {fluidAmount + "/" + this.mineralAnalyzer.sulfTank.getCapacity() + " mb", liquid};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		
		//chlo tank
		if(mouseX>= 127+x && mouseX <= 143+x && mouseY >= 26+y && mouseY <= 86+y){
			int fluidAmount = 0;
			if(mineralAnalyzer.chloTank.getFluid() != null){
				fluidAmount = this.mineralAnalyzer.chloTank.getFluidAmount();
			}
			String liquid = "";
			if(mineralAnalyzer.chloTank.getFluid() != null) liquid = mineralAnalyzer.chloTank.getFluid().getLocalizedName();
			String[] text = {fluidAmount + "/" + this.mineralAnalyzer.chloTank.getCapacity() + " mb", liquid};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		
		//fluo tank
		if(mouseX>= 149+x && mouseX <= 165+x && mouseY >= 26+y && mouseY <= 86+y){
			int fluidAmount = 0;
			if(mineralAnalyzer.fluoTank.getFluid() != null){
				fluidAmount = this.mineralAnalyzer.fluoTank.getFluidAmount();
			}
			String liquid = "";
			if(mineralAnalyzer.fluoTank.getFluid() != null) liquid = mineralAnalyzer.fluoTank.getFluid().getLocalizedName();
			String[] text = {fluidAmount + "/" + this.mineralAnalyzer.fluoTank.getCapacity() + " mb", liquid};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//drain
		if(mouseX >= 83+x && mouseX <= 101+x && mouseY >= 87+y && mouseY <= 105+y){
			List<String> tooltip = Arrays.asList("Drain Valve");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}

    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        String device = Translator.translateToLocal("container.mineralAnalyzer");
        this.fontRendererObj.drawString(device, this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, 6, 4210752);
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

        if(mineralAnalyzer.sulfTank.getFluid() != null){
			FluidStack temp = mineralAnalyzer.sulfTank.getFluid();
			int capacity = mineralAnalyzer.sulfTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 105, j + 26, zLevel, 16, 60);
			}
		}
		
		if(mineralAnalyzer.chloTank.getFluid() != null){
			FluidStack temp = mineralAnalyzer.chloTank.getFluid();
			int capacity = mineralAnalyzer.chloTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 127, j + 26, zLevel, 16, 60);
			}
		}
		
		if(mineralAnalyzer.fluoTank.getFluid() != null){
			FluidStack temp = mineralAnalyzer.fluoTank.getFluid();
			int capacity = mineralAnalyzer.fluoTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 149, j + 26, zLevel, 16, 60);
			}
		}

    }
}