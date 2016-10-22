package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.blocks.BaseTileBlock;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public class LabOven extends BaseTileBlock{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public LabOven(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityLabOven.class,GuiHandler.labOvenID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
}