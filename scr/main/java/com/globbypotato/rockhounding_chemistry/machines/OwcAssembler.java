package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class OwcAssembler extends BaseMachine{
    public OwcAssembler(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityOwcAssembler.class, GuiHandler.owcAssemblerID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
}