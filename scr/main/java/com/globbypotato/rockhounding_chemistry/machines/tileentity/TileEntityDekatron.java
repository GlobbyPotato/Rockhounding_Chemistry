package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.BaseMachine;
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
		if(this.selectMode == 1){
			executeSingleState();//count each state change
			produceImpulse();
		}else if(this.selectMode == 2){
			executeBiState();//count bi-states
			produceImpulse();
		}else if(this.selectMode == 3){
			executeCycle();//count cycles
			produceSteady();
		}else if(this.selectMode == 4){
			executeBiCycle();//count bi-states cycles
			produceSteady();
		}else if(this.selectMode == 5){
			pulseTimer();//pulse by frequency
			produceImpulse();
		}else if(this.selectMode == 6){
			steadyTimer();//steady by frequency
			//produceImpulse();
		}
	}

	private void steadyTimer() {
		if(isDekaOff() && isPowered()){
			if(this.pulseTick >= 10 + ((15 - getRedstonePower()) * 10 )){
				this.pulseTick = 0;
				enableDeka();
				this.markDirtyClient();
			}else{
				this.pulseTick++;
			}
		}else if(isDekaOn() && isPowered()){
			if(this.pulseTick >= 10 + ((15 - getRedstonePower()) * 10 )){
				this.pulseTick = 0;
				disableDeka();
				this.markDirtyClient();
			}else{
				this.pulseTick++;
			}
		}else if(isDekaOn() && !isPowered()){
			this.pulseTick = 0;
			disableDeka();
			this.markDirtyClient();
		}
	}

	private void pulseTimer() {
		if(isDekaOff() && isPowered()){
			if(this.pulseTick >= 10 + ((15 - getRedstonePower()) * 10 )){
				enableDeka();
				this.pulseTick = 0;
				this.markDirtyClient();
			}else{
				this.pulseTick++;
			}
		}
	}

	private void executeBiCycle() {
		if(this.countCycle < 2){
			if(this.pulseCount < this.pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(this.oldState != flag){
					this.checkBState++;
					this.oldState = !this.oldState;
					this.markDirtyClient();
				}
				if(this.checkBState == 2){
					this.pulseCount++;
					this.checkBState = 0;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				this.pulseCount = 0;
				this.countCycle++;
				this.markDirtyClient();
			}
		}
	}

	private void executeCycle() {
		if(this.countCycle < 2){
			if(this.pulseCount < this.pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(this.oldState != flag){
					this.pulseCount++;
					this.oldState = !this.oldState;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				this.countCycle++;
				this.pulseCount = 0;
				this.markDirtyClient();
			}
		}
	}

	private void produceSteady() {
		if(this.countCycle == 2){
			disableDeka();
			this.countCycle = 0;
			this.markDirtyClient();
		}
	}

	private void executeBiState() {
		if(isDekaOff()){
			if(this.pulseCount < this.pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(this.oldState != flag){
					this.checkBState++;
					this.oldState = !this.oldState;
					this.markDirtyClient();
				}
				if(this.checkBState == 2){
					this.pulseCount++;
					this.checkBState = 0;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				this.pulseCount = 0;
				this.markDirtyClient();
			}
		}
	}

	private void executeSingleState() {
		if(isDekaOff()){
			if(this.pulseCount < this.pulseMax){
				boolean flag = checkForPoweredBlocks();
				if(this.oldState != flag){
					this.pulseCount++;
					this.oldState = !this.oldState;
					this.markDirtyClient();
				}
			}else{
				enableDeka();
				this.pulseCount = 0;
				this.markDirtyClient();
			}
		}
	}

	private void produceImpulse() {
		if(isDekaOn()){
			if(this.outputState >= 20){
				disableDeka();
				this.outputState = 0;
				this.markDirtyClient();
			}else{
				this.outputState++;
			}
		}
	}

	private void enableDeka() {
		this.worldObj.setBlockState(this.pos, getLevel(1));
	}

	private void disableDeka() {
		this.worldObj.setBlockState(this.pos, getLevel(0));
	}

	private boolean checkForPoweredBlocks() {
		return this.worldObj.isBlockPowered(this.pos.offset(this.worldObj.getBlockState(this.pos).getValue(BaseMachine.FACING).getOpposite()));
	}

	private int getRedstonePower(){
		return this.worldObj.getRedstonePower(this.pos.offset(this.worldObj.getBlockState(this.pos).getValue(BaseMachine.FACING).getOpposite()), this.worldObj.getBlockState(this.pos).getValue(BaseMachine.FACING).getOpposite());
	}

	private boolean isPowered(){
		return checkForPoweredBlocks() && getRedstonePower() > 0;
	}

	private IBlockState getLevel(int i) {
		EnumFacing dekaFacing = this.worldObj.getBlockState(this.pos).getValue(BaseMachine.FACING);
		return ModBlocks.dekatron.getDefaultState().withProperty(BaseMachine.FACING, dekaFacing).withProperty(Dekatron.LEVEL, Integer.valueOf(i));
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	private boolean isDekaOff() {
		return this.worldObj.getBlockState(this.pos).getBlock() == ModBlocks.dekatron && this.worldObj.getBlockState(this.pos).getValue(Dekatron.LEVEL) == 0;
	}

	private boolean isDekaOn() {
		return this.worldObj.getBlockState(this.pos).getBlock() == ModBlocks.dekatron && this.worldObj.getBlockState(this.pos).getValue(Dekatron.LEVEL) == 1;
	}

	//Courtesy of mcjtylib
	public void markDirtyClient() {
		markDirty();
		if (this.worldObj != null) {
			IBlockState state = this.worldObj.getBlockState(getPos());
			this.worldObj.notifyBlockUpdate(getPos(), state, state, 3);
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
		return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), tag);
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