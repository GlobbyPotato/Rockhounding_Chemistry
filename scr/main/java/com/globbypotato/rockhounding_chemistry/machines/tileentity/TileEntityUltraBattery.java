package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.enums.EnumBattery;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiUltraBattery;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityUltraBattery extends TileEntityMachineEnergy implements IEnergyProvider{

	private ItemStackHandler template = new TemplateStackHandler(7);
	public boolean[] sideStatus = new boolean[7];
	private int getSide;

	public TileEntityUltraBattery() {
		super(0, 0, 0);
	}



	//---------------- HANDLER ----------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiUltraBattery.HEIGHT;
	}

	@Override
	public boolean hasRF(){
		return true;	
	}

	@Override
	public int getRedstoneMax() { 
		IBlockState state = worldObj.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);
		return EnumBattery.values()[meta].getCapacity(); 
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		for(int i = 0; i < sideStatus.length; i++){
			sideStatus[i] = compound.getBoolean("Status" + i);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		for(int i = 0; i < sideStatus.length; i++){
			compound.setBoolean("Status" + i, sideStatus[i]);
		}
		return compound;
	}



	//---------------- PROCESS ----------------
	@Override
	public void update() {
		acceptEnergy();
		if(!worldObj.isRemote){
			provideEnergy();
			this.markDirtyClient();
		}
	}

	private void provideEnergy() {
		if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
		    for(EnumFacing facing: EnumFacing.values()){
		    	getSide = facing.ordinal();
				if(this.getRedstone() >= rfTransfer()){
					TileEntity checkTile = this.worldObj.getTileEntity(pos.offset(facing));
					if(checkTile != null){
						if(sideCanEmit(getSide)){
							sendEnergy(checkTile, facing);
						}
					}
				}
			}
		}
	}

	private boolean sideCanEmit(int side) {
		return sideStatus[side];
	}

	private boolean sideCanReceive() {
		return sideStatus[6];
	}

	//---------------- COFH ----------------
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return this.getRedstoneMax();
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return sideCanReceive() ? this.storage.receiveEnergy(maxReceive, simulate) : 0;
	}



	//---------------- FORGE ----------------
	@Override
	public int getMaxEnergyStored() {
		return this.getRedstoneMax();
	}

	@Override
	public boolean canExtract() {
		return sideCanEmit(getSide);
	}

	@Override
	public boolean canReceive() {
		return sideCanReceive();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return canReceive() ? this.storage.receiveEnergy(maxReceive, simulate) : 0;
	}

}