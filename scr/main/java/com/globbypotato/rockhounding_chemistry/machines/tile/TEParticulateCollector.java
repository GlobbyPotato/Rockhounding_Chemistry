package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEParticulateCollector extends TileEntityInv {

	public static int outputSlots = 2;
	public static int templateSlots = 2;

	public static final int PRIMARY_SLAG = 0;
	public static final int SECONDARY_SLAG = 1;
	public static final int PRIMARY_PREVIEW = 0;
	public static final int SECONDARY_PREVIEW = 1;

	public int primaryCount;
	public int secondaryCount;

	public TEParticulateCollector() {
		super(0, outputSlots, templateSlots, 0);
	}



	//----------------------- SLOT -----------------------
	public ItemStack primSlot(){
		return this.output.getStackInSlot(PRIMARY_SLAG);
	}

	public ItemStack secoSlot(){
		return this.output.getStackInSlot(SECONDARY_SLAG);
	}

	public ItemStack primTempSlot(){
		return this.template.getStackInSlot(PRIMARY_PREVIEW);
	}

	public ItemStack secoTempSlot(){
		return this.template.getStackInSlot(SECONDARY_PREVIEW);
	}


	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.primaryCount = compound.getInteger("PrimaryCount");
		this.secondaryCount = compound.getInteger("SecondaryCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PrimaryCount", getPrimaryCount());
		compound.setInteger("SecondaryCount", getSecondaryCount());
		return compound;
	}


	
	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "particulate_collector";
	}



	//----------------------- CUSTOM -----------------------
	public int getPrimaryCount(){
		return this.primaryCount;
	}

	public int getSecondaryCount(){
		return this.secondaryCount;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			handleSlag();
		}
	}

	private void handleSlag() {
		if(getPrimaryCount() >= 100){
			if(!this.template.getStackInSlot(PRIMARY_PREVIEW).isEmpty()){
				if(this.output.canSetOrStack(primSlot(), this.template.getStackInSlot(PRIMARY_PREVIEW).copy())){
					this.output.setOrStack(PRIMARY_SLAG, this.template.getStackInSlot(PRIMARY_PREVIEW).copy());
					this.primaryCount -= 100;
					this.markDirtyClient();
				}
			}
		}

		if(getSecondaryCount() >= 100){
			if(!this.template.getStackInSlot(SECONDARY_PREVIEW).isEmpty()){
				if(this.output.canSetOrStack(secoSlot(), this.template.getStackInSlot(SECONDARY_PREVIEW).copy())){
					this.output.setOrStack(SECONDARY_SLAG, this.template.getStackInSlot(SECONDARY_PREVIEW).copy());
					this.secondaryCount -= 100;
					this.markDirtyClient();
				}
			}
		}

	}

	public void handlePreview(boolean hasMainSlag, ItemStack getMainSlag, boolean hasAltSlag, ItemStack getAltSlag) {
		boolean flag = false;

		if(hasMainSlag){
			if(this.primTempSlot().isEmpty() || !this.primTempSlot().isItemEqual(getMainSlag)){
				this.template.setStackInSlot(PRIMARY_PREVIEW, getMainSlag.copy());
				this.primTempSlot().setCount(1);
				flag= true;
			}
		}else{
			if(!this.primTempSlot().isEmpty() && getPrimaryCount() < 1){
				this.template.setStackInSlot(PRIMARY_PREVIEW, ItemStack.EMPTY);
				this.primaryCount = 0;
				flag= true;
			}
		}

		if(hasAltSlag){
			if(this.secoTempSlot().isEmpty() || !this.secoTempSlot().isItemEqual(getAltSlag)){
				this.template.setStackInSlot(SECONDARY_PREVIEW, getAltSlag.copy());
				this.secoTempSlot().setCount(1);
				flag= true;
			}
		}else{
			if(!this.secoTempSlot().isEmpty() && getSecondaryCount() < 1){
				this.template.setStackInSlot(SECONDARY_PREVIEW, ItemStack.EMPTY);
				this.secondaryCount = 0;
				flag= true;
			}
		}

		if(flag){
			this.markDirtyClient();
		}

	}

	public void handleParticulate(boolean hasMainSlag, int main_slag, boolean hasAltSlag, int secondary_slag) {
		if(hasMainSlag){
			if(this.rand.nextInt(100) < main_slag){
				this.primaryCount += this.rand.nextInt(10) + 1;
				this.markDirtyClient();
			}
		}
		if(hasAltSlag){
			if(this.rand.nextInt(100) < secondary_slag){
				this.secondaryCount += this.rand.nextInt(5) + 1;
				this.markDirtyClient();
			}
		}

	}
}