package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidpedia;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COFluidpedia extends ContainerBase<TEFluidpedia>{
	public COFluidpedia(IInventory playerInventory, TEFluidpedia te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 9; ++j){
                this.addSlotToContainer(new SlotItemHandler(template, j + i * 9, 8 + j * 18, 23 + i * 18));
            }
        }
		this.addSlotToContainer(new SlotItemHandler(template, 36, 7,   96));//char -
		this.addSlotToContainer(new SlotItemHandler(template, 37, 36,  96));//char +

		this.addSlotToContainer(new SlotItemHandler(template, 38, 119, 96));//all
		this.addSlotToContainer(new SlotItemHandler(template, 39, 136, 96));//fluid
		this.addSlotToContainer(new SlotItemHandler(template, 40, 153, 96));//gas
		
		this.addSlotToContainer(new SlotItemHandler(template, 41, 62,  96));//page -
		this.addSlotToContainer(new SlotItemHandler(template, 42, 93,  96));//page +

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot >= 0 && slot < 36){
			ItemStack heldItem = inventoryplayer.getItemStack();
			if(!heldItem.isEmpty()){
				if(heldItem.isItemEqual(BaseRecipes.sampling_ampoule)){
					if(!heldItem.hasTagCompound()){heldItem.setTagCompound(new NBTTagCompound());}
					if(heldItem.hasTagCompound()){
				  		if(!this.tile.PAGED_FLUID_LIST.isEmpty() && this.tile.PAGED_FLUID_LIST.size() > 0){
							if(slot < this.tile.PAGED_FLUID_LIST.size()){
								NBTTagCompound gas = new NBTTagCompound();
								FluidStack sample = new FluidStack(this.tile.PAGED_FLUID_LIST.get(slot), 1000);
								if(sample != null && sample.getFluid() != null){
									sample.writeToNBT(gas);
									if(sample.getFluid().isGaseous()){
										ModUtils.setGasFilter(heldItem, gas);
									}else{
										ModUtils.setFluidFilter(heldItem, gas);
									}
								}
							}
				  		}
					}
				}
			}
    		return ItemStack.EMPTY;
		}else if(slot == 36){
			this.tile.charNum--;
			if(this.tile.getChar() < 0){
				this.tile.charNum = 25;
			}
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 37){
			this.tile.charNum++;
			if(this.tile.getChar() > 25){
				this.tile.charNum = 0;
			}
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 38){
			if(this.tile.getView() == 0){
				this.tile.viewNum = -1;
				this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
			}else{
				this.tile.viewNum = 0;
				this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
			}
    		return ItemStack.EMPTY;
		}else if(slot == 39){
			this.tile.viewNum = 1;
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 40){
			this.tile.viewNum = 2;
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 41){
			this.tile.pageNum--;
			if(this.tile.getPage() < 1){
				this.tile.pageNum = 99;
			}
			this.tile.collectFluids(this.tile.getAlphabet(), this.tile.getView());
    		return ItemStack.EMPTY;
		}else if(slot == 42){
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
		return super.mergeItemStack(stack, 41, endIndex, reverseDirection);
    }

}