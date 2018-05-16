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
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 55, 34));//input solute
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 82));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 128, 15));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 3, 94, 77));//output
		this.addSlotToContainer(new SlotItemHandler(input, 4, 29, 82));//input redstone
		this.addSlotToContainer(new SlotItemHandler(input, 5, 150, 15));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 6, 8, 8));//upgrade
		this.addSlotToContainer(new SlotItemHandler(output, 0, 55, 82));//recycle

		this.addSlotToContainer(new SlotItemHandler(template, 0, 137,  121));//prev
		this.addSlotToContainer(new SlotItemHandler(template, 1, 153,  121));//next
		this.addSlotToContainer(new SlotItemHandler(template, 2, 7,  121));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 3, 128,  76));//void solvent
		this.addSlotToContainer(new SlotItemHandler(template, 4, 150,  76));//void reagent
		this.addSlotToContainer(new SlotItemHandler(template, 5, 94,  16));//void output
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 8){
			if(this.tile.isServedClosed(this.tile.hasServer(this.tile.getWorld(), this.tile.getPos()), this.tile.getServer(this.tile.getWorld(), this.tile.getPos()))){
				if(this.tile.recipeIndex >= 0){
		    		this.tile.recipeIndex--; 
	    			this.tile.activation = false;
				}else{
					this.tile.recipeIndex = MachineRecipes.labOvenRecipes.size() - 1;
	    			this.tile.activation = false;
				}
				doClickSound(player, tile.getWorld(), tile.getPos());
			}
    		return null;
    	}else if(slot == 9){
			if(this.tile.isServedClosed(this.tile.hasServer(this.tile.getWorld(), this.tile.getPos()), this.tile.getServer(this.tile.getWorld(), this.tile.getPos()))){
	    		if(this.tile.recipeIndex < MachineRecipes.labOvenRecipes.size() - 1){
	    			this.tile.recipeIndex++; 
	    			this.tile.activation = false;
	    		}else{
		    		this.tile.recipeIndex = -1; 
	    			this.tile.activation = false;
	    		}
				doClickSound(player, tile.getWorld(), tile.getPos());
			}
    		return null;
    	}else if(slot == 10){
   			this.tile.activation = !this.tile.activation; 
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 11){
    		this.tile.solventTank.setFluid(null);
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 12){
    		this.tile.reagentTank.setFluid(null);
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 13){
    		this.tile.outputTank.setFluid(null);
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 8, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 11, endIndex, reverseDirection);
		}
    }
}