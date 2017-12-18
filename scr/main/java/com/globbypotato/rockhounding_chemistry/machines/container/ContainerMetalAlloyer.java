package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMetalAlloyer extends ContainerBase<TileEntityMetalAlloyer>{

	public ContainerMetalAlloyer(IInventory playerInventory, TileEntityMetalAlloyer tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 8, 8));//fuel
        for (int x = 1; x <= 6; x++){
        	this.addSlotToContainer(new SlotItemHandler(input, x, 62 + ((x-1)*18), 36));//input dusts
        }
        this.addSlotToContainer(new SlotItemHandler(input, 7, 41, 80));//consumable
        this.addSlotToContainer(new SlotItemHandler(input, 8, 42, 36));//loader
        this.addSlotToContainer(new SlotItemHandler(input, 9, 152, 71));//upgrade

        this.addSlotToContainer(new SlotItemHandler(output, 0, 63, 80));//output
        this.addSlotToContainer(new SlotItemHandler(output, 1, 85, 80));//scrap

        this.addSlotToContainer(new SlotItemHandler(template, 0, 120,  98));//alloy template
        this.addSlotToContainer(new SlotItemHandler(template, 1, 62,  18));//dust 1
        this.addSlotToContainer(new SlotItemHandler(template, 2, 80,  18));//dust 2
        this.addSlotToContainer(new SlotItemHandler(template, 3, 98,  18));//dust 3
        this.addSlotToContainer(new SlotItemHandler(template, 4, 116, 18));//dust 4
        this.addSlotToContainer(new SlotItemHandler(template, 5, 134, 18));//dust 5
        this.addSlotToContainer(new SlotItemHandler(template, 6, 152, 18));//dust 6
        
        this.addSlotToContainer(new SlotItemHandler(template, 7, 137,  98));//prev
        this.addSlotToContainer(new SlotItemHandler(template, 8, 153,  98));//next
        this.addSlotToContainer(new SlotItemHandler(template, 9, 7,  98));//activation

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot >= 12 && slot <= 18){
    		return null;
    	}else if(slot == 19){ 
    		if(this.tile.recipeIndex >= 0){
        		this.tile.recipeIndex--; 
        		this.tile.resetGrid(); 
        		this.tile.doScan = true;
    			this.tile.activation = false;
    		}else{
    			this.tile.recipeIndex = MachineRecipes.alloyerRecipes.size() - 1;
        		this.tile.resetGrid(); 
        		this.tile.doScan = true;
    			this.tile.activation = false;
    		}
			doClickSound(player, tile.getWorld(), tile.getPos());
        	return null;
    	}else if(slot == 20){ 
    		if(this.tile.recipeIndex < MachineRecipes.alloyerRecipes.size() - 1){
	    		this.tile.recipeIndex++; 
	    		this.tile.resetGrid(); 
	    		this.tile.doScan = true;
    			this.tile.activation = false;
        	}else{
        		this.tile.recipeIndex = -1;
	    		this.tile.resetGrid(); 
	    		this.tile.doScan = true;
    			this.tile.activation = false;
        	}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 21){
   			this.tile.activation = !this.tile.activation; 
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 12, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 22, endIndex, reverseDirection);
		}
    }
}