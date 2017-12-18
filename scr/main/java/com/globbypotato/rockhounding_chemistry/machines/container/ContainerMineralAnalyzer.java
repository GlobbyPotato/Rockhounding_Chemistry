package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralAnalyzer extends ContainerBase<TileEntityMineralAnalyzer>{

	public ContainerMineralAnalyzer(IInventory playerInventory, TileEntityMineralAnalyzer tile){
		super(playerInventory, tile);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 35, 30));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 8));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 35, 60));//consumable
		this.addSlotToContainer(new SlotItemHandler(input, 3, 114, 88));//sulf
		this.addSlotToContainer(new SlotItemHandler(input, 4, 133, 88));//chlo
		this.addSlotToContainer(new SlotItemHandler(input, 5, 152, 88));//fluo
		this.addSlotToContainer(new SlotItemHandler(input, 6, 27, 8));//upgrade

		this.addSlotToContainer(new SlotItemHandler(output, 0, 67, 60));//output
		
		this.addSlotToContainer(new SlotItemHandler(template, 0, 96,  25));//drain

		this.addSlotToContainer(new SlotItemHandler(template, 1, 25,  88));//lo
		this.addSlotToContainer(new SlotItemHandler(template, 2, 63,  88));//hi
		this.addSlotToContainer(new SlotItemHandler(template, 3, 7,  88));//activation

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 8){
			this.tile.drainValve = !this.tile.drainValve;
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 9){
			if(this.tile.gravity >= 4.00F){
				this.tile.gravity -= 2.00F;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 10){
			if(this.tile.gravity <= 30.00F){
				this.tile.gravity += 2.00F;
			}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
		}else if(slot == 11){
			this.tile.activation = !this.tile.activation;
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
			return super.mergeItemStack(stack, 12, endIndex, reverseDirection);
		}
    }

}