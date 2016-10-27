package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerOwcController extends Container{
    private TileEntityOwcController owcController;
    private int powerCount;
    private int maxCapacity;
    private int yeldCount;

    public ContainerOwcController(InventoryPlayer playerInventory, TileEntityOwcController furnaceInventory){
        this.owcController = furnaceInventory;
        
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 74, 17));//acquiring
        this.addSlotToContainer(new Slot(furnaceInventory, 1, 74, 39));//extraction

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 101 + i * 18));
            }
        }
        
        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 159));
        }
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.owcController);
    }

    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.owcController.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.owcController.getField(0));
            }
            if (this.maxCapacity != this.owcController.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.owcController.getField(1));
            }
            if (this.yeldCount != this.owcController.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.owcController.getField(2));
            }
        }
        this.powerCount = this.owcController.getField(0);
        this.maxCapacity = this.owcController.getField(1);
        this.yeldCount = this.owcController.getField(2);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.owcController.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.owcController.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot == 0){
    		this.owcController.activationKey = !this.owcController.activationKey;
    		return null;
    	}else if(slot == 1){
    		this.owcController.extractionKey = !this.owcController.extractionKey;
    		return null;

    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }
}