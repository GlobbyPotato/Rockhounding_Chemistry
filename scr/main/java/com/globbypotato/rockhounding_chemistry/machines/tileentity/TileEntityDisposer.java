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

	public TileEntityDisposer() {
		super(9,0,0);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= INPUT_SLOT[0] && slot <= INPUT_SLOT.length && insertingStack instanceof ItemStack && canPassTheLock(slot, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
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
	public boolean isActive(){
		return activation || worldObj.isBlockPowered(pos);
	}

	public int getInterval(){
		return interval;
	}

	public boolean isLocked(){
		return lock;
	}

	private boolean canPassTheLock(int slot, ItemStack insertingStack) {
		return !isLocked()
			|| (isLocked() && lockList.get(slot) != null && lockList.get(slot).isItemEqual(insertingStack) );
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.activation = compound.getBoolean("Activation");
		this.interval = compound.getInteger("Interval");
		this.lock = compound.getBoolean("Lock");
		
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        lockList = new ArrayList<ItemStack>();
        resetLock();
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < lockList.size()){
            	lockList.add(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Activation", this.activation);
		compound.setInteger("Interval", this.interval);
		compound.setBoolean("Lock", this.lock);
		
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < lockList.size(); ++i){
            if (lockList.get(i) != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                lockList.get(i).writeToNBT(nbttagcompound);
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
		if(lockList.size() < 1) {resetLock(); }
		if(!worldObj.isRemote){
			if(isActive()){
				cookTime++;
				if(cookTime >= getInterval()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private void process() {
		for(Integer slot : INPUT_SLOT){
			if(input.getStackInSlot(slot) != null){
				ItemStack drop = new ItemStack(input.getStackInSlot(slot).getItem(), 1, input.getStackInSlot(slot).getItemDamage());
				drop.stackSize = 1;
				dropItemStack(drop);
				input.decrementSlot(slot);
			}
		}
	}

	private void dropItemStack(ItemStack itemStack) {
	    IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing facing = state.getValue(Disposer.FACING);
		BlockPos dropPos = pos.offset(facing);
        double d0 = dropPos.getX() + 0.5D;
        double d1 = dropPos.getY() + 0.5D;
        double d2 = dropPos.getZ() + 0.5D;
		EntityItem entityitem = new EntityItem(worldObj, d0, d1, d2, itemStack);
		entityitem.motionX = 0;
		entityitem.motionY = 0;
		entityitem.motionZ = 0;
		worldObj.spawnEntityInWorld(entityitem);
	}

	public void resetLock(){
		for(Integer slot : INPUT_SLOT){
			lockList.add(null);
		}
	}
}