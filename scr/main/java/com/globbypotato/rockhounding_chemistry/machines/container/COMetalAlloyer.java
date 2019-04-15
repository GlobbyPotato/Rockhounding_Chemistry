package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMetalAlloyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COMetalAlloyer extends ContainerBase<TEMetalAlloyer>{
	public COMetalAlloyer(IInventory playerInventory, TEMetalAlloyer te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler output = this.tile.getOutput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 61));//input

		this.addSlotToContainer(new SlotItemHandler(output, 0, 80, 61));//output

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 26, 21));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 2, 44, 21));//next
		this.addSlotToContainer(new SlotItemHandler(template, 3, 15, 61));//preview

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 145, 61));//speed

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 2){
			this.tile.dummyRecipe = null;
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
		}else if(slot == 3){
			if(this.tile.isServedClosed(this.tile.hasServer(), this.tile.getServer())){
				if(this.tile.getRecipeIndex() > -1){
		    		this.tile.recipeIndex--; 
				}else{
					this.tile.recipeIndex = MetalAlloyerRecipes.metal_alloyer_recipes.size() - 1;
				}
				this.tile.dummyRecipe = null;
				this.tile.activation = false;
				this.tile.showPreview();
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 4){
			if(this.tile.isServedClosed(this.tile.hasServer(), this.tile.getServer())){
	    		if(this.tile.getRecipeIndex() < MetalAlloyerRecipes.metal_alloyer_recipes.size() - 1){
	    			this.tile.recipeIndex++; 
	    		}else{
		    		this.tile.recipeIndex = -1; 
				}
				this.tile.dummyRecipe = null;
				this.tile.activation = false;
				this.tile.showPreview();
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 5){
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