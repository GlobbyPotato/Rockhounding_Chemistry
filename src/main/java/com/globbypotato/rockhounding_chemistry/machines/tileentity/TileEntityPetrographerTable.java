package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.items.tools.Petrographer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityPetrographerTable extends TileEntity implements ISidedInventory, ITickable {
    public ItemStack[] slots = new ItemStack[3];
    private String inventoryName;
    Item[] 	specimenList = new Item[] {	null, 
										ModItems.arsenateShards, 
										ModItems.borateShards, 
										ModItems.carbonateShards, 
										ModItems.halideShards, 
										ModItems.nativeShards, 
										ModItems.oxideShards, 
										ModItems.phosphateShards, 
										ModItems.silicateShards, 
										ModItems.sulfateShards, 
										ModItems.sulfideShards};

	public static final int TOOL_SLOT = 0;
	public static final int ORE_SLOT = 1;
	public static final int SHARD_SLOT = 2;
	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}
	@Override
	public ItemStack getStackInSlot(int index) {
        return this.slots[index];
	}
	@Override
	public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.slots, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.slots, index);
	}
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        boolean flag = stack != null && stack.isItemEqual(this.slots[index]) && ItemStack.areItemStackTagsEqual(stack, this.slots[index]);
        this.slots[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }
        if (index == 0 && !flag){
            this.markDirty();
        }
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {	}

	@Override
	public void closeInventory(EntityPlayer player) {	}

	@Override
	public void clear() {        
		for (int i = 0; i < this.slots.length; ++i) {
			this.slots[i] = null;
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
	@Override
	public int getField(int id) {
		return 0;
	}
	@Override
	public void setField(int id, int value) {
	}
	@Override
	public int getFieldCount() {
		return 0;
	}
    public String getName(){
        return this.hasCustomName() ? this.inventoryName : "container.petrographerTable";
    }

    public boolean hasCustomName(){
        return this.inventoryName != null && !this.inventoryName.isEmpty();
    }

    public void setCustomInventoryName(String name){
        this.inventoryName = name;
    }
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public void update() {
		if(hasPetro()){
			if(slots[TOOL_SLOT].hasTagCompound()){
		    	int nFlavor = slots[TOOL_SLOT].getTagCompound().getInteger("nFlavor");
		    	int nSpecimen = slots[TOOL_SLOT].getTagCompound().getInteger("nSpecimen");

		    	if(isUndefined()){
		    		slots[TOOL_SLOT].getTagCompound().setInteger("nFlavor", 0);
		    		slots[TOOL_SLOT].getTagCompound().setInteger("nSpecimen", -1);
		    	}else if(isCategory()){
		    		slots[TOOL_SLOT].getTagCompound().setInteger("nFlavor", slots[ORE_SLOT].getItemDamage());
		    		if(hasSpecimen()){
			    		slots[TOOL_SLOT].getTagCompound().setInteger("nSpecimen", slots[SHARD_SLOT].getItemDamage());
		    		}else{
			    		slots[TOOL_SLOT].getTagCompound().setInteger("nSpecimen", -1);
		    		}
		    	}
			}else{
				Petrographer.setItemNbt(slots[TOOL_SLOT]);
			}
		}
	}

	private boolean hasSpecimen() {
		return slots[SHARD_SLOT] != null && slots[SHARD_SLOT].getItem() == specimenList[slots[ORE_SLOT].getItemDamage()];
	}
	private boolean isCategory() {
		return slots[ORE_SLOT] != null && slots[ORE_SLOT].getItem() == Item.getItemFromBlock(ModBlocks.mineralOres) && slots[ORE_SLOT].getItemDamage() > 0;
	}

	private boolean isUndefined() {
		return slots[ORE_SLOT] != null && slots[ORE_SLOT].getItem() == Item.getItemFromBlock(ModBlocks.mineralOres) && slots[ORE_SLOT].getItemDamage() == 0;
	}
	private boolean hasPetro() {
		return slots[TOOL_SLOT] != null && slots[TOOL_SLOT].getItem() == ModItems.petrographer;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);
		slots = new ItemStack[getSizeInventory()];
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound compound = (NBTTagCompound) list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			if(b >= 0 && b < slots.length) {
				slots[b] = ItemStack.loadItemStackFromNBT(compound);
			}
		}
	}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		nbt.setTag("Items", list);
        return nbt;
	}

}