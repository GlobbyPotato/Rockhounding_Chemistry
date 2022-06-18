package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumAirGases;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GanPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEMultivessel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COMultivessel extends ContainerBase<TEMultivessel>{
	public COMultivessel(IInventory playerInventory, TEMultivessel te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		this.addSlotToContainer(new SlotItemHandler(template, 0, 26,  96));//drain
		this.addSlotToContainer(new SlotItemHandler(template, 1, 48,  96));//drain
		this.addSlotToContainer(new SlotItemHandler(template, 2, 70,  96));//drain
		this.addSlotToContainer(new SlotItemHandler(template, 3, 92,  96));//drain
		this.addSlotToContainer(new SlotItemHandler(template, 4, 114, 96));//drain
		this.addSlotToContainer(new SlotItemHandler(template, 5, 136, 96));//drain

		this.addSlotToContainer(new SlotItemHandler(template, 6,  25,  22));//enabler
		this.addSlotToContainer(new SlotItemHandler(template, 7,  47,  22));//enabler
		this.addSlotToContainer(new SlotItemHandler(template, 8,  69,  22));//enabler
		this.addSlotToContainer(new SlotItemHandler(template, 9,  91,  22));//enabler
		this.addSlotToContainer(new SlotItemHandler(template, 10, 113, 22));//enabler
		this.addSlotToContainer(new SlotItemHandler(template, 11, 135, 22));//enabler

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot >= 0 && slot < 6){
			this.tile.drainValve[slot] = !this.tile.drainValve[slot]; 
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else if(slot >= 6 && slot < 12){
			if(!GanPlantRecipes.inhibited_gases.contains(EnumAirGases.name(slot-6))){
				this.tile.rareEnabler[slot-6] = !this.tile.rareEnabler[slot-6];
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 12, endIndex, reverseDirection);
    }

}