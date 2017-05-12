package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.enums.EnumLaser;
import com.globbypotato.rockhounding_chemistry.machines.LaserRX;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserRX extends TileEntityLaserStorage{
	EnumFacing pinFacing = EnumFacing.UP;

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    //RECEIVER
			if(isUnpoweredRx(state())){
				if(isReceivingLaser(state())){
					worldObj.setBlockState(pos, rx().getDefaultState().withProperty(LaserRX.VARIANT, EnumLaser.values()[state().getBlock().getMetaFromState(state()) + 1]));
				}
			}else if(isPoweredRx(state())){
				if(!isReceivingLaser(state())){
					worldObj.setBlockState(pos, rx().getDefaultState().withProperty(LaserRX.VARIANT, EnumLaser.values()[state().getBlock().getMetaFromState(state()) - 1]));
				}
			}

			//TX PIN
			if(isEmittingPin(state())){
				if(state().getBlock().getMetaFromState(state()) == 4){ pinFacing = EnumFacing.UP; }else if(state().getBlock().getMetaFromState(state()) == 6){ pinFacing = EnumFacing.DOWN; }
				firstObstacle = false;
				if(countBeam >= maxRange){
					if(isReceivingLaser(state())){
			    		for(int x = 1; x <= maxRange; x++){
			    			BlockPos checkingPos = pos.offset(pinFacing, x);
							IBlockState checkingState = worldObj.getBlockState(checkingPos); 
			    			if(firstObstacle == false){
							    if(checkingState.getBlock() == air().getBlock()){
									IBlockState beamState = beam().getDefaultState().withProperty(beamFacing(), pinFacing); 
							    	worldObj.setBlockState(checkingPos, beamState);
						    		firstObstacle = false;
							    }else if(checkingState.getBlock() == beam()){
							    	if(checkingState.getValue(beamFacing()) == pinFacing ) {
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
				    	if(worldObj.getBlockState(pos.offset(pinFacing)).getBlock() == beam()){
					    	worldObj.setBlockState(pos.offset(pinFacing), air());
				    	}
					}
					countBeam = 0;
				}else{
					countBeam++;
				}
			}

			//RX PIN
			if(isReceivingPin(state())){
				if(state().getBlock().getMetaFromState(state()) == 5){ 
					pinFacing = EnumFacing.DOWN; 
				}else if(state().getBlock().getMetaFromState(state()) == 7){ 
					pinFacing = EnumFacing.UP; 
				}
				if(isReceivingLaser(state(), pinFacing)){
			    	if(pulseMode()){
				    	for(int sides = 0; sides <= 3; sides++){
			    			shutBeam(state(), sides);
				    	}
					}

			    	for(int sides = 0; sides <= 3; sides++){
			    		if(emittingSide() == 4){
			    			sendBeam(state(), sides);
			    		}
			    		else{
				    		if(emittingSide() == sides){
				    			sendBeam(state(), sides);
				    		}else{
				    			shutBeam(state(), sides);
				    		}
			    		}
			    	}
			    }else{
			    	for(int sides = 0; sides <= 3; sides++){
		    			shutBeam(state(), sides);
			    	}
			    }
			}
		}
	}

	private void sendBeam(IBlockState state, int sides) {
		firstObstacles[sides] = false;
		if(countBeam >= maxRange){
    		for(int x = 1; x <= maxRange; x++){
			    EnumFacing beamsent = EnumFacing.getHorizontal(sides);
    			BlockPos checkingPos = pos.offset(beamsent, x);
				IBlockState checkingState = worldObj.getBlockState(checkingPos); 
    			if(firstObstacles[sides] == false){
				    if(checkingState.getBlock() == air().getBlock()){
						IBlockState beamState = beam().getDefaultState().withProperty(beamFacing(), beamsent); 
				    	worldObj.setBlockState(checkingPos, beamState);
				    	firstObstacles[sides] = false;
				    }else if(checkingState.getBlock() == beam()){
				    	if(checkingState.getValue(beamFacing()) == beamsent ) {
				    		firstObstacles[sides] = false;
					    }else{
					    	firstObstacles[sides] = true;
				    	}
				    }else{
				    	firstObstacles[sides] = true;
				    }
    			}
    		}
			countBeam = 0;
		}else{
			countBeam++;
		}
	}

	private void shutBeam(IBlockState state, int sides) {
	    EnumFacing beamsent = EnumFacing.getHorizontal(sides);
    	if(worldObj.getBlockState(pos.offset(beamsent)).getBlock() == beam() && worldObj.getBlockState(pos.offset(beamsent)).getValue(beamFacing()) == beamsent){
    		worldObj.setBlockState(pos.offset(beamsent), air());
    	}
	}

	private boolean isReceivingLaser(IBlockState state, EnumFacing beamArriving) {
		return worldObj.getBlockState(pos.offset(beamArriving.getOpposite())).getBlock() == beam()
			&& worldObj.getBlockState(pos.offset(beamArriving.getOpposite())).getValue(beamFacing()) == beamArriving;
	}

	private boolean isEmittingPin(IBlockState state) {
		return state.getBlock().getMetaFromState(state) == 4 || state.getBlock().getMetaFromState(state) == 6;
	}

	private boolean isReceivingPin(IBlockState state) {
		return state.getBlock().getMetaFromState(state) == 5 || state.getBlock().getMetaFromState(state) == 7;
	}

	private boolean isUnpoweredRx(IBlockState state) {
		return state.getBlock().getMetaFromState(state) == 0 || state.getBlock().getMetaFromState(state) == 2;
	}

	private boolean isPoweredRx(IBlockState state) {
		return state.getBlock().getMetaFromState(state) == 1 || state.getBlock().getMetaFromState(state) == 3;
	}

	private boolean isReceivingLaser(IBlockState state) {
		return worldObj.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == beam() 
			|| worldObj.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == beam() 
			|| worldObj.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == beam() 
			|| worldObj.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == beam();
	}

}