package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;

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

public class ContainerMetalAlloyer extends Container{
    private TileEntityMetalAlloyer metalAlloyer;
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;

    public ContainerMetalAlloyer(InventoryPlayer playerInventory, TileEntityMetalAlloyer furnaceInventory){
        this.metalAlloyer = furnaceInventory;
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 0, 8, 20));//fuel
        for (int x = 1; x <= 6; x++){
        	this.addSlotToContainer(new Slot(furnaceInventory, x, 53 + ((x-1)*18), 51));//input dusts
        }
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 7, 76, 88));//output
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 8, 120, 88));//scrap
        this.addSlotToContainer(new Slot(furnaceInventory, 9, 98, 88));//consumable

        this.addSlotToContainer(new Slot(furnaceInventory, 10, 70, 16));//test alloy	NA

        for (int x = 11; x <= 16; x++){
        	this.addSlotToContainer(new Slot(furnaceInventory, x, 53 + ((x-11)*18), 34));//fake dusts	NA
        }

        this.addSlotToContainer(new Slot(furnaceInventory, 17, 52, 16));//minus slot	NA
        this.addSlotToContainer(new Slot(furnaceInventory, 18, 88, 16));//plus slot	NA

        this.addSlotToContainer(new Slot(furnaceInventory, 19, 23, 74));//inductor



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
        listener.sendAllWindowProperties(this, this.metalAlloyer);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.metalAlloyer.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.metalAlloyer.getField(0));
            }
            if (this.powerCurrent != this.metalAlloyer.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.metalAlloyer.getField(1));
            }
            if (this.cookTime != this.metalAlloyer.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.metalAlloyer.getField(2));
            }
            if (this.totalCookTime != this.metalAlloyer.getField(3)){
                icontainerlistener.sendProgressBarUpdate(this, 3, this.metalAlloyer.getField(3));
            }
        }
        this.cookTime = this.metalAlloyer.getField(2);
        this.totalCookTime = this.metalAlloyer.getField(3);
        this.powerCount = this.metalAlloyer.getField(0);
        this.powerCurrent = this.metalAlloyer.getField(1);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.metalAlloyer.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.metalAlloyer.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        return null;
    }
    
    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot >= 10 && slot <= 16){
    		return null;
    	}else if(slot == 17 && this.metalAlloyer.recipeCount > 0){
        		this.metalAlloyer.recipeCount--; this.metalAlloyer.resetGrid(); this.metalAlloyer.recipeScan = true;
        		return null;
    	}else if(slot == 18 && this.metalAlloyer.recipeCount < this.metalAlloyer.maxAlloys){
    		this.metalAlloyer.recipeCount++; this.metalAlloyer.resetGrid(); this.metalAlloyer.recipeScan = true;
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot){
        return slot.slotNumber < 10 || slot.slotNumber > 18;
    }

}