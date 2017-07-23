package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserTX extends TileEntityLaserBase {

	public boolean isPowered(IBlockState state, EnumFacing enumfacing) {
	    if(worldObj.getBlockState(pos.offset(enumfacing, 1)).getBlock() == repeater()  ){
		    EnumFacing repeaterfacing = worldObj.getBlockState(pos.offset(enumfacing, 1)).getValue(repeaterFacing());
		    if(enumfacing == repeaterfacing){
		    	return true;
		    }
	    }
	    return false;
	}

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    EnumFacing enumfacing = state().getValue(txFacing());
		    EnumFacing beamFacing = enumfacing;
			firstObstacle = false;
			if(countBeam >= maxRange){
				if(isPowered(state(), enumfacing.getOpposite())){
		    		for(int x = 1; x <= maxRange; x++){
		    			BlockPos checkingPos = pos.offset(enumfacing, x);
						IBlockState checkingState = worldObj.getBlockState(checkingPos); 
		    			if(firstObstacle == false){
						    if(checkingState.getBlock() == air().getBlock()){
								IBlockState beamState = beam().getDefaultState().withProperty(beamFacing(), beamFacing); 
						    	worldObj.setBlockState(checkingPos, beamState);
					    		firstObstacle = false;
						    }else if(checkingState.getBlock() == beam()){
						    	if(checkingState.getValue(beamFacing()) == beamFacing ) {
						    		firstObstacle = false;
							    }else{
							    	firstObstacle = true;
						    	}
						    }else{
						    	firstObstacle = true;
						    }
		    			}
		    		}
			    }else{
			    	if(worldObj.getBlockState(pos.offset(enumfacing)).getBlock() == beam()){
					    EnumFacing beamfacing = worldObj.getBlockState(pos.offset(enumfacing)).getValue(beamFacing());
					    if(beamfacing == enumfacing){
					    	worldObj.setBlockState(pos.offset(enumfacing), air());
					    }
			    	}
			    }
				countBeam = 0;
			}else{
				countBeam++;
			}
		}
	}

}