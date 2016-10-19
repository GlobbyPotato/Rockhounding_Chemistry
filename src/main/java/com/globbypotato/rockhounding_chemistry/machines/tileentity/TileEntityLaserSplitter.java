package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.LaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.LaserSplitter;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserSplitter extends TileEntity implements ITickable {
	private int countBeam;
	private int maxRange = 30;
	private boolean[] firstObstacle = new boolean[4];
	public int splitSide;
	public boolean isPulsing;

	
	@Override
	public void update() {
		if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
		    EnumFacing enumfacing = state.getValue(LaserSplitter.FACING);
		    EnumFacing beamarrived = enumfacing.getOpposite();
		    if(isReceivingLaser(enumfacing, beamarrived)){

		    	if(pulseMode()){
			    	for(int sides = 0; sides <= 3; sides++){
			    		if(sides != state.getValue(LaserSplitter.FACING).getHorizontalIndex()){
			    			shutBeam(state, enumfacing, sides);
			    		}
			    	}
				}

		    	for(int sides = 0; sides <= 3; sides++){
		    		if(emittingSide() == 4){
			    		if(sides != state.getValue(LaserSplitter.FACING).getHorizontalIndex()){
			    			sendBeam(state, enumfacing, sides);
			    		}
		    		}
		    		else{
			    		if(sides != state.getValue(LaserSplitter.FACING).getHorizontalIndex()){
				    		if(emittingSide() == sides){
				    			sendBeam(state, enumfacing, sides);
				    		}else{
				    			shutBeam(state, enumfacing, sides);
				    		}
			    		}
		    		}
		    	}
		    }else{
		    	for(int sides = 0; sides <= 3; sides++){
		    		if(sides != state.getValue(LaserSplitter.FACING).getHorizontalIndex()){
		    			shutBeam(state, enumfacing, sides);
		    		}
		    	}
		    }
		}
	}

	private void shutBeam(IBlockState state, EnumFacing enumfacing, int sides) {
	    EnumFacing beamsent = enumfacing.getHorizontal(sides);
    	if(worldObj.getBlockState(pos.offset(beamsent, 1)).getBlock() == ModBlocks.laserBeam &&
   		   worldObj.getBlockState(pos.offset(beamsent, 1)).getValue(LaserBeam.FACING) == beamsent){
    		worldObj.setBlockState(pos.offset(beamsent, 1), Blocks.AIR.getDefaultState());
    	}
	}

	private void sendBeam(IBlockState state, EnumFacing enumfacing, int sides) {
		firstObstacle[sides] = false;
		if(countBeam >= maxRange){
    		for(int x = 1; x <= maxRange; x++){
			    EnumFacing beamsent = enumfacing.getHorizontal(sides);
    			BlockPos checkingPos = pos.offset(beamsent, x);
				IBlockState checkingState = worldObj.getBlockState(checkingPos); 
    			if(firstObstacle[sides] == false){
				    if(checkingState.getBlock() == Blocks.AIR){
						IBlockState beamState = ModBlocks.laserBeam.getDefaultState().withProperty(LaserBeam.FACING, beamsent); 
				    	worldObj.setBlockState(checkingPos, beamState);
			    		firstObstacle[sides] = false;
				    }else if(checkingState.getBlock() == ModBlocks.laserBeam){
				    	if(checkingState.getValue(LaserBeam.FACING) == beamsent ) {
				    		firstObstacle[sides] = false;
					    }else{
					    	firstObstacle[sides] = true;
				    	}
				    }else{
				    	firstObstacle[sides] = true;
				    }
    			}
    		}
			countBeam = 0;
		}else{
			countBeam++;
		}
	}

	private boolean isReceivingLaser(EnumFacing enumfacing, EnumFacing beamFacing) {
		return worldObj.getBlockState(pos.offset(enumfacing)).getBlock() == ModBlocks.laserBeam
				&& worldObj.getBlockState(pos.offset(enumfacing)).getValue(LaserSplitter.FACING) == beamFacing;
	}

	public int emittingSide(){
		return splitSide;
	}
	
	public boolean pulseMode(){
		return isPulsing;
	}
    
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
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

}