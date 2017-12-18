package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLabOven extends ContainerBase<TileEntityLabOven> {

	public ContainerLabOven(IInventory playerInventory, TileEntityLabOven tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 62, 34));//input solute
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 8));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 128, 15));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 3, 62, 83));//output
		this.addSlotToContainer(new SlotItemHandler(input, 4, 29, 82));//input redstone
		this.addSlotToContainer(new SlotItemHandler(input, 5, 150, 15));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 6, 8, 82));//upgrade

		this.addSlotToContainer(new SlotItemHandler(template, 0, 137,  121));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 1, 153,  121));//next
		this.addSlotToContainer(new SlotItemHandler(template, 2, 7,  121));//activation
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 7){
			if(this.tile.recipeIndex >= 0){
	    		this.tile.recipeIndex--; 
    			this.tile.activation = false;
			}else{
				this.tile.recipeIndex = MachineRecipes.labOvenRecipes.size() - 1;
    			this.tile.activation = false;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 8){
    		if(this.tile.recipeIndex < MachineRecipes.labOvenRecipes.size() - 1){
    			this.tile.recipeIndex++; 
    			this.tile.activation = false;
    		}else{
	    		this.tile.recipeIndex = -1; 
    			this.tile.activation = false;
    		}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 9){
   			this.tile.activation = !this.tile.activation; 
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 10, endIndex, reverseDirection);
		}
    }
}