package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcController;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcController;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOwcController extends GuiContainer {
	
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/guiowccontroller.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityOwcController owcController;

    public GuiOwcController(InventoryPlayer playerInv, TileEntityOwcController furnaceInv){
        super(new ContainerOwcController(playerInv, furnaceInv));
        this.playerInventory = playerInv;
        this.owcController = furnaceInv;
		this.xSize = 176;
		this.ySize = 183;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   //RF
	   long currentPower = 0;
	   for(int h = 0; h < this.owcController.activeChannels(); h++){
		   currentPower += this.owcController.getField(h);
	   }
	   int maxPower = this.owcController.getMaxCapacity();
	   String rfString = TextFormatting.DARK_GRAY + "Storage: " + TextFormatting.WHITE + currentPower + "/" + maxPower + " RF";
	   //tide
	   String waterString = TextFormatting.DARK_GRAY + "Water: " + TextFormatting.AQUA + this.owcController.actualWater() + "/" + this.owcController.totalWater() + " tiles";
	   //yeld
	   int currentYeld = this.owcController.getField(10);
	   String yeldString = TextFormatting.DARK_GRAY + "Yeld: " + TextFormatting.YELLOW + currentYeld + "/" + this.owcController.maxYeld() + " RF/tide";
	   //interval
	   int tideInterval = this.owcController.tideChance() * this.owcController.conveyorMultiplier();
	   String intervalString = TextFormatting.DARK_GRAY + "Tides: " + TextFormatting.GRAY + tideInterval + " ticks";
	   //extract string
	   String extractString = TextFormatting.DARK_GRAY + "Extract: " + TextFormatting.RED + this.owcController.rfTransfer() + " RF/t)";

	   //main string
	   String multiString[] = new String[]{rfString, "", waterString, yeldString, intervalString};

	   //RF
	   if((mouseX >= 93+x && mouseX <= 117+x && mouseY >= 16+y && mouseY <= 77+y)){
		   List<String> tooltip = Arrays.asList(multiString);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //tide
	   if((mouseX >= 144+x && mouseX <= 150+x && mouseY >= 16+y && mouseY <= 77+y)){
		   List<String> tooltip = Arrays.asList(waterString);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //yeld
	   if((mouseX >= 160+x && mouseX <= 166+x && mouseY >= 16+y && mouseY <= 77+y)){
		   List<String> tooltip = Arrays.asList(yeldString);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Duality
	   if(mouseX >= 121+x && mouseX <= 138+x && mouseY >= 16+y && mouseY <= 33+y){
		   String text = "Duality Upgrade";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Conveyor
	   if(mouseX >= 121+x && mouseX <= 138+x && mouseY >= 38+y && mouseY <= 55+y){
		   int cf = 3 - this.owcController.conveyorMultiplier();
		   String text = "Conveyor Upgrade x" + cf;
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Efficiency
	   if(mouseX >= 121+x && mouseX <= 138+x && mouseY >= 60+y && mouseY <= 77+y){
		   String text = "Efficiency Upgrade x" + (this.owcController.efficiencyMultiplier()-1);
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Stored Energy
	   if(mouseX >= 99+x && mouseX <= 113+x && mouseY >= 79+y && mouseY <= 86+y){
		   String text = "Stored Energy";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Tide Value
	   if(mouseX >= 142+x && mouseX <= 152+x && mouseY >= 79+y && mouseY <= 86+y){
		   String text = "Tide";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Yeld
	   if(mouseX >= 157+x && mouseX <= 168+x && mouseY >= 79+y && mouseY <= 86+y){
		   String text = "Yeld";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //sanity check
	   if(mouseX >= 7+x && mouseX <= 70+x && mouseY >= 16+y && mouseY <= 77+y){
		   String text = "Sanity Check";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Activation
	   if(mouseX >= 73+x && mouseX <= 91+x && mouseY >= 16+y && mouseY <= 34+y){
		   String text = "Acquire Tide";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
	   //Extraction
	   if(mouseX >= 73+x && mouseX <= 91+x && mouseY >= 38+y && mouseY <= 56+y){
		   String text = "Extract RF";
		   List<String> tooltip = Arrays.asList(text);
		   drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
	   }
    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        String s = this.owcController.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        //power bar
        if (this.owcController.hasPower()){
        	int currentPower = 0;
        	for(int h = 0; h < this.owcController.activeChannels(); h++){
        		currentPower += this.owcController.getField(h);
        	}
            int k = this.getBarScaled(60, currentPower, this.owcController.getMaxCapacity());
            this.drawTexturedModalRect(i + 94, j + 17 + (60 - k), 176, 59, 23, k);
        }
        //yeld bar
        if (this.owcController.getYeld() > 0){
            int k = this.getBarScaled(60, this.owcController.getField(10), this.owcController.maxYeld());
            this.drawTexturedModalRect(i + 161, j + 17 + (60 - k), 176, 119, 5, k);
        }
        //tide bar
        if (this.owcController.actualWater() > 0){
            int k = this.getBarScaled(60, this.owcController.actualWater(), this.owcController.totalWater());
            this.drawTexturedModalRect(i + 145, j + 17 + (60 - k), 183, 119, 5, k);
        }
        //sanity check
        if(this.owcController.checkChamber()){
            this.drawTexturedModalRect(i + 10, j + 22, 176, 0, 7, 5);
        }
        if(this.owcController.checkTower()){
            this.drawTexturedModalRect(i + 10, j + 31, 176, 0, 7, 5);
        }
        if(this.owcController.checkDevices()){
            this.drawTexturedModalRect(i + 10, j + 40, 176, 0, 7, 5);
        }
        if(this.owcController.checkConduit()){
            this.drawTexturedModalRect(i + 10, j + 49, 176, 0, 7, 5);
        }
        if(this.owcController.checkVolume()){
            this.drawTexturedModalRect(i + 10, j + 58, 176, 0, 7, 5);
        }
        if(this.owcController.checkTide()){
            this.drawTexturedModalRect(i + 10, j + 67, 176, 0, 7, 5);
        }
        //upgrades
        if(this.owcController.checkDuality()){
            this.drawTexturedModalRect(i + 121, j + 16, 176, 5, 18, 18);
        }
        if(this.owcController.checkConveyor()){
            this.drawTexturedModalRect(i + 121, j + 38, 176, 23, 18, 18);
        }
        if(this.owcController.checkEfficiency()){
            this.drawTexturedModalRect(i + 121, j + 60, 176, 41, 18, 18);
        }
        //activations
        if(this.owcController.activationKey){
            this.drawTexturedModalRect(i + 73, j + 16, 194, 5, 18, 18);
        }
        if(this.owcController.extractionKey){
            this.drawTexturedModalRect(i + 73, j + 38, 194, 23, 18, 18);
        }
    }

	private int getBarScaled(int pixels, int count, int max) {
        return count > 0 && max > 0 ? count * pixels / max : 0;
	}

}