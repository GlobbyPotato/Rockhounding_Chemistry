package com.globbypotato.rockhounding_chemistry.machines.gui;

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
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;
	   String[] multiString;

	   //--
	   if(GuiUtils.hoveringArea(8, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Char", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //++
	   if(GuiUtils.hoveringArea(44, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Char", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //all
	   if(GuiUtils.hoveringArea(116, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Show All", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //fluid
	   if(GuiUtils.hoveringArea(134, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Show Fluids", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //gas
	   if(GuiUtils.hoveringArea(152, 96, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Show Gases", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //tooltips
  		if(!this.tile.FLUID_LIST.isEmpty() && this.tile.FLUID_LIST.size() > 0){
	       for (int i = 0; i < 4; ++i){
	           for (int j = 0; j < 9; ++j){
					if(j + i * 9 < this.tile.FLUID_LIST.size()){
		        	   Fluid fluid = this.tile.FLUID_LIST.get(j + i * 9);
		        	   if(GuiUtils.hoveringArea(8 + j * 18, 23 + i * 18, 16, 16, mouseX, mouseY, x, y)){
		             	   	FluidStack fluidstack = new FluidStack(this.tile.FLUID_LIST.get(j + i * 9), 1000);
		        		   	String modstring = TextFormatting.GRAY + "Owner: " + TextFormatting.RED + getMod(FluidRegistry.getDefaultFluidName(fluid).split(":")[0]);

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
			        			multiString = new String[]{modstring, "", filterstring, categorystring, tempstring};
		        		   	}else{
			        			multiString = new String[]{modstring, "", filterstring, categorystring, tempstring, substancestring, catstring};
		        		   	}
		   					tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
		   					drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
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
		this.fontRenderer.drawString(this.tile.getAlphabet(), 31, 100, 4210752);
	}

	@Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int adjustedX = 117 + this.tile.getView() * 18;
        int adjustedV = this.tile.getView() * 14;
   		this.drawTexturedModalRect(i + adjustedX, j + 97, 176, adjustedV, 14, 14);
        
   		if(!this.tile.FLUID_LIST.isEmpty() && this.tile.FLUID_LIST.size() > 0){
	        for (int u = 0; u < 4; ++u){
	            for (int v = 0; v < 9; ++v){
					if(v + u * 9 < this.tile.FLUID_LIST.size()){
		         	   FluidStack fluid = new FluidStack(this.tile.FLUID_LIST.get(v + u * 9), 1000);
		         	   GuiUtils.renderFluidBar(fluid, 1000, 1000, 8 + i + v * 18, 23 + j + u * 18, 16, 16);
					}
	            }
	        }
   		}
    }
}