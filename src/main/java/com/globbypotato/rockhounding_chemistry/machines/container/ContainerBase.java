package com.globbypotato.rockhounding_chemistry.machines.container;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ContainerBase<T extends TileEntityInv> extends Container {

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
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, (this.tile.getGUIHeight()-82) + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k){
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, (this.tile.getGUIHeight() - 24)));
        }
	}
	
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.tile.INVENTORYSIZE){
                if (!this.mergeItemStack(itemstack1, this.tile.INVENTORYSIZE, this.inventorySlots.size(), false)){
                    return ItemStack.EMPTY;
                }
            }else if (!this.mergeItemStack(itemstack1, 0, this.tile.INVENTORYSIZE, false)){
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()){
                slot.putStack(ItemStack.EMPTY);
            }else{
                slot.onSlotChanged();
            }
            
            slot.onTake(playerIn, itemstack1);

        }
        return itemstack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tile.canInteractWith(playerIn);
	}

	protected void doClickSound(EntityPlayer player, World world, BlockPos pos) {
		world.playSound(player, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.AMBIENT, 0.3F, 1.0F);
	}

}