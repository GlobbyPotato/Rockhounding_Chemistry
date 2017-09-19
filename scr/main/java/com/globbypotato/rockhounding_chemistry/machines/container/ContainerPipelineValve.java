package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelineValve;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPipelineValve extends ContainerBase<TileEntityPipelineValve> {

	public ContainerPipelineValve(IInventory playerInventory, TileEntityPipelineValve tile){
		super(playerInventory,tile);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler template = tile.getTemplate();
		for(int x = 0; x < 6; x++){
			int offset = x * 18;
			this.addSlotToContainer(new SlotItemHandler(template, x,  34 + offset, 35));//locks
		}
		for(int x = 6; x < 12; x++){
			int offset = (x-6) * 18;
			this.addSlotToContainer(new SlotItemHandler(template, x,  34 + offset, 54));//filters
		}
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
        InventoryPlayer inventoryplayer = player.inventory;
		if(slot >= 0 && slot <= 5){
			if(!this.tile.tiltStatus[slot]){
				this.tile.sideStatus[slot] = !this.tile.sideStatus[slot];
				this.tile.markDirtyClient();
				doClickSound(player, tile.getWorld(), tile.getPos());
			}
    		return null;
		}else if(slot >= 6 && slot <= 11){
			ItemStack heldItem = inventoryplayer.getItemStack();
			if(heldItem != null){
				if(heldItem.getItem() instanceof UniversalBucket){
					if(FluidUtil.getFluidContained(heldItem) != null){
						FluidStack filterfluid = FluidUtil.getFluidContained(heldItem);
						this.tile.sideFilter[slot - 6] = filterfluid;
					}
				}else if(heldItem.getItem() == Items.WATER_BUCKET){
					FluidStack filterfluid = new FluidStack(FluidRegistry.WATER, 1000);
					this.tile.sideFilter[slot - 6] = filterfluid;
				}else if(heldItem.getItem() == Items.LAVA_BUCKET){
					FluidStack filterfluid = new FluidStack(FluidRegistry.LAVA, 1000);
					this.tile.sideFilter[slot - 6] = filterfluid;
				}else{
					this.tile.sideFilter[slot - 6] = null;
				}
			}else{
				this.tile.sideFilter[slot - 6] = null;
			}
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		return super.mergeItemStack(stack, 12, endIndex, reverseDirection);
    }

}