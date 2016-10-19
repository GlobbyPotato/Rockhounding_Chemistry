package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityInv;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase<T extends TileEntityInv> extends Container {

	public T tile;
	//private static int rhOffset = 11;

	public ContainerBase(IInventory playerInv, T te) {
		this.tile = te;
		addOwnSlots();
		addPlayerSlots(playerInv);
	}

	protected void addOwnSlots() {
	}
	
	protected void addPlayerSlots(IInventory playerInventory) {
	
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = 8 + col * 18;
				//int y = row * 18 + 70;
				int y = row * 18 + tile.getGUIHeight()-82;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
			}
		}

		for (int row = 0; row < 9; ++row) {
			int x = 8 + row * 18;
			int y = tile.getGUIHeight() - 24;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < tile.SIZE) {
				if (!this.mergeItemStack(itemstack1, tile.SIZE, this.inventorySlots.size(), true)) {
					return null;

				}
			} else if (!this.mergeItemStack(itemstack1, 0, tile.SIZE, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.canInteractWith(playerIn);
	}

}