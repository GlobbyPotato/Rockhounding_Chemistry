package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.machines.container.COEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEEvaporationTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIEvaporationTank extends GuiBase {

    public UIEvaporationTank(InventoryPlayer playerInv, TEEvaporationTank tile){
    	super(new COEvaporationTank(playerInv,tile), ModUtils.HEIGHT);
		this.containerName = "container." + TEEvaporationTank.getName();
    }

}