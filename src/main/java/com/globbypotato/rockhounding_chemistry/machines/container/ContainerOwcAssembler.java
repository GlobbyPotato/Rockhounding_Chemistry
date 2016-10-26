package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerOwcAssembler extends Container{
    private TileEntityOwcAssembler owcAssembler;
    private int cookTime;
    private int totalCookTime;

    public ContainerOwcAssembler(InventoryPlayer playerInventory, TileEntityOwcAssembler furnaceInventory){
        this.owcAssembler = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 8, 17));//bulkhead
        this.addSlotToContainer(new Slot(furnaceInventory, 1, 8,  35));//concrete
        this.addSlotToContainer(new Slot(furnaceInventory, 2, 8, 53));//duct
        this.addSlotToContainer(new Slot(furnaceInventory, 3, 8, 71));//turbine
        this.addSlotToContainer(new Slot(furnaceInventory, 4, 89, 17));//generator
        this.addSlotToContainer(new Slot(furnaceInventory, 5, 89, 35));//valve
        this.addSlotToContainer(new Slot(furnaceInventory, 6, 89, 53));//storage
        this.addSlotToContainer(new Slot(furnaceInventory, 7, 89, 71));//controller

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 119 + i * 18));
            }
        }
        
        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 177));
        }
        
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.owcAssembler);
    }

    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.cookTime != this.owcAssembler.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.owcAssembler.getField(0));
            }
            if (this.totalCookTime != this.owcAssembler.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.owcAssembler.getField(1));
            }
        }
        this.cookTime = this.owcAssembler.getField(0);
        this.totalCookTime = this.owcAssembler.getField(1);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.owcAssembler.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.owcAssembler.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
    	return null;
    }
}