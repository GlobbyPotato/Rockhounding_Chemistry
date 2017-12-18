package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDepositionChamber;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDepositionChamber extends ContainerBase<TileEntityDepositionChamber> {

	public ContainerDepositionChamber(IInventory playerInventory, TileEntityDepositionChamber tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 80, 34));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 128, 80));//fluid
		this.addSlotToContainer(new SlotItemHandler(input, 2, 99, 93));//upgrade
		this.addSlotToContainer(new SlotItemHandler(input, 3, 61, 93));//upgrade
		this.addSlotToContainer(new SlotItemHandler(input, 4, 80, 93));//upgrade
		this.addSlotToContainer(new SlotItemHandler(output, 0, 80, 71));//output

		this.addSlotToContainer(new SlotItemHandler(template, 0, 137,  111));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 1, 153,  111));//next
		this.addSlotToContainer(new SlotItemHandler(template, 1, 7,  111));//activation
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 6){
			if(this.tile.recipeIndex >= 0){
	    		this.tile.recipeIndex--; 
	    		this.tile.activation = false;
	    	}else{
	    		this.tile.recipeIndex = MachineRecipes.depositionRecipes.size() - 1;
	    		this.tile.activation = false;
	    	}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 7){
    		if(this.tile.recipeIndex < MachineRecipes.depositionRecipes.size() - 1){
    			this.tile.recipeIndex++; 
	    		this.tile.activation = false;
    		}else{
    			this.tile.recipeIndex = -1; 
	    		this.tile.activation = false;
    		}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 8){
   			this.tile.activation = !this.tile.activation; 
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 6, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 9, endIndex, reverseDirection);
		}
    }

}