package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserBeam;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LaserBeam extends BaseBeam {

    public LaserBeam(String name){
        super(name);
		setCreativeTab(null);
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityLaserBeam();
	}

}