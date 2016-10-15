package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;

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

public class ContainerMineralAnalyzer extends Container{
    private TileEntityMineralAnalyzer mineralAnalyzer;
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;

    public ContainerMineralAnalyzer(InventoryPlayer playerInventory, TileEntityMineralAnalyzer furnaceInventory){
        this.mineralAnalyzer = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 54, 24));//input
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 8, 20));//fuel
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 90, 72));//output
        this.addSlotToContainer(new Slot(furnaceInventory, 3, 90, 46));//consumable
        this.addSlotToContainer(new Slot(furnaceInventory, 4, 144, 24));//sulf
        this.addSlotToContainer(new Slot(furnaceInventory, 5, 144, 46));//chlo
        this.addSlotToContainer(new Slot(furnaceInventory, 6, 144, 68));//fluo
        this.addSlotToContainer(new Slot(furnaceInventory, 7, 23, 74));//inductor

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
        listener.sendAllWindowProperties(this, this.mineralAnalyzer);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.mineralAnalyzer.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.mineralAnalyzer.getField(0));
            }
            if (this.powerCurrent != this.mineralAnalyzer.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.mineralAnalyzer.getField(1));
            }
            if (this.cookTime != this.mineralAnalyzer.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.mineralAnalyzer.getField(2));
            }
            if (this.totalCookTime != this.mineralAnalyzer.getField(3)){
                icontainerlistener.sendProgressBarUpdate(this, 3, this.mineralAnalyzer.getField(3));
            }
        }
        this.cookTime = this.mineralAnalyzer.getField(2);
        this.totalCookTime = this.mineralAnalyzer.getField(3);
        this.powerCount = this.mineralAnalyzer.getField(0);
        this.powerCurrent = this.mineralAnalyzer.getField(1);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.mineralAnalyzer.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.mineralAnalyzer.isUseableByPlayer(playerIn);
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
                if (TileEntityMineralAnalyzer.isItemFuel(itemstack1)){
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