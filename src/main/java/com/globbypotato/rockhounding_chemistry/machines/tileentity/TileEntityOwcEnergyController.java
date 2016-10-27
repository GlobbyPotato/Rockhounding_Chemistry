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

    protected int yeldCount;
    protected int powerCount;
    protected int maxCapacity;

    protected int tideInterval;

    public int storageMax(){
    	return 500000;
    }

    public int maxYeld(){
    	return 750;
    }

    public int tideChance(){
    	return 120;
    }

    public int rfTransfer(){
    	return 20;
    }

    public int totalVolume(){
    	return 3960;
    }
    
    protected int volumeLimit(){
    	return 1000;
    }

    public int totalTide(){
    	return 240;
    }

    protected int tideLimit(){
    	return 90;
    }

    protected int sanityCheckChance(){
    	return 60;
    }

    protected boolean hasPower(){
    	return powerCount >= rfTransfer();
    }

    protected void powerExtraction() {
	    IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing facing = state.getValue(OwcController.FACING);
		for(int side = 0; side < 6; side++){
			TileEntity checkTile = this.worldObj.getTileEntity(pos.offset(facing.getFront(side)));
			if(checkTile != null){
				if(powerCount >= rfTransfer()) {
					sendPower(checkTile, rfTransfer());
				}				
			}
		}
		markDirty();
	}

	protected void sendPower(TileEntity tileentity, int transfer) {
		if (tileentity instanceof IEnergyReceiver) {
			IEnergyReceiver te = (IEnergyReceiver) tileentity;
			if (te.getEnergyStored(null) < te.getMaxEnergyStored(null)) {
				te.receiveEnergy(EnumFacing.SOUTH, transfer, false);
				powerCount -= transfer;
			}
		} else if (tileentity instanceof IEnergyStorage) {
			IEnergyStorage te = (IEnergyStorage) tileentity;
			if (te.getEnergyStored() < te.getMaxEnergyStored()) {
				te.receiveEnergy(transfer, false);
				powerCount -= transfer;
			}
		}
	}




	//COFH SUPPORT
	@Override
	public int getEnergyStored(EnumFacing from) {
		return powerCount;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storageMax();
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
		return powerCount;
	}

	@Override
	public int getMaxEnergyStored() {
		return maxCapacity;
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