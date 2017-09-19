package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;
import com.globbypotato.rockhounding_core.utils.Translator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidTank;
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
	private FluidTank nitrTank;
	private FluidTank phosTank;
	private FluidTank cyanTank;

    public GuiChemicalExtractor(InventoryPlayer playerInv, TileEntityChemicalExtractor tile){
        super(tile,new ContainerChemicalExtractor(playerInv, tile));
        this.playerInventory = playerInv;
        this.chemicalExtractor = tile;
        TEXTURE = TEXTURE_REF;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.nitrTank = this.chemicalExtractor.nitrTank;
		this.phosTank = this.chemicalExtractor.phosTank;
		this.cyanTank = this.chemicalExtractor.cyanTank;
		this.containerName = "";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //fuel
	   if(mouseX >= 11+x && mouseX <= 21+x && mouseY >= 28+y && mouseY <= 78+y){
		   drawPowerInfo("ticks", this.chemicalExtractor.getCookTimeMax(), this.chemicalExtractor.getPower(), this.chemicalExtractor.getPowerMax(), mouseX, mouseY);
	   }

		//fuel status
		if(this.chemicalExtractor.getInput().getStackInSlot(this.chemicalExtractor.FUEL_SLOT) == null){
			   	//fuel
				String fuelString = TextFormatting.DARK_GRAY + "Fuel Type: " + TextFormatting.GOLD + "Common";
				String indString = TextFormatting.DARK_GRAY + "Induction: " + TextFormatting.RED + "OFF";
				String permaString = "";
				if(this.chemicalExtractor.hasFuelBlend()){
					fuelString = TextFormatting.DARK_GRAY + "Fuel Type: " + TextFormatting.GOLD + "Blend";
				}
				if(this.chemicalExtractor.canInduct()){
					indString = TextFormatting.DARK_GRAY + "Induction: " + TextFormatting.RED + "ON";
					permaString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.DARK_GREEN + "Mobile";
					if(this.chemicalExtractor.hasPermanentInduction()){
						permaString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.DARK_RED + "Permanent";
					}
				}
				String multiString[] = new String[]{fuelString, "", indString, permaString};
			if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 7+y && mouseY <= 24+y){
				   drawMultiLabel(multiString, mouseX, mouseY);
			}
		}

	   //redstone
	   if(!this.chemicalExtractor.hasFuelBlend()){
		   if(mouseX >= 31+x && mouseX <= 41+x && mouseY >= 28+y && mouseY <= 78+y){
			   drawEnergyInfo("RF", this.chemicalExtractor.getRedstone(), this.chemicalExtractor.getRedstoneMax(), mouseX, mouseY);
		   }
	   }

	   //nitric tank
	   if(mouseX>= 8+x && mouseX <= 23+x && mouseY >= 85+y && mouseY <= 144+y){
		   drawTankInfoWithConsume(this.nitrTank, ModConfig.consumedNitr, mouseX, mouseY);
	   }

	   //phos tank
	   if(mouseX>= 28+x && mouseX <= 43+x && mouseY >= 85+y && mouseY <= 144+y){
		   drawTankInfoWithConsume(this.phosTank, ModConfig.consumedPhos, mouseX, mouseY);
	   }

	   //cyan tank
	   if(mouseX>= 48+x && mouseX <= 63+x && mouseY >= 85+y && mouseY <= 144+y){
		   drawTankInfoWithConsume(this.cyanTank, ModConfig.consumedCyan, mouseX, mouseY);
	   }

	   //drain
	   if(mouseX>= 200+x && mouseX <= 217+x && mouseY >= 157+y && mouseY <= 174+y){
		   drawButtonLabel("Drain Fluids. Pipe out fluids if unused", mouseX, mouseY);
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
					   for(int ix = 0; ix < MachineRecipes.inhibitedElements.size(); ix++){
						   if(EnumElement.getName((row * 8) + col).toLowerCase().matches(MachineRecipes.inhibitedElements.get(ix).toLowerCase())){
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

	@Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String device = Translator.translateToLocal("container.chemicalExtractor");
        this.fontRendererObj.drawString(device, 8 + this.xSize / 2 - this.fontRendererObj.getStringWidth(device) / 2, this.chemicalExtractor.getGUIHeight() - 95, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //fuel bar
        if (this.chemicalExtractor.getPower() > 0){
	        int k = this.getBarScaled(50, this.chemicalExtractor.getPower(), this.chemicalExtractor.getPowerMax());
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 225, 0, 10, k);
        }

        //redstone bar
        if(!this.chemicalExtractor.hasFuelBlend()){
	        if (this.chemicalExtractor.getRedstone() > 0){
		        int k = this.getBarScaled(50, this.chemicalExtractor.getRedstone(), this.chemicalExtractor.getRedstoneMax());
	            this.drawTexturedModalRect(i + 31, j + 28 + (50 - k), 225, 109, 10, k);
	        }
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

		//blend fix
		if(this.chemicalExtractor.hasFuelBlend()){
			this.drawTexturedModalRect(i + 27, j + 7, 237, 0, 18, 72); //blend
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

		//nitric tank
		if(this.nitrTank.getFluid() != null){
			renderFluidBar(this.nitrTank.getFluid(), this.nitrTank.getCapacity(), i + 8, j + 85, 16, 60);
		}

		//fluor tank
		if(this.phosTank.getFluid() != null){
			renderFluidBar(this.phosTank.getFluid(), this.phosTank.getCapacity(), i + 28, j + 85, 16, 60);
		}
		//cyan tank
		if(this.cyanTank.getFluid() != null){
			renderFluidBar(this.cyanTank.getFluid(), this.cyanTank.getCapacity(), i + 48, j + 85, 16, 60);
		}
    }
}