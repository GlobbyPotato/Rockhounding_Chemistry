package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabBlender;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLabBlender extends ContainerBase<TileEntityLabBlender>{
	public ContainerLabBlender(IInventory playerInventory, TileEntityLabBlender te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                this.addSlotToContainer(new SlotItemHandler(input, j + i * 3, 51 + j * 18, 20 + i * 18));//inputs
            }
        }

		this.addSlotToContainer(new SlotItemHandler(input, 9, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(output, 0, 116, 75));//output
		this.addSlotToContainer(new SlotItemHandler(template, 0, 115,  19));//activation
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 11){
    		this.tile.activation = !this.tile.activation;
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 11, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 12, endIndex, reverseDirection);
		}
    }

}