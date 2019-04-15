package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEServer;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COServer extends ContainerBase<TEServer>{
	public COServer(IInventory playerInventory, TEServer te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

        for (int k = 0; k < 9; k++){
        	int offsetX = (18 * k);
        	this.addSlotToContainer(new SlotItemHandler(input, k, 8 + offsetX, 29));//inputs
        }

		this.addSlotToContainer(new SlotItemHandler(input, 9, 100, 67));//inscriber

		this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1, 60, 67));//cycle
		this.addSlotToContainer(new SlotItemHandler(template, 2, 8, 65));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 3, 26, 65));//next
		this.addSlotToContainer(new SlotItemHandler(template, 4, 134, 65));//-
		this.addSlotToContainer(new SlotItemHandler(template, 5, 152, 65));//+
		this.addSlotToContainer(new SlotItemHandler(template, 6, 134, 83));//--
		this.addSlotToContainer(new SlotItemHandler(template, 7, 152, 83));//++
		this.addSlotToContainer(new SlotItemHandler(template, 8, 80, 67));//regenerate
		this.addSlotToContainer(new SlotItemHandler(template, 9, 17, 84));//item

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 10){
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
    	}else if(slot == 11){ 
			this.tile.cycle = !this.tile.cycle;
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
        	return ItemStack.EMPTY;
		}else if(slot == 12){
    		if(this.tile.getRecipeIndex() > 0){
    			this.tile.recipeIndex--;
    		}else{
    			this.tile.recipeIndex = this.tile.servedMaxRecipes() - 1;
    		}
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 13){
    		if(this.tile.getRecipeIndex() < this.tile.servedMaxRecipes() - 1){
    			this.tile.recipeIndex++;
    		}else{
    			this.tile.recipeIndex = 0;
    		}
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 14){
    		if(this.tile.getRecipeAmount() > 0){
    			this.tile.recipeAmount--;
    		}
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 15){
    		if(this.tile.getRecipeAmount() < 1000){
    			this.tile.recipeAmount++;
    		}
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 16){
    		if(this.tile.getRecipeAmount() >= 10){
    			this.tile.recipeAmount -= 10;
    		}
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 17){
    		if(this.tile.getRecipeAmount() <= 990){
    			this.tile.recipeAmount += 10;
    		}
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 18){
			this.tile.writeFile();
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 19){
			ItemStack heldItem = inventoryplayer.getItemStack();
			if(!heldItem.isEmpty()){
				if(this.tile.hasStackFilter()){
					if(heldItem.isItemEqual(BaseRecipes.sampling_ampoule)){
						this.tile.filterFluid = ModUtils.handleAmpoule(heldItem, true, true);
						this.tile.filterStack = ItemStack.EMPTY;
					}else{
						this.tile.filterStack = ModUtils.handleFilterStack(heldItem);
						this.tile.filterFluid = null;
					}
				}
			}else{
				this.tile.filterFluid = null;
				this.tile.filterStack = ItemStack.EMPTY;
			}
			this.tile.writeFile();
    		return ItemStack.EMPTY;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 10, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 20, endIndex, reverseDirection);
    }
}