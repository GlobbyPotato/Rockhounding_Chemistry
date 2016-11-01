package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.machines.OwcController;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class TileEntityOwcEnergyController extends TileEntityOwcBaseController implements IEnergyProvider, IEnergyStorage {
    Random rand = new Random();

    public boolean activationKey;
    public boolean extractionKey;

    public int yeldCount;
    public int[] owcChannels = new int[10];

    protected int tideInterval;

    protected int storageMax(){
    	return 32500;
    }

    public int maxYeld(){
    	return 900;
    }

    public int tideChance(){
    	return 120;
    }

    public int rfTransfer(){
    	return 40;
    }

    protected int totalVolume(){
    	return 3960;
    }

    protected int volumeLimit(){
    	return 1000;
    }

    protected int totalTide(){
    	return 240;
    }

    protected int tideLimit(){
    	return 90;
    }

    public int totalWater(){
    	return totalTide() + totalVolume();
    }

    protected int sanityCheckChance(){
    	return 60;
    }

    protected int activeChannels(){
    	return 5;
    }

    protected int dualityBonus(){
    	return 1;
    }

    public int totalPower(){
    	int totPower = 0;
    	for (int x = 0; x < activeChannels(); x++){
    		totPower += this.owcChannels[x];
    	}
    	return totPower;
	}

    protected int getMaxCapacity(){
    	return this.storageMax();
    }

    public boolean hasPower(){
    	return this.totalPower() > 0;
    }

    protected void powerExtraction() {
	    IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing facing = state.getValue(OwcController.FACING);
		for(int side = 0; side < 6; side++){
			TileEntity checkTile = this.worldObj.getTileEntity(pos.offset(facing.getFront(side)));
			if(checkTile != null){
				if(this.totalPower() >= rfTransfer()) {
					sendPower(checkTile, facing.getFront(side).getOpposite(), rfTransfer());
					markDirty();
				}				
			}
		}
	}

	protected void sendPower(TileEntity tileentity, EnumFacing facing, int transfer) {
		if (tileentity instanceof IEnergyReceiver) {
			IEnergyReceiver te = (IEnergyReceiver) tileentity;
			if (te.getEnergyStored(facing) < te.getMaxEnergyStored(facing)) {
				te.receiveEnergy(facing, transfer, false);
				doTransfer(transfer);
			}
		} else if (tileentity instanceof IEnergyStorage) {
			IEnergyStorage te = (IEnergyStorage) tileentity;
			if (te.getEnergyStored() < te.getMaxEnergyStored()) {
				te.receiveEnergy(transfer, false);
				doTransfer(transfer);
			}
		}
	}

	private void doTransfer(int transfer) {
		int decreaseTransher = transfer;
		for(int x = 0; x < activeChannels(); x++){
			if(decreaseTransher > 0){
				int availableRF = this.owcChannels[x]; 
				if(decreaseTransher <= availableRF){
					this.owcChannels[x] -= decreaseTransher;
					decreaseTransher = 0;
				}else{
					this.owcChannels[x] = 0;
					decreaseTransher -= availableRF;
				}
			}
		}	
	}

	//COFH SUPPORT
	@Override
	public int getEnergyStored(EnumFacing from) {
		return this.totalPower();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return this.getMaxCapacity();
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return rfTransfer();
	}

	//FORGE ENERGY SUPPORT
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return rfTransfer();
	}

	@Override
	public int getEnergyStored() {
		return this.totalPower();
	}

	@Override
	public int getMaxEnergyStored() {
		return this.getMaxCapacity();
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

}