package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltSeasoner;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class SaltSeasoner extends BaseMachine{
    public SaltSeasoner(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntitySaltSeasoner.class,GuiHandler.saltSeasonerID);
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

}