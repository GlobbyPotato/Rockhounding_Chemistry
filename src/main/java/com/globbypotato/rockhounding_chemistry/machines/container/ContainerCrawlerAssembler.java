package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCrawlerAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrawlerAssembler extends Container{
    private TileEntityCrawlerAssembler crawlerAssembler;
    private int cookTime;
    private int totalCookTime;

    public ContainerCrawlerAssembler(InventoryPlayer playerInventory, TileEntityCrawlerAssembler furnaceInventory){
        this.crawlerAssembler = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 35, 17));//casing
        this.addSlotToContainer(new Slot(furnaceInventory, 1, 8,  17));//mode
        this.addSlotToContainer(new Slot(furnaceInventory, 2, 62, 17));//arms

        this.addSlotToContainer(new Slot(furnaceInventory, 3, 17, 39));//grid1
        this.addSlotToContainer(new Slot(furnaceInventory, 4, 35, 39));//grid2
        this.addSlotToContainer(new Slot(furnaceInventory, 5, 53, 39));//grid3
        this.addSlotToContainer(new Slot(furnaceInventory, 6, 17, 57));//grid4
        this.addSlotToContainer(new Slot(furnaceInventory, 7, 35, 57));//grid5
        this.addSlotToContainer(new Slot(furnaceInventory, 8, 53, 57));//grid6

        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 9, 35, 79));//output

        this.addSlotToContainer(new Slot(furnaceInventory, 10, 152, 48));//charger
        this.addSlotToContainer(new Slot(furnaceInventory, 11, 152, 21));//material
        
        this.addSlotToContainer(new Slot(furnaceInventory, 12, 62, 79));//memory

        this.addSlotToContainer(new Slot(furnaceInventory, 13,  89, 17));//F
        this.addSlotToContainer(new Slot(furnaceInventory, 14, 89, 39));//A
        this.addSlotToContainer(new Slot(furnaceInventory, 15, 89, 61));//T
        this.addSlotToContainer(new Slot(furnaceInventory, 16, 89, 83));//L
        this.addSlotToContainer(new Slot(furnaceInventory, 17, 111, 17));//R
        this.addSlotToContainer(new Slot(furnaceInventory, 18, 111, 39));//unused


        
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
        listener.sendAllWindowProperties(this, this.crawlerAssembler);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.cookTime != this.crawlerAssembler.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.crawlerAssembler.getField(0));
            }
            if (this.totalCookTime != this.crawlerAssembler.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.crawlerAssembler.getField(1));
            }
        }
        this.cookTime = this.crawlerAssembler.getField(0);
        this.totalCookTime = this.crawlerAssembler.getField(1);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.crawlerAssembler.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.crawlerAssembler.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
    	return null;
    }
}