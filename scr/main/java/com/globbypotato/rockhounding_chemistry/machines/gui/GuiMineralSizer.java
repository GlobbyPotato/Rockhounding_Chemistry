package com.globbypotato.rockhounding_chemistry.machines.gui;

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

    private void composeSliders(String comminution, int mouseX, int mouseY){
	   int sli = this.mineralSizer.getSlider();
	   String descr = "";
	   if(this.mineralSizer.getRecipe() != null){
		   if(this.mineralSizer.getRecipe().getOutput().size() > 1 && this.mineralSizer.getRecipe().getComminution()){
			   if(sli > 0){
				   if(this.mineralSizer.isCorrectRange()){
					   int comm = this.mineralSizer.getRecipe().getProbability().get(sli-1);
					   String exposed = this.mineralSizer.getRecipe().getOutput().get(sli-1).getDisplayName();
					   comminution = TextFormatting.GRAY + "Comminution level: " + TextFormatting.GREEN + this.mineralSizer.getSlider() + "/10";
					   descr = TextFormatting.GRAY + "Exposed gangue: " + TextFormatting.WHITE + exposed;
				   }else{
					   descr = TextFormatting.RED + "Level out of range!";
				   }
			   }
		   }else{
			   descr = TextFormatting.GOLD + "No exposure required";
		   }
	   }else{
		   descr = TextFormatting.GRAY + "No exposed gangues";
	   }
	   String[] multistring = new String[]{comminution, descr};
	   drawMultiLabel(multistring, mouseX, mouseY);
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

       //sliders
	   if(mouseX >= 153+x && mouseX <= 168+x && mouseY >= 23+y && mouseY <= 100+y){
		   String comminution = TextFormatting.GRAY + "Comminution level: " + TextFormatting.RED + "Not Available";
		   composeSliders(comminution, mouseX, mouseY);
	   }

	   // hi
	   if(mouseX >= 153+x && mouseX <= 168+x && mouseY >= 7+y && mouseY <= 22+y){
		   String comminution = "Increase comminution level";
		   composeSliders(comminution, mouseX, mouseY);
	   }

	   // lo
	   if(mouseX >= 153+x && mouseX <= 168+x && mouseY >= 100+y && mouseY <= 115+y){
		   String comminution = "Decrease comminution level";
		   composeSliders(comminution, mouseX, mouseY);
	   }

	   //25%
	   String defaultlab1 = TextFormatting.GREEN + "Secondary output - " + TextFormatting.WHITE + "if the input has more than 4 possible outputs, there is a 25% chance to retrieve an additional result from the sizing process. Its amount is randomized up to half the extractible shards value set in the config.";
	   if(mouseX >= 40+x && mouseX <= 54+x && mouseY >= 79+y && mouseY <= 93+y){
		   drawButtonLabel(defaultlab1, mouseX, mouseY);
	   }

	   //5%
	   String defaultlab2 = TextFormatting.RED + "Waste output - " + TextFormatting.WHITE + "If the input has more than 1 possible output, there is a 5% chance to recover an additional result as a sizing waste. Its amount is always 1.";
	   if(mouseX >= 81+x && mouseX <= 91+x && mouseY >= 79+y && mouseY <= 93+y){
		   drawButtonLabel(defaultlab2, mouseX, mouseY);
	   }
	   
	   //activation
	   if(mouseX >= 39+x && mouseX <= 54+x && mouseY >= 26+y && mouseY <= 41+y){
		   drawButtonLabel("Activation", mouseX, mouseY);
	   }

	   String halt = "";
       if(!this.mineralSizer.canConsumableHandleComminution()){
    	   if(mouseX >= 28+x && mouseX <= 30+x && mouseY >= 49+y && mouseY <= 60+y){
    		   halt = TextFormatting.RED + "The consumable can't handle the current comminution level!";
    		   drawButtonLabel(halt, mouseX, mouseY);
	       }
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
        if(this.mineralSizer.getSlider() == 0){
            this.drawTexturedModalRect(i + 157, j + 93, 176, 38, 8, 7);
        }else{
        	int offset = this.mineralSizer.getSlider() * 7;
            this.drawTexturedModalRect(i + 157, j + 93 - offset, 176, 31, 8, 7);
        }

        //hopper size
        if(this.mineralSizer.getSlider() < 1){
            this.drawTexturedModalRect(i + 92, j + 17, 215, 0, 27, 89);
        }else{
        	int offset = this.mineralSizer.getSlider() * 2;
            this.drawTexturedModalRect(i + 92 + offset, j + 17, 215, 0, 27, 89);
        }

		//activation
        if(this.mineralSizer.activation){
            this.drawTexturedModalRect(i + 39, j + 26, 176, 137, 16, 16);
        }

        //block consumable
        if(!this.mineralSizer.canConsumableHandleComminution()){
            this.drawTexturedModalRect(i + 28, j + 49, 177, 123, 3, 12);
        }
    }

}