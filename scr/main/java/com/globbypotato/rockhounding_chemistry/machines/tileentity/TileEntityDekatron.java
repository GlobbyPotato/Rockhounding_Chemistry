package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.Dekatron;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityDekatron extends TileEntity implements ITickable {
	private boolean oldState;
	private int pulseTick;
	private int pulseCount;
	private int checkBState;
	private int outputState;
	private int countCycle;
	private int pulseMax = 10;
	public int selectMode = 1;
	public int maxModes = 6;

	@Override
	public void update() {
		if(selectMode == 1){
			executeSingleState();//count each state change
			produceImpulse();
		}else if(selectMode == 2){
			executeBiState();//count bi-states
			produceImpulse();
		}else if(selectMode == 3){
			executeCycle();//count cycles
			produceSteady();
		}else if(selectMode == 4){
			executeBiCycle();//count bi-states cycles
			produceSteady();
		}else if(selectMode == 5){
			pulseTimer();//pulse by frequency
			produceImpulse();
		}else if(selectMode == 6){
			steadyTimer();//steady by frequency
			//produceImpulse();
		}
	}

	private void steadyTimer() {
		if(isDekaOff() && isPowered()){
			if(pulseTick >= 10 + ((15 - getRedstonePower()) * 10 )){
				pulseTick = 0;
				enableDeka();
				this.markDirtyClient();
			}else{
				pulseTick++;
			}
		}else if(isDekaOn() && isPowered()){
			if(pulseTick >= 10 + ((15 - getRedstonePower()) * 10 )){
				pulseTick = 0;
				disableDeka();
				this.markDirtyClient();
			}else{
				pulseTick++;
			}
		}else if(isDekaOn() && !isPowered()){
			pulseTick = 0;
			disableDeka();
			this.markDirtyClient();
		}
	}

	private void pulseTimer() {
		if(isDekaOff() && isPowered()){
			if(pulseTick >= 10 + ((15 - getRedstonePower()) * 10 )){
				enableDeka();
				pulseTick = 0;
				this.markDirtyClient();
			}else{
				pulseTick++;
			}
		}
	}

	private void executeBiCycle() {
		if(countCycle < 2){
			if(pulseCount < pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(oldState != flag){
					checkBState++;
					oldState = !oldState;
					this.markDirtyClient();
				}
				if(checkBState == 2){
					pulseCount++;
					checkBState = 0;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				pulseCount = 0;
				countCycle++;
				this.markDirtyClient();
			}
		}
	}

	private void executeCycle() {
		if(countCycle < 2){
			if(pulseCount < pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(oldState != flag){
					pulseCount++;
					oldState = !oldState;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				countCycle++;
				pulseCount = 0;
				this.markDirtyClient();
			}
		}
	}

	private void produceSteady() {
		if(countCycle == 2){
			disableDeka();
			countCycle = 0;
			this.markDirtyClient();
		}
	}

	private void executeBiState() {
		if(isDekaOff()){
			if(pulseCount < pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(oldState != flag){
					checkBState++;
					oldState = !oldState;
					this.markDirtyClient();
				}
				if(checkBState == 2){
					pulseCount++;
					checkBState = 0;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				pulseCount = 0;
				this.markDirtyClient();
			}
		}
	}

	private void executeSingleState() {
		if(isDekaOff()){
			if(pulseCount < pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(oldState != flag){
					pulseCount++;
					oldState = !oldState;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				pulseCount = 0;
				this.markDirtyClient();
			}
		}
	}

	private void produceImpulse() {
		if(isDekaOn()){
			if(outputState >= 20){
				disableDeka();
				outputState = 0;
				this.markDirtyClient();
			}else{
				outputState++;
			}
		}
	}

	private void enableDeka() {
		worldObj.setBlockState(pos, getLevel(1));
	}

	private void disableDeka() {
		worldObj.setBlockState(pos, getLevel(0));
	}

	private boolean checkForPoweredBlocks() {
		return worldObj.isBlockPowered(pos.offset(worldObj.getBlockState(pos).getValue(Dekatron.FACING).getOpposite()));
	}

	private int getRedstonePower(){
		return worldObj.getRedstonePower(pos.offset(worldObj.getBlockState(pos).getValue(Dekatron.FACING).getOpposite()), worldObj.getBlockState(pos).getValue(Dekatron.FACING).getOpposite());
	}

	private boolean isPowered(){
		return checkForPoweredBlocks() && getRedstonePower() > 0;
	}

	private IBlockState getLevel(int i) {
		EnumFacing dekaFacing = worldObj.getBlockState(pos).getValue(Dekatron.FACING);
		return ModBlocks.dekatron.getDefaultState().withProperty(Dekatron.FACING, dekaFacing).withProperty(Dekatron.LEVEL, Integer.valueOf(i));
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	private boolean isDekaOff() {
		return worldObj.getBlockState(pos).getBlock() == ModBlocks.dekatron && worldObj.getBlockState(pos).getValue(Dekatron.LEVEL) == 0;
	}

	private boolean isDekaOn() {
		return worldObj.getBlockState(pos).getBlock() == ModBlocks.dekatron && worldObj.getBlockState(pos).getValue(Dekatron.LEVEL) == 1;
	}

	//Courtesy of mcjtylib
	public void markDirtyClient() {
		markDirty();
		if (worldObj != null) {
			IBlockState state = worldObj.getBlockState(getPos());
			worldObj.notifyBlockUpdate(getPos(), state, state, 3);
		}
	}

	//----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.pulseCount = compound.getInteger("Pulse");
        this.countCycle = compound.getInteger("CountCycle");
        this.selectMode = compound.getInteger("SelectMode");
        this.oldState = compound.getBoolean("OldState");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("Pulse", this.pulseCount);
        compound.setInteger("CountCycle", this.countCycle);
        compound.setInteger("SelectMode", this.selectMode);
        compound.setBoolean("OldState", this.oldState);
        return compound;
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = getUpdateTag();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
	    NBTTagCompound tag = packet.getNbtCompound();
	    handleUpdateTag(tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		this.readFromNBT(tag);
	}
}