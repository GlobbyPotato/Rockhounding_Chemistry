package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPullingCrucibleTop extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guipullingcrucibletop.png");

    private final TEPullingCrucibleTop tile;

    public UIPullingCrucibleTop(InventoryPlayer playerInv, TEPullingCrucibleTop tile){
    	super(new COPullingCrucibleTop(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEPullingCrucibleTop.getName();
    }

}