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
    public int[] owcChannels = new int[11];

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
            for(int x = 0; x < this.owcChannels.length; x++){
	            if (this.owcChannels[x] != this.owcController.getField(x)){
	                icontainerlistener.sendProgressBarUpdate(this, x, this.owcController.getField(x));
	            }
            }
            if (this.owcChannels[10] != this.owcController.getField(10)){
                icontainerlistener.sendProgressBarUpdate(this, 10, this.owcController.getField(10));
            }
        }
        for(int x = 0; x < this.owcChannels.length; x++){
        	this.owcChannels[x] = this.owcController.getField(x);
        }
    	this.owcChannels[10] = this.owcController.getField(10);
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