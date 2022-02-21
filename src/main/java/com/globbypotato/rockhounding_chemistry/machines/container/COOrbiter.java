package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.OrbiterRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEOrbiter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COOrbiter extends ContainerBase<TEOrbiter>{
	public COOrbiter(IInventory playerInventory, TEOrbiter te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 23, 43));//probe
		this.addSlotToContainer(new SlotItemHandler(input, 1, 134, 117));//output

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 116));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 60, 59));//-lev
		this.addSlotToContainer(new SlotItemHandler(template, 2, 100, 59));//+lev
		this.addSlotToContainer(new SlotItemHandler(template, 3, 80, 39));//retrieve
		
		this.addSlotToContainer(new SlotItemHandler(template, 4, 9,  110));//-mod
		this.addSlotToContainer(new SlotItemHandler(template, 5, 26, 110));//+mod

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 2){ 
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 3){
			if(this.tile.getLevels() > 0){
				this.tile.numLevel--;
				this.tile.offScale = false;
				if(this.tile.getstoredXP() < this.tile.calculateRetrievedXP(player, 0)){
					this.tile.offScale = true;
				}
			}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 4){
			this.tile.offScale = false;
			if(this.tile.getstoredXP() >= this.tile.calculateRetrievedXP(player, 1)){
				this.tile.numLevel++;
			}
			if(this.tile.getstoredXP() < this.tile.calculateRetrievedXP(player, 0)){
				this.tile.offScale = true;
			}
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 5){
			this.tile.retrieveXP(player);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 6){
			if(this.tile.getRecipeIndex() > -1){
	    		this.tile.recipeIndex--; 
			}else{
				this.tile.recipeIndex = OrbiterRecipes.exp_recipes.size() - 1;
			}
			this.tile.activation = false;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 7){
    		if(this.tile.getRecipeIndex() < OrbiterRecipes.exp_recipes.size() - 1){
    			this.tile.recipeIndex++; 
    		}else{
	    		this.tile.recipeIndex = -1; 
			}
			this.tile.activation = false;
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
		return super.mergeItemStack(stack, 8, endIndex, reverseDirection);
    }

}