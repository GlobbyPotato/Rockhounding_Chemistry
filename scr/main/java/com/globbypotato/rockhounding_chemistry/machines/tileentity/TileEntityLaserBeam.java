package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class TileEntityLaserBeam extends TileEntityLaserBase {

	@Override
	public void update() {
	    EnumFacing enumfacing = state().getValue(beamFacing());
	    if(!worldObj.isRemote){
	    	if( !isSameBeamDirection(state(), enumfacing) 
	    		&& !isValidEmitter(state(), enumfacing) 
	    		&& !isValidSplitter(state(), enumfacing)
	    		&& !isValidTxPin(state(), enumfacing, 4, EnumFacing.UP) 
	    		&& !isValidTxPin(state(), enumfacing, 6, EnumFacing.DOWN) 
	    		&& !isValidRxPin(state(), enumfacing)
	    		){ worldObj.setBlockState(pos, air());
	    	}
	    }
	}

	private boolean isValidRxPin(IBlockState state, EnumFacing enumfacing) {
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == rx() && (checkstate.getBlock().getMetaFromState(checkstate) == 5 || checkstate.getBlock().getMetaFromState(checkstate) == 7)){
			return true;
		}
		return false;
	}

	private boolean isValidTxPin(IBlockState state, EnumFacing enumfacing, int meta, EnumFacing pinfacing) {
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == rx() && checkstate.getBlock().getMetaFromState(checkstate) == meta){
			if(enumfacing == pinfacing){
				return true;
			}
		}
		return false;
	}

	private boolean isValidSplitter(IBlockState state, EnumFacing enumfacing){
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == splitter()){
		    EnumFacing splitterfacing = checkstate.getValue(nodeFacing());
			if(splitterfacing != enumfacing ){
				return true;
			}
		}
		return false;
	}

	private boolean isValidEmitter(IBlockState state, EnumFacing enumfacing) {
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == tx()){
		    EnumFacing txfacing = checkstate.getValue(txFacing());
			if(txfacing == enumfacing ){
				IBlockState repeaterstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite(), 2));
				if(repeaterstate != null && repeaterstate.getBlock() == repeater()){
				    EnumFacing repeaterfacing = checkstate.getValue(repeaterFacing());
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
		if(checkstate != null && checkstate.getBlock() == beam()){
		    EnumFacing beamfacing = checkstate.getValue(beamFacing());
			if(beamfacing == enumfacing ){
				return true;
			}
		}
		return false;
	}

}