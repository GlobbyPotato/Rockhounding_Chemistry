package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.machines.gui.GuiAirCompresser;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityAirCompresser extends TileEntityMachineInv{

	public int airCount;

    public int updating;

	public TileEntityAirCompresser() {
		super(0, 0);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiAirCompresser.HEIGHT;
	}

	public int getAir(){
		return this.airCount > this.getAirMax() ? this.getAirMax() : this.airCount;
	}

	public int getAirMax(){
		return 30000;
	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.airCount = compound.getInteger("AirCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        compound.setInteger("AirCount", this.airCount);
		return compound;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {	
		if(!this.worldObj.isRemote){
			if (getAir() != this.updating) {
				this.updating = getAir();
				this.markDirtyClient();
			}
		}
	}
}