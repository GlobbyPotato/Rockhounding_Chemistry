package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.io.IOException;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COFluidpedia;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidpedia;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIFluidpedia extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guifluidpedia.png");

    private final TEFluidpedia tile;

    public UIFluidpedia(InventoryPlayer playerInv, TEFluidpedia tile){
    	super(new COFluidpedia(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEFluidpedia.getName();
		this.xSize = 201;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;
	   String[] multiString;

	   //-- char
	   if(GuiUtils.hoveringArea(7, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Char", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //++ char
	   if(GuiUtils.hoveringArea(36, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Char", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //-- page
	   if(GuiUtils.hoveringArea(62, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Page", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //++ page
	   if(GuiUtils.hoveringArea(93, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Page", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //all
	   if(GuiUtils.hoveringArea(119, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Show All", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fluid
	   if(GuiUtils.hoveringArea(136, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Show Fluids", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //gas
	   if(GuiUtils.hoveringArea(153, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Show Gases", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //tooltips
  		if(!this.tile.PAGED_FLUID_LIST.isEmpty() && this.tile.PAGED_FLUID_LIST.size() > 0){
	       for (int i = 0; i < 4; ++i){
	           for (int j = 0; j < 9; ++j){
					if(j + i * 9 < this.tile.PAGED_FLUID_LIST.size()){
		        	   Fluid fluid = this.tile.PAGED_FLUID_LIST.get(j + i * 9);
		        	   if(fluid != null){
			        	   if(GuiUtils.hoveringArea(8 + j * 18, 23 + i * 18, 16, 16, mouseX, mouseY, x, y)){
			             	   	FluidStack fluidstack = new FluidStack(this.tile.PAGED_FLUID_LIST.get(j + i * 9), 1000);
			        		   	String modstring = TextFormatting.GRAY + "Owner: " + TextFormatting.RED + getMod(FluidRegistry.getDefaultFluidName(fluid).split(":")[0]);
	
			        		   	String filterunloc = TextFormatting.GRAY + "Registry: " + TextFormatting.DARK_GRAY + fluidstack.getUnlocalizedName();
			        		   	String filterstring = TextFormatting.GRAY + "Material: " + TextFormatting.WHITE + fluidstack.getLocalizedName();
			        		   	String categorystring = TextFormatting.GRAY + "Type: " + TextFormatting.AQUA + "Fluid";
			        		   	if(fluid.isGaseous()){
			        		   		categorystring = TextFormatting.GRAY + "Type: " + TextFormatting.AQUA + "Gaseous";
			        		   	}
			        		   	String tempstring = TextFormatting.GRAY + "Temperature: " + TextFormatting.GOLD + fluid.getTemperature() + " K";
	
			        		   	int aver = ModUtils.substanceCoeff(fluid);
			        		   	double kinem = ModUtils.kinematicCoeff(fluid);
			        		   	String fluidCat = "Light";
			        		   	if(ModUtils.isHeavyFluid(fluid)){
			        		   		fluidCat = "Heavy";
			        		   	}
			        		   	String substancestring = TextFormatting.GRAY + "Coefficients: " + TextFormatting.DARK_GREEN + aver + " - " + ModUtils.doubleTranslate(ModUtils.kinematicCoeff(fluid));
			        		   	String catstring = TextFormatting.GRAY + "Category: " + TextFormatting.GREEN + fluidCat + " Fluid";
	
			        		   	if(fluid.isGaseous()){
				        			multiString = new String[]{modstring, "", filterstring, filterunloc, categorystring, tempstring};
			        		   	}else{
				        			multiString = new String[]{modstring, "", filterstring, filterunloc, categorystring, tempstring, substancestring, catstring};
			        		   	}
			   					tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
			   					drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			        	   }
		        	   }
					}
	           	}
	       	}
  		}
    }

	 private String getMod(String id) {
        ModContainer mods = Loader.instance().getIndexedModList().get(id);
        return mods != null ? mods.getName() : id;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRenderer.drawString(this.tile.getAlphabet(), 27, 100, 4210752);
		this.fontRenderer.drawString(String.valueOf(this.tile.getPage()), 80, 100, 4210752);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0){
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            if(GuiUtils.hoveringArea(173, 2,   26, 143, mouseX, mouseY, i, j)){
	            if(GuiUtils.hoveringArea(173, 2,   13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 0;}//A
	            if(GuiUtils.hoveringArea(173, 13,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 1;}//B
	            if(GuiUtils.hoveringArea(173, 24,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 2;}//C
	            if(GuiUtils.hoveringArea(173, 35,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 3;}//D
	            if(GuiUtils.hoveringArea(173, 46,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 4;}//E
	            if(GuiUtils.hoveringArea(173, 57,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 5;}//F
	            if(GuiUtils.hoveringArea(173, 68,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 6;}//G
	            if(GuiUtils.hoveringArea(173, 79,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 7;}//H
	            if(GuiUtils.hoveringArea(173, 90,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 8;}//I
	            if(GuiUtils.hoveringArea(173, 101, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 9;}//J
	            if(GuiUtils.hoveringArea(173, 112, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 10;}//K
	            if(GuiUtils.hoveringArea(173, 123, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 11;}//L
	            if(GuiUtils.hoveringArea(173, 134, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 12;}//M
	
	            if(GuiUtils.hoveringArea(186, 2,   13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 13;}//N
	            if(GuiUtils.hoveringArea(186, 13,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 14;}//O
	            if(GuiUtils.hoveringArea(186, 24,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 15;}//P
	            if(GuiUtils.hoveringArea(186, 35,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 16;}//Q
	            if(GuiUtils.hoveringArea(186, 46,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 17;}//R
	            if(GuiUtils.hoveringArea(186, 57,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 18;}//S
	            if(GuiUtils.hoveringArea(186, 68,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 19;}//T
	            if(GuiUtils.hoveringArea(186, 79,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 20;}//U
	            if(GuiUtils.hoveringArea(186, 90,  13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 21;}//V
	            if(GuiUtils.hoveringArea(186, 101, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 22;}//W
	            if(GuiUtils.hoveringArea(186, 112, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 23;}//X
	            if(GuiUtils.hoveringArea(186, 123, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 24;}//W
	            if(GuiUtils.hoveringArea(186, 134, 13, 11, mouseX, mouseY, i, j)){this.tile.charNum = 25;}//Z
	            
	            this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
        	}
        }
    }

	@Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int adjustedX = 120 + this.tile.getView() * 17;
        int adjustedV = this.tile.getView() * 14;
   		this.drawTexturedModalRect(i + adjustedX, j + 97, 202, adjustedV, 14, 14);
        
   		if(!this.tile.PAGED_FLUID_LIST.isEmpty() && this.tile.PAGED_FLUID_LIST.size() > 0){
	        for (int u = 0; u < 4; ++u){
	            for (int v = 0; v < 9; ++v){
					if(v + u * 9 < this.tile.PAGED_FLUID_LIST.size()){
		         	   FluidStack fluid = new FluidStack(this.tile.PAGED_FLUID_LIST.get(v + u * 9), 1000);
		         	   GuiUtils.renderFluidBar(fluid, 1000, 1000, 8 + i + v * 18, 23 + j + u * 18, 16, 16);
					}
	            }
	        }
   		}
    }
}