package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEDepositionChamberTop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CODepositionChamberTop extends ContainerBase<TEDepositionChamberTop>{
	public CODepositionChamberTop(IInventory playerInventory, TEDepositionChamberTop te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler output = this.tile.getOutput();
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 80, 67));//input

		this.addSlotToContainer(new SlotItemHandler(output, 0, 26, 45));//recycle

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 26, 21));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 2, 44, 21));//next

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){
    		this.tile.dummyRecipe = null;
    		return super.slotClick(slot, dragType, clickTypeIn, player);
		}else if(slot == 2){
			this.tile.dummyRecipe = null;
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
		}else if(slot == 3){
			if(this.tile.isServedClosed(this.tile.hasServer(), this.tile.getServer())){
				if(this.tile.getRecipeIndex() > -1){
		    		this.tile.recipeIndex--; 
				}else{
					this.tile.recipeIndex = DepositionChamberRecipes.deposition_chamber_recipes.size() - 1;
				}
				this.tile.dummyRecipe = null;
				this.tile.activation = false;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 4){
			if(this.tile.isServedClosed(this.tile.hasServer(), this.tile.getServer())){
	    		if(this.tile.getRecipeIndex() < DepositionChamberRecipes.deposition_chamber_recipes.size() - 1){
	    			this.tile.recipeIndex++; 
	    		}else{
		    		this.tile.recipeIndex = -1; 
				}
				this.tile.dummyRecipe = null;
				this.tile.activation = false;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 2, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 5, endIndex, reverseDirection);
    }
}