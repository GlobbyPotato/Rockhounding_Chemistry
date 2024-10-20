package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabOvenController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COLabOvenController extends ContainerBase<TELabOvenController>{
	public COLabOvenController(IInventory playerInventory, TELabOvenController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 67, 61));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 93, 61));//catalyst

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 8, 21));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 2, 26, 21));//next
		this.addSlotToContainer(new SlotItemHandler(template, 3, 24, 61));//direction

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 152, 61));//speed

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 2){
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
		}else if(slot == 3){
			if(this.tile.isServedClosed(this.tile.getServer())){
				if(this.tile.getRecipeIndex() > -1){
		    		this.tile.recipeIndex--; 
				}else{
					this.tile.recipeIndex = LabOvenRecipes.lab_oven_recipes.size() - 1;
				}
				this.tile.activation = false;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 4){
			if(this.tile.isServedClosed(this.tile.getServer())){
	    		if(this.tile.getRecipeIndex() < LabOvenRecipes.lab_oven_recipes.size() - 1){
	    			this.tile.recipeIndex++; 
	    		}else{
		    		this.tile.recipeIndex = -1; 
				}
				this.tile.activation = false;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 5){
			this.tile.direction = !this.tile.direction;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
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
		return super.mergeItemStack(stack, 6, endIndex, reverseDirection);
    }
}