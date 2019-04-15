package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidInputTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COFluidInputTank extends ContainerBase<TEFluidInputTank>{
	public COFluidInputTank(IInventory playerInventory, TEFluidInputTank te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 24, 34));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 1, 136, 34));//input reagent

		this.addSlotToContainer(new SlotItemHandler(template, 0, 27, 82));//void solvent
		this.addSlotToContainer(new SlotItemHandler(template, 1, 133, 82));//void reagent
		
		this.addSlotToContainer(new SlotItemHandler(template, 2, 27, 58));//filter solvent
		this.addSlotToContainer(new SlotItemHandler(template, 3, 133, 58));//filter reagent

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot == 2){
			this.tile.solventTank.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 3){
			this.tile.reagentTank.setFluid(null);
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
		}else if(slot == 4){
			if(!this.tile.isFiltered()){
				ItemStack heldItem = inventoryplayer.getItemStack();
				this.tile.filterManualSolvent = ModUtils.handleAmpoule(heldItem, true, false);
			}
    		return ItemStack.EMPTY;
		}else if(slot == 5){
			if(!this.tile.isFiltered()){
				ItemStack heldItem = inventoryplayer.getItemStack();
				this.tile.filterManualReagent = ModUtils.handleAmpoule(heldItem, true, false);
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
		return super.mergeItemStack(stack, 6, endIndex, reverseDirection);
    }

}