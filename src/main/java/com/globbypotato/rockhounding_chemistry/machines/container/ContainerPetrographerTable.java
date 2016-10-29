package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPetrographerTable extends Container{

    private TileEntityPetrographerTable petroTable;

    public ContainerPetrographerTable(InventoryPlayer playerInventory, TileEntityPetrographerTable furnaceInventory){
        this.petroTable = furnaceInventory;

        this.addSlotToContainer(new Slot(furnaceInventory, 0, 80, 24));//tool
        this.addSlotToContainer(new Slot(furnaceInventory, 1, 44, 60));//ore
        this.addSlotToContainer(new Slot(furnaceInventory, 2, 116, 60));//shard

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
        listener.sendAllWindowProperties(this, this.petroTable);
    }

    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.petroTable.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.petroTable.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }
}