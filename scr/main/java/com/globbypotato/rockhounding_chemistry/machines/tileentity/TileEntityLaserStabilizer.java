package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.machines.LaserBeam;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserStabilizer extends TileEntityLaserBase {

	@Override
	public void update() {
		if(!worldObj.isRemote){

		}
	}

	public boolean isPowered() {
		IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing ampfacing = state.getValue(ampliFacing());
	    BlockPos beampos = pos.offset(ampfacing.getOpposite());
	    IBlockState beamstate = worldObj.getBlockState(beampos);
	    if(beamstate != null && beamstate.getBlock() instanceof LaserBeam){
		    EnumFacing beamfacing = beamstate.getValue(beamFacing());
		    if(beamfacing == ampfacing){
		    	return true;
		    }
	    }
		return false;
	}

}