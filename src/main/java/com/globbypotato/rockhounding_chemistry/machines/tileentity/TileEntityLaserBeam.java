package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.LaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.LaserTX;

import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityLaserBeam extends TileEntity implements ITickable {

	@Override
	public void update() {
	    IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing enumfacing = state.getValue(LaserBeam.FACING);
    
	    if(!worldObj.isRemote){
	    	if( !isSameBeamDirection(state, enumfacing) 
	    		&& !isValidEmitter(state, enumfacing) 
	    		&& !isValidSplitter(state, enumfacing)){
	    		worldObj.setBlockState(pos, Blocks.AIR.getDefaultState());
	    	}
	    }
	}

	private boolean isValidSplitter(IBlockState state, EnumFacing enumfacing){
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == ModBlocks.laserSplitter){
		    EnumFacing splitterfacing = checkstate.getValue(LaserTX.FACING);
			if(splitterfacing != enumfacing ){
				return true;
			}
		}
		return false;
	}

	private boolean isValidEmitter(IBlockState state, EnumFacing enumfacing) {
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == ModBlocks.laserRedstoneTx){
		    EnumFacing txfacing = checkstate.getValue(LaserTX.FACING);
			if(txfacing == enumfacing ){
				IBlockState repeaterstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite(), 2));
				if(repeaterstate != null && repeaterstate.getBlock() == Blocks.POWERED_REPEATER){
				    EnumFacing repeaterfacing = checkstate.getValue(BlockRedstoneRepeater.FACING);
					if(repeaterfacing == enumfacing ){
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isSameBeamDirection(IBlockState state, EnumFacing enumfacing){
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == ModBlocks.laserBeam){
		    EnumFacing beamfacing = checkstate.getValue(LaserBeam.FACING);
			if(beamfacing == enumfacing ){
				return true;
			}
		}
		return false;
	}

}