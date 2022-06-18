package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEFluidpedia;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COFluidpedia extends ContainerBase<TEFluidpedia>{
	public COFluidpedia(IInventory playerInventory, TEFluidpedia te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		IItemHandler input = this.tile.getInput();

        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new SlotItemHandler(template, j + i * 9, 8 + j * 18, 23 + i * 18));
            }
        }

        for (int i = 0; i < 13; ++i){
            this.addSlotToContainer(new SlotItemHandler(template, 36+i, 176, 0 + i * 16));
        }
        for (int i = 0; i < 13; ++i){
            this.addSlotToContainer(new SlotItemHandler(template, 36+13+i, 193, 0 + i * 16));
        }

		this.addSlotToContainer(new SlotItemHandler(template, 62,  69, 97));//all
		this.addSlotToContainer(new SlotItemHandler(template, 63,  86, 97));//fluid
		this.addSlotToContainer(new SlotItemHandler(template, 64, 104, 97));//gas

		this.addSlotToContainer(new SlotItemHandler(template, 65, 120, 97));//page -
		this.addSlotToContainer(new SlotItemHandler(template, 66, 152, 97));//page +

		this.addSlotToContainer(new SlotItemHandler(input, 0, 8, 97));//ampoule

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot >= 0 && slot < 36){
			this.tile.writeAmpoule(slot);
    		return ItemStack.EMPTY;
		}else if(slot >= 36 && slot < 62){
			this.tile.charNum = slot - 36;
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
			return ItemStack.EMPTY;
		}else if(slot == 62){
			if(this.tile.getView() == 0){
				this.tile.viewNum = -1;
			}else{
				this.tile.viewNum = 0;
			}
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 63){
			this.tile.viewNum = 1;
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 64){
			this.tile.viewNum = 2;
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 65){
			this.tile.pageNum--;
			if(this.tile.getPage() < 1){
				this.tile.pageNum = 99;
			}
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 66){
			this.tile.pageNum++;
			if(this.tile.getPage() > 99){
				this.tile.pageNum = 1;
			}
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 68, endIndex, reverseDirection);
    }

}