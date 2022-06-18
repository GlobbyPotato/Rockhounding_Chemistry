package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COElementsCabinetBase extends ContainerBase<TEElementsCabinetBase>{
	public COElementsCabinetBase(IInventory playerInventory, TEElementsCabinetBase te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 7; ++j){
                this.addSlotToContainer(new SlotItemHandler(template, j + i * 7, 11 + j * 23, 22 + i * 19));
            }
        }

        for (int i = 0; i < 13; ++i){
            this.addSlotToContainer(new SlotItemHandler(template, 28+i, 176, 0 + i * 16));
        }
        for (int i = 0; i < 13; ++i){
            this.addSlotToContainer(new SlotItemHandler(template, 28+13+i, 193, 0 + i * 16));
        }

		this.addSlotToContainer(new SlotItemHandler(template, 54, 123, 99));//page -
		this.addSlotToContainer(new SlotItemHandler(template, 55, 152, 99));//page +
		this.addSlotToContainer(new SlotItemHandler(template, 56,  89, 99));//release
		this.addSlotToContainer(new SlotItemHandler(template, 57, 106, 99));//sort symbol
		this.addSlotToContainer(new SlotItemHandler(template, 58,   8, 99));//drain

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot >= 0 && slot <28) {
			this.tile.enableBalance(slot);
			this.tile.collectMaterial(this.tile.getAlphabet());
			return ItemStack.EMPTY;
		}else if(slot >= 28 && slot < 54){
			this.tile.charNum = slot - 28;
			this.tile.showAll = true;
			this.tile.collectMaterial(this.tile.getAlphabet());
			return ItemStack.EMPTY;
		}else if(slot == 54){
			this.tile.pageNum--;
			if(this.tile.getPage() < 1){
				this.tile.pageNum = 99;
			}
			this.tile.collectMaterial(this.tile.getAlphabet());
    		return ItemStack.EMPTY;
		}else if(slot == 55){
			this.tile.pageNum++;
			if(this.tile.getPage() > 99){
				this.tile.pageNum = 1;
			}
			this.tile.collectMaterial(this.tile.getAlphabet());
    		return ItemStack.EMPTY;
		}else if(slot == 56){
			this.tile.showAll = false;
			this.tile.collectMaterial(this.tile.getAlphabet());
    		return ItemStack.EMPTY;
		}else if(slot == 57){
			this.tile.sorting = !this.tile.sorting;
			this.tile.collectMaterial(this.tile.getAlphabet());
    		return ItemStack.EMPTY;
		}else if(slot == 58){
			this.tile.drain = !this.tile.drain;
			this.tile.switchDrain();
			this.tile.collectMaterial(this.tile.getAlphabet());
    		return ItemStack.EMPTY;
		}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 59, endIndex, reverseDirection);
    }

}