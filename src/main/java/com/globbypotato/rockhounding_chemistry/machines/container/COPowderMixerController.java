package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PowderMixerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPowderMixerController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPowderMixerController extends ContainerBase<TEPowderMixerController>{
	public COPowderMixerController(IInventory playerInventory, TEPowderMixerController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler output = this.tile.getOutput();
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(output, 0, 149, 84));//output

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 8, 21));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 2, 26, 21));//next

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 1){
			this.tile.dummyRecipe = null;
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
		}else if(slot == 2){
			if(this.tile.isServedClosed(this.tile.getServer())){
				if(this.tile.getRecipeIndex() > -1){
		    		this.tile.recipeIndex--; 
				}else{
					this.tile.recipeIndex = PowderMixerRecipes.powder_mixer_recipes.size() - 1;
				}
				this.tile.activation = false;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 3){
			if(this.tile.isServedClosed(this.tile.getServer())){
	    		if(this.tile.getRecipeIndex() < PowderMixerRecipes.powder_mixer_recipes.size() - 1){
	    			this.tile.recipeIndex++; 
	    		}else{
		    		this.tile.recipeIndex = -1; 
				}
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
		if(super.mergeItemStack(stack, startIndex, 1, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 4, endIndex, reverseDirection);
    }
}