package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawlerAssembler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMineCrawlerAssembler extends GuiBase {
	
    public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guicrawlerassembler.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityMineCrawlerAssembler crawlerAssembler;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 201;

    public GuiMineCrawlerAssembler(InventoryPlayer playerInv, TileEntityMineCrawlerAssembler tile){
        super(tile, new ContainerMineCrawlerAssembler(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.crawlerAssembler = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.crawler_assembler";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;
	   
	   String baseString = TextFormatting.WHITE + "Setup Chip";
	   String nameString;
	   String tierString;
	   String descrString;
	   String multiString[];

		//Selector
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Mode Selector";
		tierString = TextFormatting.DARK_GRAY + "Breaking: " + TextFormatting.AQUA + "Basic Logic Chip";
		String tier2String = TextFormatting.DARK_GRAY + "Coring: " + TextFormatting.AQUA + "Advanced Logic Chip";
		descrString = TextFormatting.GRAY + "Changes the mining drop method";
		multiString = new String[]{nameString, tierString, tier2String, descrString};
		if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 16+y && mouseY <= 33+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Filler Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Filler Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "2";
		descrString = TextFormatting.GRAY + "Prevents fluids or gravity blocks to invade the path";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 88+x && mouseX <= 106+x && mouseY >= 16+y && mouseY <= 33+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Absorber Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Absorber Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "1";
		descrString = TextFormatting.GRAY + "Picks and drops the fluids being filled";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 88+x && mouseX <= 106+x && mouseY >= 38+y && mouseY <= 55+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Tunneler Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Tunneler Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "4";
		descrString = TextFormatting.GRAY + "Covers empty sides and roof of the path creating a tunnel";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 88+x && mouseX <= 106+x && mouseY >= 60+y && mouseY <= 77+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Lighter Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Lighter Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "3";
		descrString = TextFormatting.GRAY + "Places a torch when lighting in the path gets too low";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 88+x && mouseX <= 106+x && mouseY >= 82+y && mouseY <= 99+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Railmaker Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Railmaker Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "2";
		descrString = TextFormatting.GRAY + "Places rails in the path as the machine moves on";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 110+x && mouseX <= 128+x && mouseY >= 16+y && mouseY <= 33+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Decorator Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Decorator Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "4";
		descrString = TextFormatting.GRAY + "Decorates the tunnel with a secondary block";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 110+x && mouseX <= 128+x && mouseY >= 38+y && mouseY <= 55+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Pathfinder Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Pathfinder Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "1";
		descrString = TextFormatting.GRAY + "Creates a path if the surface is not at the same level";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 110+x && mouseX <= 128+x && mouseY >= 60+y && mouseY <= 77+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		//Storage Setup
		nameString = TextFormatting.DARK_GRAY + "Name: " + TextFormatting.GOLD + "Storage Setup";
		tierString = TextFormatting.DARK_GRAY + "Required Tier: " + TextFormatting.AQUA + "3";
		descrString = TextFormatting.GRAY + "Recycles mined blocks to be used as filling material";
		multiString = new String[]{baseString, nameString, tierString, descrString};
		if(mouseX >= 110+x && mouseX <= 128+x && mouseY >= 82+y && mouseY <= 99+y){
			drawMultiLabel(multiString, mouseX, mouseY);
		}

		if(this.crawlerAssembler.getInput().getStackInSlot(this.crawlerAssembler.SLOT_CASING) == null){
			if(mouseX >= 34+x && mouseX <= 51+x && mouseY >= 16+y && mouseY <= 33+y){
				drawButtonLabel("Put here a Mine Crawler Casing to assembly or the Mine Crawler to dismantle", mouseX, mouseY);
			}
		}

		if(this.crawlerAssembler.getInput().getStackInSlot(this.crawlerAssembler.SLOT_ARMS) == null){
			if(mouseX >= 61+x && mouseX <= 78+x && mouseY >= 16+y && mouseY <= 33+y){
				drawButtonLabel("Put here 12 Tungsten Carbide Support arms", mouseX, mouseY);
			}
		}

		int totHeads = 0;
		for(int heads = 3; heads <= 8; heads++){
			if(this.crawlerAssembler.getInput().getStackInSlot(heads) != null){
				totHeads++;
			}
		}
		if(totHeads == 0){
			if(mouseX >= 16+x && mouseX <= 69+x && mouseY >= 38+y && mouseY <= 73+y){
				drawButtonLabel("Put here the mining heads to form the desired tier", mouseX, mouseY);
			}
		}

		if(this.crawlerAssembler.getInput().getStackInSlot(this.crawlerAssembler.SLOT_MEMORY) == null){
			if(mouseX >= 61+x && mouseX <= 78+x && mouseY >= 78+y && mouseY <= 95+y){
				drawButtonLabel("Put here the Mine Crawler Memory Card", mouseX, mouseY);
			}
		}

		if(this.crawlerAssembler.getInput().getStackInSlot(this.crawlerAssembler.SLOT_MATERIAL) == null){
			if(mouseX >= 151+x && mouseX <= 168+x && mouseY >= 20+y && mouseY <= 37+y){
				drawButtonLabel("Put here the material to load in the Memory Card", mouseX, mouseY);
			}
		}

		if(this.crawlerAssembler.getInput().getStackInSlot(this.crawlerAssembler.SLOT_LOADER) == null){
			if(mouseX >= 151+x && mouseX <= 168+x && mouseY >= 47+y && mouseY <= 64+y){
				drawButtonLabel("Put here the Mine Crawler or a Memory Card to load", mouseX, mouseY);
			}
		}

    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//smelt bar
        int k = this.getBarScaled(54, this.crawlerAssembler.cookTime, this.crawlerAssembler.getMaxCookTime());
        this.drawTexturedModalRect(i + 16, j + 100, 176, 0, k, 5);
    }

}