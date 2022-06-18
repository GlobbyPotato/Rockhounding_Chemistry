package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TELabBlenderTank extends TileEntityInv {
    public static final int INPUT_SLOT[] = new int[]{0,1,2,3,4,5,6};

	public static int inputSlots = INPUT_SLOT.length;
	public static int templateSlots = 1;

	public List<ItemStack> lockList = new ArrayList<ItemStack>();
	public boolean lock;

	public TELabBlenderTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= INPUT_SLOT[0] && slot < inputSlots && handleInputs(slot, insertingStack) ){
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
		return "lab_blender_tank";
	}



	//----------------------- CUSTOM -----------------------
	public boolean isLocked(){
		return this.lock;
	}

	public void resetLock(){
		for (int i = 0; i < inputSlots; i++) {
			this.lockList.add(ItemStack.EMPTY);
		}
	}

	boolean handleInputs(int slot, ItemStack insertingStack) {
		if(isLocked()){
			return isMatchingIngredient(slot, insertingStack);
		}
		return true;
	}

	private boolean isMatchingIngredient(int slot, ItemStack insertingStack){
		if(slot < this.lockList.size()){
			if(!insertingStack.isEmpty() && !this.lockList.get(slot).isEmpty()){
				if(insertingStack.isItemEqual(this.lockList.get(slot))){
					return true;
				}
			}
		}
		return false;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){	}

}