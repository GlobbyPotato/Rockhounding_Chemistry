package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChemicalExtractor extends ContainerBase<TileEntityChemicalExtractor>{
	Slot drain;

    public ContainerChemicalExtractor(IInventory playerInventory, TileEntityChemicalExtractor tile){
    	super(playerInventory,tile);
    }
    
	@Override
	protected void addOwnSlots() {

		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

        this.addSlotToContainer(new SlotItemHandler(input, 0, 48, 8));//input
        this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 8));//fuel
        this.addSlotToContainer(new SlotItemHandler(input, 2, 48, 28));//consumable
        this.addSlotToContainer(new SlotItemHandler(input, 3, 28, 8));//redstone
        this.addSlotToContainer(new SlotItemHandler(input, 4, 8, 148));//chlor
        this.addSlotToContainer(new SlotItemHandler(input, 5, 28, 148));//fluo
        this.addSlotToContainer(new SlotItemHandler(input, 6, 48, 148));//cyan
        this.addSlotToContainer(new SlotItemHandler(input, 7, 178, 158));//cylinder

        //cabinets
		for(int x=0; x<=6; x++){
			for(int y=0; y<=7; y++){
		        this.addSlotToContainer(new SlotItemHandler(output, (x*8)+y, 68 + (19 * y) , 10 + (21* x)));//output
			}
		}
		
		drain = this.addSlotToContainer(new SlotItemHandler(template, 0, 201,  158));//drain
	}
	
	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 64){
			this.tile.drainValve = !this.tile.drainValve;
    		return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 64, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 65, endIndex, reverseDirection);
		}
    }

}