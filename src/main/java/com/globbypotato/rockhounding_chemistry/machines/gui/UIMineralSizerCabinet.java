package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COMineralSizerCabinet;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCabinet;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIMineralSizerCabinet extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimineralsizercabinet.png");

    public UIMineralSizerCabinet(InventoryPlayer playerInv, TEMineralSizerCabinet tile){
    	super(new COMineralSizerCabinet(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
		this.containerName = "container." + TEMineralSizerCabinet.getName();
    }

}