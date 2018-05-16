package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.machines.Disposer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDisposer;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDisposer extends TileEntityMachineEnergy {
	public static final int INPUT_SLOT[] = new int[]{0,1,2,3,4,5,6,7,8,};
	public int interval;
	public boolean lock;
	public List<ItemStack> lockList = new ArrayList<ItemStack>();
	private ItemStackHandler template = new TemplateStackHandler(6);

	public static int totInput = 9;
	public static int totOutput = 0;

	public TileEntityDisposer() {
		super(totInput, totOutput, 0);

		this.input =  new MachineStackHandler(totInput,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= INPUT_SLOT[0] && slot <= INPUT_SLOT.length && insertingStack != null && canPassTheLock(slot, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiDisposer.HEIGHT;
	}



	//----------------------- CUSTOM -----------------------
	@Override
	public boolean isActive(){
		return this.activation || this.worldObj.isBlockPowered(this.pos);
	}

	public int getInterval(){
		return this.interval;
	}

	public boolean isLocked(){
		return this.lock;
	}

	public boolean canPassTheLock(int slot, ItemStack insertingStack) {
		return !isLocked()
			|| (isLocked() && this.lockList.get(slot) != null && this.lockList.get(slot).isItemEqual(insertingStack) );
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.interval = compound.getInteger("Interval");
		this.lock = compound.getBoolean("Locked");
		
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.lockList = new ArrayList<ItemStack>();
        resetLock();
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < this.lockList.size()){
            	this.lockList.add(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Interval", this.interval);
		compound.setBoolean("Locked", this.lock);
		
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.lockList.size(); ++i){
            if (this.lockList.get(i) != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.lockList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);

		return compound;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		acceptEnergy();
		if(this.lockList.size() < 1) {resetLock(); }
		if(!this.worldObj.isRemote){
			if(isActive()){
				this.cookTime++;
				if(this.cookTime >= getInterval()) {
					this.cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private void process() {
		for(Integer slot : INPUT_SLOT){
			if(this.input.getStackInSlot(slot) != null){
				ItemStack drop = this.input.getStackInSlot(slot).copy();
				drop.stackSize = 1;
				dropItemStack(drop);
				this.input.decrementSlot(slot);
			}
		}
	}

	private void dropItemStack(ItemStack itemStack) {
	    IBlockState state = this.worldObj.getBlockState(this.pos);
	    EnumFacing facing = state.getValue(Disposer.FACING);
		BlockPos dropPos = this.pos.offset(facing);
        double d0 = dropPos.getX() + 0.5D;
        double d1 = dropPos.getY() + 0.5D;
        double d2 = dropPos.getZ() + 0.5D;
		EntityItem entityitem = new EntityItem(this.worldObj, d0, d1, d2, itemStack);
		entityitem.motionX = 0;
		entityitem.motionY = 0;
		entityitem.motionZ = 0;
		this.worldObj.spawnEntityInWorld(entityitem);
	}

	public void resetLock(){
		for(Integer slot : INPUT_SLOT){
			this.lockList.add(null);
		}
	}
}