package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

public class TEDisposer extends TileEntityInv {
    public static final int INPUT_SLOT[] = new int[]{0,1,2,3,4,5,6,7,8};

	public static int inputSlots = INPUT_SLOT.length;
	public static int templateSlots = 6;

	public List<ItemStack> lockList = new ArrayList<ItemStack>();
	public int interval;
	public boolean lock;

	public TEDisposer() {
		super(inputSlots, 0, templateSlots, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
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



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.interval = compound.getInteger("Interval");
		this.lock = compound.getBoolean("Lock");

		NBTTagList nbttaglist = compound.getTagList("Filter", 10);
        this.lockList = new ArrayList<ItemStack>();
        resetLock();
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < this.lockList.size()){
            	this.lockList.add(j, new ItemStack(nbttagcompound));
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Interval", getInterval());
		compound.setBoolean("Lock", isLocked());

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.lockList.size(); ++i){
            if (!this.lockList.get(i).isEmpty()){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.lockList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Filter", nbttaglist);
        return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(int slot){
		return this.input.getStackInSlot(slot);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "disposer";
	}

	@Override
	public boolean isComparatorSensible(){
		return false;
	}


	//----------------------- CUSTOM -----------------------
	public int getInterval(){
		return this.interval;
	}

	public boolean isLocked(){
		return this.lock;
	}

	public void resetLock(){
		for (int i = 0; i < inputSlots; i++) {
			this.lockList.add(ItemStack.EMPTY);
		}
	}

	public boolean canPassTheLock(int slot, ItemStack insertingStack) {
		return !isLocked()
			|| (isLocked() && this.lockList.get(slot) != null && this.lockList.get(slot).isItemEqual(insertingStack) );
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(this.lockList.size() < 1) {resetLock(); }
		if(!this.world.isRemote){
			if(isActive()){
				this.cooktime++;
				if(this.getCooktime() >= getInterval()) {
					this.cooktime = 0;
					process();
				}
			}
		}
	}

	private void process() {
		for(Integer slot : INPUT_SLOT){
			if(!inputSlot(slot).isEmpty()){
				ItemStack drop = inputSlot(slot).copy();
				drop.setCount(1);
				dropItemStack(drop);
				this.input.decrementSlot(slot);
			}
		}
	}

	private void dropItemStack(ItemStack itemStack) {
	    IBlockState state = this.world.getBlockState(this.pos);
		BlockPos dropPos = this.pos.offset(getFacing().getOpposite());
        double d0 = dropPos.getX() + 0.5D;
        double d1 = dropPos.getY() + 0.5D;
        double d2 = dropPos.getZ() + 0.5D;
		EntityItem entityitem = new EntityItem(this.world, d0, d1, d2, itemStack);
		entityitem.motionX = 0;
		entityitem.motionY = 0;
		entityitem.motionZ = 0;
		this.world.spawnEntity(entityitem);
	}
}