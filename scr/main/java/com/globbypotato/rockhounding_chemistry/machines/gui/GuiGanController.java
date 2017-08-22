package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerGanController;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityGanController;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGanController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guigancontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/guigancontrollerjei.png");
    
    private final InventoryPlayer playerInventory;
    private final TileEntityGanController ganController;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;

    public GuiGanController(InventoryPlayer playerInv, TileEntityGanController tile){
        super(tile, new ContainerGanController(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.ganController = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.ganController";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //RF
	   String rfString = TextFormatting.DARK_GRAY + "Energy Storage: " + TextFormatting.RED + this.ganController.getRedstone() + "/" + this.ganController.getRedstoneMax() + " RF";
	   //production use
	   String productString = TextFormatting.DARK_GRAY + "Distillating Energy: " + TextFormatting.GOLD + this.ganController.getRedstoneCost() + " RF/tick";
	   //compress use
	   String compresString = TextFormatting.DARK_GRAY + "Compressing Energy: " + TextFormatting.GOLD + this.ganController.getChillingCost() + " RF/tick";
	   //tier
	   String tierString = TextFormatting.DARK_GRAY + "System Tier: " + TextFormatting.GRAY + this.ganController.getTier() + " - " + this.ganController.getTierName();
	   //air
	   String airString = TextFormatting.DARK_GRAY + "Compressed Air: " + TextFormatting.AQUA + this.ganController.getAirAmount() + " unit/tick";
	   //refrigerant
	   String refrigerantString = TextFormatting.DARK_GRAY + "Refrigerant: " + TextFormatting.DARK_AQUA + this.ganController.getRefrigerantAmount() + " mB/tick" + TextFormatting.WHITE + " - (" + this.ganController.getTemperature() + "K)";
	   //nitrogen
	   String nitrogenString = TextFormatting.DARK_GRAY + "Liquid Nitrogen: " + TextFormatting.WHITE + this.ganController.getNitrogenAmount() + " mB/tick";

	   //main string
	   String multiString[] = new String[]{rfString, tierString, "", compresString, productString, "", refrigerantString, airString, nitrogenString};

	   //RF area
	   if((mouseX >= 85+x && mouseX <= 107+x && mouseY >= 17+y && mouseY <= 76+y)){
			drawMultiLabel(multiString, mouseX, mouseY);
	   }

	   //Tier
	   if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 33+y && mouseY <= 50+y){
		   String text = "Tier: " +  this.ganController.getTierName();
			drawButtonLabel(text, mouseX, mouseY);
	   }

	   //coolant
	   if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 53+y && mouseY <= 70+y){
		   String text = TextFormatting.GRAY + "Refrigerant:" + TextFormatting.GOLD + " Not Available";
		   if(this.ganController.hasRefrigerant()){
			   if(this.ganController.isValidTemperature()){
				   text = TextFormatting.GRAY + "Refrigerant: " + TextFormatting.GREEN + this.ganController.getChillerTank().getFluid().getLocalizedName() + TextFormatting.WHITE + " - (" + this.ganController.getTemperature() + "K)";
			   }else{
				   text = TextFormatting.GRAY + "Refrigerant:" + TextFormatting.RED  +" Invalid. High temperature";
			   }
	       }
			drawButtonLabel(text, mouseX, mouseY);
	   }

	   //compress tooltip
	   if(mouseX >= 54+x && mouseX <= 70+x && mouseY >= 33+y && mouseY <= 50+y){
		   String modeIn = "";
		   if(this.ganController.compressKey){ modeIn = "Producing Nitrogen"; }else{ modeIn = "Compressing air"; }
		   String text = "Mode Selected: " + modeIn;
			drawButtonLabel(text, mouseX, mouseY);
	   }

	   //activation
	   if(mouseX >= 110+x && mouseX <= 125+x && mouseY >= 16+y && mouseY <= 31+y){
			drawButtonLabel("Activation", mouseX, mouseY);
	   }

	   //Sanity check
	   if(mouseX >= 7+x && mouseX <= 70+x && mouseY >= 16+y && mouseY <= 30+y){
			drawButtonLabel("Sanity Check", mouseX, mouseY);
	   }
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.ganController.hasRedstone()){
            int k = this.getBarScaled(60, this.ganController.getRedstone(), this.ganController.getRedstoneMax());
            this.drawTexturedModalRect(i + 85, j + 17 + (60 - k), 176, 59, 23, k);
        }
        
        if(this.ganController.isProducing()){
	        //production
            this.drawTexturedModalRect(i + 53, j + 33, 176, 23, 18, 18);
        }

        if(this.ganController.isActivated()){
	        //sanity check
	        if(this.ganController.checkDevices()){
	            this.drawTexturedModalRect(i + 10, j + 22, 176, 0, 7, 5);
	        }
	        
	        //activation
            this.drawTexturedModalRect(i + 110, j + 16, 176, 41, 16, 16);
            
	        //upgrades
	        if(this.ganController.checkTier()){
	            this.drawTexturedModalRect(i + 7, j + 33, 176, 5, 18, 18);
	        }

	        //coolant
	        if(this.ganController.hasRefrigerant() && this.ganController.isValidTemperature()){
	            this.drawTexturedModalRect(i + 7, j + 53, 194, 5, 18, 18);
	        }
        }

    }
}