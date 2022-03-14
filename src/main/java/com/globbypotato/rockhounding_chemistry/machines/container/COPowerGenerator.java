package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.TEPowerGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COPowerGenerator extends ContainerBase<TEPowerGenerator>{
	public COPowerGenerator(IInventory playerInventory, TEPowerGenerator te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler input = this.tile.getInput();
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 26,  24));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 1, 124, 24));//redstone

		this.addSlotToContainer(new SlotItemHandler(template, 0, 152, 97));//activation
		
		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 66, 86));//inductor
		this.addSlotToContainer(new SlotItemHandler(upgrade, 1, 97, 42));//turbine

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 2){ 
			this.tile.activation = !this.tile.activation;
			this.tile.enableRedstone = !this.tile.enableRedstone;
			this.tile.enablePower = false;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
	    	return ItemStack.EMPTY;
		}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 2, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 3, endIndex, reverseDirection);
    }

}