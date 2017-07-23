package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMetalAlloyer extends ContainerBase<TileEntityMetalAlloyer>{
	Slot templateAlloy;
	Slot templateDust1;
	Slot templateDust2;
	Slot templateDust3;
	Slot templateDust4;
	Slot templateDust5;
	Slot templateDust6;

	Slot templateNext;
	Slot templatePrev;

	Slot activation;

	public ContainerMetalAlloyer(IInventory playerInventory, TileEntityMetalAlloyer tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 8, 20));//fuel
        for (int x = 1; x <= 6; x++){
        	this.addSlotToContainer(new SlotItemHandler(input, x, 53 + ((x-1)*18), 53));//input dusts
        }
        this.addSlotToContainer(new SlotItemHandler(input, 7, 98, 90));//consumable

        this.addSlotToContainer(new SlotItemHandler(output, 0, 76, 90));//output
        this.addSlotToContainer(new SlotItemHandler(output, 1, 120, 90));//scrap

        templateAlloy = this.addSlotToContainer(new SlotItemHandler(template, 0, 33,  15));//alloy template
        templateDust1 = this.addSlotToContainer(new SlotItemHandler(template, 1, 53,  35));//dust 1
        templateDust2 = this.addSlotToContainer(new SlotItemHandler(template, 2, 71,  35));//dust 2
        templateDust3 = this.addSlotToContainer(new SlotItemHandler(template, 3, 89,  35));//dust 3
        templateDust4 = this.addSlotToContainer(new SlotItemHandler(template, 4, 107, 35));//dust 4
        templateDust5 = this.addSlotToContainer(new SlotItemHandler(template, 5, 125, 35));//dust 5
        templateDust6 = this.addSlotToContainer(new SlotItemHandler(template, 6, 143, 35));//dust 6
        
        templateNext = this.addSlotToContainer(new SlotItemHandler(template, 7, 137,  15));//prev
        templatePrev = this.addSlotToContainer(new SlotItemHandler(template, 8, 153,  15));//next
        activation = this.addSlotToContainer(new SlotItemHandler(template, 9, 34,  53));//activation

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot >= 10 && slot <= 16){
    		return null;
    	}else if(slot == 17){ 
    		if(this.tile.recipeIndex > -1){
        		this.tile.recipeIndex--; 
        		this.tile.resetGrid(); 
        		this.tile.doScan = true;
    			this.tile.activation = false;
    		}
        	return null;
    	}else if(slot == 18){ 
    		if(this.tile.recipeIndex < MachineRecipes.alloyerRecipes.size() - 1){
	    		this.tile.recipeIndex++; 
	    		this.tile.resetGrid(); 
	    		this.tile.doScan = true;
    			this.tile.activation = false;
        	}
    		return null;
    	}else if(slot == 19){
   			this.tile.activation = !this.tile.activation; 
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 10, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 20, endIndex, reverseDirection);
		}
    }
}