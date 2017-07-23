package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserSplitter extends TileEntityLaserStorage {
	
    //----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.splitSide = compound.getInteger("SplitSide");
        this.isPulsing = compound.getBoolean("Pulse");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("SplitSide", emittingSide());
        compound.setBoolean("Pulse", pulseMode());
        return compound;
    }

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    EnumFacing enumfacing = state().getValue(nodeFacing());
		    EnumFacing beamarrived = enumfacing.getOpposite();
		    if(isReceivingLaser(enumfacing, beamarrived)){

		    	if(pulseMode()){
			    	for(int sides = 0; sides <= 3; sides++){
			    		if(sides != state().getValue(nodeFacing()).getHorizontalIndex()){
			    			shutBeam(state(), enumfacing, sides);
			    		}
			    	}
				}

		    	for(int sides = 0; sides <= 3; sides++){
		    		if(emittingSide() == 4){
		    			sendBeam(state(), enumfacing, sides);
		    		}else{
			    		if(sides != state().getValue(nodeFacing()).getHorizontalIndex()){
				    		if(emittingSide() == sides){
				    			sendBeam(state(), enumfacing, sides);
				    		}else{
				    			shutBeam(state(), enumfacing, sides);
				    		}
			    		}
		    		}
		    	}
		    }else{
		    	for(int sides = 0; sides <= 3; sides++){
		    		if(sides != state().getValue(nodeFacing()).getHorizontalIndex()){
		    			shutBeam(state(), enumfacing, sides);
		    		}
		    	}
		    }
		}
	}

	private void shutBeam(IBlockState state, EnumFacing enumfacing, int sides) {
	    EnumFacing beamsent = enumfacing.getHorizontal(sides);
    	if(worldObj.getBlockState(pos.offset(beamsent)).getBlock() == beam() && worldObj.getBlockState(pos.offset(beamsent)).getValue(beamFacing()) == beamsent){
    		worldObj.setBlockState(pos.offset(beamsent), air());
    	}
	}

	private void sendBeam(IBlockState state, EnumFacing enumfacing, int sides) {
		firstObstacles[sides] = false;
		if(countBeam >= maxRange){
    		for(int x = 1; x <= maxRange; x++){
			    EnumFacing beamsent = enumfacing.getHorizontal(sides);
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

	private boolean isReceivingLaser(EnumFacing enumfacing, EnumFacing beamArriving) {
		return worldObj.getBlockState(pos.offset(enumfacing)).getBlock() == beam()
			&& worldObj.getBlockState(pos.offset(enumfacing)).getValue(beamFacing()) == beamArriving;
	}

}