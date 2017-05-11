package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumLaser;
import com.globbypotato.rockhounding_chemistry.machines.LaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.LaserRX;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserRX extends TileEntity implements ITickable{
	private int countBeam;
	private int maxRange = 32;
	private boolean firstObstacle;
	private boolean[] firstObstacles = new boolean[4];
	EnumFacing pinFacing = EnumFacing.UP;
	public boolean isPulsing;
	public int splitSide;

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
		    //RECEIVER
			if(isUnpoweredRx(state)){
				if(isReceivingLaser(state)){
					worldObj.setBlockState(pos, ModBlocks.laserRedstoneRx.getDefaultState().withProperty(LaserRX.VARIANT, EnumLaser.values()[state.getBlock().getMetaFromState(state) + 1]));
				}
			}else if(isPoweredRx(state)){
				if(!isReceivingLaser(state)){
					worldObj.setBlockState(pos, ModBlocks.laserRedstoneRx.getDefaultState().withProperty(LaserRX.VARIANT, EnumLaser.values()[state.getBlock().getMetaFromState(state) - 1]));
				}
			}

			//TX PIN
			if(isEmittingPin(state)){
				if(state.getBlock().getMetaFromState(state) == 4){ pinFacing = EnumFacing.UP; }else if(state.getBlock().getMetaFromState(state) == 6){ pinFacing = EnumFacing.DOWN; }
				firstObstacle = false;
				if(countBeam >= maxRange){
					if(isReceivingLaser(state)){
			    		for(int x = 1; x <= maxRange; x++){
			    			BlockPos checkingPos = pos.offset(pinFacing, x);
							IBlockState checkingState = worldObj.getBlockState(checkingPos); 
			    			if(firstObstacle == false){
							    if(checkingState.getBlock() == Blocks.AIR){
									IBlockState beamState = ModBlocks.laserBeam.getDefaultState().withProperty(LaserBeam.FACING, pinFacing); 
							    	worldObj.setBlockState(checkingPos, beamState);
						    		firstObstacle = false;
							    }else if(checkingState.getBlock() == ModBlocks.laserBeam){
							    	if(checkingState.getValue(LaserBeam.FACING) == pinFacing ) {
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
				    	if(worldObj.getBlockState(pos.offset(pinFacing)).getBlock() == ModBlocks.laserBeam){
					    	worldObj.setBlockState(pos.offset(pinFacing), Blocks.AIR.getDefaultState());
				    	}
					}
					countBeam = 0;
				}else{
					countBeam++;
				}
			}

			//RX PIN
			if(isReceivingPin(state)){
				if(state.getBlock().getMetaFromState(state) == 5){ pinFacing = EnumFacing.DOWN; }else if(state.getBlock().getMetaFromState(state) == 7){ pinFacing = EnumFacing.UP; }
				if(isReceivingLaser(state, pinFacing)){
			    	if(pulseMode()){
				    	for(int sides = 0; sides <= 3; sides++){
			    			shutBeam(state, sides);
				    	}
					}

			    	for(int sides = 0; sides <= 3; sides++){
			    		if(emittingSide() == 4){
			    			sendBeam(state, sides);
			    		}
			    		else{
				    		if(emittingSide() == sides){
				    			sendBeam(state, sides);
				    		}else{
				    			shutBeam(state, sides);
				    		}
			    		}
			    	}
			    }else{
			    	for(int sides = 0; sides <= 3; sides++){
		    			shutBeam(state, sides);
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
				    if(checkingState.getBlock() == Blocks.AIR){
						IBlockState beamState = ModBlocks.laserBeam.getDefaultState().withProperty(LaserBeam.FACING, beamsent); 
				    	worldObj.setBlockState(checkingPos, beamState);
				    	firstObstacles[sides] = false;
				    }else if(checkingState.getBlock() == ModBlocks.laserBeam){
				    	if(checkingState.getValue(LaserBeam.FACING) == beamsent ) {
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
    	if(worldObj.getBlockState(pos.offset(beamsent)).getBlock() == ModBlocks.laserBeam &&
   		   worldObj.getBlockState(pos.offset(beamsent)).getValue(LaserBeam.FACING) == beamsent){
    		worldObj.setBlockState(pos.offset(beamsent), Blocks.AIR.getDefaultState());
    	}
	}

	public int emittingSide(){
		return splitSide;
	}

	public boolean pulseMode(){
		return isPulsing;
	}

	private boolean isReceivingLaser(IBlockState state, EnumFacing beamArriving) {
		return worldObj.getBlockState(pos.offset(beamArriving.getOpposite())).getBlock() == ModBlocks.laserBeam && worldObj.getBlockState(pos.offset(beamArriving.getOpposite())).getValue(LaserBeam.FACING) == beamArriving;
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
		return worldObj.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == ModBlocks.laserBeam ||
			   worldObj.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == ModBlocks.laserBeam ||
			   worldObj.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == ModBlocks.laserBeam ||
			   worldObj.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == ModBlocks.laserBeam;
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