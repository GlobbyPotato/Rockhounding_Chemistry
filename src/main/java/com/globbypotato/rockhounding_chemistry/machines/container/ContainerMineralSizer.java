package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralSizer extends ContainerBase<TileEntityMineralSizer>{
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;

	public ContainerMineralSizer(IInventory playerInventory, TileEntityMineralSizer te) {
		super(playerInventory,te);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		
		 this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 28));//input
	        this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
	        this.addSlotToContainer(new SlotItemHandler(input, 2, 81, 48));//consumable
	        this.addSlotToContainer(new SlotItemHandler(input, 3, 23, 74));//inductor
	        
	        this.addSlotToContainer(new SlotItemHandler(output, 0, 120, 48));//output
	        this.addSlotToContainer(new SlotItemHandler(output, 1, 144, 48));//secondary
	        this.addSlotToContainer(new SlotItemHandler(output, 2, 120, 72));//waste

	}
	/*
    public ContainerMineralSizer(InventoryPlayer playerInventory, TileEntityMineralSizer furnaceInventory){
        this.tile = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 44, 28));//input
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 8, 20));//fuel
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 120, 48));//output
        this.addSlotToContainer(new Slot(furnaceInventory, 3, 81, 48));//consumable
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 4, 144, 48));//secondary
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 5, 120, 72));//waste
        this.addSlotToContainer(new Slot(furnaceInventory, 6, 23, 74));//inductor

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 109 + i * 18));
            }
        }
        
        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 167));
        }
    }*/
//
//	@Override
//    public void addListener(IContainerListener listener){
//        super.addListener(listener);
//        listener.sendAllWindowProperties(this, this.tile);
//    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
	@Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.tile.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.tile.getField(0));
            }
            if (this.powerCurrent != this.tile.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
            }
            if (this.cookTime != this.tile.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.tile.getField(2));
            }
            if (this.totalCookTime != this.tile.getField(3)){
                icontainerlistener.sendProgressBarUpdate(this, 3, this.tile.getField(3));
            }
        }
        this.cookTime = this.tile.getField(2);
        this.totalCookTime = this.tile.getField(3);
        this.powerCount = this.tile.getField(0);
        this.powerCurrent = this.tile.getField(1);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.tile.setField(id, data);
    }


    /**
     * Take a stack from the specified inventory slot.
     */
    /*
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2){
                if (!this.mergeItemStack(itemstack1, 5, 41, true)){
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }else if (index != 1 && index != 0){
                if (TileEntityMineralSizer.isItemFuel(itemstack1)){
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)){
                        return null;
                    }
                }else if (index >= 5 && index < 32){
                    if (!this.mergeItemStack(itemstack1, 32, 41, false)){
                        return null;
                    }
                }else if (index >= 32 && index < 41 && !this.mergeItemStack(itemstack1, 5, 32, false)){
                    return null;
                }
            }else if (!this.mergeItemStack(itemstack1, 5, 41, false)){
                return null;
            }
            if (itemstack1.stackSize == 0){
                slot.putStack((ItemStack)null);
            }else{
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize){
                return null;
            }
            slot.onPickupFromSlot(playerIn, itemstack1);
        }
        return itemstack;
    }*/
}