package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineralSizer extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralsizer.png");

    private final InventoryPlayer playerInventory;
    private final TileEntityMineralSizer mineralSizer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 203;

    public GuiMineralSizer(InventoryPlayer playerInv, TileEntityMineralSizer tile){
        super(tile, new ContainerMineralSizer(playerInv,tile));
       this.TEXTURE = TEXTURE_REF;
        this.mineralSizer = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.mineral_sizer";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   //fuel
	   if(mouseX >= 10+x && mouseX <= 21+x && mouseY >= 27+y && mouseY <= 78+y){
		   drawPowerInfo("ticks", this.mineralSizer.getMaxCookTime(), this.mineralSizer.getPower(), this.mineralSizer.getPowerMax(), mouseX, mouseY);
	   }

		//fuel status
		String[] fuelstatusString = handleFuelStatus(this.mineralSizer.isFuelGated(), this.mineralSizer.hasFuelBlend(), this.mineralSizer.canInduct(), this.mineralSizer.allowPermanentInduction());
		if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 7+y && mouseY <= 24+y){
			drawMultiLabel(fuelstatusString, mouseX, mouseY);
		}

	   String[] multistring;
	   List<String> tooltip;
	   String commDescr = "";
	   String commValue = TextFormatting.GRAY + "Comminution level: " + TextFormatting.GREEN + this.mineralSizer.getComminution();
	   String commState = TextFormatting.GRAY + "Switching Mode: " + TextFormatting.GOLD + "Manual switch";
	   if(this.mineralSizer.isPowered()){
		   commState = TextFormatting.GRAY + "Switching Mode: " + TextFormatting.GOLD + "Redstone signal";
	   }

       //sliders
	   if(mouseX >= 153+x && mouseX <= 168+x && mouseY >= 21+y && mouseY <= 101+y){
		   commDescr = "Current comminution level";
		   multistring = new String[]{commDescr, commValue, commState};
		   drawMultiLabel(multistring, mouseX, mouseY);
	   }

	   // hi
	   if(mouseX >= 153+x && mouseX <= 168+x && mouseY >= 5+y && mouseY <= 20+y){
		   commDescr = "Increase comminution level";
		   if(this.mineralSizer.isPowered()){
			   commDescr = "Fixed comminution level";
		   }else if(!this.mineralSizer.hasComminution()){
			   commDescr = "No increment needed";
		   }
		   multistring = new String[]{commDescr, commValue, commState};
		   drawMultiLabel(multistring, mouseX, mouseY);
	   }

	   // lo
	   if(mouseX >= 153+x && mouseX <= 168+x && mouseY >= 102+y && mouseY <= 117+y){
		   commDescr = "Decrease comminution level";
		   if(this.mineralSizer.isPowered()){
			   commDescr = "Fixed comminution level";
		   }else if(!this.mineralSizer.hasComminution()){
			   commDescr = "No decrement needed";
		   }
		   multistring = new String[]{commDescr, commValue, commState};
		   drawMultiLabel(multistring, mouseX, mouseY);
	   }

	   //exposed
       if(this.mineralSizer.hasComminution() && this.mineralSizer.hasResult()){
    	  tooltip = new ArrayList<String>();
    	   tooltip.add(TextFormatting.GRAY + "Exposed Gangues:");
    	   for(int k = 0; k < this.mineralSizer.recipeOutput().size(); k++){
    		   if(this.mineralSizer.recipeComminution().get(k) == this.mineralSizer.getComminution()){
    			   tooltip.add(this.mineralSizer.recipeOutput().get(k).getDisplayName());
    		   }
    	   }
    	   if(mouseX >= 57+x && mouseX <= 78+x && mouseY >= 95+y && mouseY <= 116+y){
			 drawHoveringText(tooltip, mouseX, mouseY, fontRendererObj);
		   }
       }

	   //30%
	   String defaultlab1 = TextFormatting.GREEN + "Secondary output - " + TextFormatting.WHITE + "if the input has more than 1 possible output at the same comminution level, there is a 30% chance to retrieve an additional result from the sizing process. Its amount is randomized up to half the extractible shards value set in the config.";
	   if(mouseX >= 40+x && mouseX <= 54+x && mouseY >= 79+y && mouseY <= 93+y){
		   drawButtonLabel(defaultlab1, mouseX, mouseY);
	   }

	   //15%
	   String defaultlab2 = TextFormatting.RED + "Waste output - " + TextFormatting.WHITE + "If the input has more than 1 possible output, there is a 15% chance to recover an additional result as a sizing waste. Its amount is always 1.";
	   if(mouseX >= 81+x && mouseX <= 95+x && mouseY >= 79+y && mouseY <= 94+y){
		   drawButtonLabel(defaultlab2, mouseX, mouseY);
	   }

	   //activation
	   if(mouseX >= 39+x && mouseX <= 54+x && mouseY >= 26+y && mouseY <= 41+y){
		   drawButtonLabel("Activation", mouseX, mouseY);
	   }

    }

	@Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //power bar
        if (this.mineralSizer.getPower() > 0){
            int k = this.getBarScaled(50, this.mineralSizer.getPower(), this.mineralSizer.getPowerMax());
            this.drawTexturedModalRect(i + 11, j + 28 + (50 - k), 176, 51, 10, k);
        }

        //smelt bar
        if (this.mineralSizer.cookTime > 0){
            int k = this.getBarScaled(31, this.mineralSizer.cookTime, this.mineralSizer.getMaxCookTime());
            this.drawTexturedModalRect(i + 52, j + 39, 176, 0, k, 31);
        }

        //inductor
        if(this.mineralSizer.hasPermanentInduction()){
            this.drawTexturedModalRect(i + 7, j + 7, 176, 103, 18, 18);
        }

        //sliders
        if(this.mineralSizer.getComminution() == 0){
            this.drawTexturedModalRect(i + 157, j + 96, 176, 38, 8, 6);
        }else{
        	int offset = this.mineralSizer.getComminution() * 5;
            this.drawTexturedModalRect(i + 157, j + 96 - offset, 176, 31, 8, 6);
        }

        //hopper size
    	int offset = this.mineralSizer.getComminution();
        this.drawTexturedModalRect(i + 100 + offset, j + 17, 215, 0, 27, 89);

		//activation
        if(this.mineralSizer.activation){
            this.drawTexturedModalRect(i + 39, j + 26, 176, 137, 16, 16);
        }

		//show result
        if(this.mineralSizer.hasComminution() && this.mineralSizer.hasResult()){
            this.drawTexturedModalRect(i + 58,  j + 96, 176, 154, 20, 17);
        }

		//signal
        if(this.mineralSizer.isPowered() && this.mineralSizer.hasComminution()){
            this.drawTexturedModalRect(i + 155, j + 7, 176, 172, 12, 12);
            this.drawTexturedModalRect(i + 155, j + 104, 176, 172, 12, 12);
       	}else if(!this.mineralSizer.hasComminution()){
            this.drawTexturedModalRect(i + 155, j + 7, 176, 185, 12, 12);
            this.drawTexturedModalRect(i + 155, j + 104, 176, 185, 12, 12);
        }
    }

}