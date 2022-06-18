package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEReformerReactor;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIReformerReactor extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guireformerchamber.png");

    private final TEReformerReactor tile;

    public UIReformerReactor(InventoryPlayer playerInv, TEReformerReactor tile){
    	super(new COReformerReactor(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEReformerReactor.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

    }
}