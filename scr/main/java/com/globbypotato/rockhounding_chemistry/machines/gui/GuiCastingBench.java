package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCastingBench extends GuiBase {
	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guicastingbench.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityCastingBench castingBench;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 183;

    public GuiCastingBench(InventoryPlayer playerInv, TileEntityCastingBench tile){
        super(tile, new ContainerCastingBench(playerInv,tile));
        this.TEXTURE = TEXTURE_REF;
        this.castingBench = tile;
        this.playerInventory = playerInv;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.castingBench";
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//casting
		for(int k = 0; k < EnumCasting.size(); k++){
			if(mouseX >= 50 + (20 * k) + x && mouseX <= 65 + (20 * k) + x && mouseY >= 58+y && mouseY <= 74+y){
				drawButtonLabel(EnumCasting.formalName(k) + " Pattern", mouseX, mouseY);
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
        if (this.castingBench.cookTime > 0){
            int k = this.getBarScaled(32, this.castingBench.cookTime, this.castingBench.getMaxCookTime());
            this.drawTexturedModalRect(i + 72, j + 33, 176, 0, k, 14);
        }

		//casting
        int k = this.castingBench.getCurrentCast();
        this.drawTexturedModalRect(i + 50 + (20 * k), j + 58, 177 + (20 * k), 15, 16, 16);
    }
}