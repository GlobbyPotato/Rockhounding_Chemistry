package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COLeachingVatController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELeachingVatController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UILeachingVatController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guileachingvatcontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guileachingvatjei.png");

    private final TELeachingVatController tile;

    public UILeachingVatController(InventoryPlayer playerInv, TELeachingVatController tile){
    	super(new COLeachingVatController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TELeachingVatController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

	   String commDescr = "";
	   String commValue = TextFormatting.GRAY + "Specific Gravity: " + TextFormatting.GREEN + GuiUtils.floatRounder(this.tile.getGravity());
	   String commRange = TextFormatting.GRAY + "Filtered interval: " + TextFormatting.LIGHT_PURPLE + "from " + GuiUtils.floatRounder(this.tile.minGravity()) + " to " + GuiUtils.floatRounder(this.tile.maxGravity());

	   //display
	   if(GuiUtils.hoveringArea(73, 75, 30, 16, mouseX, mouseY, x, y)){
		   commDescr = "Current gravity level";
		   multistring = new String[]{commDescr, commValue, commRange};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   // lo
	   if(GuiUtils.hoveringArea(57, 75, 16, 16, mouseX, mouseY, x, y)){
		   commDescr = "Decrease gravity level";
		   multistring = new String[]{commDescr, commValue, commRange};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   // hi
	   if(GuiUtils.hoveringArea(103, 75, 16, 16, mouseX, mouseY, x, y)){
		   commDescr = "Increase gravity level";
		   multistring = new String[]{commDescr, commValue, commRange};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(127, 27, 14, 14, mouseX, mouseY, x, y)){
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   String gs = TextFormatting.GRAY + "Filter Step: " + TextFormatting.LIGHT_PURPLE + GuiUtils.floatRounder(this.tile.filterMove()); 
		   String ff = TextFormatting.GRAY + "Filter Range: " + TextFormatting.LIGHT_PURPLE + "+/- " + GuiUtils.floatRounder(this.tile.filterRange()); 
		   multistring = new String[]{sf, tk, "", gs, ff};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //exposed
       if(this.tile.hasResult()){
    	   tooltip = new ArrayList<String>();
    	   tooltip.add(commRange);
    	   for(int k = 0; k < this.tile.recipeGravity().size(); k++){
    		   if(this.tile.recipeGravity().get(k) >= this.tile.minGravity() && this.tile.recipeGravity().get(k) <= this.tile.maxGravity()){
    			   tooltip.add(this.tile.recipeOutput().get(k).getDisplayName());
    		   }
    	   }
    	   if(GuiUtils.hoveringArea(121, 75, 22, 19, mouseX, mouseY, x, y)){
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
       }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(151, 57, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //filter upgrade
	   if(GuiUtils.hoveringArea(34, 74, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.filterSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel("Leaching Filter Upgrade", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //bugged
       if(this.tile.isBugged()){
		   if(GuiUtils.hoveringArea(122, 52, 13, 13, mouseX, mouseY, x, y)){
			   String bug = TextFormatting.RED + "Warning: " + TextFormatting.WHITE + " Aborted process, uncomplete setup or no output available."; 
			   tooltip = GuiUtils.drawLabel(bug, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
       }
    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRenderer.drawString(String.valueOf(GuiUtils.floatRounder(this.tile.getGravity())), 81, 80, 4210752);
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
            int k = GuiUtils.getScaledValue(23, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 77, j + 49, 176, 63, 22, k);
        }

		//show result
        if(this.tile.hasResult()){
            this.drawTexturedModalRect(i + 121,  j + 75, 176, 33, 20, 17);
        }

		//is bugged
        if(this.tile.isBugged()){
            this.drawTexturedModalRect(i + 122,  j + 52, 176, 87, 13, 13);
        }

    }

}