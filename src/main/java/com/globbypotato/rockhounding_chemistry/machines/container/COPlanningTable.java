package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PlanningTableRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPlanningTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPlanningTable extends ContainerBase<TEPlanningTable>{
	public COPlanningTable(IInventory playerInventory, TEPlanningTable te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

		//input
        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                this.addSlotToContainer(new SlotItemHandler(input, j + i * 4, 31 + j * 38, 41 + i * 19));
            }
        }

        //ghost
        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 4; ++j){
                this.addSlotToContainer(new SlotItemHandler(template, j + i * 4, 14 + j * 38, 41 + i * 19));
            }
        }

		this.addSlotToContainer(new SlotItemHandler(template, 16, 179, 98));//build
		this.addSlotToContainer(new SlotItemHandler(template, 17, 8, 21));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 18, 26, 21));//next

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 32){
			//assembly
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			this.tile.assemblyStructure();
	    	return ItemStack.EMPTY;
		}else if(slot == 33){
			if(this.tile.getRecipeIndex() > -1){
	    		this.tile.recipeIndex--; 
			}else{
				this.tile.recipeIndex = PlanningTableRecipes.planning_table_recipes.size() - 1;
			}
			this.tile.showPreview();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 34){
    		if(this.tile.getRecipeIndex() < PlanningTableRecipes.planning_table_recipes.size() - 1){
    			this.tile.recipeIndex++; 
    		}else{
	    		this.tile.recipeIndex = -1; 
			}
			this.tile.showPreview();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot >= 16 && slot <= 31){
			return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 16, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 35, endIndex, reverseDirection);
    }
}