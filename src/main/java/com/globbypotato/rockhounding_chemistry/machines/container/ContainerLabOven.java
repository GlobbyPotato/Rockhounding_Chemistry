package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerLabOven extends Container{
    private TileEntityLabOven labOven;
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;
    private int redstoneCount;
    private int redstoneCurrent;

    public ContainerLabOven(InventoryPlayer playerInventory, TileEntityLabOven furnaceInventory){
        this.labOven = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 44, 59));//input solute
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 8, 20));//fuel
        this.addSlotToContainer(new Slot(furnaceInventory, 2, 80, 59));//output
        this.addSlotToContainer(new Slot(furnaceInventory, 3, 116, 59));//input solvent
        this.addSlotToContainer(new Slot(furnaceInventory, 4, 152, 20));//input redstone

        this.addSlotToContainer(new Slot(furnaceInventory, 5, 28, 20));//rec-
        this.addSlotToContainer(new Slot(furnaceInventory, 6, 132, 20));//rec+

        this.addSlotToContainer(new Slot(furnaceInventory, 7, 44, 42));//rec i

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
            }
        }
        
        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 171));
        }
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.labOven);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.labOven.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.labOven.getField(0));
            }
            if (this.powerCurrent != this.labOven.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.labOven.getField(1));
            }
            if (this.cookTime != this.labOven.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.labOven.getField(2));
            }
            if (this.totalCookTime != this.labOven.getField(3)){
                icontainerlistener.sendProgressBarUpdate(this, 3, this.labOven.getField(3));
            }
            if (this.redstoneCount != this.labOven.getField(4)){
                icontainerlistener.sendProgressBarUpdate(this, 4, this.labOven.getField(4));
            }
            if (this.redstoneCurrent != this.labOven.getField(5)){
                icontainerlistener.sendProgressBarUpdate(this, 5, this.labOven.getField(5));
            }
        }
        this.cookTime = this.labOven.getField(2);
        this.totalCookTime = this.labOven.getField(3);
        this.powerCount = this.labOven.getField(0);
        this.powerCurrent = this.labOven.getField(1);
        this.redstoneCount = this.labOven.getField(4);
        this.redstoneCurrent = this.labOven.getField(5);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.labOven.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.labOven.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
    	return null;
    }
    
    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot == 7){
    		return null;
    	}else if(slot == 5 && this.labOven.recipeCount > 0){
        		this.labOven.recipeCount--; this.labOven.resetGrid(); this.labOven.recipeScan = true;
        		return null;
    	}else if(slot == 6 && this.labOven.recipeCount < this.labOven.maxAcids){
    		this.labOven.recipeCount++; this.labOven.resetGrid(); this.labOven.recipeScan = true;
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot){
        return slot.slotNumber < 5 || slot.slotNumber > 7;
    }

}