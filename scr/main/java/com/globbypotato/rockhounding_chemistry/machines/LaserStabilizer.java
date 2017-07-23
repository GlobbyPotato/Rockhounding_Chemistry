package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserStabilizer;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class LaserStabilizer extends BaseLaser {

    public LaserStabilizer(float hardness, float resistance, String name){
        super(hardness, resistance, name, Material.IRON, SoundType.METAL);
    }

    @Override
    public AxisAlignedBB getBox(){
    	return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.72D, 1.0D);
    }
    
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityLaserStabilizer();
    }

}