package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMineralSizer extends Container{
    private TileEntityMineralSizer mineralSizer;
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;

    public ContainerMineralSizer(InventoryPlayer playerInventory, TileEntityMineralSizer furnaceInventory){
        this.mineralSizer = furnaceInventory;
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
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.mineralSizer);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.mineralSizer.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.mineralSizer.getField(0));
            }
            if (this.powerCurrent != this.mineralSizer.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.mineralSizer.getField(1));
            }
            if (this.cookTime != this.mineralSizer.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.mineralSizer.getField(2));
            }
            if (this.totalCookTime != this.mineralSizer.getField(3)){
                icontainerlistener.sendProgressBarUpdate(this, 3, this.mineralSizer.getField(3));
            }
        }
        this.cookTime = this.mineralSizer.getField(2);
        this.totalCookTime = this.mineralSizer.getField(3);
        this.powerCount = this.mineralSizer.getField(0);
        this.powerCurrent = this.mineralSizer.getField(1);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.mineralSizer.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.mineralSizer.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
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
    }
}