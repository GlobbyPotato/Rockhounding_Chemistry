package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COReformerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerController;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;
import com.google.common.base.Strings;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIReformerController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guireformercontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guigasreformingjei.png");

    private final TEReformerController tile;

    public UIReformerController(InventoryPlayer playerInv, TEReformerController tile){
    	super(new COReformerController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEReformerController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //prev
	   if(GuiUtils.hoveringArea(7, 23, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.prev_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //prev
	   if(GuiUtils.hoveringArea(25, 23, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.next_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(148, 44, 14, 14, mouseX, mouseY, x, y)){
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   String rf = TextFormatting.GRAY + "Energy: " + TextFormatting.RED + this.tile.powerConsume() + " RF/t";
		   String inTier = TextFormatting.GRAY + "System Catalyst Tier: " + TextFormatting.GOLD + this.tile.inputTier() + "x";
		   String outTier = TextFormatting.GRAY + "Product Catalyst Tier: " + TextFormatting.GOLD + this.tile.outputTier() +"x";
		   multistring = new String[]{sf, rf, inTier, outTier};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

		//direction
	    if(GuiUtils.hoveringArea(80, 74, 16, 16, mouseX, mouseY, x, y)){
	    	if(this.tile.getDirection()) {
	    		tooltip = GuiUtils.drawLabel("Right to Left setup", mouseX, mouseY);
	    	}else {
	    		tooltip = GuiUtils.drawLabel("Left to Right setup", mouseX, mouseY);
	    	}
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

	   //gas 1 consume
	   if(GuiUtils.hoveringArea(41, 71, 12, 32, mouseX, mouseY, x, y)){
		   String gasusage = TextFormatting.GRAY + "Channel 1: " + TextFormatting.GREEN + this.tile.calculatedDrainA() + " mB";
		   if(this.tile.isOutputGaseous()){
			   gasusage = TextFormatting.GRAY + "Channel 1: " + TextFormatting.GREEN + GuiUtils.translateMC(this.tile.calculatedDrainA()) + " cu";
		   }
		   tooltip = GuiUtils.drawLabel(gasusage, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //gas 2 consume
	   if(GuiUtils.hoveringArea(123, 71, 12, 32, mouseX, mouseY, x, y)){
		   String gasusage = TextFormatting.GRAY + "Channel 2: " + TextFormatting.GREEN + this.tile.calculatedDrainB() + " mB";
		   if(this.tile.isOutputGaseous()){
			   gasusage = TextFormatting.GRAY + "Channel 2: " + TextFormatting.GREEN + GuiUtils.translateMC(this.tile.calculatedDrainB()) + " cu";
		   }
		   tooltip = GuiUtils.drawLabel(gasusage, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //gas produced
	   if(GuiUtils.hoveringArea(65, 46, 46, 23, mouseX, mouseY, x, y)){
		   String gasusage = TextFormatting.GRAY + "Production Yeld: " + TextFormatting.GREEN + this.tile.calculatedProduct() + " mB";
		   if(this.tile.isOutputGaseous()){
			   gasusage = TextFormatting.GRAY + "Production Yeld: " + TextFormatting.GREEN + GuiUtils.translateMC(this.tile.calculatedProduct()) + " cu";
		   }
		   tooltip = GuiUtils.drawLabel(gasusage, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //gaseous check
	   String type = "Recipe output is gaseous";
	   if(!this.tile.isOutputGaseous()){
		   type = "Recipe output is aqueous";
	   }
	   if(GuiUtils.hoveringArea(15, 64, 14, 14, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(type, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
	   
	   //speed upgrade
	   if(GuiUtils.hoveringArea(147, 44, 16, 16, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

    }

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = this.no_recipe_label;
		if(this.tile.isValidPreset()){
			if(Strings.isNullOrEmpty(this.tile.getRecipeList(this.tile.getSelectedRecipe()).getRecipeName())){
				recipeLabel = this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOutput().getLocalizedName();
			}else{
				recipeLabel = this.tile.getRecipeList(this.tile.getSelectedRecipe()).getRecipeName();
			}
		}
		this.fontRenderer.drawString(recipeLabel, 44, 28, 4210752);
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);


        //activation
        if(this.tile.isActive()){
	    	if(this.tile.isPowered()){
	    		this.drawTexturedModalRect(i + 81, j + 97, 190, 10, 14, 14);
	    	}else{
	    		this.drawTexturedModalRect(i + 81, j + 97, 176, 10, 14, 14);
	    	}
	    }

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int p = GuiUtils.getScaledValue(23, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 65, j + 46, 176, 55, 46, 23 - p);
        }else{
            this.drawTexturedModalRect(i + 65, j + 46, 176, 55, 46, 23);
        }

		//direction
        if(this.tile.getDirection()){
       		this.drawTexturedModalRect(i + 82, j + 76, 204, 11, 12, 12);
        }

        //type
        if(!this.tile.isOutputGaseous()){
        	this.drawTexturedModalRect(i + 16, j + 65, 177, 41, 12, 12);
 	   	}

    }
}