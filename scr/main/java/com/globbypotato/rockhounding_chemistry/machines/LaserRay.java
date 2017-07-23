package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserRay;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LaserRay extends BaseBeam {

    public LaserRay(String name){
        super(name);
		setCreativeTab(null);
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityLaserRay();
	}

}