package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerBase<T extends TileEntityMachineInv> extends Container {

	public T tile;

	public ContainerBase(IInventory playerInv, T te) {
		this.tile = te;
		addOwnSlots();
		addPlayerSlots(playerInv);
	}

	abstract void addOwnSlots();
	
	protected void addPlayerSlots(IInventory playerInventory) {
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, (tile.getGUIHeight()-82) + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, (tile.getGUIHeight() - 24)));
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
	
	protected void doClickSound(EntityPlayer player, World world, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.AMBIENT, 0.3F, 1.0F);
	}

}