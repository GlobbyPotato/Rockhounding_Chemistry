package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public interface IStorage extends IEnergyReceiver, IEnergyStorage{

	abstract int getPower();
	abstract int getPowerMax();
	abstract int getRedstone();
	abstract int getRedstoneMax();
	abstract int getChargeMax();
	abstract int getYeld();
	abstract int getYeldMax();

	abstract boolean hasRF();
	abstract boolean canInduct();

	public default boolean hasPower(){
		return getPower() > 0;
	}

	public default boolean isFullPower(){
		return getPower() >= getPowerMax();
	}

	public default boolean hasRedstone(){
		return getRedstone() > 0;
	}

	public default boolean isFullRedstone(){
		return getRedstone() >= getRedstoneMax();
	}

	public default boolean hasCharge(){
		return getRedstone() > 0;
	}

	public default boolean isFullCharge(){
		return getRedstone() >= getChargeMax();
	}

	//---------------- COFH ----------------
	@Override
	public default int getEnergyStored(EnumFacing from) {
		return this.getRedstone();
	}

	@Override
	public default int getMaxEnergyStored(EnumFacing from) {
		return this.getRedstoneMax();
	}

	@Override
	public default boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	//---------------- FORGE ----------------
	@Override
	public default int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public default int getEnergyStored() {
		return this.getRedstone();
	}

	@Override
	public default int getMaxEnergyStored() {
		return this.getRedstoneMax();
	}

	@Override
	public default boolean canExtract() {
		return false;
	}

	@Override
	public default boolean canReceive() {
		return true;
	}

}