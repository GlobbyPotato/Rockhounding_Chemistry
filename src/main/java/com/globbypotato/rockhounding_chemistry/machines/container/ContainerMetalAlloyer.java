package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMetalAlloyer extends ContainerBase<TileEntityMetalAlloyer>{
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;
	Slot templateAlloy;
	Slot templateDust1;
	Slot templateDust2;
	Slot templateDust3;
	Slot templateDust4;
	Slot templateDust5;
	Slot templateDust6;

	public ContainerMetalAlloyer(InventoryPlayer playerInventory, TileEntityMetalAlloyer tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 8, 20));//fuel
        for (int x = 1; x <= 6; x++){
        	this.addSlotToContainer(new SlotItemHandler(input, x, 53 + ((x-1)*18), 51));//input dusts
        }
        this.addSlotToContainer(new SlotItemHandler(input, 7, 98, 88));//consumable
        this.addSlotToContainer(new SlotItemHandler(input, 8, 23, 74));//inductor
        this.addSlotToContainer(new SlotItemHandler(input, 9, 52, 16));//prev
        this.addSlotToContainer(new SlotItemHandler(input, 10, 88, 16));//next

        this.addSlotToContainer(new SlotItemHandler(output, 0, 76, 88));//output
        this.addSlotToContainer(new SlotItemHandler(output, 1, 120, 88));//scrap

        templateAlloy = this.addSlotToContainer(new SlotItemHandler(template, 0, 70,  16));//alloy template
        templateDust1 = this.addSlotToContainer(new SlotItemHandler(template, 1, 53,  34));//dust 1
        templateDust2 = this.addSlotToContainer(new SlotItemHandler(template, 2, 71,  34));//dust 2
        templateDust3 = this.addSlotToContainer(new SlotItemHandler(template, 3, 89,  34));//dust 3
        templateDust4 = this.addSlotToContainer(new SlotItemHandler(template, 4, 107, 34));//dust 4
        templateDust5 = this.addSlotToContainer(new SlotItemHandler(template, 5, 125, 34));//dust 5
        templateDust6 = this.addSlotToContainer(new SlotItemHandler(template, 6, 143, 34));//dust 6
	}

	@Override
	public void addListener(IContainerListener listener){
		super.addListener(listener);
	}

	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); ++i){
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
			if (this.powerCount != this.tile.getField(0)){
				icontainerlistener.sendProgressBarUpdate(this, 0, this.tile.getField(0));
			}
			if (this.powerCurrent != this.tile.getField(1)){
				icontainerlistener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
			}
			if (this.cookTime != this.tile.getField(2)){
				icontainerlistener.sendProgressBarUpdate(this, 2, this.tile.getField(2));
			}
			if (this.totalCookTime != this.tile.getField(3)){
				icontainerlistener.sendProgressBarUpdate(this, 3, this.tile.getField(3));
			}
		}
		this.powerCount = this.tile.getField(0);
		this.powerCurrent = this.tile.getField(1);
		this.cookTime = this.tile.getField(2);
		this.totalCookTime = this.tile.getField(3);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data){
		this.tile.setField(id, data);
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot >= 13 && slot <= 19){
    		return null;
    	}else if(slot == 9 && this.tile.recipeDisplayIndex > -1){
        		this.tile.recipeDisplayIndex--; 
        		this.tile.resetGrid(); 
        		this.tile.recipeScan = true;
        		return null;
    	}else if(slot == 10 && this.tile.recipeDisplayIndex < this.tile.maxAlloys - 1){
    		this.tile.recipeDisplayIndex++; 
    		this.tile.resetGrid(); 
    		this.tile.recipeScan = true;
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 9, reverseDirection)){
			return true;
		}else if(super.mergeItemStack(stack, 11, 13, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 20, endIndex, reverseDirection);
		}
    }
}