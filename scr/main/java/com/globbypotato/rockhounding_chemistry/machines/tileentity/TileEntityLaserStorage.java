package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileEntityLaserStorage extends TileEntityLaserBase{

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