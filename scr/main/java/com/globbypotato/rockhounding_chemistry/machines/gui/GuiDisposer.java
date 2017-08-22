package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerDisposer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDisposer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDisposer extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guidisposer.png");

    private final InventoryPlayer playerInventory;
    private final TileEntityDisposer disposer;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 201;

    public GuiDisposer(InventoryPlayer playerInv, TileEntityDisposer tile){
        super(tile, new ContainerDisposer(playerInv,tile));
       this.TEXTURE = TEXTURE_REF;
        this.disposer = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.disposer";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

		//activation
		if(mouseX >= 43+x && mouseX <= 58+x && mouseY >= 38+y && mouseY <= 53+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}

		//lock
		if(mouseX >= 117+x && mouseX <= 132+x && mouseY >= 38+y && mouseY <= 53+y){
			if(this.disposer.isLocked()){
				drawButtonLabel("Locked", mouseX, mouseY);
			}else{
				drawButtonLabel("Unlocked", mouseX, mouseY);
			}
		}

		//fast
		if(mouseX >= 58+x && mouseX <= 73+x && mouseY >= 75+y && mouseY <= 90+y){
			drawButtonLabel("Fast: -2 ticks", mouseX, mouseY);
		}

		//slow
		if(mouseX >= 102+x && mouseX <= 117+x && mouseY >= 75+y && mouseY <= 90+y){
			drawButtonLabel("Slow: +2 ticks", mouseX, mouseY);
		}

		//faster
		if(mouseX >= 42+x && mouseX <= 57+x && mouseY >= 75+y && mouseY <= 90+y){
			drawButtonLabel("Faster: -10 ticks", mouseX, mouseY);
		}

		//slower
		if(mouseX >= 118+x && mouseX <= 133+x && mouseY >= 75+y && mouseY <= 90+y){
			drawButtonLabel("Slower: +10 ticks", mouseX, mouseY);
		}

		//lock stacks
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
        		if(mouseX >= 61 + x + (18*j) && mouseX <= 78 + x + (18*j) && mouseY >= 19 + y + (18*i) && mouseY <= 36 + y + (18*i)){
        			if(this.disposer.lockList.get((j + i * 3)) != null){
        				drawButtonLabel(this.disposer.lockList.get((j + i * 3)).getDisplayName(), mouseX, mouseY);
        			}
        		}
            }
        }

    }

    @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String name = String.valueOf(this.disposer.getInterval());
        this.fontRendererObj.drawString(name, 77, 79, 4210752);
	}


    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        //activation
        if(this.disposer.isActive()){
            this.drawTexturedModalRect(i + 43, j + 38, 176, 0, 16, 16);
        }
        
        //lock
        if(this.disposer.isLocked()){
            this.drawTexturedModalRect(i + 117, j + 38, 176, 16, 16, 16);
           
            //locked
            for (int col = 0; col < 3; ++col){
                for (int row = 0; row < 3; ++row){
        			if(this.disposer.lockList.get((col + row * 3)) != null){
        	            this.drawTexturedModalRect(i + 62 + (18*col), j + 20 + (18*row), 176, 32, 16, 16);
        			}
                }
            }
        }

        
    }

}