package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;

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

public class ContainerChemicalExtractor extends Container{
    private TileEntityChemicalExtractor chemicalExtractor;
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;
    private int redstoneCount;
    private int redstoneCurrent;
    private int[] elementsCount = new int [56];

    public ContainerChemicalExtractor(InventoryPlayer playerInventory, TileEntityChemicalExtractor furnaceInventory){
        this.chemicalExtractor = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 57, 52));//input
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 8, 20));//fuel
        this.addSlotToContainer(new Slot(furnaceInventory, 2, 28, 20));//redstone
        this.addSlotToContainer(new Slot(furnaceInventory, 3, 57, 74));//consumable
        this.addSlotToContainer(new Slot(furnaceInventory, 4, 48, 20));//syngas
        this.addSlotToContainer(new Slot(furnaceInventory, 5, 68, 20));//fluo

        //cabinets
		for(int x=0; x<=7; x++){
			for(int y=0; y<=6; y++){
		        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 6+(y*8)+x, 88 + (19 * x) , 22 + (21* y)));//output
			}
		}

        
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 174 + i * 18));
            }
        }
        
        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 232));
        }
        
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.chemicalExtractor);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.powerCount != this.chemicalExtractor.getField(0)){
                icontainerlistener.sendProgressBarUpdate(this, 0, this.chemicalExtractor.getField(0));
            }
            if (this.powerCurrent != this.chemicalExtractor.getField(1)){
                icontainerlistener.sendProgressBarUpdate(this, 1, this.chemicalExtractor.getField(1));
            }
            if (this.cookTime != this.chemicalExtractor.getField(2)){
                icontainerlistener.sendProgressBarUpdate(this, 2, this.chemicalExtractor.getField(2));
            }
            if (this.totalCookTime != this.chemicalExtractor.getField(3)){
                icontainerlistener.sendProgressBarUpdate(this, 3, this.chemicalExtractor.getField(3));
            }
            if (this.redstoneCount != this.chemicalExtractor.getField(4)){
                icontainerlistener.sendProgressBarUpdate(this, 4, this.chemicalExtractor.getField(4));
            }
            if (this.redstoneCurrent != this.chemicalExtractor.getField(5)){
                icontainerlistener.sendProgressBarUpdate(this, 5, this.chemicalExtractor.getField(5));
            }
            for (int el = 0; el < elementsCount.length; el++){
                if (this.elementsCount[el] != this.chemicalExtractor.getField(6+el)){
                    icontainerlistener.sendProgressBarUpdate(this, 6+el, this.chemicalExtractor.getField(6+el));
                }
            }

        }
        this.cookTime = this.chemicalExtractor.getField(2);
        this.totalCookTime = this.chemicalExtractor.getField(3);
        this.powerCount = this.chemicalExtractor.getField(0);
        this.powerCurrent = this.chemicalExtractor.getField(1);
        this.redstoneCount = this.chemicalExtractor.getField(4);
        this.redstoneCurrent = this.chemicalExtractor.getField(5);
        for (int el = 0; el < elementsCount.length; el++){
            this.elementsCount[el] = this.chemicalExtractor.getField(6+el);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.chemicalExtractor.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return this.chemicalExtractor.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
    	return null;
/*        ItemStack itemstack = null;
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
                if (TileEntityChemicalExtractor.isItemFuel(itemstack1)){
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
        return itemstack;*/
    }
}