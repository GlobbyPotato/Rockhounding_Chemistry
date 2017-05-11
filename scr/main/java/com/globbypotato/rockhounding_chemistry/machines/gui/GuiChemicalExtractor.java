package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.utils.RenderUtils;
import com.globbypotato.rockhounding_chemistry.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChemicalExtractor extends GuiBase {
    private final InventoryPlayer playerInventory;
    private final TileEntityChemicalExtractor chemicalExtractor;
	public static final int WIDTH = 225;
	public static final int HEIGHT = 253;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guichemicalextractor.png");
	public static final ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/guichemicalextractorjei.png");

    public GuiChemicalExtractor(InventoryPlayer playerInv, TileEntityChemicalExtractor tile){
        super(tile,new ContainerChemicalExtractor(playerInv, tile));
        this.playerInventory = playerInv;
        this.chemicalExtractor = tile;
        TEXTURE = TEXTURE_REF;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //fuel
	   if(mouseX >= 11+x && mouseX <= 21+x && mouseY >= 28+y && mouseY <= 78+y){
		   String text = this.chemicalExtractor.powerCount + "/" + this.chemicalExtractor.powerMax + " ticks";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //redstone
	   if(mouseX >= 31+x && mouseX <= 41+x && mouseY >= 28+y && mouseY <= 78+y){
		   String text = this.chemicalExtractor.redstoneCount + "/" + this.chemicalExtractor.redstoneMax + " RF";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
		//nitric tank
		if(mouseX>= 8+x && mouseX <= 23+x && mouseY >= 85+y && mouseY <= 144+y){
			int fluidAmount = 0;
			if(chemicalExtractor.nitrTank.getFluid() != null){
				fluidAmount = this.chemicalExtractor.nitrTank.getFluidAmount();
			}
			String liquid = "";
			if(chemicalExtractor.nitrTank.getFluid() != null) liquid = chemicalExtractor.nitrTank.getFluid().getLocalizedName();
			String[] text = {fluidAmount + "/" + this.chemicalExtractor.nitrTank.getCapacity() + " mb", liquid};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//fluo tank
		if(mouseX>= 28+x && mouseX <= 43+x && mouseY >= 85+y && mouseY <= 144+y){
			int fluidAmount = 0;
			if(chemicalExtractor.phosTank.getFluid() != null){
				fluidAmount = this.chemicalExtractor.phosTank.getFluidAmount();
			}
			String liquid = "";
			if(chemicalExtractor.phosTank.getFluid() != null) liquid = chemicalExtractor.phosTank.getFluid().getLocalizedName();
			String[] text = {fluidAmount + "/" + this.chemicalExtractor.phosTank.getCapacity() + " mb",liquid};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//cyan tank
		if(mouseX>= 48+x && mouseX <= 63+x && mouseY >= 85+y && mouseY <= 144+y){
			int fluidAmount = 0;
			if(chemicalExtractor.cyanTank.getFluid() != null){
				fluidAmount = this.chemicalExtractor.cyanTank.getFluidAmount();
			}
			String liquid = "";
			if(chemicalExtractor.cyanTank.getFluid() != null) liquid = chemicalExtractor.cyanTank.getFluid().getLocalizedName();
			String[] text = {fluidAmount + "/" + this.chemicalExtractor.cyanTank.getCapacity() + " mb",liquid};
			List<String> tooltip = Arrays.asList(text);
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//drain
		if(mouseX>= 200+x && mouseX <= 217+x && mouseY >= 157+y && mouseY <= 174+y){
			List<String> tooltip = Arrays.asList("Drain Valve");
			drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		}
		//elements
		int colOffset = 67 + x;
		int rowOffset = 7 + y;
		for(int row = 0; row < 7; row++){
			for(int col = 0; col < 8; col++){
			   if(mouseX >= colOffset + (col * 19) && mouseX <= colOffset + (col * 19) + 19 && mouseY >= rowOffset + (row * 21) && mouseY <= rowOffset + (row * 21) + 21){
				   int enumDust = (row * 8) + col;
				   if(enumDust < EnumElement.size()){
					   String inhibit = "";
					   for(int ix = 0; ix < ModRecipes.inhibitedElements.size(); ix++){
						   if(EnumElement.getName((row * 8) + col).toLowerCase().matches(ModRecipes.inhibitedElements.get(ix).toLowerCase())){
							   inhibit = " - (Inhibited)";
						   }
					   }
					   String text = EnumElement.getName((row * 8) + col).substring(0, 1).toUpperCase() + EnumElement.getName((row * 8) + col).substring(1) + " : " + TextFormatting.YELLOW + this.chemicalExtractor.elementList[enumDust] + "/" + ModConfig.factorExtractor + TextFormatting.RED + inhibit ;
					   List<String> tooltip = Arrays.asList(text);
					   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
				   }
			   }
			}			
		}
    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
        String device = Translator.translateToLocal("container.chemicalExtractor");
        this.fontRendererObj.drawString(device, 8 + this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, this.chemicalExtractor.getGUIHeight() - 95, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //fuel bar
        if (this.chemicalExtractor.powerCount > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.powerCount, this.chemicalExtractor.powerMax);
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 225, 0, 10, k);
        }
        //redstone bar
        if (this.chemicalExtractor.redstoneCount > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.redstoneCount, this.chemicalExtractor.redstoneMax);
            this.drawTexturedModalRect(i + 31, j + 28 + (50 - k), 225, 109, 10, k);
        }
        //smelt bar
        int k = this.getBarScaled(29, this.chemicalExtractor.cookTime, this.chemicalExtractor.getCookTimeMax());
        this.drawTexturedModalRect(i + 53, j + 49, 225, 50, 6, 29 - k);
        //valve
        if(this.chemicalExtractor.drainValve){
            this.drawTexturedModalRect(i + 200, j + 157, 225, 159, 18, 18);
        }
        //induction
        if(this.chemicalExtractor.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 7, 225, 177, 18, 18);
        }
		//cabinet bars
		for(int hSlot = 0; hSlot <= 6; hSlot++){
			for(int vSlot = 0; vSlot <= 7; vSlot++){
				int slotID = (hSlot * 8) + vSlot;
		        int cab = this.getBarScaled(19, this.chemicalExtractor.elementList[slotID], ModConfig.factorExtractor);
				if(cab > 19){cab = 19;}
				this.drawTexturedModalRect(i + 80 + (19 * vSlot), j + 7 + (21 * hSlot), 225, 89, 4, 19 - cab);
			}
		}
		//syngas tank
		if(chemicalExtractor.nitrTank.getFluid() != null){
			FluidStack temp = chemicalExtractor.nitrTank.getFluid();
			int capacity = chemicalExtractor.nitrTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 8, j + 85, zLevel, 16, 60);
			}
		}
		//fluor tank
		if(chemicalExtractor.phosTank.getFluid() != null){
			FluidStack temp = chemicalExtractor.phosTank.getFluid();
			int capacity = chemicalExtractor.phosTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 28, j + 85, zLevel, 16, 60);
			}
		}
		//cyan tank
		if(chemicalExtractor.cyanTank.getFluid() != null){
			FluidStack temp = chemicalExtractor.cyanTank.getFluid();
			int capacity = chemicalExtractor.cyanTank.getCapacity();
			if(temp.amount > 5){
				RenderUtils.bindBlockTexture();
				RenderUtils.renderGuiTank(temp,capacity, temp.amount, i + 48, j + 85, zLevel, 16, 60);
			}
		}
    }
}