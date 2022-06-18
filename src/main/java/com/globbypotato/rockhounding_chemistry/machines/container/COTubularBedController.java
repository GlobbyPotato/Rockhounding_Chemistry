package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TETubularBedController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COTubularBedController extends ContainerBase<TETubularBedController>{
	public COTubularBedController(IInventory playerInventory, TETubularBedController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 8, 21));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 2, 26, 21));//next

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 134, 58));//speed

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 0){
			this.tile.activation = !this.tile.activation;
			this.tile.dummyRecipe = null;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
		}else if(slot == 1){
			if(this.tile.isServedClosed(this.tile.getServer())){
				if(this.tile.getRecipeIndex() > -1){
		    		this.tile.recipeIndex--; 
				}else{
					this.tile.recipeIndex = BedReactorRecipes.bed_reactor_recipes.size() - 1;
				}
				this.tile.activation = false;
				this.tile.dummyRecipe = null;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 2){
			if(this.tile.isServedClosed(this.tile.getServer())){
	    		if(this.tile.getRecipeIndex() < BedReactorRecipes.bed_reactor_recipes.size() - 1){
	    			this.tile.recipeIndex++; 
	    		}else{
		    		this.tile.recipeIndex = -1; 
				}
				this.tile.activation = false;
				this.tile.dummyRecipe = null;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 3, endIndex, reverseDirection);
    }
}